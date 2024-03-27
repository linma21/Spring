package kr.co.sboard.service;


import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public List<FileDTO> fileUpload(ArticleDTO articleDTO) {

        String path = new File(fileUploadPath).getAbsolutePath();

        List<FileDTO> files = new ArrayList<>();
        log.info("fileUpload...1");
        for (MultipartFile mf : articleDTO.getFiles()) {
            log.info("fileUpload...2");
            // 파일 첨부 여부 확인
            if (!mf.isEmpty()) {
                log.info("fileUpload...3");
                String oName = mf.getOriginalFilename();
                log.info("fileUpload...4" + oName);
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                log.info("sName  : " + sName);
                try {
                    // 저장
                    mf.transferTo(new File(path, sName));

                    FileDTO fileDTO = FileDTO.builder()
                            .oName(oName)
                            .sName(sName)
                            .build();
                    files.add(fileDTO);

                } catch (IOException e) {
                    log.error("fileUpload : " + e.getMessage());
                }
            }
        }
        return files;
    }

    @Transactional
    public ResponseEntity<?> fileDownload(int fno){

        kr.co.sboard.entity.File file =  fileRepository.findById(fno).get();
        log.info("fileDownload ....1 ");
        try {
            Path path = Paths.get(fileUploadPath + file.getSName());
            String contentType = Files.probeContentType(path);
            log.info("fileDownload ....2 ");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(file.getOName(), StandardCharsets.UTF_8).build());
            log.info("fileDownload ....3 ");
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            // 파일 다운로드 카운트 업데이트
            file.setDownload(file.getDownload() + 1);
            fileRepository.save(file);
            log.info("fileDownload ....4");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }catch (IOException e){
            log.error("fileDownload : " + e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> fileDownloadCount(int fno){

        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();
        log.info("fileDownloadCount ....1 ");
        // 다운로드 카운트 json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", file.getDownload());
        log.info("fileDownloadCount : " + resultMap.toString());
        return ResponseEntity.ok().body(resultMap);

    }
    @Transactional
    public ResponseEntity<?> deleteFile(int fno){

        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();
        if(file != null){
            fileRepository.deleteById(fno);

            // article file 수 --
            Article article = articleRepository.findById(file.getAno()).get();
            article.setFile(article.getFile() - 1);
            articleRepository.save(article);

            return ResponseEntity.ok().body(file);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file not found");
    }
}
