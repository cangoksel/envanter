package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.FaaliyetAlani;

import tr.gov.sb.sygm.repository.FaaliyetAlaniRepository;
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
 * REST controller for managing FaaliyetAlani.
 */
@RestController
@RequestMapping("/api")
public class FaaliyetAlaniResource {

    private final Logger log = LoggerFactory.getLogger(FaaliyetAlaniResource.class);

    private static final String ENTITY_NAME = "faaliyetAlani";

    private final FaaliyetAlaniRepository faaliyetAlaniRepository;

    public FaaliyetAlaniResource(FaaliyetAlaniRepository faaliyetAlaniRepository) {
        this.faaliyetAlaniRepository = faaliyetAlaniRepository;
    }

    /**
     * POST  /faaliyet-alanis : Create a new faaliyetAlani.
     *
     * @param faaliyetAlani the faaliyetAlani to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faaliyetAlani, or with status 400 (Bad Request) if the faaliyetAlani has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faaliyet-alanis")
    @Timed
    public ResponseEntity<FaaliyetAlani> createFaaliyetAlani(@Valid @RequestBody FaaliyetAlani faaliyetAlani) throws URISyntaxException {
        log.debug("REST request to save FaaliyetAlani : {}", faaliyetAlani);
        if (faaliyetAlani.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new faaliyetAlani cannot already have an ID")).body(null);
        }
        FaaliyetAlani result = faaliyetAlaniRepository.save(faaliyetAlani);
        return ResponseEntity.created(new URI("/api/faaliyet-alanis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faaliyet-alanis : Updates an existing faaliyetAlani.
     *
     * @param faaliyetAlani the faaliyetAlani to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faaliyetAlani,
     * or with status 400 (Bad Request) if the faaliyetAlani is not valid,
     * or with status 500 (Internal Server Error) if the faaliyetAlani couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faaliyet-alanis")
    @Timed
    public ResponseEntity<FaaliyetAlani> updateFaaliyetAlani(@Valid @RequestBody FaaliyetAlani faaliyetAlani) throws URISyntaxException {
        log.debug("REST request to update FaaliyetAlani : {}", faaliyetAlani);
        if (faaliyetAlani.getId() == null) {
            return createFaaliyetAlani(faaliyetAlani);
        }
        FaaliyetAlani result = faaliyetAlaniRepository.save(faaliyetAlani);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faaliyetAlani.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faaliyet-alanis : get all the faaliyetAlanis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faaliyetAlanis in body
     */
    @GetMapping("/faaliyet-alanis")
    @Timed
    public List<FaaliyetAlani> getAllFaaliyetAlanis() {
        log.debug("REST request to get all FaaliyetAlanis");
        return faaliyetAlaniRepository.findAll();
    }

    /**
     * GET  /faaliyet-alanis/:id : get the "id" faaliyetAlani.
     *
     * @param id the id of the faaliyetAlani to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faaliyetAlani, or with status 404 (Not Found)
     */
    @GetMapping("/faaliyet-alanis/{id}")
    @Timed
    public ResponseEntity<FaaliyetAlani> getFaaliyetAlani(@PathVariable Long id) {
        log.debug("REST request to get FaaliyetAlani : {}", id);
        FaaliyetAlani faaliyetAlani = faaliyetAlaniRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faaliyetAlani));
    }

    /**
     * DELETE  /faaliyet-alanis/:id : delete the "id" faaliyetAlani.
     *
     * @param id the id of the faaliyetAlani to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faaliyet-alanis/{id}")
    @Timed
    public ResponseEntity<Void> deleteFaaliyetAlani(@PathVariable Long id) {
        log.debug("REST request to delete FaaliyetAlani : {}", id);
        faaliyetAlaniRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
