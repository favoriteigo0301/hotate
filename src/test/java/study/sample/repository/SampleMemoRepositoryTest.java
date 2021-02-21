package study.sample.repository;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.db.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Changes;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.sample.entity.SampleMemoEntity;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class SampleMemoRepositoryTest {

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    private static Destination destination;

    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from sample_memo"),
                insertInto("sample_memo")
                        .columns("id", "user_id", "subject", "memo", "created_at", "updated_at")
                        .values(99,1,"java","積み上げる", LocalDateTime.now(), LocalDateTime.now())
                        .values(98,2,"Junit","テストコードは奥が深い", LocalDateTime.now(), LocalDateTime.now())
                        .build());

        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    /**
     * 主キー削除確認
     */
    @Test
    void deletePrimaryKeyTest(@Autowired DataSource dataSource) {
        Changes changes = new Changes(dataSource);

        changes.setStartPointNow();

        // 削除実行
        sampleMemoRepository.deleteById(99L);

        changes.setEndPointNow();

        // 削除確認
        assertThat(changes)
                .onTable("sample_memo")
                .changeOfDeletion()
                .hasPksValues(99);
    }

    /**
     * 削除対象外データが削除されていないことを確認
     */
    @Test
    void notDeleteData(@Autowired DataSource dataSource) {
        Table table = new Table(dataSource, "sample_memo");
        assertThat(table)
                .column("id").value().isEqualTo("98")
                .column("user_id").value().isEqualTo("2")
                .column("subject").value().isEqualTo("Junit")
                .column("memo").value().isEqualTo("テストコードは奥が深い");
    }

    /**
     * 新規登録されていることを確認する
     * @param dataSource
     */
    @Test
    void registerSampleMemoTest(@Autowired DataSource dataSource) {
        // テストデータ
        SampleMemoEntity testEntity = new SampleMemoEntity();
        testEntity.setSubject("commewイベント");
        testEntity.setMemo("輪読会は良いかも");
        testEntity.setUserId(99);
        testEntity.setCreatedAt(LocalDateTime.now());
        testEntity.setUpdatedAt(LocalDateTime.now());

        Changes change = new Changes(dataSource);
        change.setStartPointNow();

        // テスト対象メソッド実行
        sampleMemoRepository.save(testEntity);
        sampleMemoRepository.flush();

       change.setEndPointNow();
       assertThat(change)
               .changeOfCreationOnTable("sample_memo")
               .isCreation()
               .rowAtEndPoint()
               .value("subject").isEqualTo("commewイベント")
               .value("memo").isEqualTo("輪読会は良いかも")
               .value("user_id").isEqualTo(99);
    }

    /**
     * 更新されていることを確認
     */
    @Test
    void updateSampleMemoTest(@Autowired DataSource dataSource) {
        // テストデータ
        SampleMemoEntity testEntity = new SampleMemoEntity();
        testEntity.setId(98L);
        testEntity.setSubject("キックボクシング");
        testEntity.setMemo("楽しかった");
        testEntity.setUserId(99);
        testEntity.setUpdatedAt(LocalDateTime.now());
        Changes change = new Changes(dataSource);

        change.setStartPointNow();
        // テスト対象メソッド実行
        sampleMemoRepository.save(testEntity);
        sampleMemoRepository.flush();
        change.setEndPointNow();

        assertThat(change)
                .changeOfModificationOnTable("sample_memo")
                .isModification()
                .hasPksValues(98L)
                .rowAtStartPoint()
                    .value("subject").isEqualTo("Junit")
                    .value("user_id").isEqualTo(2)
                    .value("memo").isEqualTo("テストコードは奥が深い")
                .rowAtEndPoint()
                    .value("subject").isEqualTo("キックボクシング")
                    .value("user_id").isEqualTo(99)
                    .value("memo").isEqualTo("楽しかった");
    }

    @Order(1)
    @Test
    void selectByPrimaryKeyTest()  {
        Optional<SampleMemoEntity> actualEntity = sampleMemoRepository.findById(99L);
        assertEquals(actualEntity.get().getId(), 99L);
        assertEquals(actualEntity.get().getSubject(), "java");
        assertEquals(actualEntity.get().getUserId(), 1);
        assertEquals(actualEntity.get().getMemo(), "積み上げる");
    }
}