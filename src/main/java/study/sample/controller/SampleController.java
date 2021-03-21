package study.sample.controller;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.sample.config.SampleConfiguration;
import study.sample.dao.SampleDao;
import study.sample.entity.SampleMemoEntity;
import study.sample.repository.SampleMemoRepository;
import study.sample.service.SampleMemoService;
import study.sample.form.SampleMemoRequest;

import java.util.List;

/**
 *  SpringBootの動作を確認するためのサンプルクラス
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    SampleMemoRepository memoRepository;

    /**
     * SpringJdbcを動かすためのDAO(用途を理解するために記述）
     */
    @Autowired
    SampleDao memoDao;

    @Autowired
    SampleMemoService service;

    @Autowired
    SampleConfiguration sampleConfiguration;

    @GetMapping("/test")
    public ModelAndView index(ModelAndView mav) {
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

    /**
     * 登録ボタン後処理
     * @param request
     * @param result
     * @param mav
     * @param attributes
     * @return
     */
    @PostMapping("/memo/index")
    public ModelAndView memoIndex(@Validated @ModelAttribute("request") SampleMemoRequest request,
                                  BindingResult result, ModelAndView mav,
                                  RedirectAttributes attributes)  {
        if (!result.hasErrors()) {
            service.registration(request);
            mav.setViewName("redirect:/sample/memo/list");
            attributes.addFlashAttribute("flashMessage", "登録に成功しました");
        } else if (request.getCategories().length == 0){
            mav.addObject("request",request);
            mav.addObject("categoryError","タグ名を入力してください");
            mav.setViewName("sample_memo");
        } else {
            mav.addObject("request",request);
            mav.setViewName("sample_memo");
        }
        return mav;
    }

    @GetMapping("/api/memo/{id}")
    public ModelAndView apiMemo(@ModelAttribute("request") SampleMemoRequest request,
                                BindingResult result, ModelAndView mav, @PathVariable(name = "id") long id) {
        mav.setViewName("sample_memo");
        service.getSampleMemo();
        return mav;
    }

    @GetMapping("/memo/list")
    public ModelAndView showMemoList(ModelAndView mav) {
        mav.addObject("sampleMemoList", service.getSampleMemos(1));
        mav.setViewName("sample_memo_list");
        return mav;
    }

    @GetMapping("/memo/list/{page}")
    public ModelAndView showMemoPage(ModelAndView mav, @PathVariable int page) {
        System.out.println("検索ID" + page);
        mav.addObject("sampleMemoList", service.getSampleMemos(page));
        mav.setViewName("sample_memo_list");
        return mav;
    }

}
