package kr.co.ch04.dao;

import kr.co.ch04.dto.User5DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User5DAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertUser5(User5DTO user5DTO){

    }
    public User5DTO selectUser5(String uid){
        return null;
    }
    public List<User5DTO> selectUser5s(){
        return null;
    }
    public void updateUser5(User5DTO user5DTO){

    }
    public void deleteUser5(String uid){}
}
