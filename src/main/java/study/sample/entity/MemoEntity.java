package study.sample.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sample_memo")
@Data
public class MemoEntity {
    @Id
    @GeneratedValue
    private long id;

    private String subject;

    private String memo;

    private byte image;




}
