package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.FaaliyetKodu;

import tr.gov.sb.sygm.repository.FaaliyetKoduRepository;
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
 * REST controller for managing FaaliyetKodu.
 */
@RestController
@RequestMapping("/api")
public class FaaliyetKoduResource {

    private final Logger log = LoggerFactory.getLogger(FaaliyetKoduResource.class);

    private static final String ENTITY_NAME = "faaliyetKodu";

    private final FaaliyetKoduRepository faaliyetKoduRepository;

    public FaaliyetKoduResource(FaaliyetKoduRepository faaliyetKoduRepository) {
        this.faaliyetKoduRepository = faaliyetKoduRepository;
    }

    /**
     * POST  /faaliyet-kodus : Create a new faaliyetKodu.
     *
     * @param faaliyetKodu the faaliyetKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faaliyetKodu, or with status 400 (Bad Request) if the faaliyetKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faaliyet-kodus")
    @Timed
    public ResponseEntity<FaaliyetKodu> createFaaliyetKodu(@Valid @RequestBody FaaliyetKodu faaliyetKodu) throws URISyntaxException {
        log.debug("REST request to save FaaliyetKodu : {}", faaliyetKodu);
        if (faaliyetKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new faaliyetKodu cannot already have an ID")).body(null);
        }
        FaaliyetKodu result = faaliyetKoduRepository.save(faaliyetKodu);
        return ResponseEntity.created(new URI("/api/faaliyet-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faaliyet-kodus : Updates an existing faaliyetKodu.
     *
     * @param faaliyetKodu the faaliyetKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faaliyetKodu,
     * or with status 400 (Bad Request) if the faaliyetKodu is not valid,
     * or with status 500 (Internal Server Error) if the faaliyetKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faaliyet-kodus")
    @Timed
    public ResponseEntity<FaaliyetKodu> updateFaaliyetKodu(@Valid @RequestBody FaaliyetKodu faaliyetKodu) throws URISyntaxException {
        log.debug("REST request to update FaaliyetKodu : {}", faaliyetKodu);
        if (faaliyetKodu.getId() == null) {
            return createFaaliyetKodu(faaliyetKodu);
        }
        FaaliyetKodu result = faaliyetKoduRepository.save(faaliyetKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faaliyetKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faaliyet-kodus : get all the faaliyetKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faaliyetKodus in body
     */
    @GetMapping("/faaliyet-kodus")
    @Timed
    public List<FaaliyetKodu> getAllFaaliyetKodus() {
        log.debug("REST request to get all FaaliyetKodus");
        return faaliyetKoduRepository.findAll();
    }

    /**
     * GET  /faaliyet-kodus/:id : get the "id" faaliyetKodu.
     *
     * @param id the id of the faaliyetKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faaliyetKodu, or with status 404 (Not Found)
     */
    @GetMapping("/faaliyet-kodus/{id}")
    @Timed
    public ResponseEntity<FaaliyetKodu> getFaaliyetKodu(@PathVariable Long id) {
        log.debug("REST request to get FaaliyetKodu : {}", id);
        FaaliyetKodu faaliyetKodu = faaliyetKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faaliyetKodu));
    }

    /**
     * DELETE  /faaliyet-kodus/:id : delete the "id" faaliyetKodu.
     *
     * @param id the id of the faaliyetKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faaliyet-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteFaaliyetKodu(@PathVariable Long id) {
        log.debug("REST request to delete FaaliyetKodu : {}", id);
        faaliyetKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
