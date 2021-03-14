package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import study.sample.bean.SampleMemoBean;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        sampleMemoEntity.setCategoryIds(request.getCategories());
        sampleMemoEntity.setCreatedAt(LocalDateTime.now());
        sampleMemoEntity.setUpdatedAt(LocalDateTime.now());

        sampleMemoRepository.saveAndFlush(sampleMemoEntity);
    }

    /**
     * サンプルメモAPIからサンプルメモを取得する（TODO 使用するか未定）
     * @return
     */
    public SampleMemoEntity getSampleMemo() {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/memo/")
                .queryParam("id",98);

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

    public List<SampleMemoBean> getSampleMemos() {
        List<SampleMemoEntity> sampleMemoEntities = sampleMemoRepository.findAll();
        List<SampleMemoBean> beans =
                sampleMemoEntities.stream()
                .map(s -> {
                    SampleMemoBean bean = new SampleMemoBean();
                    bean.setId(s.getId());
                    bean.setSubject(s.getSubject());
                    bean.setMemo(s.getMemo());
                    userRepository.findById(s.getUserId()).ifPresent(f-> bean.setUserName(f.getName()));
                    userRepository.findById(s.getUserId()).ifPresent(f-> bean.setSampleMemoEntities(f.getEntityList()));

                    System.out.println("userリポジトリ" + bean.getSampleMemoEntities().size());
                    return bean;

                }).map(m -> {
                    if (!StringUtils.hasText(m.getUserName())) {
                        m.setUserName("ゲストさん");
                    }
                    return m;
                }).collect(Collectors.toList());
        return beans;
    }
}
