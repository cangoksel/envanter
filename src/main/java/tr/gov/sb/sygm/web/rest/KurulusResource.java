package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Kurulus;

import tr.gov.sb.sygm.repository.KurulusRepository;
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
 * REST controller for managing Kurulus.
 */
@RestController
@RequestMapping("/api")
public class KurulusResource {

    private final Logger log = LoggerFactory.getLogger(KurulusResource.class);

    private static final String ENTITY_NAME = "kurulus";

    private final KurulusRepository kurulusRepository;

    public KurulusResource(KurulusRepository kurulusRepository) {
        this.kurulusRepository = kurulusRepository;
    }

    /**
     * POST  /kuruluses : Create a new kurulus.
     *
     * @param kurulus the kurulus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kurulus, or with status 400 (Bad Request) if the kurulus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kuruluses")
    @Timed
    public ResponseEntity<Kurulus> createKurulus(@Valid @RequestBody Kurulus kurulus) throws URISyntaxException {
        log.debug("REST request to save Kurulus : {}", kurulus);
        if (kurulus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kurulus cannot already have an ID")).body(null);
        }
        Kurulus result = kurulusRepository.save(kurulus);
        return ResponseEntity.created(new URI("/api/kuruluses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kuruluses : Updates an existing kurulus.
     *
     * @param kurulus the kurulus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kurulus,
     * or with status 400 (Bad Request) if the kurulus is not valid,
     * or with status 500 (Internal Server Error) if the kurulus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kuruluses")
    @Timed
    public ResponseEntity<Kurulus> updateKurulus(@Valid @RequestBody Kurulus kurulus) throws URISyntaxException {
        log.debug("REST request to update Kurulus : {}", kurulus);
        if (kurulus.getId() == null) {
            return createKurulus(kurulus);
        }
        Kurulus result = kurulusRepository.save(kurulus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kurulus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kuruluses : get all the kuruluses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kuruluses in body
     */
    @GetMapping("/kuruluses")
    @Timed
    public List<Kurulus> getAllKuruluses() {
        log.debug("REST request to get all Kuruluses");
        return kurulusRepository.findAll();
    }

    /**
     * GET  /kuruluses/:id : get the "id" kurulus.
     *
     * @param id the id of the kurulus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kurulus, or with status 404 (Not Found)
     */
    @GetMapping("/kuruluses/{id}")
    @Timed
    public ResponseEntity<Kurulus> getKurulus(@PathVariable Long id) {
        log.debug("REST request to get Kurulus : {}", id);
        Kurulus kurulus = kurulusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kurulus));
    }

    /**
     * DELETE  /kuruluses/:id : delete the "id" kurulus.
     *
     * @param id the id of the kurulus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kuruluses/{id}")
    @Timed
    public ResponseEntity<Void> deleteKurulus(@PathVariable Long id) {
        log.debug("REST request to delete Kurulus : {}", id);
        kurulusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
