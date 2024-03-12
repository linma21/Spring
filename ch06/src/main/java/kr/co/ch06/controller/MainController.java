package kr.co.ch06.controller;

import kr.co.ch06.dto.User1DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    //private Logger logger = LoggerFactory.getLogger(MainController.class); --> @Slf4j

    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        log.debug("index()...");

        String str1 = "hello world!";
        String str2 = "hello String Boot!";
        
        // 생성자 초기화
        User1DTO user1 = new User1DTO("A101", "김유신","1996-11-21","010-1234-1234", 23);
        log.debug(user1.toString());

        // 세터 초기화
        User1DTO user2 = new User1DTO();
        user2.setUid("A102");
        user2.setName("김춘추");
        user2.setAge(32);
        user2.setBirth("1999-11-11");
        user2.setHp("010-1234-1111");
        log.debug(user2.toString());

        // Builder 패턴 초기화
        User1DTO user3 = User1DTO.builder()
                                    .uid("A103")
                                    .name("장보고")
                                    .birth("1998-11-11")
                                    .hp("010-1111-1111")
                                    .age(33)
                                    .build();
        log.debug(user3.toString());
        User1DTO user4 = null;

        // 리스트 생성
        List<User1DTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        model.addAttribute("str1", str1);
        model.addAttribute("str2", str2);
        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        model.addAttribute("user3", user3);
        model.addAttribute("user4", user4);
        model.addAttribute("users", users);

        return "/index";
    }
    @GetMapping("/getParam")
    public String getParam(String name, int age){
        log.info("name : "+ name);
        log.info("age : "+ age);

        return "redirect:/index";
    }
    @PostMapping("/postParam")
    public String postParam(User1DTO user1DTO){
        log.info("uid : "+ user1DTO.getUid());
        log.info("name : "+ user1DTO.getName());

        return "redirect:/index";
    }

    @GetMapping("/sub2/content1")
    public String content1(){
        return "/sub2/content1";
    }
    @GetMapping("/sub2/content2")
    public String content2(){
        return "/sub2/content2";
    }
}
