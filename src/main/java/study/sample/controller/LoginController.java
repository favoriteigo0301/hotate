package study.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import study.sample.form.LoginRequest;

@Controller
@RequestMapping("/sample")
public class LoginController {

    @GetMapping("/login")
    public String index(@ModelAttribute("loginRequest")LoginRequest request)  {
        return "login";
    }
}
