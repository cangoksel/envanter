package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.UrunAltKodu;

import tr.gov.sb.sygm.repository.UrunAltKoduRepository;
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
 * REST controller for managing UrunAltKodu.
 */
@RestController
@RequestMapping("/api")
public class UrunAltKoduResource {

    private final Logger log = LoggerFactory.getLogger(UrunAltKoduResource.class);

    private static final String ENTITY_NAME = "urunAltKodu";

    private final UrunAltKoduRepository urunAltKoduRepository;

    public UrunAltKoduResource(UrunAltKoduRepository urunAltKoduRepository) {
        this.urunAltKoduRepository = urunAltKoduRepository;
    }

    /**
     * POST  /urun-alt-kodus : Create a new urunAltKodu.
     *
     * @param urunAltKodu the urunAltKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urunAltKodu, or with status 400 (Bad Request) if the urunAltKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urun-alt-kodus")
    @Timed
    public ResponseEntity<UrunAltKodu> createUrunAltKodu(@Valid @RequestBody UrunAltKodu urunAltKodu) throws URISyntaxException {
        log.debug("REST request to save UrunAltKodu : {}", urunAltKodu);
        if (urunAltKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urunAltKodu cannot already have an ID")).body(null);
        }
        UrunAltKodu result = urunAltKoduRepository.save(urunAltKodu);
        return ResponseEntity.created(new URI("/api/urun-alt-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urun-alt-kodus : Updates an existing urunAltKodu.
     *
     * @param urunAltKodu the urunAltKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urunAltKodu,
     * or with status 400 (Bad Request) if the urunAltKodu is not valid,
     * or with status 500 (Internal Server Error) if the urunAltKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urun-alt-kodus")
    @Timed
    public ResponseEntity<UrunAltKodu> updateUrunAltKodu(@Valid @RequestBody UrunAltKodu urunAltKodu) throws URISyntaxException {
        log.debug("REST request to update UrunAltKodu : {}", urunAltKodu);
        if (urunAltKodu.getId() == null) {
            return createUrunAltKodu(urunAltKodu);
        }
        UrunAltKodu result = urunAltKoduRepository.save(urunAltKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urunAltKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urun-alt-kodus : get all the urunAltKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urunAltKodus in body
     */
    @GetMapping("/urun-alt-kodus")
    @Timed
    public List<UrunAltKodu> getAllUrunAltKodus() {
        log.debug("REST request to get all UrunAltKodus");
        return urunAltKoduRepository.findAll();
    }

    /**
     * GET  /urun-alt-kodus/:id : get the "id" urunAltKodu.
     *
     * @param id the id of the urunAltKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urunAltKodu, or with status 404 (Not Found)
     */
    @GetMapping("/urun-alt-kodus/{id}")
    @Timed
    public ResponseEntity<UrunAltKodu> getUrunAltKodu(@PathVariable Long id) {
        log.debug("REST request to get UrunAltKodu : {}", id);
        UrunAltKodu urunAltKodu = urunAltKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urunAltKodu));
    }

    /**
     * DELETE  /urun-alt-kodus/:id : delete the "id" urunAltKodu.
     *
     * @param id the id of the urunAltKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urun-alt-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrunAltKodu(@PathVariable Long id) {
        log.debug("REST request to delete UrunAltKodu : {}", id);
        urunAltKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
