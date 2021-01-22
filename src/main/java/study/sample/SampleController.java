package study.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import study.sample.dao.SampleDao;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.SampleMemo;
import study.sample.service.SampleMemoService;

/**
 *  SpringBootの動作を確認するためのサンプルクラス
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    SampleMemo memoRepository;

    @Autowired
    SampleDao memoDao;

    @Autowired
    SampleMemoService service;

    @GetMapping("/index")
    public ModelAndView test(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/count")
    public ModelAndView count(ModelAndView mav){
        mav.setViewName("index");
        System.out.println("メモテーブル件数"+memoRepository.count());
        return mav;
    }

    @GetMapping("/jdbc/count")
    public ModelAndView jdbcCount(ModelAndView mav) {
        mav.setViewName("index");
        System.out.println("jdbcでのcount文"+ memoDao.count() );
        return mav;
    }

    @GetMapping("/memo")
    public String sampleMemo(@ModelAttribute("request") SampleMemoRequest request, ModelAndView mav) {
        mav.addObject("request", request);
        return "sample_memo";
    }

    @PostMapping("/memo/index")
    public ModelAndView memoIndex(@Validated @ModelAttribute("request") SampleMemoRequest request, BindingResult result, ModelAndView mav)  {
        mav.addObject("request",request);
        if (!result.hasErrors()) {
            service.createDirectory();
            service.uploadImage(request.getImage());
        }
        mav.setViewName("sample_memo");
        return mav;
    }
}
