package study.sample.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * カテゴリーエンティティ
 */
@Entity
@Table(name = "sample_category")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "register_count")
    private int registerCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
