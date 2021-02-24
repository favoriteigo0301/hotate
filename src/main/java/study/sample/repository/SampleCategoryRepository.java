package study.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.sample.entity.CategoryEntity;

@Repository
public interface SampleCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
