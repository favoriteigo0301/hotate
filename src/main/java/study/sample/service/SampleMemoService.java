package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import study.sample.bean.SampleMemoBean;
import study.sample.bean.ScreenBean;
import study.sample.config.SampleConfiguration;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.SamplePageable;
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

    @Autowired
    private SampleConfiguration configuration;

    public SampleMemoService() {
    }

    /**
     * サンプルメモ登録
     * @param request
     */
    public void registration(SampleMemoRequest request) {
        SampleMemoEntity sampleMemoEntity = new SampleMemoEntity();
        sampleMemoEntity.setSubject(request.getSubject());
        sampleMemoEntity.setUserId(1);
        sampleMemoEntity.setMemo(request.getMemo());
        sampleMemoEntity.setCreatedAt(LocalDateTime.now());
        sampleMemoEntity.setUpdatedAt(LocalDateTime.now());

        SampleMemoEntity registrationEntity = sampleMemoRepository.saveAndFlush(sampleMemoEntity);
        System.out.println("登録後のID" + registrationEntity.getId());
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

    /**
     * 技術調査のためにAPI化してるけど必要かはよくわからない
     * @return
     */
    public SampleMemoEntity[] getList() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/memo/list");

        RestTemplate restTemplate = new RestTemplate();
        SampleMemoEntity[] response =  restTemplate.getForObject(builder.toUriString(), SampleMemoEntity[].class);

        return response;
    }

    /**
     *
     * @return
     */
    public ScreenBean getSampleMemos(int page) {
        ScreenBean screenBean = new ScreenBean();
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        SamplePageable pageable = new SamplePageable(page - 1,configuration.getMaxPageSize(), sort);
        Page<SampleMemoEntity> sampleMemoEntities = sampleMemoRepository.findAll(pageable);

        // ページング設定
        screenBean.setTotalElement(sampleMemoEntities.getTotalElements());
        screenBean.setTotalPage(sampleMemoEntities.getTotalPages());
        screenBean.setCurrentPage(page);

        List<SampleMemoBean> beans =
                sampleMemoEntities.stream()
                .map(s -> {
                    SampleMemoBean bean = new SampleMemoBean();
                    bean.setId(s.getId());
                    bean.setSubject(s.getSubject());
                    bean.setMemo(s.getMemo());
                    userRepository.findById(s.getUserId()).ifPresent(f-> bean.setUserName(f.getName()));
                    return bean;
                }).map(m -> {
                    if (!StringUtils.hasText(m.getUserName())) {
                        m.setUserName("ゲストさん");
                    }
                    return m;
                }).collect(Collectors.toList());
        screenBean.setSampleMemoBeanList(beans);

        return screenBean;
    }
}
