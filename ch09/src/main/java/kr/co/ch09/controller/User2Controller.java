package kr.co.ch09.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController // return되는 값을 다로 응답 데이터로 보냄
public class User2Controller {

    @GetMapping("/user2")
    public void list(){}

    @GetMapping("/user2/{uid}")
    public void user2(){}

    @PostMapping("/user2")
    public void register(){}

    @PutMapping("/user2")
    public void modify(){}

    @DeleteMapping("/user2/{uid}")
    public void delete(){}
}
