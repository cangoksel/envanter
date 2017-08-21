package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Ulke;

import tr.gov.sb.sygm.repository.UlkeRepository;
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
 * REST controller for managing Ulke.
 */
@RestController
@RequestMapping("/api")
public class UlkeResource {

    private final Logger log = LoggerFactory.getLogger(UlkeResource.class);

    private static final String ENTITY_NAME = "ulke";

    private final UlkeRepository ulkeRepository;

    public UlkeResource(UlkeRepository ulkeRepository) {
        this.ulkeRepository = ulkeRepository;
    }

    /**
     * POST  /ulkes : Create a new ulke.
     *
     * @param ulke the ulke to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ulke, or with status 400 (Bad Request) if the ulke has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ulkes")
    @Timed
    public ResponseEntity<Ulke> createUlke(@Valid @RequestBody Ulke ulke) throws URISyntaxException {
        log.debug("REST request to save Ulke : {}", ulke);
        if (ulke.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ulke cannot already have an ID")).body(null);
        }
        Ulke result = ulkeRepository.save(ulke);
        return ResponseEntity.created(new URI("/api/ulkes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ulkes : Updates an existing ulke.
     *
     * @param ulke the ulke to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ulke,
     * or with status 400 (Bad Request) if the ulke is not valid,
     * or with status 500 (Internal Server Error) if the ulke couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ulkes")
    @Timed
    public ResponseEntity<Ulke> updateUlke(@Valid @RequestBody Ulke ulke) throws URISyntaxException {
        log.debug("REST request to update Ulke : {}", ulke);
        if (ulke.getId() == null) {
            return createUlke(ulke);
        }
        Ulke result = ulkeRepository.save(ulke);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ulke.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ulkes : get all the ulkes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ulkes in body
     */
    @GetMapping("/ulkes")
    @Timed
    public List<Ulke> getAllUlkes() {
        log.debug("REST request to get all Ulkes");
        return ulkeRepository.findAll();
    }

    /**
     * GET  /ulkes/:id : get the "id" ulke.
     *
     * @param id the id of the ulke to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ulke, or with status 404 (Not Found)
     */
    @GetMapping("/ulkes/{id}")
    @Timed
    public ResponseEntity<Ulke> getUlke(@PathVariable Long id) {
        log.debug("REST request to get Ulke : {}", id);
        Ulke ulke = ulkeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ulke));
    }

    /**
     * DELETE  /ulkes/:id : delete the "id" ulke.
     *
     * @param id the id of the ulke to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ulkes/{id}")
    @Timed
    public ResponseEntity<Void> deleteUlke(@PathVariable Long id) {
        log.debug("REST request to delete Ulke : {}", id);
        ulkeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
