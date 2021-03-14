package study.sample.bean;

import lombok.Data;
import study.sample.entity.SampleMemoEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class SampleMemoBean {
    private long id;
    private String userName;
    private String categoryName;
    private String subject;
    private String memo;
    private List<SampleMemoEntity> sampleMemoEntities = new ArrayList<>();
}
