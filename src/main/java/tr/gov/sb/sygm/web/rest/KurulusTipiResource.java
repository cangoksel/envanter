package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.KurulusTipi;

import tr.gov.sb.sygm.repository.KurulusTipiRepository;
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
 * REST controller for managing KurulusTipi.
 */
@RestController
@RequestMapping("/api")
public class KurulusTipiResource {

    private final Logger log = LoggerFactory.getLogger(KurulusTipiResource.class);

    private static final String ENTITY_NAME = "kurulusTipi";

    private final KurulusTipiRepository kurulusTipiRepository;

    public KurulusTipiResource(KurulusTipiRepository kurulusTipiRepository) {
        this.kurulusTipiRepository = kurulusTipiRepository;
    }

    /**
     * POST  /kurulus-tipis : Create a new kurulusTipi.
     *
     * @param kurulusTipi the kurulusTipi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kurulusTipi, or with status 400 (Bad Request) if the kurulusTipi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kurulus-tipis")
    @Timed
    public ResponseEntity<KurulusTipi> createKurulusTipi(@Valid @RequestBody KurulusTipi kurulusTipi) throws URISyntaxException {
        log.debug("REST request to save KurulusTipi : {}", kurulusTipi);
        if (kurulusTipi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kurulusTipi cannot already have an ID")).body(null);
        }
        KurulusTipi result = kurulusTipiRepository.save(kurulusTipi);
        return ResponseEntity.created(new URI("/api/kurulus-tipis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kurulus-tipis : Updates an existing kurulusTipi.
     *
     * @param kurulusTipi the kurulusTipi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kurulusTipi,
     * or with status 400 (Bad Request) if the kurulusTipi is not valid,
     * or with status 500 (Internal Server Error) if the kurulusTipi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kurulus-tipis")
    @Timed
    public ResponseEntity<KurulusTipi> updateKurulusTipi(@Valid @RequestBody KurulusTipi kurulusTipi) throws URISyntaxException {
        log.debug("REST request to update KurulusTipi : {}", kurulusTipi);
        if (kurulusTipi.getId() == null) {
            return createKurulusTipi(kurulusTipi);
        }
        KurulusTipi result = kurulusTipiRepository.save(kurulusTipi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kurulusTipi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kurulus-tipis : get all the kurulusTipis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kurulusTipis in body
     */
    @GetMapping("/kurulus-tipis")
    @Timed
    public List<KurulusTipi> getAllKurulusTipis() {
        log.debug("REST request to get all KurulusTipis");
        return kurulusTipiRepository.findAll();
    }

    /**
     * GET  /kurulus-tipis/:id : get the "id" kurulusTipi.
     *
     * @param id the id of the kurulusTipi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kurulusTipi, or with status 404 (Not Found)
     */
    @GetMapping("/kurulus-tipis/{id}")
    @Timed
    public ResponseEntity<KurulusTipi> getKurulusTipi(@PathVariable Long id) {
        log.debug("REST request to get KurulusTipi : {}", id);
        KurulusTipi kurulusTipi = kurulusTipiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kurulusTipi));
    }

    /**
     * DELETE  /kurulus-tipis/:id : delete the "id" kurulusTipi.
     *
     * @param id the id of the kurulusTipi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kurulus-tipis/{id}")
    @Timed
    public ResponseEntity<Void> deleteKurulusTipi(@PathVariable Long id) {
        log.debug("REST request to delete KurulusTipi : {}", id);
        kurulusTipiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
