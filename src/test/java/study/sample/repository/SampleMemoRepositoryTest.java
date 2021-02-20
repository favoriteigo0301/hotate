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
                        .columns("id", "user_id", "subject", "memo")
                        .values(99,1,"java","積み上げる")
                        .values(98,2,"Junit","テストコードは奥が深い")
                        .build());

        DbSetup dbSetup = new DbSetup(destination, operation);
        dbSetup.launch();
    }

    /**
     * 主キー削除確認
     */
    @Test
    public void deletePrimaryKeyTest(@Autowired DataSource dataSource) {
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
    public void notDeleteData(@Autowired DataSource dataSource) {
        Table table = new Table(dataSource, "sample_memo");
        assertThat(table)
                .column("id").value().isEqualTo("98")
                .column("user_id").value().isEqualTo("2")
                .column("subject").value().isEqualTo("Junit")
                .column("memo").value().isEqualTo("テストコードは奥が深い");
    }
}