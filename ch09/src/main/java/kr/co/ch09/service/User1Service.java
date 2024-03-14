package kr.co.ch09.service;

import kr.co.ch09.dto.User1DTO;
import kr.co.ch09.entity.User1;
import kr.co.ch09.repository.User1Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class User1Service {

    private final User1Repository repository;

    public void insertUser1(User1DTO user1DTO){
        User1 user1 = user1DTO.toEntity();
        repository.save(user1);
    }
    public User1DTO selectUser1(String uid){
        User1 user1 = repository.findById(uid).get();
        return  user1.toDTO();
    }
    public List<User1DTO> selectUser1s(){
        return repository.findAll().stream()
                .map(entity -> User1DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .birth(entity.getBirth())
                        .hp(entity.getHp())
                        .age(entity.getAge())
                        .build())
                .collect(Collectors.toList());
    }
    public User1DTO updateUser1(User1DTO user1DTO){

        // 수정
        repository.save(user1DTO.toEntity());

        // 수정한 사용자 조회/ 반환
        Optional<User1> result = repository.findById(user1DTO.getUid());
        return result.get().toDTO();
    }
    public void deleteUser1(String uid){
        repository.deleteById(uid);

        /*
        Optional<User1> result = repository.findById(uid);
        if(result.isPresent()){
        }
        */
    }
}
