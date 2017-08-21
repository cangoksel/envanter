package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.TesisBilgisi;

import tr.gov.sb.sygm.repository.TesisBilgisiRepository;
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
 * REST controller for managing TesisBilgisi.
 */
@RestController
@RequestMapping("/api")
public class TesisBilgisiResource {

    private final Logger log = LoggerFactory.getLogger(TesisBilgisiResource.class);

    private static final String ENTITY_NAME = "tesisBilgisi";

    private final TesisBilgisiRepository tesisBilgisiRepository;

    public TesisBilgisiResource(TesisBilgisiRepository tesisBilgisiRepository) {
        this.tesisBilgisiRepository = tesisBilgisiRepository;
    }

    /**
     * POST  /tesis-bilgisis : Create a new tesisBilgisi.
     *
     * @param tesisBilgisi the tesisBilgisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tesisBilgisi, or with status 400 (Bad Request) if the tesisBilgisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tesis-bilgisis")
    @Timed
    public ResponseEntity<TesisBilgisi> createTesisBilgisi(@Valid @RequestBody TesisBilgisi tesisBilgisi) throws URISyntaxException {
        log.debug("REST request to save TesisBilgisi : {}", tesisBilgisi);
        if (tesisBilgisi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tesisBilgisi cannot already have an ID")).body(null);
        }
        TesisBilgisi result = tesisBilgisiRepository.save(tesisBilgisi);
        return ResponseEntity.created(new URI("/api/tesis-bilgisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tesis-bilgisis : Updates an existing tesisBilgisi.
     *
     * @param tesisBilgisi the tesisBilgisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tesisBilgisi,
     * or with status 400 (Bad Request) if the tesisBilgisi is not valid,
     * or with status 500 (Internal Server Error) if the tesisBilgisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tesis-bilgisis")
    @Timed
    public ResponseEntity<TesisBilgisi> updateTesisBilgisi(@Valid @RequestBody TesisBilgisi tesisBilgisi) throws URISyntaxException {
        log.debug("REST request to update TesisBilgisi : {}", tesisBilgisi);
        if (tesisBilgisi.getId() == null) {
            return createTesisBilgisi(tesisBilgisi);
        }
        TesisBilgisi result = tesisBilgisiRepository.save(tesisBilgisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tesisBilgisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tesis-bilgisis : get all the tesisBilgisis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tesisBilgisis in body
     */
    @GetMapping("/tesis-bilgisis")
    @Timed
    public List<TesisBilgisi> getAllTesisBilgisis() {
        log.debug("REST request to get all TesisBilgisis");
        return tesisBilgisiRepository.findAll();
    }

    /**
     * GET  /tesis-bilgisis/:id : get the "id" tesisBilgisi.
     *
     * @param id the id of the tesisBilgisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tesisBilgisi, or with status 404 (Not Found)
     */
    @GetMapping("/tesis-bilgisis/{id}")
    @Timed
    public ResponseEntity<TesisBilgisi> getTesisBilgisi(@PathVariable Long id) {
        log.debug("REST request to get TesisBilgisi : {}", id);
        TesisBilgisi tesisBilgisi = tesisBilgisiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tesisBilgisi));
    }

    /**
     * DELETE  /tesis-bilgisis/:id : delete the "id" tesisBilgisi.
     *
     * @param id the id of the tesisBilgisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tesis-bilgisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteTesisBilgisi(@PathVariable Long id) {
        log.debug("REST request to delete TesisBilgisi : {}", id);
        tesisBilgisiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
