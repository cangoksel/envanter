package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Kisi;

import tr.gov.sb.sygm.repository.KisiRepository;
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
 * REST controller for managing Kisi.
 */
@RestController
@RequestMapping("/api")
public class KisiResource {

    private final Logger log = LoggerFactory.getLogger(KisiResource.class);

    private static final String ENTITY_NAME = "kisi";

    private final KisiRepository kisiRepository;

    public KisiResource(KisiRepository kisiRepository) {
        this.kisiRepository = kisiRepository;
    }

    /**
     * POST  /kisis : Create a new kisi.
     *
     * @param kisi the kisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kisi, or with status 400 (Bad Request) if the kisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kisis")
    @Timed
    public ResponseEntity<Kisi> createKisi(@Valid @RequestBody Kisi kisi) throws URISyntaxException {
        log.debug("REST request to save Kisi : {}", kisi);
        if (kisi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kisi cannot already have an ID")).body(null);
        }
        Kisi result = kisiRepository.save(kisi);
        return ResponseEntity.created(new URI("/api/kisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kisis : Updates an existing kisi.
     *
     * @param kisi the kisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kisi,
     * or with status 400 (Bad Request) if the kisi is not valid,
     * or with status 500 (Internal Server Error) if the kisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kisis")
    @Timed
    public ResponseEntity<Kisi> updateKisi(@Valid @RequestBody Kisi kisi) throws URISyntaxException {
        log.debug("REST request to update Kisi : {}", kisi);
        if (kisi.getId() == null) {
            return createKisi(kisi);
        }
        Kisi result = kisiRepository.save(kisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kisis : get all the kisis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kisis in body
     */
    @GetMapping("/kisis")
    @Timed
    public List<Kisi> getAllKisis() {
        log.debug("REST request to get all Kisis");
        return kisiRepository.findAll();
    }

    /**
     * GET  /kisis/:id : get the "id" kisi.
     *
     * @param id the id of the kisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kisi, or with status 404 (Not Found)
     */
    @GetMapping("/kisis/{id}")
    @Timed
    public ResponseEntity<Kisi> getKisi(@PathVariable Long id) {
        log.debug("REST request to get Kisi : {}", id);
        Kisi kisi = kisiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kisi));
    }

    /**
     * DELETE  /kisis/:id : delete the "id" kisi.
     *
     * @param id the id of the kisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteKisi(@PathVariable Long id) {
        log.debug("REST request to delete Kisi : {}", id);
        kisiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
