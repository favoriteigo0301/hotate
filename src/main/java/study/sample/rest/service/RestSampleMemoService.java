package study.sample.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RestSampleMemoService {

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    public SampleMemoEntity getSampleMemoById(long id) {
        Optional<SampleMemoEntity> entity = sampleMemoRepository.findById(id);

        return entity.get();
    }

    public List<SampleMemoEntity> getSampleMemoList() {
        List<SampleMemoEntity> entityList = sampleMemoRepository.findAll();
        return entityList;
    }

}
