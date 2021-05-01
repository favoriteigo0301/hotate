package study.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.sample.exception.TargetNotException;
import study.sample.form.LoginRequest;
import study.sample.service.LoginService;

@Controller
@RequestMapping("/sample")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String index(@ModelAttribute("loginRequest")LoginRequest request)  {
        return "login";
    }

    @GetMapping("/login/signup")
    public String signup(@ModelAttribute("loginRequest")LoginRequest request) {
        return "signup";
    }

    @PostMapping("/login/register")
    public ModelAndView register(@ModelAttribute("loginRequest")LoginRequest request,
                                 ModelAndView mav, BindingResult result,RedirectAttributes attributes) {

        loginService.register(request);

        mav.setViewName("login");
        return mav;
    }
}
