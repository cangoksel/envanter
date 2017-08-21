package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Urun;

import tr.gov.sb.sygm.repository.UrunRepository;
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
 * REST controller for managing Urun.
 */
@RestController
@RequestMapping("/api")
public class UrunResource {

    private final Logger log = LoggerFactory.getLogger(UrunResource.class);

    private static final String ENTITY_NAME = "urun";

    private final UrunRepository urunRepository;

    public UrunResource(UrunRepository urunRepository) {
        this.urunRepository = urunRepository;
    }

    /**
     * POST  /uruns : Create a new urun.
     *
     * @param urun the urun to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urun, or with status 400 (Bad Request) if the urun has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uruns")
    @Timed
    public ResponseEntity<Urun> createUrun(@Valid @RequestBody Urun urun) throws URISyntaxException {
        log.debug("REST request to save Urun : {}", urun);
        if (urun.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new urun cannot already have an ID")).body(null);
        }
        Urun result = urunRepository.save(urun);
        return ResponseEntity.created(new URI("/api/uruns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uruns : Updates an existing urun.
     *
     * @param urun the urun to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urun,
     * or with status 400 (Bad Request) if the urun is not valid,
     * or with status 500 (Internal Server Error) if the urun couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uruns")
    @Timed
    public ResponseEntity<Urun> updateUrun(@Valid @RequestBody Urun urun) throws URISyntaxException {
        log.debug("REST request to update Urun : {}", urun);
        if (urun.getId() == null) {
            return createUrun(urun);
        }
        Urun result = urunRepository.save(urun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urun.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uruns : get all the uruns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uruns in body
     */
    @GetMapping("/uruns")
    @Timed
    public ResponseEntity<List<Urun>> getAllUruns(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Uruns");
        Page<Urun> page = urunRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uruns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uruns/:id : get the "id" urun.
     *
     * @param id the id of the urun to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urun, or with status 404 (Not Found)
     */
    @GetMapping("/uruns/{id}")
    @Timed
    public ResponseEntity<Urun> getUrun(@PathVariable Long id) {
        log.debug("REST request to get Urun : {}", id);
        Urun urun = urunRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urun));
    }

    /**
     * DELETE  /uruns/:id : delete the "id" urun.
     *
     * @param id the id of the urun to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uruns/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrun(@PathVariable Long id) {
        log.debug("REST request to delete Urun : {}", id);
        urunRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
