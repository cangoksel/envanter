package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.MkysKodu;

import tr.gov.sb.sygm.repository.MkysKoduRepository;
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
 * REST controller for managing MkysKodu.
 */
@RestController
@RequestMapping("/api")
public class MkysKoduResource {

    private final Logger log = LoggerFactory.getLogger(MkysKoduResource.class);

    private static final String ENTITY_NAME = "mkysKodu";

    private final MkysKoduRepository mkysKoduRepository;

    public MkysKoduResource(MkysKoduRepository mkysKoduRepository) {
        this.mkysKoduRepository = mkysKoduRepository;
    }

    /**
     * POST  /mkys-kodus : Create a new mkysKodu.
     *
     * @param mkysKodu the mkysKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mkysKodu, or with status 400 (Bad Request) if the mkysKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mkys-kodus")
    @Timed
    public ResponseEntity<MkysKodu> createMkysKodu(@Valid @RequestBody MkysKodu mkysKodu) throws URISyntaxException {
        log.debug("REST request to save MkysKodu : {}", mkysKodu);
        if (mkysKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mkysKodu cannot already have an ID")).body(null);
        }
        MkysKodu result = mkysKoduRepository.save(mkysKodu);
        return ResponseEntity.created(new URI("/api/mkys-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mkys-kodus : Updates an existing mkysKodu.
     *
     * @param mkysKodu the mkysKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mkysKodu,
     * or with status 400 (Bad Request) if the mkysKodu is not valid,
     * or with status 500 (Internal Server Error) if the mkysKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mkys-kodus")
    @Timed
    public ResponseEntity<MkysKodu> updateMkysKodu(@Valid @RequestBody MkysKodu mkysKodu) throws URISyntaxException {
        log.debug("REST request to update MkysKodu : {}", mkysKodu);
        if (mkysKodu.getId() == null) {
            return createMkysKodu(mkysKodu);
        }
        MkysKodu result = mkysKoduRepository.save(mkysKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mkysKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mkys-kodus : get all the mkysKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mkysKodus in body
     */
    @GetMapping("/mkys-kodus")
    @Timed
    public List<MkysKodu> getAllMkysKodus() {
        log.debug("REST request to get all MkysKodus");
        return mkysKoduRepository.findAll();
    }

    /**
     * GET  /mkys-kodus/:id : get the "id" mkysKodu.
     *
     * @param id the id of the mkysKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mkysKodu, or with status 404 (Not Found)
     */
    @GetMapping("/mkys-kodus/{id}")
    @Timed
    public ResponseEntity<MkysKodu> getMkysKodu(@PathVariable Long id) {
        log.debug("REST request to get MkysKodu : {}", id);
        MkysKodu mkysKodu = mkysKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mkysKodu));
    }

    /**
     * DELETE  /mkys-kodus/:id : delete the "id" mkysKodu.
     *
     * @param id the id of the mkysKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mkys-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMkysKodu(@PathVariable Long id) {
        log.debug("REST request to delete MkysKodu : {}", id);
        mkysKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
