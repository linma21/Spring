package kr.co.sboard.mapper;

import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public void insertUser(UserDTO userDTO);
    public int checkUid(String uid);

    public UserDTO selectUser(String uid);

    public int selectCountUser(@Param("type") String type, @Param("value") String value);
    public List<UserDTO> selectUsers();
    public void updateUser(UserDTO userDTO);
    public void deleteUser(String uid);

    public TermsDTO selectTerms();
}
