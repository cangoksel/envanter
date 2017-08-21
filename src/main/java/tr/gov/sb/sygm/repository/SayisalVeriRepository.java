package tr.gov.sb.sygm.repository;

import tr.gov.sb.sygm.domain.SayisalVeri;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SayisalVeri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SayisalVeriRepository extends JpaRepository<SayisalVeri,Long> {
    
}
