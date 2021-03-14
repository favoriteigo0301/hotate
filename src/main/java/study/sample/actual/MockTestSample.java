package study.sample.actual;

import study.sample.entity.SampleMemoEntity;

public class MockTestSample {

    public SampleMemoEntity getSampleMemo() {

        return getSampleMemoEntity();
    }

    private SampleMemoEntity getSampleMemoEntity() {
        SampleMemoEntity sampleMemoEntity = new SampleMemoEntity();

        return  sampleMemoEntity;
    }

}
