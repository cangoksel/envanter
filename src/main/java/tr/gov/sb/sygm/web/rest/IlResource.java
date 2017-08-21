package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Il;

import tr.gov.sb.sygm.repository.IlRepository;
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
 * REST controller for managing Il.
 */
@RestController
@RequestMapping("/api")
public class IlResource {

    private final Logger log = LoggerFactory.getLogger(IlResource.class);

    private static final String ENTITY_NAME = "il";

    private final IlRepository ilRepository;

    public IlResource(IlRepository ilRepository) {
        this.ilRepository = ilRepository;
    }

    /**
     * POST  /ils : Create a new il.
     *
     * @param il the il to create
     * @return the ResponseEntity with status 201 (Created) and with body the new il, or with status 400 (Bad Request) if the il has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ils")
    @Timed
    public ResponseEntity<Il> createIl(@Valid @RequestBody Il il) throws URISyntaxException {
        log.debug("REST request to save Il : {}", il);
        if (il.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new il cannot already have an ID")).body(null);
        }
        Il result = ilRepository.save(il);
        return ResponseEntity.created(new URI("/api/ils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ils : Updates an existing il.
     *
     * @param il the il to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated il,
     * or with status 400 (Bad Request) if the il is not valid,
     * or with status 500 (Internal Server Error) if the il couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ils")
    @Timed
    public ResponseEntity<Il> updateIl(@Valid @RequestBody Il il) throws URISyntaxException {
        log.debug("REST request to update Il : {}", il);
        if (il.getId() == null) {
            return createIl(il);
        }
        Il result = ilRepository.save(il);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, il.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ils : get all the ils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ils in body
     */
    @GetMapping("/ils")
    @Timed
    public List<Il> getAllIls() {
        log.debug("REST request to get all Ils");
        return ilRepository.findAll();
    }

    /**
     * GET  /ils/:id : get the "id" il.
     *
     * @param id the id of the il to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the il, or with status 404 (Not Found)
     */
    @GetMapping("/ils/{id}")
    @Timed
    public ResponseEntity<Il> getIl(@PathVariable Long id) {
        log.debug("REST request to get Il : {}", id);
        Il il = ilRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(il));
    }

    /**
     * DELETE  /ils/:id : delete the "id" il.
     *
     * @param id the id of the il to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ils/{id}")
    @Timed
    public ResponseEntity<Void> deleteIl(@PathVariable Long id) {
        log.debug("REST request to delete Il : {}", id);
        ilRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
