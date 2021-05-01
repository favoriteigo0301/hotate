package study.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.sample.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByName(String name);

    @Query("select distinct u from UserEntity u where (:name is null or u.name = :name) ")
    List<UserEntity> test(@Param("name") String name);

    @Query("select distinct u from UserEntity u where (:id is null or u.id = :id) ")
    List<UserEntity> test2(@Param("id") Long id);

}
