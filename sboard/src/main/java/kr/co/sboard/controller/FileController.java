package kr.co.sboard.controller;

import kr.co.sboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/download/{fno}")
    public ResponseEntity<?> fileDownload(@PathVariable("fno") int fno) {
        log.info("fileDownload : " + fno);
        return fileService.fileDownload(fno);
    }

    @GetMapping("/file/downloadCount/{fno}")
    public ResponseEntity<?> fileDownloadCount(@PathVariable("fno") int fno) {
        log.info("fileDownloadCount : " + fno);
        return fileService.fileDownloadCount(fno);
    }
    @DeleteMapping("/file/{fno}")
    public ResponseEntity<?> deleteFile(@PathVariable("fno") int fno){
        log.info("deleteFile : "+ fno);
        return fileService.deleteFile(fno);
    }
}
