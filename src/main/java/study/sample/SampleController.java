package study.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  SpringBootの動作を確認するためのテストクラス
 */
@Controller
public class SampleController {

    @GetMapping("/sample/index")
    public ModelAndView test(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }
}
