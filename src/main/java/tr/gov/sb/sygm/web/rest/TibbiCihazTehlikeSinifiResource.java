package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.TibbiCihazTehlikeSinifi;

import tr.gov.sb.sygm.repository.TibbiCihazTehlikeSinifiRepository;
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
 * REST controller for managing TibbiCihazTehlikeSinifi.
 */
@RestController
@RequestMapping("/api")
public class TibbiCihazTehlikeSinifiResource {

    private final Logger log = LoggerFactory.getLogger(TibbiCihazTehlikeSinifiResource.class);

    private static final String ENTITY_NAME = "tibbiCihazTehlikeSinifi";

    private final TibbiCihazTehlikeSinifiRepository tibbiCihazTehlikeSinifiRepository;

    public TibbiCihazTehlikeSinifiResource(TibbiCihazTehlikeSinifiRepository tibbiCihazTehlikeSinifiRepository) {
        this.tibbiCihazTehlikeSinifiRepository = tibbiCihazTehlikeSinifiRepository;
    }

    /**
     * POST  /tibbi-cihaz-tehlike-sinifis : Create a new tibbiCihazTehlikeSinifi.
     *
     * @param tibbiCihazTehlikeSinifi the tibbiCihazTehlikeSinifi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tibbiCihazTehlikeSinifi, or with status 400 (Bad Request) if the tibbiCihazTehlikeSinifi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tibbi-cihaz-tehlike-sinifis")
    @Timed
    public ResponseEntity<TibbiCihazTehlikeSinifi> createTibbiCihazTehlikeSinifi(@Valid @RequestBody TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi) throws URISyntaxException {
        log.debug("REST request to save TibbiCihazTehlikeSinifi : {}", tibbiCihazTehlikeSinifi);
        if (tibbiCihazTehlikeSinifi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tibbiCihazTehlikeSinifi cannot already have an ID")).body(null);
        }
        TibbiCihazTehlikeSinifi result = tibbiCihazTehlikeSinifiRepository.save(tibbiCihazTehlikeSinifi);
        return ResponseEntity.created(new URI("/api/tibbi-cihaz-tehlike-sinifis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tibbi-cihaz-tehlike-sinifis : Updates an existing tibbiCihazTehlikeSinifi.
     *
     * @param tibbiCihazTehlikeSinifi the tibbiCihazTehlikeSinifi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tibbiCihazTehlikeSinifi,
     * or with status 400 (Bad Request) if the tibbiCihazTehlikeSinifi is not valid,
     * or with status 500 (Internal Server Error) if the tibbiCihazTehlikeSinifi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tibbi-cihaz-tehlike-sinifis")
    @Timed
    public ResponseEntity<TibbiCihazTehlikeSinifi> updateTibbiCihazTehlikeSinifi(@Valid @RequestBody TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi) throws URISyntaxException {
        log.debug("REST request to update TibbiCihazTehlikeSinifi : {}", tibbiCihazTehlikeSinifi);
        if (tibbiCihazTehlikeSinifi.getId() == null) {
            return createTibbiCihazTehlikeSinifi(tibbiCihazTehlikeSinifi);
        }
        TibbiCihazTehlikeSinifi result = tibbiCihazTehlikeSinifiRepository.save(tibbiCihazTehlikeSinifi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tibbiCihazTehlikeSinifi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tibbi-cihaz-tehlike-sinifis : get all the tibbiCihazTehlikeSinifis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tibbiCihazTehlikeSinifis in body
     */
    @GetMapping("/tibbi-cihaz-tehlike-sinifis")
    @Timed
    public List<TibbiCihazTehlikeSinifi> getAllTibbiCihazTehlikeSinifis() {
        log.debug("REST request to get all TibbiCihazTehlikeSinifis");
        return tibbiCihazTehlikeSinifiRepository.findAll();
    }

    /**
     * GET  /tibbi-cihaz-tehlike-sinifis/:id : get the "id" tibbiCihazTehlikeSinifi.
     *
     * @param id the id of the tibbiCihazTehlikeSinifi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tibbiCihazTehlikeSinifi, or with status 404 (Not Found)
     */
    @GetMapping("/tibbi-cihaz-tehlike-sinifis/{id}")
    @Timed
    public ResponseEntity<TibbiCihazTehlikeSinifi> getTibbiCihazTehlikeSinifi(@PathVariable Long id) {
        log.debug("REST request to get TibbiCihazTehlikeSinifi : {}", id);
        TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tibbiCihazTehlikeSinifi));
    }

    /**
     * DELETE  /tibbi-cihaz-tehlike-sinifis/:id : delete the "id" tibbiCihazTehlikeSinifi.
     *
     * @param id the id of the tibbiCihazTehlikeSinifi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tibbi-cihaz-tehlike-sinifis/{id}")
    @Timed
    public ResponseEntity<Void> deleteTibbiCihazTehlikeSinifi(@PathVariable Long id) {
        log.debug("REST request to delete TibbiCihazTehlikeSinifi : {}", id);
        tibbiCihazTehlikeSinifiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
