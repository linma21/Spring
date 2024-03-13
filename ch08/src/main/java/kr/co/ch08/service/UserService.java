package kr.co.ch08.service;

import kr.co.ch08.dto.UserDTO;
import kr.co.ch08.entity.User;
import kr.co.ch08.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private  final UserRepository userRepository;
    // 비밀번호 암호화 설정 주입
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO){

        // 비밀 번호 암호화
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        User user = userDTO.toEntity();
        userRepository.save(user);
    }
    public UserDTO selectUser(UserDTO userDTO){

        Optional<User> result = userRepository.findById(userDTO.getUid());

        if(!result.isEmpty()){
            User user = result.get();

            // 비밀번호 검증
            if(passwordEncoder.matches(userDTO.getPass(), user.getPass())){
                log.info(user.toString());
                return user.toDTO();
            }
        }
            return null;
    }
    public List<UserDTO> selectUsers(){
        return null;
    }
    public void updateUser(UserDTO userDTO){}
    public void deleteUser(String uid){}
}
