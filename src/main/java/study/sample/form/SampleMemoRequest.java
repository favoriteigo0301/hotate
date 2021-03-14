package study.sample.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SampleMemoRequest {

    @NotBlank(message = "ユーザ名を入力してください")
    private String userName;

    @NotBlank(message = "タイトルを入力してください")
    private String title;

    @NotBlank(message = "メモを入力してください")
    private String memo;

    private String categories;

    public SampleMemoRequest() {
    }
}