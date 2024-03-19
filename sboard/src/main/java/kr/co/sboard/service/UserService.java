package kr.co.sboard.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public TermsDTO selectTerms() {
        return mapper.selectTerms();
    }
    public void insertUser (UserDTO userDTO){
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        mapper.insertUser(userDTO);
    }
    @Value("${spring.mail.username}")
    private String sender;

    public void sendMailCode(HttpSession session, String receiver){

        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증 코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));

        String title = "sboard 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);
        }catch (Exception e){
            log.error("sendMailCode : " + e.getMessage());
        }

    }

    public UserDTO selectUser (String uid){
        return mapper.selectUser(uid);
    }
    public int selectCountUser (String type, String value){
        return mapper.selectCountUser(type, value);
    }
    public void selectUsers (){}
    public void updateUser (UserDTO userDTO){}
    public void deleteUser (String uid){}
}
