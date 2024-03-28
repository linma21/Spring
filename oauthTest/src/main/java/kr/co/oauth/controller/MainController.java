package kr.co.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        return "/index";
    }
    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }
    @ResponseBody
    @GetMapping("/login/oauth2/code/kakao")
    public String codeKakao(@RequestParam("code") String code){
        log.info("code : "+ code);

        return "code :" +code;
    }
}
