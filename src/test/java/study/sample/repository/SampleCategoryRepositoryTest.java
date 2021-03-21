package study.sample.repository;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.db.api.Assertions.*;


import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Changes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.sample.entity.CategoryEntity;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@SpringBootTest
class SampleCategoryRepositoryTest {

    @Autowired
    private SampleCategoryRepository sampleCategoryRepository;

    private static Destination destination;

    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from sample_category"),
                insertInto("sample_category")
                    .columns("id", "memo_id", "name", "created_at", "updated_at")
                        .values(99, 1,"Java",  LocalDateTime.now(), LocalDateTime.now())
                        .values(98, 2,"PHP", LocalDateTime.now(), LocalDateTime.now())
                        .values(97, 3,"Java",  LocalDateTime.now(), LocalDateTime.now())
                        .build()
        );

        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    @Test
    void createTest(@Autowired DataSource dataSource) {
        CategoryEntity testEntity = new CategoryEntity();
        testEntity.setName("SQL");
        testEntity.setMemoId(1L);
        testEntity.setCreatedAt(LocalDateTime.now());
        testEntity.setUpdatedAt(LocalDateTime.now());

        Changes changes = new Changes(dataSource);
        changes.setStartPointNow();

        sampleCategoryRepository.save(testEntity);
        sampleCategoryRepository.flush();

        changes.setEndPointNow();
        assertThat(changes)
                .changeOfCreationOnTable("sample_category")
                .isCreation()
                .rowAtEndPoint()
                .value("name").isEqualTo("SQL");
    }
}
