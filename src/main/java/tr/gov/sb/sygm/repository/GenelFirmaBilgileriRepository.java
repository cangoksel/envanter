package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.GenelFirmaBilgileri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GenelFirmaBilgileri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenelFirmaBilgileriRepository extends JpaRepository<GenelFirmaBilgileri,Long> {
    
}
