package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.SayisalVeri;

import tr.gov.sb.sygm.repository.SayisalVeriRepository;
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
 * REST controller for managing SayisalVeri.
 */
@RestController
@RequestMapping("/api")
public class SayisalVeriResource {

    private final Logger log = LoggerFactory.getLogger(SayisalVeriResource.class);

    private static final String ENTITY_NAME = "sayisalVeri";

    private final SayisalVeriRepository sayisalVeriRepository;

    public SayisalVeriResource(SayisalVeriRepository sayisalVeriRepository) {
        this.sayisalVeriRepository = sayisalVeriRepository;
    }

    /**
     * POST  /sayisal-veris : Create a new sayisalVeri.
     *
     * @param sayisalVeri the sayisalVeri to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sayisalVeri, or with status 400 (Bad Request) if the sayisalVeri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sayisal-veris")
    @Timed
    public ResponseEntity<SayisalVeri> createSayisalVeri(@Valid @RequestBody SayisalVeri sayisalVeri) throws URISyntaxException {
        log.debug("REST request to save SayisalVeri : {}", sayisalVeri);
        if (sayisalVeri.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sayisalVeri cannot already have an ID")).body(null);
        }
        SayisalVeri result = sayisalVeriRepository.save(sayisalVeri);
        return ResponseEntity.created(new URI("/api/sayisal-veris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sayisal-veris : Updates an existing sayisalVeri.
     *
     * @param sayisalVeri the sayisalVeri to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sayisalVeri,
     * or with status 400 (Bad Request) if the sayisalVeri is not valid,
     * or with status 500 (Internal Server Error) if the sayisalVeri couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sayisal-veris")
    @Timed
    public ResponseEntity<SayisalVeri> updateSayisalVeri(@Valid @RequestBody SayisalVeri sayisalVeri) throws URISyntaxException {
        log.debug("REST request to update SayisalVeri : {}", sayisalVeri);
        if (sayisalVeri.getId() == null) {
            return createSayisalVeri(sayisalVeri);
        }
        SayisalVeri result = sayisalVeriRepository.save(sayisalVeri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sayisalVeri.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sayisal-veris : get all the sayisalVeris.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sayisalVeris in body
     */
    @GetMapping("/sayisal-veris")
    @Timed
    public ResponseEntity<List<SayisalVeri>> getAllSayisalVeris(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SayisalVeris");
        Page<SayisalVeri> page = sayisalVeriRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sayisal-veris");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sayisal-veris/:id : get the "id" sayisalVeri.
     *
     * @param id the id of the sayisalVeri to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sayisalVeri, or with status 404 (Not Found)
     */
    @GetMapping("/sayisal-veris/{id}")
    @Timed
    public ResponseEntity<SayisalVeri> getSayisalVeri(@PathVariable Long id) {
        log.debug("REST request to get SayisalVeri : {}", id);
        SayisalVeri sayisalVeri = sayisalVeriRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sayisalVeri));
    }

    /**
     * DELETE  /sayisal-veris/:id : delete the "id" sayisalVeri.
     *
     * @param id the id of the sayisalVeri to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sayisal-veris/{id}")
    @Timed
    public ResponseEntity<Void> deleteSayisalVeri(@PathVariable Long id) {
        log.debug("REST request to delete SayisalVeri : {}", id);
        sayisalVeriRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
