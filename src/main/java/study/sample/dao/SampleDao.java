package study.sample.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SampleDao {

    @Autowired
    JdbcTemplate mysql;


    public int count() {
       int count =  mysql.queryForObject("select count(*) from sample_memo",Integer.class);
        return count;
    }

}
