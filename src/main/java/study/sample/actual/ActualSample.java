package study.sample.actual;

import study.sample.entity.SampleMemoEntity;

import java.util.stream.Stream;

public class ActualSample {

    MockTestSample sample = new MockTestSample();

    public SampleMemoEntity testSample() {
        SampleMemoEntity entity = sample.getSampleMemo();
        System.out.println("success");


        return entity;
    }

    public Stream<SampleMemoEntity> testStream() {
        SampleMemoEntity entity = new SampleMemoEntity();
        entity.setId(1);
        Stream<SampleMemoEntity> test = Stream.of(entity);

        return test;

    }

}
