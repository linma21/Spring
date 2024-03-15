package kr.co.ch09.controller;

import kr.co.ch09.dto.User5DTO;
import kr.co.ch09.service.User5Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
public class User5Controller {

    private final User5Service user5Service;

    @GetMapping("/user5")
    public ResponseEntity<?> list(){

        return user5Service.selectUser5s();
    }
    @GetMapping("/user5/{seq}")
    public ResponseEntity<?> user5(@PathVariable("seq") int seq){
        log.info("Seq" + seq);
        return user5Service.selectUser5(seq);
    }
    @PostMapping("/user5")
    public ResponseEntity<?> register(@RequestBody User5DTO user5DTO){

        return user5Service.insertUser5(user5DTO);
    }
    @PutMapping("/user5")
    public ResponseEntity<?> modify(@RequestBody User5DTO user5DTO){

        return user5Service.updateUser5(user5DTO);
    }
    @DeleteMapping("/user5/{seq}")
    public ResponseEntity<?> delete(@PathVariable("seq") int seq){

        return user5Service.deleteUser5(seq);
    }
}
