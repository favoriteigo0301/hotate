package study.sample.controller;

import  static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import  static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.sample.form.SampleMemoRequest;

/**
 * サンプルコントローラテストクラス
 * テストメソッド名はテスト元クラスのメソッド最後尾に「Test」とする
 */
@SpringBootTest
public class SampleControllerTest {
    private MockMvc mock;

    @Autowired
    SampleController testTarget;

    SampleMemoRequest request;

    @BeforeEach
    public void setUp() {
        mock = MockMvcBuilders.standaloneSetup(testTarget)
                .build();
        request = new SampleMemoRequest();
    }

    @Test
    public void sampleMemoTest() throws Exception {
        mock.perform(get("/sample/memo"))
                .andExpect(status().isOk())
                .andExpect(view().name("sample_memo"))
                .andExpect(model().attribute("request", request));
    }
}
