package kr.co.sboard.security;

import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findById(username); // result에 사용자 객체가 있음


        UserDetails userDetails = null;
        if(result.isPresent()){
            // 해당하는 사용자가 존재하면 인증 객체 생성
            userDetails = MyUserDetails.builder()
                    .user(result.get())
                    .build();
        }
        // Security ContextHolder 저장
        return userDetails;
    }
}
