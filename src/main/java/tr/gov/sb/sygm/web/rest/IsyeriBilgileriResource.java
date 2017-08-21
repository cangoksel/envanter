package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.IsyeriBilgileri;

import tr.gov.sb.sygm.repository.IsyeriBilgileriRepository;
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
 * REST controller for managing IsyeriBilgileri.
 */
@RestController
@RequestMapping("/api")
public class IsyeriBilgileriResource {

    private final Logger log = LoggerFactory.getLogger(IsyeriBilgileriResource.class);

    private static final String ENTITY_NAME = "isyeriBilgileri";

    private final IsyeriBilgileriRepository isyeriBilgileriRepository;

    public IsyeriBilgileriResource(IsyeriBilgileriRepository isyeriBilgileriRepository) {
        this.isyeriBilgileriRepository = isyeriBilgileriRepository;
    }

    /**
     * POST  /isyeri-bilgileris : Create a new isyeriBilgileri.
     *
     * @param isyeriBilgileri the isyeriBilgileri to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isyeriBilgileri, or with status 400 (Bad Request) if the isyeriBilgileri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/isyeri-bilgileris")
    @Timed
    public ResponseEntity<IsyeriBilgileri> createIsyeriBilgileri(@Valid @RequestBody IsyeriBilgileri isyeriBilgileri) throws URISyntaxException {
        log.debug("REST request to save IsyeriBilgileri : {}", isyeriBilgileri);
        if (isyeriBilgileri.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new isyeriBilgileri cannot already have an ID")).body(null);
        }
        IsyeriBilgileri result = isyeriBilgileriRepository.save(isyeriBilgileri);
        return ResponseEntity.created(new URI("/api/isyeri-bilgileris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /isyeri-bilgileris : Updates an existing isyeriBilgileri.
     *
     * @param isyeriBilgileri the isyeriBilgileri to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isyeriBilgileri,
     * or with status 400 (Bad Request) if the isyeriBilgileri is not valid,
     * or with status 500 (Internal Server Error) if the isyeriBilgileri couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/isyeri-bilgileris")
    @Timed
    public ResponseEntity<IsyeriBilgileri> updateIsyeriBilgileri(@Valid @RequestBody IsyeriBilgileri isyeriBilgileri) throws URISyntaxException {
        log.debug("REST request to update IsyeriBilgileri : {}", isyeriBilgileri);
        if (isyeriBilgileri.getId() == null) {
            return createIsyeriBilgileri(isyeriBilgileri);
        }
        IsyeriBilgileri result = isyeriBilgileriRepository.save(isyeriBilgileri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isyeriBilgileri.getId().toString()))
            .body(result);
    }

    /**
     * GET  /isyeri-bilgileris : get all the isyeriBilgileris.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of isyeriBilgileris in body
     */
    @GetMapping("/isyeri-bilgileris")
    @Timed
    public List<IsyeriBilgileri> getAllIsyeriBilgileris() {
        log.debug("REST request to get all IsyeriBilgileris");
        return isyeriBilgileriRepository.findAll();
    }

    /**
     * GET  /isyeri-bilgileris/:id : get the "id" isyeriBilgileri.
     *
     * @param id the id of the isyeriBilgileri to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isyeriBilgileri, or with status 404 (Not Found)
     */
    @GetMapping("/isyeri-bilgileris/{id}")
    @Timed
    public ResponseEntity<IsyeriBilgileri> getIsyeriBilgileri(@PathVariable Long id) {
        log.debug("REST request to get IsyeriBilgileri : {}", id);
        IsyeriBilgileri isyeriBilgileri = isyeriBilgileriRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isyeriBilgileri));
    }

    /**
     * DELETE  /isyeri-bilgileris/:id : delete the "id" isyeriBilgileri.
     *
     * @param id the id of the isyeriBilgileri to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/isyeri-bilgileris/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsyeriBilgileri(@PathVariable Long id) {
        log.debug("REST request to delete IsyeriBilgileri : {}", id);
        isyeriBilgileriRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
