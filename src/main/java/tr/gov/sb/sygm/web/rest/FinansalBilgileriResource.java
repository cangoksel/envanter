package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.FinansalBilgileri;

import tr.gov.sb.sygm.repository.FinansalBilgileriRepository;
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
 * REST controller for managing FinansalBilgileri.
 */
@RestController
@RequestMapping("/api")
public class FinansalBilgileriResource {

    private final Logger log = LoggerFactory.getLogger(FinansalBilgileriResource.class);

    private static final String ENTITY_NAME = "finansalBilgileri";

    private final FinansalBilgileriRepository finansalBilgileriRepository;

    public FinansalBilgileriResource(FinansalBilgileriRepository finansalBilgileriRepository) {
        this.finansalBilgileriRepository = finansalBilgileriRepository;
    }

    /**
     * POST  /finansal-bilgileris : Create a new finansalBilgileri.
     *
     * @param finansalBilgileri the finansalBilgileri to create
     * @return the ResponseEntity with status 201 (Created) and with body the new finansalBilgileri, or with status 400 (Bad Request) if the finansalBilgileri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finansal-bilgileris")
    @Timed
    public ResponseEntity<FinansalBilgileri> createFinansalBilgileri(@Valid @RequestBody FinansalBilgileri finansalBilgileri) throws URISyntaxException {
        log.debug("REST request to save FinansalBilgileri : {}", finansalBilgileri);
        if (finansalBilgileri.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new finansalBilgileri cannot already have an ID")).body(null);
        }
        FinansalBilgileri result = finansalBilgileriRepository.save(finansalBilgileri);
        return ResponseEntity.created(new URI("/api/finansal-bilgileris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finansal-bilgileris : Updates an existing finansalBilgileri.
     *
     * @param finansalBilgileri the finansalBilgileri to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated finansalBilgileri,
     * or with status 400 (Bad Request) if the finansalBilgileri is not valid,
     * or with status 500 (Internal Server Error) if the finansalBilgileri couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finansal-bilgileris")
    @Timed
    public ResponseEntity<FinansalBilgileri> updateFinansalBilgileri(@Valid @RequestBody FinansalBilgileri finansalBilgileri) throws URISyntaxException {
        log.debug("REST request to update FinansalBilgileri : {}", finansalBilgileri);
        if (finansalBilgileri.getId() == null) {
            return createFinansalBilgileri(finansalBilgileri);
        }
        FinansalBilgileri result = finansalBilgileriRepository.save(finansalBilgileri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, finansalBilgileri.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finansal-bilgileris : get all the finansalBilgileris.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of finansalBilgileris in body
     */
    @GetMapping("/finansal-bilgileris")
    @Timed
    public List<FinansalBilgileri> getAllFinansalBilgileris() {
        log.debug("REST request to get all FinansalBilgileris");
        return finansalBilgileriRepository.findAll();
    }

    /**
     * GET  /finansal-bilgileris/:id : get the "id" finansalBilgileri.
     *
     * @param id the id of the finansalBilgileri to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the finansalBilgileri, or with status 404 (Not Found)
     */
    @GetMapping("/finansal-bilgileris/{id}")
    @Timed
    public ResponseEntity<FinansalBilgileri> getFinansalBilgileri(@PathVariable Long id) {
        log.debug("REST request to get FinansalBilgileri : {}", id);
        FinansalBilgileri finansalBilgileri = finansalBilgileriRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finansalBilgileri));
    }

    /**
     * DELETE  /finansal-bilgileris/:id : delete the "id" finansalBilgileri.
     *
     * @param id the id of the finansalBilgileri to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finansal-bilgileris/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinansalBilgileri(@PathVariable Long id) {
        log.debug("REST request to delete FinansalBilgileri : {}", id);
        finansalBilgileriRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
