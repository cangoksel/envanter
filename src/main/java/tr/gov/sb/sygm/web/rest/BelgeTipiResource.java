package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.BelgeTipi;

import tr.gov.sb.sygm.repository.BelgeTipiRepository;
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
 * REST controller for managing BelgeTipi.
 */
@RestController
@RequestMapping("/api")
public class BelgeTipiResource {

    private final Logger log = LoggerFactory.getLogger(BelgeTipiResource.class);

    private static final String ENTITY_NAME = "belgeTipi";

    private final BelgeTipiRepository belgeTipiRepository;

    public BelgeTipiResource(BelgeTipiRepository belgeTipiRepository) {
        this.belgeTipiRepository = belgeTipiRepository;
    }

    /**
     * POST  /belge-tipis : Create a new belgeTipi.
     *
     * @param belgeTipi the belgeTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new belgeTipi, or with status 400 (Bad Request) if the belgeTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/belge-tipis")
    @Timed
    public ResponseEntity<BelgeTipi> createBelgeTipi(@Valid @RequestBody BelgeTipi belgeTipi) throws URISyntaxException {
        log.debug("REST request to save BelgeTipi : {}", belgeTipi);
        if (belgeTipi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new belgeTipi cannot already have an ID")).body(null);
        }
        BelgeTipi result = belgeTipiRepository.save(belgeTipi);
        return ResponseEntity.created(new URI("/api/belge-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /belge-tipis : Updates an existing belgeTipi.
     *
     * @param belgeTipi the belgeTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated belgeTipi,
     * or with status 400 (Bad Request) if the belgeTipi is not valid,
     * or with status 500 (Internal Server Error) if the belgeTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/belge-tipis")
    @Timed
    public ResponseEntity<BelgeTipi> updateBelgeTipi(@Valid @RequestBody BelgeTipi belgeTipi) throws URISyntaxException {
        log.debug("REST request to update BelgeTipi : {}", belgeTipi);
        if (belgeTipi.getId() == null) {
            return createBelgeTipi(belgeTipi);
        }
        BelgeTipi result = belgeTipiRepository.save(belgeTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, belgeTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /belge-tipis : get all the belgeTipis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of belgeTipis in body
     */
    @GetMapping("/belge-tipis")
    @Timed
    public List<BelgeTipi> getAllBelgeTipis() {
        log.debug("REST request to get all BelgeTipis");
        return belgeTipiRepository.findAll();
    }

    /**
     * GET  /belge-tipis/:id : get the "id" belgeTipi.
     *
     * @param id the id of the belgeTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the belgeTipi, or with status 404 (Not Found)
     */
    @GetMapping("/belge-tipis/{id}")
    @Timed
    public ResponseEntity<BelgeTipi> getBelgeTipi(@PathVariable Long id) {
        log.debug("REST request to get BelgeTipi : {}", id);
        BelgeTipi belgeTipi = belgeTipiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(belgeTipi));
    }

    /**
     * DELETE  /belge-tipis/:id : delete the "id" belgeTipi.
     *
     * @param id the id of the belgeTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/belge-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteBelgeTipi(@PathVariable Long id) {
        log.debug("REST request to delete BelgeTipi : {}", id);
        belgeTipiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
