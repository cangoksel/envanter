package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.ActKodu;

import tr.gov.sb.sygm.repository.ActKoduRepository;
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
 * REST controller for managing ActKodu.
 */
@RestController
@RequestMapping("/api")
public class ActKoduResource {

    private final Logger log = LoggerFactory.getLogger(ActKoduResource.class);

    private static final String ENTITY_NAME = "actKodu";

    private final ActKoduRepository actKoduRepository;

    public ActKoduResource(ActKoduRepository actKoduRepository) {
        this.actKoduRepository = actKoduRepository;
    }

    /**
     * POST  /act-kodus : Create a new actKodu.
     *
     * @param actKodu the actKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actKodu, or with status 400 (Bad Request) if the actKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/act-kodus")
    @Timed
    public ResponseEntity<ActKodu> createActKodu(@Valid @RequestBody ActKodu actKodu) throws URISyntaxException {
        log.debug("REST request to save ActKodu : {}", actKodu);
        if (actKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new actKodu cannot already have an ID")).body(null);
        }
        ActKodu result = actKoduRepository.save(actKodu);
        return ResponseEntity.created(new URI("/api/act-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /act-kodus : Updates an existing actKodu.
     *
     * @param actKodu the actKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actKodu,
     * or with status 400 (Bad Request) if the actKodu is not valid,
     * or with status 500 (Internal Server Error) if the actKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/act-kodus")
    @Timed
    public ResponseEntity<ActKodu> updateActKodu(@Valid @RequestBody ActKodu actKodu) throws URISyntaxException {
        log.debug("REST request to update ActKodu : {}", actKodu);
        if (actKodu.getId() == null) {
            return createActKodu(actKodu);
        }
        ActKodu result = actKoduRepository.save(actKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /act-kodus : get all the actKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actKodus in body
     */
    @GetMapping("/act-kodus")
    @Timed
    public List<ActKodu> getAllActKodus() {
        log.debug("REST request to get all ActKodus");
        return actKoduRepository.findAll();
    }

    /**
     * GET  /act-kodus/:id : get the "id" actKodu.
     *
     * @param id the id of the actKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actKodu, or with status 404 (Not Found)
     */
    @GetMapping("/act-kodus/{id}")
    @Timed
    public ResponseEntity<ActKodu> getActKodu(@PathVariable Long id) {
        log.debug("REST request to get ActKodu : {}", id);
        ActKodu actKodu = actKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actKodu));
    }

    /**
     * DELETE  /act-kodus/:id : delete the "id" actKodu.
     *
     * @param id the id of the actKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/act-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteActKodu(@PathVariable Long id) {
        log.debug("REST request to delete ActKodu : {}", id);
        actKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
