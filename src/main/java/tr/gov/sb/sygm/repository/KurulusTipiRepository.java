package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.KurulusTipi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KurulusTipi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KurulusTipiRepository extends JpaRepository<KurulusTipi,Long> {
    
}
