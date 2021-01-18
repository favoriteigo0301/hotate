package study.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import study.sample.dao.SampleDao;
import study.sample.form.SampleMemoRequest;
import study.sample.repository.SampleMemo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                if (error.getField().equals("title")) {
                    mav.addObject("titleError", error.getDefaultMessage());
                } else if (error.getField().equals("detail")) {
                    mav.addObject("detailError", error.getDefaultMessage());
                }
            }
        }

        Path path = Paths.get("/var/tmp");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        System.out.println(request.getTitle());
        System.out.println(request.getImage());


        int dot = request.getImage().getOriginalFilename().lastIndexOf(".");
        String extention = "";
        if (dot > 0) {
            extention = request.getImage().getOriginalFilename().substring(dot).toLowerCase();
        }
        String fileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        System.out.println(fileName);
        Path uploadFile = Paths.get("/var/tmp/" + fileName + extention);

        try(OutputStream os = Files.newOutputStream(uploadFile,StandardOpenOption.CREATE)) {
            byte[] bytes = request.getImage().getBytes();
        } catch (IOException e) {
            System.err.println(e);
        }

        mav.setViewName("sample_memo");
        return mav;
    }
}
