package kr.co.sboard.controller;

import kr.co.sboard.dto.HeartDTO;
import kr.co.sboard.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/heart")
    public ResponseEntity<?> insertHeart(@RequestBody HeartDTO likeDTO){
        return heartService.insert(likeDTO);
    }
    @DeleteMapping("/heart/{uid}/{no}")
    public ResponseEntity<?> deleteHeart(@PathVariable("uid") String uid, @PathVariable("no") int no ){
        log.info("deleteHeart : " + uid);
        log.info("deleteHeart : " + no);
        return heartService.delete(uid, no);
    }
}
