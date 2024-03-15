package kr.co.ch10.controller;

import kr.co.ch10.dto.UserDTO;
import kr.co.ch10.entity.User;
import kr.co.ch10.jwt.JwtProvider;
import kr.co.ch10.security.MyUserDetails;
import kr.co.ch10.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){

        try {
            // Security 인증 처리
            UsernamePasswordAuthenticationToken authToken // 아이디와 패스워드를 갖는 객체
                    = new UsernamePasswordAuthenticationToken(userDTO.getUid(), userDTO.getPass());

            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);
            log.info("login...try1");
            // 인증된 사용자 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            log.info("login...try2");
            // 토큰 발급(액세스, 리프레시)
            String access = jwtProvider.createToken(user, 1); // 1일
            String refresh = jwtProvider.createToken(user, 7); // 7일

            // 리프레시 토큰 DB 저장
            log.info("login...try3 create Token finish...");
            // 엑세스 토큰 클라이언트 전송
            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("access", access);
            log.info("login...try4 sendToken to client");
            return ResponseEntity.ok().body(map);
        }catch (Exception e){
            log.info("login... catch"+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> list(){
        return service.selectUsers();
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<?> user(@PathVariable("uid") String uid){
        return service.selectUser(uid);
    }
    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        return service.insertUser(userDTO);
    }
    @PutMapping("/user")
    public ResponseEntity<?> modify(@RequestBody UserDTO userDTO){
        return service.updateUser(userDTO);
    }
    @PutMapping("/user/{uid}")
    public ResponseEntity<?> delete(@PathVariable("uid") String uid){
        return service.deleteUser(uid);
    }

}
