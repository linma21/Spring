package kr.co.sboard.aspect;

import kr.co.sboard.entity.Config;
import kr.co.sboard.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class BoardNameAspect {
    private final ConfigRepository configRepository;

    // .* : 모든 메서드
    // (..) : 변수 제한 없음
    @Pointcut("execution(* kr.co.sboard.controller.ArticleController.*(..))")
    public void boardNameAttribute(){}

    @AfterReturning(pointcut = "boardNameAttribute() && args(model, cate, ..)")
    public void addBoardName(Model model, String cate){

        Optional<Config> optConfig = configRepository.findById(cate);
        String boardName = optConfig.get().getBoardName();
        model.addAttribute("boardName", boardName);
    }
}
