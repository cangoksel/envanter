package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Kurum;

import tr.gov.sb.sygm.repository.KurumRepository;
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
 * REST controller for managing Kurum.
 */
@RestController
@RequestMapping("/api")
public class KurumResource {

    private final Logger log = LoggerFactory.getLogger(KurumResource.class);

    private static final String ENTITY_NAME = "kurum";

    private final KurumRepository kurumRepository;

    public KurumResource(KurumRepository kurumRepository) {
        this.kurumRepository = kurumRepository;
    }

    /**
     * POST  /kurums : Create a new kurum.
     *
     * @param kurum the kurum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kurum, or with status 400 (Bad Request) if the kurum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kurums")
    @Timed
    public ResponseEntity<Kurum> createKurum(@Valid @RequestBody Kurum kurum) throws URISyntaxException {
        log.debug("REST request to save Kurum : {}", kurum);
        if (kurum.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kurum cannot already have an ID")).body(null);
        }
        Kurum result = kurumRepository.save(kurum);
        return ResponseEntity.created(new URI("/api/kurums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kurums : Updates an existing kurum.
     *
     * @param kurum the kurum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kurum,
     * or with status 400 (Bad Request) if the kurum is not valid,
     * or with status 500 (Internal Server Error) if the kurum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kurums")
    @Timed
    public ResponseEntity<Kurum> updateKurum(@Valid @RequestBody Kurum kurum) throws URISyntaxException {
        log.debug("REST request to update Kurum : {}", kurum);
        if (kurum.getId() == null) {
            return createKurum(kurum);
        }
        Kurum result = kurumRepository.save(kurum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kurum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kurums : get all the kurums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kurums in body
     */
    @GetMapping("/kurums")
    @Timed
    public List<Kurum> getAllKurums() {
        log.debug("REST request to get all Kurums");
        return kurumRepository.findAll();
    }

    /**
     * GET  /kurums/:id : get the "id" kurum.
     *
     * @param id the id of the kurum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kurum, or with status 404 (Not Found)
     */
    @GetMapping("/kurums/{id}")
    @Timed
    public ResponseEntity<Kurum> getKurum(@PathVariable Long id) {
        log.debug("REST request to get Kurum : {}", id);
        Kurum kurum = kurumRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kurum));
    }

    /**
     * DELETE  /kurums/:id : delete the "id" kurum.
     *
     * @param id the id of the kurum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kurums/{id}")
    @Timed
    public ResponseEntity<Void> deleteKurum(@PathVariable Long id) {
        log.debug("REST request to delete Kurum : {}", id);
        kurumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
