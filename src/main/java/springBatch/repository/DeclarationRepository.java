package springBatch.repository;

import springBatch.entity.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
}