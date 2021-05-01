package study.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.sample.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
