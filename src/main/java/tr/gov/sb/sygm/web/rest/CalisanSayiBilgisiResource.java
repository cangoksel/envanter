package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.CalisanSayiBilgisi;

import tr.gov.sb.sygm.repository.CalisanSayiBilgisiRepository;
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
 * REST controller for managing CalisanSayiBilgisi.
 */
@RestController
@RequestMapping("/api")
public class CalisanSayiBilgisiResource {

    private final Logger log = LoggerFactory.getLogger(CalisanSayiBilgisiResource.class);

    private static final String ENTITY_NAME = "calisanSayiBilgisi";

    private final CalisanSayiBilgisiRepository calisanSayiBilgisiRepository;

    public CalisanSayiBilgisiResource(CalisanSayiBilgisiRepository calisanSayiBilgisiRepository) {
        this.calisanSayiBilgisiRepository = calisanSayiBilgisiRepository;
    }

    /**
     * POST  /calisan-sayi-bilgisis : Create a new calisanSayiBilgisi.
     *
     * @param calisanSayiBilgisi the calisanSayiBilgisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calisanSayiBilgisi, or with status 400 (Bad Request) if the calisanSayiBilgisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calisan-sayi-bilgisis")
    @Timed
    public ResponseEntity<CalisanSayiBilgisi> createCalisanSayiBilgisi(@Valid @RequestBody CalisanSayiBilgisi calisanSayiBilgisi) throws URISyntaxException {
        log.debug("REST request to save CalisanSayiBilgisi : {}", calisanSayiBilgisi);
        if (calisanSayiBilgisi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new calisanSayiBilgisi cannot already have an ID")).body(null);
        }
        CalisanSayiBilgisi result = calisanSayiBilgisiRepository.save(calisanSayiBilgisi);
        return ResponseEntity.created(new URI("/api/calisan-sayi-bilgisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calisan-sayi-bilgisis : Updates an existing calisanSayiBilgisi.
     *
     * @param calisanSayiBilgisi the calisanSayiBilgisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calisanSayiBilgisi,
     * or with status 400 (Bad Request) if the calisanSayiBilgisi is not valid,
     * or with status 500 (Internal Server Error) if the calisanSayiBilgisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calisan-sayi-bilgisis")
    @Timed
    public ResponseEntity<CalisanSayiBilgisi> updateCalisanSayiBilgisi(@Valid @RequestBody CalisanSayiBilgisi calisanSayiBilgisi) throws URISyntaxException {
        log.debug("REST request to update CalisanSayiBilgisi : {}", calisanSayiBilgisi);
        if (calisanSayiBilgisi.getId() == null) {
            return createCalisanSayiBilgisi(calisanSayiBilgisi);
        }
        CalisanSayiBilgisi result = calisanSayiBilgisiRepository.save(calisanSayiBilgisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calisanSayiBilgisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calisan-sayi-bilgisis : get all the calisanSayiBilgisis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calisanSayiBilgisis in body
     */
    @GetMapping("/calisan-sayi-bilgisis")
    @Timed
    public List<CalisanSayiBilgisi> getAllCalisanSayiBilgisis() {
        log.debug("REST request to get all CalisanSayiBilgisis");
        return calisanSayiBilgisiRepository.findAll();
    }

    /**
     * GET  /calisan-sayi-bilgisis/:id : get the "id" calisanSayiBilgisi.
     *
     * @param id the id of the calisanSayiBilgisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calisanSayiBilgisi, or with status 404 (Not Found)
     */
    @GetMapping("/calisan-sayi-bilgisis/{id}")
    @Timed
    public ResponseEntity<CalisanSayiBilgisi> getCalisanSayiBilgisi(@PathVariable Long id) {
        log.debug("REST request to get CalisanSayiBilgisi : {}", id);
        CalisanSayiBilgisi calisanSayiBilgisi = calisanSayiBilgisiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(calisanSayiBilgisi));
    }

    /**
     * DELETE  /calisan-sayi-bilgisis/:id : delete the "id" calisanSayiBilgisi.
     *
     * @param id the id of the calisanSayiBilgisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calisan-sayi-bilgisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalisanSayiBilgisi(@PathVariable Long id) {
        log.debug("REST request to delete CalisanSayiBilgisi : {}", id);
        calisanSayiBilgisiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
