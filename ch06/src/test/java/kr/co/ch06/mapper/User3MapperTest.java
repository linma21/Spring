package kr.co.ch06.mapper;

import kr.co.ch06.dto.User3DTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class User3MapperTest {

    @Autowired
    private User3Mapper mapper;
    

    @DisplayName("user3 등록")
    void insertUser3() {
        // give
        User3DTO user3DTO = User3DTO.builder()
                .uid("J101")
                .name("홍길동")
                .birth("1999-11-11")
                .hp("010-1234-1000")
                .addr("부산시 동래구")
                .build();
        // when
        mapper.insertUser3(user3DTO);
        // then
        User3DTO resultUser3 = mapper.selectUser3(user3DTO.getUid());
        Assertions.assertEquals(user3DTO.getUid(), resultUser3.getUid());
    }

    @DisplayName("user3 조회")
    void selectUser3() {
        String uid = "J101";
        User3DTO user3DTO = mapper.selectUser3(uid);
        log.info(user3DTO.toString());
        assertEquals(uid, user3DTO.getUid());
    }

    @DisplayName("user3 목록")
    void selectUser3s() {
        List<User3DTO> users = mapper.selectUser3s();
        for(User3DTO user3DTO : users){
            log.info(user3DTO.toString());
        }
        assertFalse(users.isEmpty());
    }

    @DisplayName("user3 수정")
    void updateUser3() {
        User3DTO user3DTO = User3DTO.builder()
                .uid("J101")
                .name("홍길동")
                .birth("1999-11-22")
                .hp("010-1234-1000")
                .addr("부산시 동동동")
                .build();

        mapper.updateUser3(user3DTO);
        User3DTO resultUser3 = mapper.selectUser3(user3DTO.getUid());
        assertEquals(user3DTO.getName(), resultUser3.getName());
    }

    @Test
    @DisplayName("user3 삭제")
    void deleteUser3() {
        String uid = "j101";
        mapper.deleteUser3(uid);
        User3DTO resultUser3 = mapper.selectUser3(uid);
        assertNull(resultUser3);
    }
}