package study.sample.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "ユーザ名を入力してください")
    private String userName;

    @NotBlank(message = "パスワードを入力してください")
    private String password;

    private String actionFlg;

    public LoginRequest() {

    }

}
