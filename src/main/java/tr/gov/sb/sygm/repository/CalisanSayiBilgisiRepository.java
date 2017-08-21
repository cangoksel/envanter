package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.CalisanSayiBilgisi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CalisanSayiBilgisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalisanSayiBilgisiRepository extends JpaRepository<CalisanSayiBilgisi,Long> {
    
}
