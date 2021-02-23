package study.sample.repository;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * サンプルメモページネーションクラス
 */
public class SamplePageable extends PageRequest {

    protected SamplePageable(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
