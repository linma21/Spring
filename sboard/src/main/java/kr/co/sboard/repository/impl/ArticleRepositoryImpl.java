package kr.co.sboard.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.QArticle;
import kr.co.sboard.entity.QUser;
import kr.co.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    public Page<Tuple> selectArticles(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String cate = pageRequestDTO.getCate();

        QueryResults<Tuple> results = jpaQueryFactory
                                            .select(qArticle, qUser.nick)
                                            .from(qArticle)
                                            .where(qArticle.cate.eq(cate).and(qArticle.parent.eq(0)))
                                            .join(qUser)
                                            .on(qArticle.writer.eq(qUser.uid))
                                            .orderBy(qArticle.no.desc())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetchResults();

        List<Tuple> content = results.getResults();
        long total = results.getTotal();

        // 페이지 처리용 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }
    @Override
    public Page<Tuple> searchArticles(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String cate = pageRequestDTO.getCate();
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        // 검색 종류에 따른 where절 표현식 생성
        if(type.equals("title")){
            expression = qArticle.cate.eq(cate).and(qArticle.title.contains(keyword));
            log.info("expression" + expression);

        }else if(type.equals("content")){
            expression = qArticle.cate.eq(cate).and(qArticle.content.contains(keyword));
            log.info("expression" + expression);

        }else if(type.equals("title_content")){
            BooleanExpression titleContains = qArticle.cate.eq(cate).and(qArticle.title.contains(keyword));
            BooleanExpression contentContains = qArticle.cate.eq(cate).and(qArticle.content.contains(keyword));
            expression = qArticle.cate.eq(cate).and(titleContains).or(contentContains);
            log.info("expression" + expression);

        }else if(type.equals("writer")){
            expression = qArticle.cate.eq(cate).and(qArticle.parent.eq(0).and(qUser.nick.contains(keyword)));
            log.info("expression" + expression);
        }
        // select * from article where `cate`= '' and `type` contains(k) limit 0,10;
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qArticle, qUser.nick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.writer.eq(qUser.uid))
                .where(expression)
                .orderBy(qArticle.no.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> content = results.getResults();
        long total = results.getTotal();

        // 페이지 처리용 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }

    // 댓글 목록
    @Override
    public List<Tuple> selectComments(int parent){

        List<Tuple> results = jpaQueryFactory
                .select(qArticle, qUser.nick)
                .from(qArticle)
                .where(qArticle.parent.eq(parent))
                .join(qUser)
                .on(qArticle.writer.eq(qUser.uid))
                .fetch();

        return results;

    }
}
