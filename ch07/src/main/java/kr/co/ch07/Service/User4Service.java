package kr.co.ch07.Service;

import kr.co.ch07.dto.User4DTO;
import kr.co.ch07.entity.User4;
import kr.co.ch07.repository.User4Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class User4Service {
    private final User4Repository repository;

    public void insertUser4(User4DTO user4DTO){
        User4 user4 = user4DTO.toEntity();
        repository.save(user4);
    }
    public User4DTO selectUser4(String uid){
        Optional<User4> user4 = repository.findById(uid);

        return user4.get().toEntity();
    }
    public List<User4DTO> selectUser4s(){
        List<User4> user4s = repository.findAll();
        List<User4DTO> users = user4s.stream()
                .map(entity -> User4DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .hp(entity.getHp())
                        .age(entity.getAge())
                        .addr(entity.getAddr())
                        .build())
                .collect(Collectors.toList());
        return users;
    }
    public void updateUser4(User4DTO user4DTO){
        User4 user4 = user4DTO.toEntity();
        repository.save(user4);
    }
    public void deleteUser4(String uid){
        repository.deleteById(uid);
    }
}
