package kr.co.ch07.Service;

import kr.co.ch07.dto.User2DTO;
import kr.co.ch07.entity.User2;
import kr.co.ch07.repository.User2Repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class User2Service {

    private final User2Repository repository;

    public void insertUser2(User2DTO user2DTO){
        User2 user2 = user2DTO.toEntity();
        repository.save(user2);
    }
    public User2DTO selectUser2(String uid){
        Optional<User2> result = repository.findById(uid);
        return result.get().toDTO();
    }
    public List<User2DTO> selectUser2s(){
        List<User2> user2s = repository.findAll();
        List<User2DTO> users = user2s.stream()
                .map(entity -> User2DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .birth(entity.getBirth())
                        .addr(entity.getAddr())
                        .build())
                .collect(Collectors.toList());

        return users;
    }
    public void updateUser2(User2DTO user2DTO){
        User2 user2 = user2DTO.toEntity();
        repository.save(user2);
    }
    public void deleteUser2(String uid){
        repository.deleteById(uid);
    }
}
