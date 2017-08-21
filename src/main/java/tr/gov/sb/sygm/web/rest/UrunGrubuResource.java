package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.UrunGrubu;

import tr.gov.sb.sygm.repository.UrunGrubuRepository;
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
 * REST controller for managing UrunGrubu.
 */
@RestController
@RequestMapping("/api")
public class UrunGrubuResource {

    private final Logger log = LoggerFactory.getLogger(UrunGrubuResource.class);

    private static final String ENTITY_NAME = "urunGrubu";

    private final UrunGrubuRepository urunGrubuRepository;

    public UrunGrubuResource(UrunGrubuRepository urunGrubuRepository) {
        this.urunGrubuRepository = urunGrubuRepository;
    }

    /**
     * POST  /urun-grubus : Create a new urunGrubu.
     *
     * @param urunGrubu the urunGrubu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urunGrubu, or with status 400 (Bad Request) if the urunGrubu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urun-grubus")
    @Timed
    public ResponseEntity<UrunGrubu> createUrunGrubu(@Valid @RequestBody UrunGrubu urunGrubu) throws URISyntaxException {
        log.debug("REST request to save UrunGrubu : {}", urunGrubu);
        if (urunGrubu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urunGrubu cannot already have an ID")).body(null);
        }
        UrunGrubu result = urunGrubuRepository.save(urunGrubu);
        return ResponseEntity.created(new URI("/api/urun-grubus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urun-grubus : Updates an existing urunGrubu.
     *
     * @param urunGrubu the urunGrubu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urunGrubu,
     * or with status 400 (Bad Request) if the urunGrubu is not valid,
     * or with status 500 (Internal Server Error) if the urunGrubu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urun-grubus")
    @Timed
    public ResponseEntity<UrunGrubu> updateUrunGrubu(@Valid @RequestBody UrunGrubu urunGrubu) throws URISyntaxException {
        log.debug("REST request to update UrunGrubu : {}", urunGrubu);
        if (urunGrubu.getId() == null) {
            return createUrunGrubu(urunGrubu);
        }
        UrunGrubu result = urunGrubuRepository.save(urunGrubu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urunGrubu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urun-grubus : get all the urunGrubus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urunGrubus in body
     */
    @GetMapping("/urun-grubus")
    @Timed
    public List<UrunGrubu> getAllUrunGrubus() {
        log.debug("REST request to get all UrunGrubus");
        return urunGrubuRepository.findAll();
    }

    /**
     * GET  /urun-grubus/:id : get the "id" urunGrubu.
     *
     * @param id the id of the urunGrubu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urunGrubu, or with status 404 (Not Found)
     */
    @GetMapping("/urun-grubus/{id}")
    @Timed
    public ResponseEntity<UrunGrubu> getUrunGrubu(@PathVariable Long id) {
        log.debug("REST request to get UrunGrubu : {}", id);
        UrunGrubu urunGrubu = urunGrubuRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urunGrubu));
    }

    /**
     * DELETE  /urun-grubus/:id : delete the "id" urunGrubu.
     *
     * @param id the id of the urunGrubu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urun-grubus/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrunGrubu(@PathVariable Long id) {
        log.debug("REST request to delete UrunGrubu : {}", id);
        urunGrubuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
