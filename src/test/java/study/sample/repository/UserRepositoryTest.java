package study.sample.repository;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.sample.entity.UserEntity;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static Destination destination;

    /**
     * テスト実行前にテストデータを登録する
     * @param dataSource
     */
    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from users"),
            insertInto("users")
                .columns("id", "name","password","created_at", "updated_at")
                .values(99,"イッシー","sakura0301", createdAt, updatedAt)
                .values(100,"不吉な人", "sakura0301",createdAt, updatedAt)
                .build());
        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    /**
     * 主キー検索で存在することを確認
     */
    @Test
    void existUserPrimaryKeyTest() {
        boolean expected = true;
        boolean actual = userRepository.existsById(99L);

        assertEquals(expected, actual);
    }

    @Test
    void userPrimaryKeyTest() {
        Optional<UserEntity> entity = userRepository.findById(99L);
        assertEquals(99L,entity.get().getId());
        System.out.println("ロール件数" + entity.get().getRoleEntity().size());
    }
}
