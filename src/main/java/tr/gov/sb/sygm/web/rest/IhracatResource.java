package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Ihracat;

import tr.gov.sb.sygm.repository.IhracatRepository;
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
 * REST controller for managing Ihracat.
 */
@RestController
@RequestMapping("/api")
public class IhracatResource {

    private final Logger log = LoggerFactory.getLogger(IhracatResource.class);

    private static final String ENTITY_NAME = "ihracat";

    private final IhracatRepository ihracatRepository;

    public IhracatResource(IhracatRepository ihracatRepository) {
        this.ihracatRepository = ihracatRepository;
    }

    /**
     * POST  /ihracats : Create a new ihracat.
     *
     * @param ihracat the ihracat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ihracat, or with status 400 (Bad Request) if the ihracat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ihracats")
    @Timed
    public ResponseEntity<Ihracat> createIhracat(@Valid @RequestBody Ihracat ihracat) throws URISyntaxException {
        log.debug("REST request to save Ihracat : {}", ihracat);
        if (ihracat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ihracat cannot already have an ID")).body(null);
        }
        Ihracat result = ihracatRepository.save(ihracat);
        return ResponseEntity.created(new URI("/api/ihracats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ihracats : Updates an existing ihracat.
     *
     * @param ihracat the ihracat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ihracat,
     * or with status 400 (Bad Request) if the ihracat is not valid,
     * or with status 500 (Internal Server Error) if the ihracat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ihracats")
    @Timed
    public ResponseEntity<Ihracat> updateIhracat(@Valid @RequestBody Ihracat ihracat) throws URISyntaxException {
        log.debug("REST request to update Ihracat : {}", ihracat);
        if (ihracat.getId() == null) {
            return createIhracat(ihracat);
        }
        Ihracat result = ihracatRepository.save(ihracat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ihracat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ihracats : get all the ihracats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ihracats in body
     */
    @GetMapping("/ihracats")
    @Timed
    public List<Ihracat> getAllIhracats() {
        log.debug("REST request to get all Ihracats");
        return ihracatRepository.findAll();
    }

    /**
     * GET  /ihracats/:id : get the "id" ihracat.
     *
     * @param id the id of the ihracat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ihracat, or with status 404 (Not Found)
     */
    @GetMapping("/ihracats/{id}")
    @Timed
    public ResponseEntity<Ihracat> getIhracat(@PathVariable Long id) {
        log.debug("REST request to get Ihracat : {}", id);
        Ihracat ihracat = ihracatRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ihracat));
    }

    /**
     * DELETE  /ihracats/:id : delete the "id" ihracat.
     *
     * @param id the id of the ihracat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ihracats/{id}")
    @Timed
    public ResponseEntity<Void> deleteIhracat(@PathVariable Long id) {
        log.debug("REST request to delete Ihracat : {}", id);
        ihracatRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
