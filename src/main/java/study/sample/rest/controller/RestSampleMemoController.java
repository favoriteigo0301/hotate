package study.sample.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import study.sample.entity.SampleMemoEntity;
import study.sample.rest.service.RestSampleMemoService;

import java.util.List;

/**
 * サンプルメモRestコントローラクラス
 */

@RestController
@RequestMapping(path = "/api/memo",produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSampleMemoController {

    @Autowired
    private RestSampleMemoService restSampleMemoService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "サンプルメモ情報を参照します", notes = "パラメータで指定したIDのメモ情報を取得します")
    public SampleMemoEntity getSampleMemo(@RequestParam long id) {
        SampleMemoEntity entity = restSampleMemoService.getSampleMemoById(id);
        return entity;
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleMemoEntity> getSampleMemoList() {
        List<SampleMemoEntity> entityList = restSampleMemoService.getSampleMemoList();
        return entityList;
    }
}