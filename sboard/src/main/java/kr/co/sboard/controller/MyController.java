package kr.co.sboard.controller;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.security.MyUserDetails;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/my/setting")
    public String setting(){
        return "/my/setting";
    }

    @GetMapping("/my/setting/{uid}")
    public ResponseEntity<?> withdrawal(@PathVariable("uid") String uid){
        log.info("withdrawal : "+uid);
        return userService.withdrawal(uid);
    }

    @ResponseBody
    @PostMapping("/my/setting")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        //log.info("updateUserCont : "+userDTO.toString());
        userService.updateUser(userDTO);

        // 세션에 사용자의 변경된 정보 저장
        // 현재 Authentication 정보 호출
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUid(), userDTO.getPass()));
        // 현재 Authentication로 사용자 인증 후 새 Authentication 정보를 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("updateUserCont : "+authentication.getPrincipal().toString());
        return ResponseEntity.ok().body(userDTO);


    }

}
