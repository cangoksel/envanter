package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.GtipKodu;

import tr.gov.sb.sygm.repository.GtipKoduRepository;
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
 * REST controller for managing GtipKodu.
 */
@RestController
@RequestMapping("/api")
public class GtipKoduResource {

    private final Logger log = LoggerFactory.getLogger(GtipKoduResource.class);

    private static final String ENTITY_NAME = "gtipKodu";

    private final GtipKoduRepository gtipKoduRepository;

    public GtipKoduResource(GtipKoduRepository gtipKoduRepository) {
        this.gtipKoduRepository = gtipKoduRepository;
    }

    /**
     * POST  /gtip-kodus : Create a new gtipKodu.
     *
     * @param gtipKodu the gtipKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gtipKodu, or with status 400 (Bad Request) if the gtipKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gtip-kodus")
    @Timed
    public ResponseEntity<GtipKodu> createGtipKodu(@Valid @RequestBody GtipKodu gtipKodu) throws URISyntaxException {
        log.debug("REST request to save GtipKodu : {}", gtipKodu);
        if (gtipKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gtipKodu cannot already have an ID")).body(null);
        }
        GtipKodu result = gtipKoduRepository.save(gtipKodu);
        return ResponseEntity.created(new URI("/api/gtip-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gtip-kodus : Updates an existing gtipKodu.
     *
     * @param gtipKodu the gtipKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gtipKodu,
     * or with status 400 (Bad Request) if the gtipKodu is not valid,
     * or with status 500 (Internal Server Error) if the gtipKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gtip-kodus")
    @Timed
    public ResponseEntity<GtipKodu> updateGtipKodu(@Valid @RequestBody GtipKodu gtipKodu) throws URISyntaxException {
        log.debug("REST request to update GtipKodu : {}", gtipKodu);
        if (gtipKodu.getId() == null) {
            return createGtipKodu(gtipKodu);
        }
        GtipKodu result = gtipKoduRepository.save(gtipKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gtipKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gtip-kodus : get all the gtipKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gtipKodus in body
     */
    @GetMapping("/gtip-kodus")
    @Timed
    public List<GtipKodu> getAllGtipKodus() {
        log.debug("REST request to get all GtipKodus");
        return gtipKoduRepository.findAll();
    }

    /**
     * GET  /gtip-kodus/:id : get the "id" gtipKodu.
     *
     * @param id the id of the gtipKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gtipKodu, or with status 404 (Not Found)
     */
    @GetMapping("/gtip-kodus/{id}")
    @Timed
    public ResponseEntity<GtipKodu> getGtipKodu(@PathVariable Long id) {
        log.debug("REST request to get GtipKodu : {}", id);
        GtipKodu gtipKodu = gtipKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gtipKodu));
    }

    /**
     * DELETE  /gtip-kodus/:id : delete the "id" gtipKodu.
     *
     * @param id the id of the gtipKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gtip-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteGtipKodu(@PathVariable Long id) {
        log.debug("REST request to delete GtipKodu : {}", id);
        gtipKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
