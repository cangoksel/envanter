package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.UrunGrubuKodu;

import tr.gov.sb.sygm.repository.UrunGrubuKoduRepository;
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
 * REST controller for managing UrunGrubuKodu.
 */
@RestController
@RequestMapping("/api")
public class UrunGrubuKoduResource {

    private final Logger log = LoggerFactory.getLogger(UrunGrubuKoduResource.class);

    private static final String ENTITY_NAME = "urunGrubuKodu";

    private final UrunGrubuKoduRepository urunGrubuKoduRepository;

    public UrunGrubuKoduResource(UrunGrubuKoduRepository urunGrubuKoduRepository) {
        this.urunGrubuKoduRepository = urunGrubuKoduRepository;
    }

    /**
     * POST  /urun-grubu-kodus : Create a new urunGrubuKodu.
     *
     * @param urunGrubuKodu the urunGrubuKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urunGrubuKodu, or with status 400 (Bad Request) if the urunGrubuKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urun-grubu-kodus")
    @Timed
    public ResponseEntity<UrunGrubuKodu> createUrunGrubuKodu(@Valid @RequestBody UrunGrubuKodu urunGrubuKodu) throws URISyntaxException {
        log.debug("REST request to save UrunGrubuKodu : {}", urunGrubuKodu);
        if (urunGrubuKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urunGrubuKodu cannot already have an ID")).body(null);
        }
        UrunGrubuKodu result = urunGrubuKoduRepository.save(urunGrubuKodu);
        return ResponseEntity.created(new URI("/api/urun-grubu-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urun-grubu-kodus : Updates an existing urunGrubuKodu.
     *
     * @param urunGrubuKodu the urunGrubuKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urunGrubuKodu,
     * or with status 400 (Bad Request) if the urunGrubuKodu is not valid,
     * or with status 500 (Internal Server Error) if the urunGrubuKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urun-grubu-kodus")
    @Timed
    public ResponseEntity<UrunGrubuKodu> updateUrunGrubuKodu(@Valid @RequestBody UrunGrubuKodu urunGrubuKodu) throws URISyntaxException {
        log.debug("REST request to update UrunGrubuKodu : {}", urunGrubuKodu);
        if (urunGrubuKodu.getId() == null) {
            return createUrunGrubuKodu(urunGrubuKodu);
        }
        UrunGrubuKodu result = urunGrubuKoduRepository.save(urunGrubuKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urunGrubuKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urun-grubu-kodus : get all the urunGrubuKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urunGrubuKodus in body
     */
    @GetMapping("/urun-grubu-kodus")
    @Timed
    public List<UrunGrubuKodu> getAllUrunGrubuKodus() {
        log.debug("REST request to get all UrunGrubuKodus");
        return urunGrubuKoduRepository.findAll();
    }

    /**
     * GET  /urun-grubu-kodus/:id : get the "id" urunGrubuKodu.
     *
     * @param id the id of the urunGrubuKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urunGrubuKodu, or with status 404 (Not Found)
     */
    @GetMapping("/urun-grubu-kodus/{id}")
    @Timed
    public ResponseEntity<UrunGrubuKodu> getUrunGrubuKodu(@PathVariable Long id) {
        log.debug("REST request to get UrunGrubuKodu : {}", id);
        UrunGrubuKodu urunGrubuKodu = urunGrubuKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urunGrubuKodu));
    }

    /**
     * DELETE  /urun-grubu-kodus/:id : delete the "id" urunGrubuKodu.
     *
     * @param id the id of the urunGrubuKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urun-grubu-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrunGrubuKodu(@PathVariable Long id) {
        log.debug("REST request to delete UrunGrubuKodu : {}", id);
        urunGrubuKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
