package study.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.sample.entity.Memo;

@Repository
public interface SampleMemo extends JpaRepository<Memo, Long> {


}
