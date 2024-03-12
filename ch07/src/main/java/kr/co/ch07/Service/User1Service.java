package kr.co.ch07.Service;

import kr.co.ch07.dto.User1DTO;
import kr.co.ch07.entity.User1;
import kr.co.ch07.repository.User1Repository;
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
        // DTO를 Entity로 변환
        User1 user1 = user1DTO.toEntity();

        // Entity 저장(데이터베이스 Insert)
        repository.save(user1);
    }
    public User1DTO selectUser1(String uid){

        /* Optional<엔티티타입>
            - null 값 검증처리를 간편하게 활용하는 구조체
            - Spring JPA 조회 결과 타입
         */
        Optional<User1> result = repository.findById(uid);

        // result.get() = Entity
        // result.get().toDTO() = DTO
        return result.get().toDTO();
    }
    public List<User1DTO> selectUser1s(){
        // 전체 조회
        List<User1> user1s = repository.findAll();

        // Entity List를 DTO List로 변환 - 일반적인 방식
        /*
        List<User1DTO> users = new ArrayList<>();
        for(User1 user1 : user1s){
            users.add(user1.toDTO());
        }
         */
        List<User1DTO> user1DTOS = user1s.stream()
                .map(entity -> User1DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .birth(entity.getBirth())
                        .hp(entity.getHp())
                        .age(entity.getAge())
                        .build())
                .collect(Collectors.toList());

        return user1DTOS;
    }
    public void updateUser1(User1DTO user1DTO){

        // DTO를 Entity로 변환
        User1 user1 = user1DTO.toEntity();

        // Entity 수정(데이터베이스 Update)
        repository.save(user1);
    }
    public void deleteUser1(String uid){
        // Entity 삭제(데이터베이스 Delete)
        repository.deleteById(uid);
    }
}
