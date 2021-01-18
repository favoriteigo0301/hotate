package study.sample.form;


import jdk.jfr.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class SampleMemoRequest {

    @NotBlank(message = "タイトルを入力してください")
    private String title;

    @NotBlank(message = "内容を入力してください")
    private String detail;

    private MultipartFile image;

    public SampleMemoRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
