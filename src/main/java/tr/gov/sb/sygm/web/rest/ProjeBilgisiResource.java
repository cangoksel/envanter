package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.ProjeBilgisi;

import tr.gov.sb.sygm.repository.ProjeBilgisiRepository;
import tr.gov.sb.sygm.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProjeBilgisi.
 */
@RestController
@RequestMapping("/api")
public class ProjeBilgisiResource {

    private final Logger log = LoggerFactory.getLogger(ProjeBilgisiResource.class);

    private static final String ENTITY_NAME = "projeBilgisi";

    private final ProjeBilgisiRepository projeBilgisiRepository;

    public ProjeBilgisiResource(ProjeBilgisiRepository projeBilgisiRepository) {
        this.projeBilgisiRepository = projeBilgisiRepository;
    }

    /**
     * POST  /proje-bilgisis : Create a new projeBilgisi.
     *
     * @param projeBilgisi the projeBilgisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projeBilgisi, or with status 400 (Bad Request) if the projeBilgisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proje-bilgisis")
    @Timed
    public ResponseEntity<ProjeBilgisi> createProjeBilgisi(@Valid @RequestBody ProjeBilgisi projeBilgisi) throws URISyntaxException {
        log.debug("REST request to save ProjeBilgisi : {}", projeBilgisi);
        if (projeBilgisi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projeBilgisi cannot already have an ID")).body(null);
        }
        ProjeBilgisi result = projeBilgisiRepository.save(projeBilgisi);
        return ResponseEntity.created(new URI("/api/proje-bilgisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proje-bilgisis : Updates an existing projeBilgisi.
     *
     * @param projeBilgisi the projeBilgisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projeBilgisi,
     * or with status 400 (Bad Request) if the projeBilgisi is not valid,
     * or with status 500 (Internal Server Error) if the projeBilgisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proje-bilgisis")
    @Timed
    public ResponseEntity<ProjeBilgisi> updateProjeBilgisi(@Valid @RequestBody ProjeBilgisi projeBilgisi) throws URISyntaxException {
        log.debug("REST request to update ProjeBilgisi : {}", projeBilgisi);
        if (projeBilgisi.getId() == null) {
            return createProjeBilgisi(projeBilgisi);
        }
        ProjeBilgisi result = projeBilgisiRepository.save(projeBilgisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projeBilgisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proje-bilgisis : get all the projeBilgisis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projeBilgisis in body
     */
    @GetMapping("/proje-bilgisis")
    @Timed
    public List<ProjeBilgisi> getAllProjeBilgisis() {
        log.debug("REST request to get all ProjeBilgisis");
        return projeBilgisiRepository.findAll();
    }

    /**
     * GET  /proje-bilgisis/:id : get the "id" projeBilgisi.
     *
     * @param id the id of the projeBilgisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projeBilgisi, or with status 404 (Not Found)
     */
    @GetMapping("/proje-bilgisis/{id}")
    @Timed
    public ResponseEntity<ProjeBilgisi> getProjeBilgisi(@PathVariable Long id) {
        log.debug("REST request to get ProjeBilgisi : {}", id);
        ProjeBilgisi projeBilgisi = projeBilgisiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projeBilgisi));
    }

    /**
     * DELETE  /proje-bilgisis/:id : delete the "id" projeBilgisi.
     *
     * @param id the id of the projeBilgisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proje-bilgisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjeBilgisi(@PathVariable Long id) {
        log.debug("REST request to delete ProjeBilgisi : {}", id);
        projeBilgisiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
