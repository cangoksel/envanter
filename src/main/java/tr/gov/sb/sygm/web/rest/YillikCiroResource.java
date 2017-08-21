package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.YillikCiro;

import tr.gov.sb.sygm.repository.YillikCiroRepository;
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
 * REST controller for managing YillikCiro.
 */
@RestController
@RequestMapping("/api")
public class YillikCiroResource {

    private final Logger log = LoggerFactory.getLogger(YillikCiroResource.class);

    private static final String ENTITY_NAME = "yillikCiro";

    private final YillikCiroRepository yillikCiroRepository;

    public YillikCiroResource(YillikCiroRepository yillikCiroRepository) {
        this.yillikCiroRepository = yillikCiroRepository;
    }

    /**
     * POST  /yillik-ciros : Create a new yillikCiro.
     *
     * @param yillikCiro the yillikCiro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yillikCiro, or with status 400 (Bad Request) if the yillikCiro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/yillik-ciros")
    @Timed
    public ResponseEntity<YillikCiro> createYillikCiro(@Valid @RequestBody YillikCiro yillikCiro) throws URISyntaxException {
        log.debug("REST request to save YillikCiro : {}", yillikCiro);
        if (yillikCiro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new yillikCiro cannot already have an ID")).body(null);
        }
        YillikCiro result = yillikCiroRepository.save(yillikCiro);
        return ResponseEntity.created(new URI("/api/yillik-ciros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yillik-ciros : Updates an existing yillikCiro.
     *
     * @param yillikCiro the yillikCiro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yillikCiro,
     * or with status 400 (Bad Request) if the yillikCiro is not valid,
     * or with status 500 (Internal Server Error) if the yillikCiro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/yillik-ciros")
    @Timed
    public ResponseEntity<YillikCiro> updateYillikCiro(@Valid @RequestBody YillikCiro yillikCiro) throws URISyntaxException {
        log.debug("REST request to update YillikCiro : {}", yillikCiro);
        if (yillikCiro.getId() == null) {
            return createYillikCiro(yillikCiro);
        }
        YillikCiro result = yillikCiroRepository.save(yillikCiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, yillikCiro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yillik-ciros : get all the yillikCiros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of yillikCiros in body
     */
    @GetMapping("/yillik-ciros")
    @Timed
    public List<YillikCiro> getAllYillikCiros() {
        log.debug("REST request to get all YillikCiros");
        return yillikCiroRepository.findAll();
    }

    /**
     * GET  /yillik-ciros/:id : get the "id" yillikCiro.
     *
     * @param id the id of the yillikCiro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yillikCiro, or with status 404 (Not Found)
     */
    @GetMapping("/yillik-ciros/{id}")
    @Timed
    public ResponseEntity<YillikCiro> getYillikCiro(@PathVariable Long id) {
        log.debug("REST request to get YillikCiro : {}", id);
        YillikCiro yillikCiro = yillikCiroRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(yillikCiro));
    }

    /**
     * DELETE  /yillik-ciros/:id : delete the "id" yillikCiro.
     *
     * @param id the id of the yillikCiro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/yillik-ciros/{id}")
    @Timed
    public ResponseEntity<Void> deleteYillikCiro(@PathVariable Long id) {
        log.debug("REST request to delete YillikCiro : {}", id);
        yillikCiroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
