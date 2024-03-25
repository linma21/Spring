package kr.co.sboard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    @GetMapping("/my/setting")
    public String setting(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        User user = myUserDetails.getUser();
        model.addAttribute("user", user);
        return "/my/setting";
    }
}
