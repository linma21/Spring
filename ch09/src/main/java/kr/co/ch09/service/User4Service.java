package kr.co.ch09.service;

import kr.co.ch09.dto.User4DTO;
import kr.co.ch09.entity.User4;
import kr.co.ch09.repository.User4Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class User4Service {
    private final User4Repository user4Repository;

    public ResponseEntity<?> insertUser4(User4DTO user4DTO){
        if(user4Repository.existsById(user4DTO.getUid())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user4DTO.getUid() +" already exist");
        }else {
            user4Repository.save(user4DTO.toEntity());
            return ResponseEntity.ok().body(user4DTO);
        }
    }
    public ResponseEntity<?> selectUser4(String uid){
        try {
            User4 user4 = user4Repository.findById(uid).orElseThrow();
            return ResponseEntity.ok().body(user4);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<List<User4DTO>> selectUser4s(){
        List<User4DTO> user4DTOS = user4Repository.findAll().stream()
                .map(entity -> User4DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .gender(entity.getGender())
                        .age(entity.getAge())
                        .hp(entity.getHp())
                        .addr(entity.getAddr())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(user4DTOS);
    }
    public ResponseEntity<?> updateUser4(User4DTO user4DTO){
        if(user4Repository.existsById(user4DTO.getUid())){
            user4Repository.save(user4DTO.toEntity());
            return ResponseEntity.ok().body(user4DTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<?> deleteUser4(String uid){
        Optional<User4> delUser = user4Repository.findById(uid);

        if(delUser.isPresent()){
            user4Repository.deleteById(uid);
            return ResponseEntity.ok().body(delUser.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
}
