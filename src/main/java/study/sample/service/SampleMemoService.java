package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import study.sample.entity.SampleMemoEntity;
import study.sample.entity.SampleMemoListEntity;
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

    public void regist(SampleMemoRequest request) {
        SampleMemoEntity sampleMemoEntity = new SampleMemoEntity();
//        sampleMemoEntity.setSubject(request.getTitle());
//        sampleMemoEntity.setMemo(request.getDetail());
        sampleMemoEntity.setCreatedAt(LocalDateTime.now());
        sampleMemoEntity.setUpdatedAt(LocalDateTime.now());

        sampleMemoRepository.save(sampleMemoEntity);
    }

    /**
     * サンプルメモAPIからサンプルメモを取得する
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

    public SampleMemoEntity[] getList() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/memo/list");

        RestTemplate restTemplate = new RestTemplate();
        SampleMemoEntity[] response =  restTemplate.getForObject(builder.toUriString(), SampleMemoEntity[].class);

        return response;
    }
}
