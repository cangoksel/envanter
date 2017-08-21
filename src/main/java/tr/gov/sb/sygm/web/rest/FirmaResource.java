package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Firma;

import tr.gov.sb.sygm.repository.FirmaRepository;
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
 * REST controller for managing Firma.
 */
@RestController
@RequestMapping("/api")
public class FirmaResource {

    private final Logger log = LoggerFactory.getLogger(FirmaResource.class);

    private static final String ENTITY_NAME = "firma";

    private final FirmaRepository firmaRepository;

    public FirmaResource(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
    }

    /**
     * POST  /firmas : Create a new firma.
     *
     * @param firma the firma to create
     * @return the ResponseEntity with status 201 (Created) and with body the new firma, or with status 400 (Bad Request) if the firma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/firmas")
    @Timed
    public ResponseEntity<Firma> createFirma(@Valid @RequestBody Firma firma) throws URISyntaxException {
        log.debug("REST request to save Firma : {}", firma);
        if (firma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new firma cannot already have an ID")).body(null);
        }
        Firma result = firmaRepository.save(firma);
        return ResponseEntity.created(new URI("/api/firmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /firmas : Updates an existing firma.
     *
     * @param firma the firma to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated firma,
     * or with status 400 (Bad Request) if the firma is not valid,
     * or with status 500 (Internal Server Error) if the firma couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/firmas")
    @Timed
    public ResponseEntity<Firma> updateFirma(@Valid @RequestBody Firma firma) throws URISyntaxException {
        log.debug("REST request to update Firma : {}", firma);
        if (firma.getId() == null) {
            return createFirma(firma);
        }
        Firma result = firmaRepository.save(firma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, firma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /firmas : get all the firmas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of firmas in body
     */
    @GetMapping("/firmas")
    @Timed
    public ResponseEntity<List<Firma>> getAllFirmas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Firmas");
        Page<Firma> page = firmaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/firmas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /firmas/:id : get the "id" firma.
     *
     * @param id the id of the firma to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the firma, or with status 404 (Not Found)
     */
    @GetMapping("/firmas/{id}")
    @Timed
    public ResponseEntity<Firma> getFirma(@PathVariable Long id) {
        log.debug("REST request to get Firma : {}", id);
        Firma firma = firmaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(firma));
    }

    /**
     * DELETE  /firmas/:id : delete the "id" firma.
     *
     * @param id the id of the firma to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/firmas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFirma(@PathVariable Long id) {
        log.debug("REST request to delete Firma : {}", id);
        firmaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
