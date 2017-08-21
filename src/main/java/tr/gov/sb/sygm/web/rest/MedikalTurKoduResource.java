package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.MedikalTurKodu;

import tr.gov.sb.sygm.repository.MedikalTurKoduRepository;
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
 * REST controller for managing MedikalTurKodu.
 */
@RestController
@RequestMapping("/api")
public class MedikalTurKoduResource {

    private final Logger log = LoggerFactory.getLogger(MedikalTurKoduResource.class);

    private static final String ENTITY_NAME = "medikalTurKodu";

    private final MedikalTurKoduRepository medikalTurKoduRepository;

    public MedikalTurKoduResource(MedikalTurKoduRepository medikalTurKoduRepository) {
        this.medikalTurKoduRepository = medikalTurKoduRepository;
    }

    /**
     * POST  /medikal-tur-kodus : Create a new medikalTurKodu.
     *
     * @param medikalTurKodu the medikalTurKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medikalTurKodu, or with status 400 (Bad Request) if the medikalTurKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medikal-tur-kodus")
    @Timed
    public ResponseEntity<MedikalTurKodu> createMedikalTurKodu(@Valid @RequestBody MedikalTurKodu medikalTurKodu) throws URISyntaxException {
        log.debug("REST request to save MedikalTurKodu : {}", medikalTurKodu);
        if (medikalTurKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medikalTurKodu cannot already have an ID")).body(null);
        }
        MedikalTurKodu result = medikalTurKoduRepository.save(medikalTurKodu);
        return ResponseEntity.created(new URI("/api/medikal-tur-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medikal-tur-kodus : Updates an existing medikalTurKodu.
     *
     * @param medikalTurKodu the medikalTurKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medikalTurKodu,
     * or with status 400 (Bad Request) if the medikalTurKodu is not valid,
     * or with status 500 (Internal Server Error) if the medikalTurKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medikal-tur-kodus")
    @Timed
    public ResponseEntity<MedikalTurKodu> updateMedikalTurKodu(@Valid @RequestBody MedikalTurKodu medikalTurKodu) throws URISyntaxException {
        log.debug("REST request to update MedikalTurKodu : {}", medikalTurKodu);
        if (medikalTurKodu.getId() == null) {
            return createMedikalTurKodu(medikalTurKodu);
        }
        MedikalTurKodu result = medikalTurKoduRepository.save(medikalTurKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medikalTurKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medikal-tur-kodus : get all the medikalTurKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medikalTurKodus in body
     */
    @GetMapping("/medikal-tur-kodus")
    @Timed
    public List<MedikalTurKodu> getAllMedikalTurKodus() {
        log.debug("REST request to get all MedikalTurKodus");
        return medikalTurKoduRepository.findAll();
    }

    /**
     * GET  /medikal-tur-kodus/:id : get the "id" medikalTurKodu.
     *
     * @param id the id of the medikalTurKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medikalTurKodu, or with status 404 (Not Found)
     */
    @GetMapping("/medikal-tur-kodus/{id}")
    @Timed
    public ResponseEntity<MedikalTurKodu> getMedikalTurKodu(@PathVariable Long id) {
        log.debug("REST request to get MedikalTurKodu : {}", id);
        MedikalTurKodu medikalTurKodu = medikalTurKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medikalTurKodu));
    }

    /**
     * DELETE  /medikal-tur-kodus/:id : delete the "id" medikalTurKodu.
     *
     * @param id the id of the medikalTurKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medikal-tur-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedikalTurKodu(@PathVariable Long id) {
        log.debug("REST request to delete MedikalTurKodu : {}", id);
        medikalTurKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
