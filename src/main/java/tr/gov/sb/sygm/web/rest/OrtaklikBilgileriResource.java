package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.OrtaklikBilgileri;

import tr.gov.sb.sygm.repository.OrtaklikBilgileriRepository;
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
 * REST controller for managing OrtaklikBilgileri.
 */
@RestController
@RequestMapping("/api")
public class OrtaklikBilgileriResource {

    private final Logger log = LoggerFactory.getLogger(OrtaklikBilgileriResource.class);

    private static final String ENTITY_NAME = "ortaklikBilgileri";

    private final OrtaklikBilgileriRepository ortaklikBilgileriRepository;

    public OrtaklikBilgileriResource(OrtaklikBilgileriRepository ortaklikBilgileriRepository) {
        this.ortaklikBilgileriRepository = ortaklikBilgileriRepository;
    }

    /**
     * POST  /ortaklik-bilgileris : Create a new ortaklikBilgileri.
     *
     * @param ortaklikBilgileri the ortaklikBilgileri to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ortaklikBilgileri, or with status 400 (Bad Request) if the ortaklikBilgileri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ortaklik-bilgileris")
    @Timed
    public ResponseEntity<OrtaklikBilgileri> createOrtaklikBilgileri(@Valid @RequestBody OrtaklikBilgileri ortaklikBilgileri) throws URISyntaxException {
        log.debug("REST request to save OrtaklikBilgileri : {}", ortaklikBilgileri);
        if (ortaklikBilgileri.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ortaklikBilgileri cannot already have an ID")).body(null);
        }
        OrtaklikBilgileri result = ortaklikBilgileriRepository.save(ortaklikBilgileri);
        return ResponseEntity.created(new URI("/api/ortaklik-bilgileris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ortaklik-bilgileris : Updates an existing ortaklikBilgileri.
     *
     * @param ortaklikBilgileri the ortaklikBilgileri to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ortaklikBilgileri,
     * or with status 400 (Bad Request) if the ortaklikBilgileri is not valid,
     * or with status 500 (Internal Server Error) if the ortaklikBilgileri couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ortaklik-bilgileris")
    @Timed
    public ResponseEntity<OrtaklikBilgileri> updateOrtaklikBilgileri(@Valid @RequestBody OrtaklikBilgileri ortaklikBilgileri) throws URISyntaxException {
        log.debug("REST request to update OrtaklikBilgileri : {}", ortaklikBilgileri);
        if (ortaklikBilgileri.getId() == null) {
            return createOrtaklikBilgileri(ortaklikBilgileri);
        }
        OrtaklikBilgileri result = ortaklikBilgileriRepository.save(ortaklikBilgileri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ortaklikBilgileri.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ortaklik-bilgileris : get all the ortaklikBilgileris.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ortaklikBilgileris in body
     */
    @GetMapping("/ortaklik-bilgileris")
    @Timed
    public List<OrtaklikBilgileri> getAllOrtaklikBilgileris() {
        log.debug("REST request to get all OrtaklikBilgileris");
        return ortaklikBilgileriRepository.findAll();
    }

    /**
     * GET  /ortaklik-bilgileris/:id : get the "id" ortaklikBilgileri.
     *
     * @param id the id of the ortaklikBilgileri to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ortaklikBilgileri, or with status 404 (Not Found)
     */
    @GetMapping("/ortaklik-bilgileris/{id}")
    @Timed
    public ResponseEntity<OrtaklikBilgileri> getOrtaklikBilgileri(@PathVariable Long id) {
        log.debug("REST request to get OrtaklikBilgileri : {}", id);
        OrtaklikBilgileri ortaklikBilgileri = ortaklikBilgileriRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ortaklikBilgileri));
    }

    /**
     * DELETE  /ortaklik-bilgileris/:id : delete the "id" ortaklikBilgileri.
     *
     * @param id the id of the ortaklikBilgileri to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ortaklik-bilgileris/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrtaklikBilgileri(@PathVariable Long id) {
        log.debug("REST request to delete OrtaklikBilgileri : {}", id);
        ortaklikBilgileriRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
