package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/list")
    public String list(@PageableDefault(page = 1)Pageable pageable,
                       @ModelAttribute("cate") String cate
                        , Model model){
        Page<ArticleDTO> articles = articleService.paging(pageable);
        /*
         * blockLimit : page 개수 설정
         * 현재 사용자가 선택한 페이지 앞 뒤로 3페이지씩만 보여준다.
         * ex : 현재 사용자가 4페이지라면 2, 3, (4), 5, 6
         */
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), articles.getTotalPages());
        // 페이지 번호 목록을 생성하여 모델에 추가
        int[] pageNumbers = IntStream.rangeClosed(startPage, endPage).toArray();
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("articles", articles);
        return "/article/list";
    }
    @GetMapping("/article/write")
    public String write(@ModelAttribute("cate") String cate){
        return "/article/write";
    }
    @PostMapping("/article/write")
    public String write(HttpServletRequest req, ArticleDTO articleDTO){

        String regIp = req.getRemoteAddr();
        articleDTO.setRegIp(regIp);

        articleService.insertArticle(articleDTO);
        return "redirect:/article/list?cate="+articleDTO.getCate();
    }
}
