package springBatch.repository;

import springBatch.entity.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
	boolean existsByMessageRefId(String msgRefId);
}