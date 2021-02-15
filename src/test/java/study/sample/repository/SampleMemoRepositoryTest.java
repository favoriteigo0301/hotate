package study.sample.repository;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.db.api.Assertions.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Changes;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class SampleMemoRepositoryTest {

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    private static Destination destination;

    @BeforeAll
    public static void setUp(@Autowired DataSource dataSource) {
        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from sample_memo"),
                insertInto("sample_memo")
                        .columns("id", "user_name", "subject", "memo")
                        .values(99,"イッシー","テスト","疲れた")
                        .values(98,"イッシー2","テスト2","疲れた")
                        .build());

        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    /**
     * サンプルメモテーブルの全削除確認
     */
    @Test
    public void deleteTest(@Autowired DataSource dataSource) {
        Changes changes = new Changes(dataSource);

        changes.setStartPointNow();

        // サンプルメモテーブル
        sampleMemoRepository.deleteById(99L);

        changes.setEndPointNow();

        // 削除確認
        assertThat(changes)
                .onTable("sample_memo")
                .changeOfDeletion()
                .hasPksValues(99);
    }

    /**
     * サンプルメモテーブルの全削除確認
     */
    @Test
    public void tableDataTest(@Autowired DataSource dataSource) {
        Table table = new Table(dataSource, "sample_memo");
        assertThat(table)
                .column("id").value().isEqualTo("98")
                .column("user_name").value().isEqualTo("イッシー2")
                .column("subject").value().isEqualTo("テスト2")
                .column("memo").value().isEqualTo("疲れた");
    }
}