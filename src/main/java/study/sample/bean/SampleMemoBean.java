package study.sample.bean;

import lombok.Data;

@Data
public class SampleMemoBean {
    private long id;
    private String userName;
    private String categoryName;
    private String subject;
    private String memo;
}
