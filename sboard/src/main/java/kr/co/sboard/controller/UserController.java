package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;


    @GetMapping("/user/login")
    public String login(@ModelAttribute("success") String success, Model model){
            // 매개변수 success에 @ModelAttribute 선언으로 View 참조 가능
        return ("/user/login");
    }
    @GetMapping("/user/terms")
    public String terms(Model model){
        TermsDTO termsDTO = userService.selectTerms();
        model.addAttribute(termsDTO);
        return ("/user/terms");
    }
    @GetMapping("/user/register")
    public String register(@RequestParam("sms") String sms, Model model){
        log.info("getSMS : " + sms);
        model.addAttribute("sms", sms);
        return ("/user/register");
    }
    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO){
        log.info("postSMS : " + userDTO.getSms());
        String regIp = req.getRemoteAddr();
        userDTO.setRegIp(regIp);
        log.info(userDTO.toString());
        userService.insertUser(userDTO);
        return ("redirect:/user/login");
    }
    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                        @PathVariable("type") String type,
                                       @PathVariable("value") String value){
        int count = userService.selectCountUser(type, value);
        log.info("checkUser : "+ count);

        if(type.equals("email")){
            userService.sendMailCode(session, value);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }

    @ResponseBody
    @GetMapping("/email/{code}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("code") String code){

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(code)){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }

    }
    @GetMapping("/user/findId")
    public String findId(){
        return "/user/findId";
    }
    @GetMapping("/user/findIdResult")
    public String findIdResult(HttpSession session, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("sessUser");
        model.addAttribute("userDTO", userDTO);
        return "/user/findIdResult";
    }
    @PostMapping("/user/findId")
    public ResponseEntity<?> findId(@RequestBody UserDTO userDTO, HttpSession session){
        String name = userDTO.getName();
        String email = userDTO.getEmail();

        UserDTO sessUser = userService.findIdByEmail(name, email);
        if(sessUser != null) {
            session.setAttribute("sessUser", sessUser);
            return ResponseEntity.ok().body(sessUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("name is wrong");
    }
    @GetMapping("/user/findPassword")
    public String findPassword(){
        return "/user/findPassword";
    }
    @PostMapping("/user/findPassword")
    public ResponseEntity<?> findPassword(@RequestBody UserDTO userDTO, HttpSession session){
        String uid = userDTO.getUid();
        String email = userDTO.getEmail();

        UserDTO sessUser = userService.findPassword(uid, email);
        if(sessUser != null) {
            session.setAttribute("sessUser", sessUser);
            return ResponseEntity.ok().body(sessUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("uid is wrong");
    }

    @GetMapping("/user/findPasswordChange")
    public String findPasswordChange(HttpSession session, Model model){
        model.addAttribute("sessUser", session.getAttribute("sessUser"));
        return "/user/findPasswordChange";
    }
    @PutMapping("/user/findPasswordChange")
    public ResponseEntity<?> findPasswordChange(@RequestBody UserDTO userDTO){
        return userService.updateUserPass(userDTO);
    }
}
