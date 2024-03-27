package kr.co.sboard.repository;

import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.custom.ArticleRepositoryCustom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {

    /*
        JPA 페이지네이션 처리를 위한 Page 타입으로 변환
        PAge 타입은 페이지에 포함된 엔티티 목록을 표현
     */
    Page<Article> findByParentAndCate(int parent, String cate, Pageable pageable);

    // 글 조회수 업
    @Modifying
    @Query("UPDATE Article a SET a.hit = a.hit + 1 WHERE a.no = :no")
    void incrementHitByNo(@Param("no") int no);

    // 글 삭제시 연관 댓글 삭제
    void deleteArticlesByParent(int parent);

    // 댓글 작성 시 원문 comment 업
    @Modifying
    @Query("UPDATE Article a SET a.comment = a.comment + 1 WHERE a.no = :no")
    void incrementCommentByNo(@Param("no") int no);

    // 댓글 조회
    List<Article> findByParent(int parent);
}
