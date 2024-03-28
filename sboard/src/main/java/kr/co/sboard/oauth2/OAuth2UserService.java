package kr.co.sboard.oauth2;

import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import kr.co.sboard.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("loadUser...1" + accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("loadUser...2" + provider);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("loadUser...3" + oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("loadUser...4 " + attributes);

        User user = null;

        if(provider.equals("kakao")){
            Map<String, Object> kakaoAc = (Map<String, Object>) attributes.get("kakao_account");
            log.info("loadUser...5 " + kakaoAc);
            Map<String, Object> profile = (Map<String, Object>) kakaoAc.get("profile");
            log.info("loadUser...6 " + profile);

            // 회원가입 처리
            String nick = (String) profile.get("nickname");
            String email = (String) kakaoAc.get("email");
            log.info("loadUser...7" + email);

            String uid = email.substring(0, email.lastIndexOf('@'));
            String role = "USER";

            // 사용자 확인
            user = userRepository.findById(uid)
                    .orElse(User.builder()
                            .uid(uid)
                            .email(email)
                            .name(nick)
                            .nick(nick)
                            .role(role)
                            .provider(provider)
                            .build());

            userRepository.save(user);

        }else if(provider.equals("google")){

            // 사용자 확인 및 회원가입 처리
            String email = (String) attributes.get("email");
            String uid = email.substring(0, email.lastIndexOf('@'));
            String name = (String) attributes.get("name");
            String role = "USER";

            // 사용자 확인
            user = userRepository.findById(uid)
                    .orElse(User.builder()
                            .uid(uid)
                            .email(email)
                            .name(name)
                            .nick(name)
                            .role(role)
                            .provider(provider)
                            .build());
        }



        // 저장 or 수정
        userRepository.save(user);
        return MyUserDetails.builder()
                .user(user)
                .build();
    }
}
