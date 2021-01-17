package study.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import study.sample.dao.SampleDao;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.SampleMemo;

/**
 *  SpringBootの動作を確認するためのサンプルクラス
 */
@Controller
public class SampleController {

    @Autowired
    SampleMemo memoRepository;

    @Autowired
    SampleDao memoDao;

    @GetMapping("/sample/index")
    public ModelAndView test(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/sample/count")
    public ModelAndView count(ModelAndView mav){
        mav.setViewName("index");
        System.out.println("メモテーブル件数"+memoRepository.count());
        return mav;
    }

    @GetMapping("sample/jdbc/count")
    public ModelAndView jdbcCount(ModelAndView mav) {
        mav.setViewName("index");
        System.out.println("jdbcでのcount文"+ memoDao.count() );
        return mav;
    }

    @GetMapping("sample/memo")
    public String sampleMemo(@ModelAttribute("request") SampleMemoRequest request, ModelAndView mav) {
        mav.addObject("request", request);
        return "sample_memo";
    }

    @PostMapping("sample/memo/index")
    public ModelAndView memoIndex(@Validated @ModelAttribute("request") SampleMemoRequest request, BindingResult result, ModelAndView mav) {
        mav.addObject("request",request);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                if (error.getField().equals("title")) {
                    mav.addObject("titleError", error.getDefaultMessage());
                } else if (error.getField().equals("detail")) {
                    mav.addObject("detailError", error.getDefaultMessage());
                }
            }
        }
        mav.setViewName("sample_memo");
        return mav;
    }
}
