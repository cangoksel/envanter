package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.CalisanTipi;

import tr.gov.sb.sygm.repository.CalisanTipiRepository;
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
 * REST controller for managing CalisanTipi.
 */
@RestController
@RequestMapping("/api")
public class CalisanTipiResource {

    private final Logger log = LoggerFactory.getLogger(CalisanTipiResource.class);

    private static final String ENTITY_NAME = "calisanTipi";

    private final CalisanTipiRepository calisanTipiRepository;

    public CalisanTipiResource(CalisanTipiRepository calisanTipiRepository) {
        this.calisanTipiRepository = calisanTipiRepository;
    }

    /**
     * POST  /calisan-tipis : Create a new calisanTipi.
     *
     * @param calisanTipi the calisanTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calisanTipi, or with status 400 (Bad Request) if the calisanTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calisan-tipis")
    @Timed
    public ResponseEntity<CalisanTipi> createCalisanTipi(@Valid @RequestBody CalisanTipi calisanTipi) throws URISyntaxException {
        log.debug("REST request to save CalisanTipi : {}", calisanTipi);
        if (calisanTipi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new calisanTipi cannot already have an ID")).body(null);
        }
        CalisanTipi result = calisanTipiRepository.save(calisanTipi);
        return ResponseEntity.created(new URI("/api/calisan-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calisan-tipis : Updates an existing calisanTipi.
     *
     * @param calisanTipi the calisanTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calisanTipi,
     * or with status 400 (Bad Request) if the calisanTipi is not valid,
     * or with status 500 (Internal Server Error) if the calisanTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calisan-tipis")
    @Timed
    public ResponseEntity<CalisanTipi> updateCalisanTipi(@Valid @RequestBody CalisanTipi calisanTipi) throws URISyntaxException {
        log.debug("REST request to update CalisanTipi : {}", calisanTipi);
        if (calisanTipi.getId() == null) {
            return createCalisanTipi(calisanTipi);
        }
        CalisanTipi result = calisanTipiRepository.save(calisanTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calisanTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calisan-tipis : get all the calisanTipis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calisanTipis in body
     */
    @GetMapping("/calisan-tipis")
    @Timed
    public List<CalisanTipi> getAllCalisanTipis() {
        log.debug("REST request to get all CalisanTipis");
        return calisanTipiRepository.findAll();
    }

    /**
     * GET  /calisan-tipis/:id : get the "id" calisanTipi.
     *
     * @param id the id of the calisanTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calisanTipi, or with status 404 (Not Found)
     */
    @GetMapping("/calisan-tipis/{id}")
    @Timed
    public ResponseEntity<CalisanTipi> getCalisanTipi(@PathVariable Long id) {
        log.debug("REST request to get CalisanTipi : {}", id);
        CalisanTipi calisanTipi = calisanTipiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(calisanTipi));
    }

    /**
     * DELETE  /calisan-tipis/:id : delete the "id" calisanTipi.
     *
     * @param id the id of the calisanTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calisan-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalisanTipi(@PathVariable Long id) {
        log.debug("REST request to delete CalisanTipi : {}", id);
        calisanTipiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
