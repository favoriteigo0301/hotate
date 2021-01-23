package study.sample.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;

import java.util.Optional;

@Service
public class RestSampleMemoService {

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    public SampleMemoEntity getSampleMemoById(long id) {
        Optional<SampleMemoEntity> entity = sampleMemoRepository.findById(id);
        System.out.println("戻り地" + entity.get());

        return entity.get();
    }
}
