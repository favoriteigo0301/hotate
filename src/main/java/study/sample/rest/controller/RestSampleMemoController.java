package study.sample.rest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "getSampleMemo", notes = "パラメータで指定したIDのメモ情報を取得します")
    public SampleMemoEntity getSampleMemo(@ApiParam(value = "メモIDでメモ情報を検索します", required = true)
                                            @RequestParam long id ) {
        SampleMemoEntity entity = restSampleMemoService.getSampleMemoById(id);

        System.out.println(entity);
        return entity;
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleMemoEntity> getSampleMemoList() {
        List<SampleMemoEntity> entityList = restSampleMemoService.getSampleMemoList();
        return entityList;
    }
}