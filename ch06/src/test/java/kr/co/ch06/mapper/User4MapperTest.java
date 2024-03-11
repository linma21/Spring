package kr.co.ch06.mapper;

import kr.co.ch06.dto.User4DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class User4MapperTest {
    @Autowired
    private User4Mapper mapper;


    @DisplayName("user4 등록")
    void insertUser4() {
        User4DTO user4DTO = User4DTO.builder()
                .uid("B101")
                .name("홍길동")
                .gender("M")
                .age(55)
                .hp("010-5789-1234")
                .addr("부산 동래구")
                .build();
        mapper.insertUser4(user4DTO);
        User4DTO resultUser4 = mapper.selectUser4(user4DTO.getUid());
        Assertions.assertEquals(user4DTO.getUid(), resultUser4.getUid());
    }


    @DisplayName("user4 조회")
    void selectUser4() {
        String uid = "B101";
        User4DTO user4DTO = mapper.selectUser4(uid);
        assertEquals(uid,user4DTO.getUid());
    }

    @DisplayName("user4 목록")
    void selectUser4s() {
        List<User4DTO> users = mapper.selectUser4s();
        for(User4DTO user4DTO : users){
            log.info(user4DTO.toString());
        }
        assertFalse(users.isEmpty());
    }

    @DisplayName("user4 수정")
    void updateUser4() {
        User4DTO user4DTO = User4DTO.builder()
                .uid("B101")
                .name("홍길동")
                .gender("M")
                .age(12)
                .hp("010-5789-1234")
                .addr("부산 동동동")
                .build();

        mapper.updateUser4(user4DTO);
        User4DTO resultUser4 = mapper.selectUser4(user4DTO.getUid());
        assertEquals(user4DTO.getName(), resultUser4.getName());
    }
    @Test
    @DisplayName("user4 삭제")
    void deleteUser4() {
        String uid = "B101";
        mapper.deleteUser4(uid);
        User4DTO user4DTO = mapper.selectUser4(uid);
        assertNull(user4DTO);
    }
}