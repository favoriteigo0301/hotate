package study.sample.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScreenBean {
    private List<SampleMemoBean> sampleMemoBeanList = new ArrayList<SampleMemoBean>();
    private int totalPage;
    private long totalElement;
    private int currentPage;
}
