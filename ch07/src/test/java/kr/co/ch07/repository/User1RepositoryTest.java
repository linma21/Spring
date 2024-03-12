package kr.co.ch07.repository;

import kr.co.ch07.entity.User1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class User1RepositoryTest {
    /*
        Junit 테스트에서는 @Autowired로 주입
        생성자 주입은
     */

    @Autowired
    private User1Repository repository;


    @DisplayName("User1 등록")
    public void insertUser1(){
        User1 user1Entity = User1.builder()
                .uid("J102")
                .name("홍길동")
                .birth("1999-11-11")
                .hp("010-1234-9999")
                .age(55)
                .build();

        // when : entity 저장
        repository.save(user1Entity);


    }

    @DisplayName("User1 조회")
    public void selectUser1(){
        String uid ="J102";
        // when : 조회하기
        Optional<User1> result = repository.findById(uid);
        User1 user1 = result.get();
        log.info(user1.toString());
    }

    @DisplayName("User1 목록")
    public void selectUser1s(){
    }
    @DisplayName("User1 수정")
    public void updateUser1(){}
    @DisplayName("User1 삭제")
    public void deleteUser1(){}

    // 사용자 정의 쿼리 메서드 테스트
    @Test
    public void findUser1ByUid(){
        User1 user1 = repository.findUser1ByUid("P101");
        log.warn(user1.toString());
    }
    @Test
    public void findUser1ByName(){
        List<User1> user1s = repository.findUser1ByName("홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByNameNot(){
        List<User1> user1s = repository.findUser1ByNameNot("홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByUidAndName(){
        User1 user1 = repository.findUser1ByUidAndName("j101", "홍길동");
        log.warn(user1.toString());
    }
    @Test
    public void findUser1ByUidOrName(){
        List<User1> user1s = repository.findUser1ByUidOrName("j101","홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeGreaterThan(){
        List<User1> user1s = repository.findUser1ByUidOrName("j101","홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeGreaterThanEqual(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThanEqual(55);
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeLessThan(){
        List<User1> user1s = repository.findUser1ByAgeLessThan(55);
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeLessThanEqual(){
        List<User1> user1s = repository.findUser1ByAgeLessThanEqual(55);
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeBetween(){
        List<User1> user1s = repository.findUser1ByAgeBetween(10,50);
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByNameLike(){
        List<User1> user1s = repository.findUser1ByNameLike("홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByNameContains(){
        List<User1> user1s = repository.findUser1ByNameContains("홍길동");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByNameStartsWith(){
        List<User1> user1s = repository.findUser1ByNameStartsWith("김");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByNameEndsWith(){
        List<User1> user1s = repository.findUser1ByNameEndsWith("신");
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByOrderByName(){
        List<User1> user1s = repository.findUser1ByOrderByName();
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByOrderByAgeAsc(){
        List<User1> user1s = repository.findUser1ByOrderByAgeAsc();
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByOrderByAgeDesc(){
        List<User1> user1s = repository.findUser1ByOrderByAgeDesc();
        log.warn(user1s.toString());
    }
    @Test
    public void findUser1ByAgeGreaterThanOrderByAgeDesc(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThanOrderByAgeDesc(30);
        log.warn(user1s.toString());
    }
    @Test
    public void countUser1ByUid(){
        int count = repository.countUser1ByUid("j101");
        log.warn(String.valueOf(count));
    }
    @Test
    public void countUser1ByName(){
        int count = repository.countUser1ByName("홍길동");
        log.warn(String.valueOf(count));
    }

    // JPQL 정의
    @Test
    @Query("select u1 from User1 as u1 where u1.age < 30")
    public void selectUser1UnderAge30(){
        List<User1> user1s = repository.selectUser1UnderAge30();
        log.warn(user1s.toString());
    }

    @Test
    @Query("select u1 from User1 as u1 where u1.name = ?1")
    public void selectUser1ByName(){
        List<User1> user1s = repository.selectUser1ByName("홍길동");
        log.warn(user1s.toString());
    }

    @Test
    @Query("select u1 from User1 as u1 where u1.name = :name")
    public void selectUser1ByNameParam(){
        List<User1> user1s = repository.selectUser1ByNameParam("홍길동");
        log.warn(user1s.toString());
    }

    @Test
    @Query("select u1.uid, u1.name, u1.age from User1 as u1 where u1.uid = :uid")
    public void selectUser1ByUid(){
        List<Object[]> user1s = repository.selectUser1ByUid("j101");
        log.warn(user1s.toString());
    }
}