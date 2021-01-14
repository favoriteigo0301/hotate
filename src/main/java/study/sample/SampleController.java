package study.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import study.sample.dao.SampleDao;
import study.sample.repository.SampleMemo;

/**
 *  SpringBootの動作を確認するためのテストクラス
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
    public String sampleMemo() {
        return "sample_memo";
    }

}
