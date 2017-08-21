package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.Adres;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Adres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresRepository extends JpaRepository<Adres,Long> {
    
}
