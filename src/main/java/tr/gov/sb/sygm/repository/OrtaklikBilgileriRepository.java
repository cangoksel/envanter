package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.OrtaklikBilgileri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrtaklikBilgileri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrtaklikBilgileriRepository extends JpaRepository<OrtaklikBilgileri,Long> {
    
}
