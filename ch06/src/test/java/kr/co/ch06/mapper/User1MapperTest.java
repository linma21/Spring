package kr.co.ch06.mapper;

import kr.co.ch06.dto.User1DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest // 생략 가능
class User1MapperTest {
    @Autowired
    private User1Mapper mapper;


    @DisplayName("user1 등록")
    void insertUser1() {
        log.info("insetUser1...");
        // 테스트 코드 패턴 : given - when - then

        // given : 테스트 실행 준비 단계
        User1DTO user1DTO = User1DTO.builder()
                        .uid("J102")
                        .name("홍길동")
                        .birth("1999-11-01")
                        .hp("010-1234-0112")
                        .age(45)
                        .build();
        // when : 테스트 진행 단계
        mapper.insertUser1(user1DTO);

        // then : 테스트 결과 검증 단계 (Assertion 단정문 이용)
        User1DTO resultUser1 = mapper.selectUser1(user1DTO.getUid());
        Assertions.assertEquals(user1DTO.getUid(), resultUser1.getUid());
    }


    @DisplayName("user1 조회")
    void selectUser1() {
        log.info("selectUser1...");

        // given
        String uid = "J101";

        // when
        User1DTO user1DTO = mapper.selectUser1(uid);
        log.info(user1DTO.toString());
        // then
        assertEquals(uid, user1DTO.getUid());
    }

    @DisplayName("user1 목록")
    void selectUser1s() {
        log.info("selectUser1s...");

        // given

        // when
        List<User1DTO> users = mapper.selectUser1s();
        for(User1DTO user1DTO : users){
            log.info(user1DTO.toString());
        }
        // then
        assertFalse(users.isEmpty());
    }
    @Test
    @DisplayName("user1 수정")
    void updateUser1() {
        log.info("updateUser1...");

        // given
        User1DTO user1DTO = User1DTO.builder()
                                .uid("j102")
                                .name("홍길똥")
                                .birth("2024-01-01")
                                .hp("010-2024-1234")
                                .age(99)
                                .build();

        // when
        mapper.updateUser1(user1DTO);

        // then
        User1DTO resultUser1 = mapper.selectUser1(user1DTO.getUid());
        assertEquals(user1DTO.getName(), resultUser1.getName());
    }
    @Test
    @DisplayName("user1 삭제")
    void deleteUser1() {
        log.info("deleteUser1...");

        // given
        String uid = "j102";

        // when
        mapper.deleteUser1(uid);

        // then
        User1DTO resultUser1 = mapper.selectUser1(uid);
        assertNull(resultUser1);
    }
}