package study.sample.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SampleMemoRequest {

    @NotBlank(message = "タイトルを入力してください")
    private String subject;

    @NotBlank(message = "メモを入力してください")
    private String memo;

    private String[] categories;

    public SampleMemoRequest() {
    }
}