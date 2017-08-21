package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.UrunGrubu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UrunGrubu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UrunGrubuRepository extends JpaRepository<UrunGrubu,Long> {
    
}
