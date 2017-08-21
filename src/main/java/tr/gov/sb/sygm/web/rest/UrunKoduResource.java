package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.UrunKodu;

import tr.gov.sb.sygm.repository.UrunKoduRepository;
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
 * REST controller for managing UrunKodu.
 */
@RestController
@RequestMapping("/api")
public class UrunKoduResource {

    private final Logger log = LoggerFactory.getLogger(UrunKoduResource.class);

    private static final String ENTITY_NAME = "urunKodu";

    private final UrunKoduRepository urunKoduRepository;

    public UrunKoduResource(UrunKoduRepository urunKoduRepository) {
        this.urunKoduRepository = urunKoduRepository;
    }

    /**
     * POST  /urun-kodus : Create a new urunKodu.
     *
     * @param urunKodu the urunKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urunKodu, or with status 400 (Bad Request) if the urunKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urun-kodus")
    @Timed
    public ResponseEntity<UrunKodu> createUrunKodu(@Valid @RequestBody UrunKodu urunKodu) throws URISyntaxException {
        log.debug("REST request to save UrunKodu : {}", urunKodu);
        if (urunKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urunKodu cannot already have an ID")).body(null);
        }
        UrunKodu result = urunKoduRepository.save(urunKodu);
        return ResponseEntity.created(new URI("/api/urun-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urun-kodus : Updates an existing urunKodu.
     *
     * @param urunKodu the urunKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urunKodu,
     * or with status 400 (Bad Request) if the urunKodu is not valid,
     * or with status 500 (Internal Server Error) if the urunKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urun-kodus")
    @Timed
    public ResponseEntity<UrunKodu> updateUrunKodu(@Valid @RequestBody UrunKodu urunKodu) throws URISyntaxException {
        log.debug("REST request to update UrunKodu : {}", urunKodu);
        if (urunKodu.getId() == null) {
            return createUrunKodu(urunKodu);
        }
        UrunKodu result = urunKoduRepository.save(urunKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urunKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urun-kodus : get all the urunKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urunKodus in body
     */
    @GetMapping("/urun-kodus")
    @Timed
    public List<UrunKodu> getAllUrunKodus() {
        log.debug("REST request to get all UrunKodus");
        return urunKoduRepository.findAll();
    }

    /**
     * GET  /urun-kodus/:id : get the "id" urunKodu.
     *
     * @param id the id of the urunKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urunKodu, or with status 404 (Not Found)
     */
    @GetMapping("/urun-kodus/{id}")
    @Timed
    public ResponseEntity<UrunKodu> getUrunKodu(@PathVariable Long id) {
        log.debug("REST request to get UrunKodu : {}", id);
        UrunKodu urunKodu = urunKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urunKodu));
    }

    /**
     * DELETE  /urun-kodus/:id : delete the "id" urunKodu.
     *
     * @param id the id of the urunKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urun-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrunKodu(@PathVariable Long id) {
        log.debug("REST request to delete UrunKodu : {}", id);
        urunKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
