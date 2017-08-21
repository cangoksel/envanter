package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.NaceKodu;

import tr.gov.sb.sygm.repository.NaceKoduRepository;
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
 * REST controller for managing NaceKodu.
 */
@RestController
@RequestMapping("/api")
public class NaceKoduResource {

    private final Logger log = LoggerFactory.getLogger(NaceKoduResource.class);

    private static final String ENTITY_NAME = "naceKodu";

    private final NaceKoduRepository naceKoduRepository;

    public NaceKoduResource(NaceKoduRepository naceKoduRepository) {
        this.naceKoduRepository = naceKoduRepository;
    }

    /**
     * POST  /nace-kodus : Create a new naceKodu.
     *
     * @param naceKodu the naceKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new naceKodu, or with status 400 (Bad Request) if the naceKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nace-kodus")
    @Timed
    public ResponseEntity<NaceKodu> createNaceKodu(@Valid @RequestBody NaceKodu naceKodu) throws URISyntaxException {
        log.debug("REST request to save NaceKodu : {}", naceKodu);
        if (naceKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new naceKodu cannot already have an ID")).body(null);
        }
        NaceKodu result = naceKoduRepository.save(naceKodu);
        return ResponseEntity.created(new URI("/api/nace-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nace-kodus : Updates an existing naceKodu.
     *
     * @param naceKodu the naceKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated naceKodu,
     * or with status 400 (Bad Request) if the naceKodu is not valid,
     * or with status 500 (Internal Server Error) if the naceKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nace-kodus")
    @Timed
    public ResponseEntity<NaceKodu> updateNaceKodu(@Valid @RequestBody NaceKodu naceKodu) throws URISyntaxException {
        log.debug("REST request to update NaceKodu : {}", naceKodu);
        if (naceKodu.getId() == null) {
            return createNaceKodu(naceKodu);
        }
        NaceKodu result = naceKoduRepository.save(naceKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, naceKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nace-kodus : get all the naceKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of naceKodus in body
     */
    @GetMapping("/nace-kodus")
    @Timed
    public List<NaceKodu> getAllNaceKodus() {
        log.debug("REST request to get all NaceKodus");
        return naceKoduRepository.findAll();
    }

    /**
     * GET  /nace-kodus/:id : get the "id" naceKodu.
     *
     * @param id the id of the naceKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the naceKodu, or with status 404 (Not Found)
     */
    @GetMapping("/nace-kodus/{id}")
    @Timed
    public ResponseEntity<NaceKodu> getNaceKodu(@PathVariable Long id) {
        log.debug("REST request to get NaceKodu : {}", id);
        NaceKodu naceKodu = naceKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(naceKodu));
    }

    /**
     * DELETE  /nace-kodus/:id : delete the "id" naceKodu.
     *
     * @param id the id of the naceKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nace-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteNaceKodu(@PathVariable Long id) {
        log.debug("REST request to delete NaceKodu : {}", id);
        naceKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
