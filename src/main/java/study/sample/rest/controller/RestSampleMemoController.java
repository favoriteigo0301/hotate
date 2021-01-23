package study.sample.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import study.sample.service.SampleMemoService;

/**
 * サンプルメモRestコントローラクラス
 *
 */

@RestController
@RequestMapping(path = "/api/memo",produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSampleMemoController {

    @Autowired
    private SampleMemoService sampleMemoService;

    @GetMapping(path = "/list")
    public void getSampleMemo(@RequestParam int id) {
        System.out.println("API呼べたよ！");
        System.out.println("パラメータ受け取り" + id);

    }


}
