package study.sample.form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class SampleMemoRequest {

    @NotBlank(message = "ユーザ名を入力してください")
    private String userName;

    @NotBlank(message = "タイトルを入力してください")
    private String title;

    @NotBlank(message = "内容を入力してください")
    private String detail;

    private String category;

    private String [] categories;

    public SampleMemoRequest() {
    }

}
