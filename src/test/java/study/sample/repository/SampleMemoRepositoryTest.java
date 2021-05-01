package study.sample.repository;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.db.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Changes;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.sample.config.SampleConfiguration;
import study.sample.entity.SampleMemoEntity;
import study.sample.entity.UserEntity;

import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * メモリポジトリテストクラス
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SampleMemoRepositoryTest {

    @Autowired
    private SampleMemoRepository sampleMemoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SampleConfiguration sampleConfiguration;

    private static Destination destination;

    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        destination = new DataSourceDestination(dataSource);
        Operation operation = sequenceOf(
                sql("delete from sample_memo"),
                insertInto("sample_memo")
                        .columns("id", "user_id","subject", "memo", "created_at", "updated_at")
                        .values(99,1,"java","積み上げる", LocalDateTime.now(), LocalDateTime.now())
                        .values(98,2,"Junit","テストコードは奥が深い", LocalDateTime.now(), LocalDateTime.now())
                        .values(9999,2,"SpringBoot","ページネーション機能作成中", LocalDateTime.now(), LocalDateTime.now())
                        .values(8888,2,"Junit","パラメータ化テスト実施", LocalDateTime.now(), LocalDateTime.now())
                        .values(7777,2,"pagenation","１ページ2件で実施", LocalDateTime.now(), LocalDateTime.now())
                        .values(6666,2,"SpringBoot","頑張って学習する", LocalDateTime.now(), LocalDateTime.now())
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
     * 主キー削除エラー確認
     */
    @Test
    void deletePrimaryKeyErrorTest(@Autowired DataSource dataSource) {
        Changes changes = new Changes(dataSource);

        changes.setStartPointNow();

        // 削除実行
        assertThrows(InvalidDataAccessApiUsageException.class, () -> sampleMemoRepository.deleteById(null));
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
               .value("user_id").isEqualTo(99)
               .value("user_id").isNotNull();

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

    @Test
    void test() {
        Specification<UserEntity> spec = (root,criteriaQuery,criteriaBuilder)->{
            Root<SampleMemoEntity> joinEntity = criteriaQuery.from(SampleMemoEntity.class);
            criteriaQuery.distinct(true);
            Predicate p1 = criteriaBuilder.equal(joinEntity.get("userId"), root.get("id"));
            Predicate p2 = criteriaBuilder.equal(root.get("id"), 1L);
            return criteriaBuilder.and(p1, p2);
        };
        userRepository.findAll(spec);
        assertEquals(true, true);

    }

    /**
     * 主キー検索できることを確認する
     */
    @Order(3)
    @Test
    void selectByPrimaryKeyTest()  {
        Optional<SampleMemoEntity> actualEntity = sampleMemoRepository.findById(99L);
        assertEquals(actualEntity.get().getId(), 99L);
        assertEquals(actualEntity.get().getSubject(), "java");
        assertEquals(actualEntity.get().getUserId(), 1);
        assertEquals(actualEntity.get().getMemo(), "積み上げる");
    }

    /**
     * 全件取得が降順であることを確認する
     */
    @Order(2)
    @Test
    void selectListTest() {
        List<SampleMemoEntity> actualEntityList = sampleMemoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertEquals(actualEntityList.size(), 6);
        assertEquals(actualEntityList.get(0).getId(), 9999L);
    }

    /**
     * ページネーションを確認する
     */
    @Order(1)
    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    void selectPageNationTest(int page) {
        // ソート順はidの降順
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        SamplePageable samplePageable = new SamplePageable(page,sampleConfiguration.getMaxPageSize(), sort);

        // テスト対象メソッド実行
        Page<SampleMemoEntity> sampleMemoEntities = sampleMemoRepository.findAll(samplePageable);
        System.out.println(page +"ページ毎のid"+sampleMemoEntities.getContent().get(0).getId());

        // ページ毎のデータを確認
        if (page == 0) {
            assertEquals(sampleMemoEntities.getContent().get(0).getMemo(), "ページネーション機能作成中");
            assertEquals(sampleMemoEntities.getContent().get(1).getMemo(), "パラメータ化テスト実施");
        } else if (page == 1) {
            assertEquals(sampleMemoEntities.getContent().get(0).getMemo(), "１ページ2件で実施");
            assertEquals(sampleMemoEntities.getContent().get(1).getMemo(), "頑張って学習する");
        } else {
            assertEquals(sampleMemoEntities.getContent().get(0).getMemo(), "積み上げる");
            assertEquals(sampleMemoEntities.getContent().get(1).getMemo(), "テストコードは奥が深い");
        }

        assertEquals(sampleMemoEntities.getTotalPages(), 3);
        assertEquals(sampleConfiguration.getMaxPageSize(), 2);
    }
}