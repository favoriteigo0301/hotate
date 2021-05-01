package study.sample.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static com.ninja_squad.dbsetup.Operations.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private static Destination destination;

    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from role"),
                insertInto("role")
                        .columns("id", "user_id", "name", "created_at", "updated_at")
                        .values(1, 99,"メモアプリ",  LocalDateTime.now(), LocalDateTime.now())
                        .values(2, 99,"commewapi", LocalDateTime.now(), LocalDateTime.now())
                        .values(3, 99,"ホームページ",  LocalDateTime.now(), LocalDateTime.now())
                        .build()
        );

        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    @Test
    void test() {
        assertEquals(true, true);
    }

}
