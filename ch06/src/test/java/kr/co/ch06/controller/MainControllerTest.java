package kr.co.ch06.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MainController.class)
class MainControllerTest {

    /*
      MockMvc는 웹 애플리케이션을 서버에 실행하지 않고
      Spring MVC 동작을 재현해서 테스트 하는 가상 객체
    */
    @Autowired
    private MockMvc mockMvc;


    void index() throws Exception {
        mockMvc.perform(get("/index"))              // index 요청 테스트
                .andExpect(status().isOk())         // 요청 결과 상태 테스트
                .andExpect(view().name("/index"))   // 요청 뷰 테스트
                .andDo(print());                    //요청 결과 상태 출력
    }

    // MainController에 getParam() 요청 메서드 정의
    @Test
    public void getParam() throws Exception {
        mockMvc
                .perform(get("/getParam")
                        .param("name", "홍길동")
                        .param("age", "23"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/index"))
                .andDo(print());

        /*
        for(int i=0 ; i<1000 ; i++) {
            mockMvc
                .perform(get("/getParam")
                        .param("name", "홍길동")
                        .param("age", "23"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/index"))
                .andDo(print());
        }
        */
    }
    // MainController에 postParam() 요청 메서드 정의

    public void postParam() throws Exception {
        mockMvc
                .perform(post("/postParam")
                        .param("uid", "p101")
                        .param("name", "김유신"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"))
                .andDo(print());
    }
    void content1() {
    }

    void content2() {
    }
}