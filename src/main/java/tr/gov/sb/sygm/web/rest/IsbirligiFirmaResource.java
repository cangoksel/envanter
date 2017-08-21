package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.IsbirligiFirma;

import tr.gov.sb.sygm.repository.IsbirligiFirmaRepository;
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
 * REST controller for managing IsbirligiFirma.
 */
@RestController
@RequestMapping("/api")
public class IsbirligiFirmaResource {

    private final Logger log = LoggerFactory.getLogger(IsbirligiFirmaResource.class);

    private static final String ENTITY_NAME = "isbirligiFirma";

    private final IsbirligiFirmaRepository isbirligiFirmaRepository;

    public IsbirligiFirmaResource(IsbirligiFirmaRepository isbirligiFirmaRepository) {
        this.isbirligiFirmaRepository = isbirligiFirmaRepository;
    }

    /**
     * POST  /isbirligi-firmas : Create a new isbirligiFirma.
     *
     * @param isbirligiFirma the isbirligiFirma to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isbirligiFirma, or with status 400 (Bad Request) if the isbirligiFirma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/isbirligi-firmas")
    @Timed
    public ResponseEntity<IsbirligiFirma> createIsbirligiFirma(@Valid @RequestBody IsbirligiFirma isbirligiFirma) throws URISyntaxException {
        log.debug("REST request to save IsbirligiFirma : {}", isbirligiFirma);
        if (isbirligiFirma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new isbirligiFirma cannot already have an ID")).body(null);
        }
        IsbirligiFirma result = isbirligiFirmaRepository.save(isbirligiFirma);
        return ResponseEntity.created(new URI("/api/isbirligi-firmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /isbirligi-firmas : Updates an existing isbirligiFirma.
     *
     * @param isbirligiFirma the isbirligiFirma to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isbirligiFirma,
     * or with status 400 (Bad Request) if the isbirligiFirma is not valid,
     * or with status 500 (Internal Server Error) if the isbirligiFirma couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/isbirligi-firmas")
    @Timed
    public ResponseEntity<IsbirligiFirma> updateIsbirligiFirma(@Valid @RequestBody IsbirligiFirma isbirligiFirma) throws URISyntaxException {
        log.debug("REST request to update IsbirligiFirma : {}", isbirligiFirma);
        if (isbirligiFirma.getId() == null) {
            return createIsbirligiFirma(isbirligiFirma);
        }
        IsbirligiFirma result = isbirligiFirmaRepository.save(isbirligiFirma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isbirligiFirma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /isbirligi-firmas : get all the isbirligiFirmas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of isbirligiFirmas in body
     */
    @GetMapping("/isbirligi-firmas")
    @Timed
    public List<IsbirligiFirma> getAllIsbirligiFirmas() {
        log.debug("REST request to get all IsbirligiFirmas");
        return isbirligiFirmaRepository.findAll();
    }

    /**
     * GET  /isbirligi-firmas/:id : get the "id" isbirligiFirma.
     *
     * @param id the id of the isbirligiFirma to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isbirligiFirma, or with status 404 (Not Found)
     */
    @GetMapping("/isbirligi-firmas/{id}")
    @Timed
    public ResponseEntity<IsbirligiFirma> getIsbirligiFirma(@PathVariable Long id) {
        log.debug("REST request to get IsbirligiFirma : {}", id);
        IsbirligiFirma isbirligiFirma = isbirligiFirmaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isbirligiFirma));
    }

    /**
     * DELETE  /isbirligi-firmas/:id : delete the "id" isbirligiFirma.
     *
     * @param id the id of the isbirligiFirma to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/isbirligi-firmas/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsbirligiFirma(@PathVariable Long id) {
        log.debug("REST request to delete IsbirligiFirma : {}", id);
        isbirligiFirmaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
