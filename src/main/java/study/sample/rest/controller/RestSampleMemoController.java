package study.sample.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import study.sample.entity.SampleMemoEntity;
import study.sample.rest.service.RestSampleMemoService;

/**
 * サンプルメモRestコントローラクラス
 *
 */

@RestController
@RequestMapping(path = "/api/memo",produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSampleMemoController {

    @Autowired
    private RestSampleMemoService restSampleMemoService;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleMemoEntity getSampleMemo(@RequestParam long id) {
        SampleMemoEntity entity = restSampleMemoService.getSampleMemoById(id);
        return  entity;
    }


}
