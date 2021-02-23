package study.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.sample.entity.SampleMemoEntity;

@Repository
public interface SampleMemoRepository extends JpaRepository<SampleMemoEntity, Long> {
    Page<SampleMemoEntity> findAll(Pageable pageable);

}
