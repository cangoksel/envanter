package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.ProdkomKodu;

import tr.gov.sb.sygm.repository.ProdkomKoduRepository;
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
 * REST controller for managing ProdkomKodu.
 */
@RestController
@RequestMapping("/api")
public class ProdkomKoduResource {

    private final Logger log = LoggerFactory.getLogger(ProdkomKoduResource.class);

    private static final String ENTITY_NAME = "prodkomKodu";

    private final ProdkomKoduRepository prodkomKoduRepository;

    public ProdkomKoduResource(ProdkomKoduRepository prodkomKoduRepository) {
        this.prodkomKoduRepository = prodkomKoduRepository;
    }

    /**
     * POST  /prodkom-kodus : Create a new prodkomKodu.
     *
     * @param prodkomKodu the prodkomKodu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prodkomKodu, or with status 400 (Bad Request) if the prodkomKodu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prodkom-kodus")
    @Timed
    public ResponseEntity<ProdkomKodu> createProdkomKodu(@Valid @RequestBody ProdkomKodu prodkomKodu) throws URISyntaxException {
        log.debug("REST request to save ProdkomKodu : {}", prodkomKodu);
        if (prodkomKodu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prodkomKodu cannot already have an ID")).body(null);
        }
        ProdkomKodu result = prodkomKoduRepository.save(prodkomKodu);
        return ResponseEntity.created(new URI("/api/prodkom-kodus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prodkom-kodus : Updates an existing prodkomKodu.
     *
     * @param prodkomKodu the prodkomKodu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prodkomKodu,
     * or with status 400 (Bad Request) if the prodkomKodu is not valid,
     * or with status 500 (Internal Server Error) if the prodkomKodu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prodkom-kodus")
    @Timed
    public ResponseEntity<ProdkomKodu> updateProdkomKodu(@Valid @RequestBody ProdkomKodu prodkomKodu) throws URISyntaxException {
        log.debug("REST request to update ProdkomKodu : {}", prodkomKodu);
        if (prodkomKodu.getId() == null) {
            return createProdkomKodu(prodkomKodu);
        }
        ProdkomKodu result = prodkomKoduRepository.save(prodkomKodu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prodkomKodu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prodkom-kodus : get all the prodkomKodus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prodkomKodus in body
     */
    @GetMapping("/prodkom-kodus")
    @Timed
    public List<ProdkomKodu> getAllProdkomKodus() {
        log.debug("REST request to get all ProdkomKodus");
        return prodkomKoduRepository.findAll();
    }

    /**
     * GET  /prodkom-kodus/:id : get the "id" prodkomKodu.
     *
     * @param id the id of the prodkomKodu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prodkomKodu, or with status 404 (Not Found)
     */
    @GetMapping("/prodkom-kodus/{id}")
    @Timed
    public ResponseEntity<ProdkomKodu> getProdkomKodu(@PathVariable Long id) {
        log.debug("REST request to get ProdkomKodu : {}", id);
        ProdkomKodu prodkomKodu = prodkomKoduRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prodkomKodu));
    }

    /**
     * DELETE  /prodkom-kodus/:id : delete the "id" prodkomKodu.
     *
     * @param id the id of the prodkomKodu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prodkom-kodus/{id}")
    @Timed
    public ResponseEntity<Void> deleteProdkomKodu(@PathVariable Long id) {
        log.debug("REST request to delete ProdkomKodu : {}", id);
        prodkomKoduRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
