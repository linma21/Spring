package kr.co.ch09.service;

import kr.co.ch09.dto.User5DTO;
import kr.co.ch09.entity.User5;
import kr.co.ch09.repository.User5Repository;
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
public class User5Service {
    private final User5Repository user5Repository;

    public ResponseEntity<?> insertUser5(User5DTO user5DTO){
        if(user5Repository.existsById(user5DTO.getSeq())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user5DTO.getSeq() + " already found");
        }else {
            user5Repository.save(user5DTO.toEntity());
            return ResponseEntity.ok().body(user5DTO);
        }
    }
    public ResponseEntity<?> selectUser5(int seq){
        try {
            User5 user5 = user5Repository.findById(seq).orElseThrow();
            return ResponseEntity.ok().body(user5);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<?> selectUser5s(){
        List<User5DTO> user5DTOs = user5Repository.findAll().stream()
                .map(entity -> User5DTO.builder()
                        .seq(entity.getSeq())
                        .name(entity.getName())
                        .gender(entity.getGender())
                        .age(entity.getAge())
                        .addr(entity.getAddr())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(user5DTOs);
    }
    public ResponseEntity<?> updateUser5(User5DTO user5DTO){
        if(user5Repository.existsById(user5DTO.getSeq())){
            user5Repository.save(user5DTO.toEntity());
            return ResponseEntity.ok().body(user5DTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<?> deleteUser5(int seq){
        Optional<User5> delUser = user5Repository.findById(seq);
        if(delUser.isPresent()){
            user5Repository.deleteById(seq);
            return ResponseEntity.ok().body(delUser);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
}
