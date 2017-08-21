package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.UretimBilgisi;

import tr.gov.sb.sygm.repository.UretimBilgisiRepository;
import tr.gov.sb.sygm.web.rest.util.HeaderUtil;
import tr.gov.sb.sygm.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UretimBilgisi.
 */
@RestController
@RequestMapping("/api")
public class UretimBilgisiResource {

    private final Logger log = LoggerFactory.getLogger(UretimBilgisiResource.class);

    private static final String ENTITY_NAME = "uretimBilgisi";

    private final UretimBilgisiRepository uretimBilgisiRepository;

    public UretimBilgisiResource(UretimBilgisiRepository uretimBilgisiRepository) {
        this.uretimBilgisiRepository = uretimBilgisiRepository;
    }

    /**
     * POST  /uretim-bilgisis : Create a new uretimBilgisi.
     *
     * @param uretimBilgisi the uretimBilgisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uretimBilgisi, or with status 400 (Bad Request) if the uretimBilgisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uretim-bilgisis")
    @Timed
    public ResponseEntity<UretimBilgisi> createUretimBilgisi(@Valid @RequestBody UretimBilgisi uretimBilgisi) throws URISyntaxException {
        log.debug("REST request to save UretimBilgisi : {}", uretimBilgisi);
        if (uretimBilgisi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new uretimBilgisi cannot already have an ID")).body(null);
        }
        UretimBilgisi result = uretimBilgisiRepository.save(uretimBilgisi);
        return ResponseEntity.created(new URI("/api/uretim-bilgisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uretim-bilgisis : Updates an existing uretimBilgisi.
     *
     * @param uretimBilgisi the uretimBilgisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uretimBilgisi,
     * or with status 400 (Bad Request) if the uretimBilgisi is not valid,
     * or with status 500 (Internal Server Error) if the uretimBilgisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uretim-bilgisis")
    @Timed
    public ResponseEntity<UretimBilgisi> updateUretimBilgisi(@Valid @RequestBody UretimBilgisi uretimBilgisi) throws URISyntaxException {
        log.debug("REST request to update UretimBilgisi : {}", uretimBilgisi);
        if (uretimBilgisi.getId() == null) {
            return createUretimBilgisi(uretimBilgisi);
        }
        UretimBilgisi result = uretimBilgisiRepository.save(uretimBilgisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uretimBilgisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uretim-bilgisis : get all the uretimBilgisis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uretimBilgisis in body
     */
    @GetMapping("/uretim-bilgisis")
    @Timed
    public ResponseEntity<List<UretimBilgisi>> getAllUretimBilgisis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UretimBilgisis");
        Page<UretimBilgisi> page = uretimBilgisiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uretim-bilgisis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uretim-bilgisis/:id : get the "id" uretimBilgisi.
     *
     * @param id the id of the uretimBilgisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uretimBilgisi, or with status 404 (Not Found)
     */
    @GetMapping("/uretim-bilgisis/{id}")
    @Timed
    public ResponseEntity<UretimBilgisi> getUretimBilgisi(@PathVariable Long id) {
        log.debug("REST request to get UretimBilgisi : {}", id);
        UretimBilgisi uretimBilgisi = uretimBilgisiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uretimBilgisi));
    }

    /**
     * DELETE  /uretim-bilgisis/:id : delete the "id" uretimBilgisi.
     *
     * @param id the id of the uretimBilgisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uretim-bilgisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteUretimBilgisi(@PathVariable Long id) {
        log.debug("REST request to delete UretimBilgisi : {}", id);
        uretimBilgisiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
