package study.sample.form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class SampleMemoRequest {

    @NotBlank(message = "タイトルを入力してください")
    private String title;

    @NotBlank(message = "内容を入力してください")
    private String detail;

    private MultipartFile image;

    public SampleMemoRequest() {
    }

}
