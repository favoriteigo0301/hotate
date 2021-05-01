package study.sample.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@ToString(exclude = {"roleEntity", "sampleMemoEntityList"})
public class UserEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    /**
     * 名前
     */
    @Column(name = "name")
    private String name;

    /**
     * パスワード
     */
    @Column(name = "password")
    private String password;

    /**
     * 作成日
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * ロール情報リスト
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
    private List<RoleEntity> roleEntity;

    /**
     * メモ情報リスト
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
    private List<SampleMemoEntity> sampleMemoEntityList;

    /**
     * 登録時は作成費と更新日を設定する
     */
    @PrePersist
    public void onPersist() {
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    /**
     * 更新時に更新日を設定する
     */
    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }
}
