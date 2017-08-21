package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Bolge;

import tr.gov.sb.sygm.repository.BolgeRepository;
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
 * REST controller for managing Bolge.
 */
@RestController
@RequestMapping("/api")
public class BolgeResource {

    private final Logger log = LoggerFactory.getLogger(BolgeResource.class);

    private static final String ENTITY_NAME = "bolge";

    private final BolgeRepository bolgeRepository;

    public BolgeResource(BolgeRepository bolgeRepository) {
        this.bolgeRepository = bolgeRepository;
    }

    /**
     * POST  /bolges : Create a new bolge.
     *
     * @param bolge the bolge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bolge, or with status 400 (Bad Request) if the bolge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bolges")
    @Timed
    public ResponseEntity<Bolge> createBolge(@Valid @RequestBody Bolge bolge) throws URISyntaxException {
        log.debug("REST request to save Bolge : {}", bolge);
        if (bolge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bolge cannot already have an ID")).body(null);
        }
        Bolge result = bolgeRepository.save(bolge);
        return ResponseEntity.created(new URI("/api/bolges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bolges : Updates an existing bolge.
     *
     * @param bolge the bolge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bolge,
     * or with status 400 (Bad Request) if the bolge is not valid,
     * or with status 500 (Internal Server Error) if the bolge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bolges")
    @Timed
    public ResponseEntity<Bolge> updateBolge(@Valid @RequestBody Bolge bolge) throws URISyntaxException {
        log.debug("REST request to update Bolge : {}", bolge);
        if (bolge.getId() == null) {
            return createBolge(bolge);
        }
        Bolge result = bolgeRepository.save(bolge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bolge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bolges : get all the bolges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bolges in body
     */
    @GetMapping("/bolges")
    @Timed
    public List<Bolge> getAllBolges() {
        log.debug("REST request to get all Bolges");
        return bolgeRepository.findAll();
    }

    /**
     * GET  /bolges/:id : get the "id" bolge.
     *
     * @param id the id of the bolge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bolge, or with status 404 (Not Found)
     */
    @GetMapping("/bolges/{id}")
    @Timed
    public ResponseEntity<Bolge> getBolge(@PathVariable Long id) {
        log.debug("REST request to get Bolge : {}", id);
        Bolge bolge = bolgeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bolge));
    }

    /**
     * DELETE  /bolges/:id : delete the "id" bolge.
     *
     * @param id the id of the bolge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bolges/{id}")
    @Timed
    public ResponseEntity<Void> deleteBolge(@PathVariable Long id) {
        log.debug("REST request to delete Bolge : {}", id);
        bolgeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
