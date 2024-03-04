package kr.co.ch03;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {

    @Pointcut("execution(void kr.co.ch03.BasicService.insert(..))")
    public void insertPointcut(){} // 내용이 없는 참조 메서드

    @Pointcut("execution(void kr.co.ch03..*Service.select*(..))") //Service로 끝나는 모든 Class의 select로 시작하는 모든 메서드
    public void selectPointcut(){}

    @Before("insertPointcut() || selectPointcut()")
    public void beforeLog(){
        System.out.println("-----------------------");
        System.out.println("부가 기능 - beforeLog()...");
    }
    @After("insertPointcut() || selectPointcut()")
    public void afterLog(){
        System.out.println("부가 기능 - afterLog()...");
        System.out.println("-----------------------");
    }
    @AfterReturning("insertPointcut()")
    public void afterReturnLog(){
        System.out.println("부가 기능 - afterReturnLog()...");
    }
    @Around("insertPointcut()")
    public void aroundLog(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("부가 기능 - aroundLog()...1");
        pjp.proceed(); // 핵심기능 실행
        System.out.println("부가 기능 - aroundLog()...2");
    }
    @AfterThrowing("selectPointcut()")
    public void afterThrowLog(){
        System.out.println("부가 기능 - AfterThrowing()...");
    }

}
