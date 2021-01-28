package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.UserRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * サンプルロジッククラス
 * ビジネスロジックを当クラスに記述する
 */
@Service
public class SampleMemoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    public SampleMemoService() {
    }

    public boolean createDirectory() {
        Path path = Paths.get("/var/tmp");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.err.println(e);
            }
            return true;
        }
        return false;
    }

    private String getExtentionFromUploadFile(MultipartFile image) {
        int dot = image.getOriginalFilename().lastIndexOf(".");
        String extention = "";
        if (dot > 0) {
            extention = image.getOriginalFilename().substring(dot).toLowerCase();
        }
        return extention;
    }

    private byte[] uploadImage(MultipartFile image) {
        String extention = getExtentionFromUploadFile(image);
        String fileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        Path uploadFile = Paths.get("/var/tmp/" + fileName + extention);
        byte[] byteImage = null;

        try(OutputStream os = Files.newOutputStream(uploadFile, StandardOpenOption.CREATE)) {
            byteImage = image.getBytes();
        } catch (IOException e) {
            System.err.println(e);
        }

        return byteImage;

    }

    public void regist(SampleMemoRequest request) {
        byte[] byteImage = uploadImage(request.getImage());
        SampleMemoEntity sampleMemoEntity = new SampleMemoEntity();
        sampleMemoEntity.setUserId(1);
        sampleMemoEntity.setSubject(request.getTitle());
        sampleMemoEntity.setMemo(request.getDetail());
        sampleMemoEntity.setImage(byteImage);
        sampleMemoEntity.setCreatedAt(LocalDateTime.now());
        sampleMemoEntity.setUpdatedAt(LocalDateTime.now());

        sampleMemoRepository.save(sampleMemoEntity);
    }

    /**
     * サンプルメモAPIからサンプルメモリストを取得する
     * @return
     */
    public SampleMemoEntity getSampleMemo() {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/memo/")
                .queryParam("id",1);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleMemoEntity> response = restTemplate.getForEntity(builder.toUriString(), SampleMemoEntity.class);

        return response.getBody();
    }
}
