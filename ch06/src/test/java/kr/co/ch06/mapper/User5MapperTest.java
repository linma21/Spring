package kr.co.ch06.mapper;

import kr.co.ch06.dto.User5DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class User5MapperTest {
    @Autowired
    private User5Mapper mapper;


    @DisplayName("user5 등록")
    void insertUser5() {

        User5DTO user5DTO = User5DTO.builder()
                .name("홍길동")
                .gender("F")
                .age(55)
                .addr("부산 동래구")
                .build();

        mapper.insertUser5(user5DTO);
        log.info(user5DTO.toString());

        User5DTO resultUser5 = mapper.selectUser5(user5DTO.getSeq());
        assertEquals(user5DTO.getSeq(), resultUser5.getSeq());

    }

    @DisplayName("user5 조회")
    void selectUser5() {
        int seq = 14;
        User5DTO user5DTO = mapper.selectUser5(seq);
        log.info(user5DTO.toString());
        assertNotNull(user5DTO);
    }

    @DisplayName("user5 목록")
    void selectUser5s() {
        List<User5DTO> users = mapper.selectUser5s();
        for(User5DTO user5DTO : users ){
            log.info(user5DTO.toString());
        }
        assertFalse(users.isEmpty());
    }

    @DisplayName("user5 수정")
    void updateUser5() {
        User5DTO user5DTO = User5DTO.builder()
                .seq(14)
                .name("홍길동")
                .gender("F")
                .age(55)
                .addr("부산 동동동")
                .build();
        mapper.updateUser5(user5DTO);
        User5DTO resultUser5 = mapper.selectUser5(user5DTO.getSeq());
        assertEquals(user5DTO.getSeq(), resultUser5.getSeq());
    }
    @Test
    @DisplayName("user5 삭제")
    void deleteUser5() {
        int seq = 14;
        mapper.deleteUser5(seq);
        User5DTO user5DTO = mapper.selectUser5(seq);
        assertNull(user5DTO);
    }
}