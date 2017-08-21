package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Belge;

import tr.gov.sb.sygm.repository.BelgeRepository;
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
 * REST controller for managing Belge.
 */
@RestController
@RequestMapping("/api")
public class BelgeResource {

    private final Logger log = LoggerFactory.getLogger(BelgeResource.class);

    private static final String ENTITY_NAME = "belge";

    private final BelgeRepository belgeRepository;

    public BelgeResource(BelgeRepository belgeRepository) {
        this.belgeRepository = belgeRepository;
    }

    /**
     * POST  /belges : Create a new belge.
     *
     * @param belge the belge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new belge, or with status 400 (Bad Request) if the belge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/belges")
    @Timed
    public ResponseEntity<Belge> createBelge(@Valid @RequestBody Belge belge) throws URISyntaxException {
        log.debug("REST request to save Belge : {}", belge);
        if (belge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new belge cannot already have an ID")).body(null);
        }
        Belge result = belgeRepository.save(belge);
        return ResponseEntity.created(new URI("/api/belges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /belges : Updates an existing belge.
     *
     * @param belge the belge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated belge,
     * or with status 400 (Bad Request) if the belge is not valid,
     * or with status 500 (Internal Server Error) if the belge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/belges")
    @Timed
    public ResponseEntity<Belge> updateBelge(@Valid @RequestBody Belge belge) throws URISyntaxException {
        log.debug("REST request to update Belge : {}", belge);
        if (belge.getId() == null) {
            return createBelge(belge);
        }
        Belge result = belgeRepository.save(belge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, belge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /belges : get all the belges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of belges in body
     */
    @GetMapping("/belges")
    @Timed
    public List<Belge> getAllBelges() {
        log.debug("REST request to get all Belges");
        return belgeRepository.findAll();
    }

    /**
     * GET  /belges/:id : get the "id" belge.
     *
     * @param id the id of the belge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the belge, or with status 404 (Not Found)
     */
    @GetMapping("/belges/{id}")
    @Timed
    public ResponseEntity<Belge> getBelge(@PathVariable Long id) {
        log.debug("REST request to get Belge : {}", id);
        Belge belge = belgeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(belge));
    }

    /**
     * DELETE  /belges/:id : delete the "id" belge.
     *
     * @param id the id of the belge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/belges/{id}")
    @Timed
    public ResponseEntity<Void> deleteBelge(@PathVariable Long id) {
        log.debug("REST request to delete Belge : {}", id);
        belgeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
