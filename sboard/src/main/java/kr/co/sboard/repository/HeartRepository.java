package kr.co.sboard.repository;

import kr.co.sboard.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Integer> {

    Heart findByUidAndNo(String uid, int no);

    void deleteByUidAndNo(String uid, int no);
}
