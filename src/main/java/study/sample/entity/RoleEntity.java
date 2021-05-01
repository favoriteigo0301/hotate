package study.sample.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {

    /**
     * ロールID
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

}
