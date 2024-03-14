package kr.co.ch09.service;

import kr.co.ch09.dto.User3DTO;
import kr.co.ch09.entity.User3;
import kr.co.ch09.repository.User3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class User3Service {
    private final User3Repository user3Repository;

    public ResponseEntity<?> insertUser3(User3DTO user3DTO){
        if(user3Repository.existsById(user3DTO.getUid())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user3DTO.getUid() + " already exist");
        }else {
            user3Repository.save(user3DTO.toEntity());
            return ResponseEntity.ok().body(user3DTO);
        }
    }
    public ResponseEntity<?> selectUser3(String uid){
        try {
            User3 user3 = user3Repository.findById(uid).orElseThrow();
            return ResponseEntity.ok().body(user3);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<List<User3DTO>> selectUser3s(){
        List<User3DTO> user3DTOs = user3Repository.findAll().stream()
                .map(entity -> User3DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .birth(entity.getBirth())
                        .hp(entity.getHp())
                        .addr(entity.getAddr())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(user3DTOs);
    }
    public ResponseEntity<?> updateUser3(User3DTO user3DTO){
        if(user3Repository.existsById(user3DTO.getUid())){
            user3Repository.save(user3DTO.toEntity());
            return ResponseEntity.ok().body(user3DTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }
    public ResponseEntity<?> deleteUser3(String uid){
        Optional<User3> delUser = user3Repository.findById(uid);

        if(delUser.isPresent()){
            user3Repository.deleteById(uid);
            return ResponseEntity.ok().body(delUser.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }

    }
}
