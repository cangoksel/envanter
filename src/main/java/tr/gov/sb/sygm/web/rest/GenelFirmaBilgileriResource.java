package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.GenelFirmaBilgileri;

import tr.gov.sb.sygm.repository.GenelFirmaBilgileriRepository;
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
 * REST controller for managing GenelFirmaBilgileri.
 */
@RestController
@RequestMapping("/api")
public class GenelFirmaBilgileriResource {

    private final Logger log = LoggerFactory.getLogger(GenelFirmaBilgileriResource.class);

    private static final String ENTITY_NAME = "genelFirmaBilgileri";

    private final GenelFirmaBilgileriRepository genelFirmaBilgileriRepository;

    public GenelFirmaBilgileriResource(GenelFirmaBilgileriRepository genelFirmaBilgileriRepository) {
        this.genelFirmaBilgileriRepository = genelFirmaBilgileriRepository;
    }

    /**
     * POST  /genel-firma-bilgileris : Create a new genelFirmaBilgileri.
     *
     * @param genelFirmaBilgileri the genelFirmaBilgileri to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genelFirmaBilgileri, or with status 400 (Bad Request) if the genelFirmaBilgileri has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/genel-firma-bilgileris")
    @Timed
    public ResponseEntity<GenelFirmaBilgileri> createGenelFirmaBilgileri(@Valid @RequestBody GenelFirmaBilgileri genelFirmaBilgileri) throws URISyntaxException {
        log.debug("REST request to save GenelFirmaBilgileri : {}", genelFirmaBilgileri);
        if (genelFirmaBilgileri.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new genelFirmaBilgileri cannot already have an ID")).body(null);
        }
        GenelFirmaBilgileri result = genelFirmaBilgileriRepository.save(genelFirmaBilgileri);
        return ResponseEntity.created(new URI("/api/genel-firma-bilgileris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genel-firma-bilgileris : Updates an existing genelFirmaBilgileri.
     *
     * @param genelFirmaBilgileri the genelFirmaBilgileri to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genelFirmaBilgileri,
     * or with status 400 (Bad Request) if the genelFirmaBilgileri is not valid,
     * or with status 500 (Internal Server Error) if the genelFirmaBilgileri couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/genel-firma-bilgileris")
    @Timed
    public ResponseEntity<GenelFirmaBilgileri> updateGenelFirmaBilgileri(@Valid @RequestBody GenelFirmaBilgileri genelFirmaBilgileri) throws URISyntaxException {
        log.debug("REST request to update GenelFirmaBilgileri : {}", genelFirmaBilgileri);
        if (genelFirmaBilgileri.getId() == null) {
            return createGenelFirmaBilgileri(genelFirmaBilgileri);
        }
        GenelFirmaBilgileri result = genelFirmaBilgileriRepository.save(genelFirmaBilgileri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genelFirmaBilgileri.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genel-firma-bilgileris : get all the genelFirmaBilgileris.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genelFirmaBilgileris in body
     */
    @GetMapping("/genel-firma-bilgileris")
    @Timed
    public List<GenelFirmaBilgileri> getAllGenelFirmaBilgileris() {
        log.debug("REST request to get all GenelFirmaBilgileris");
        return genelFirmaBilgileriRepository.findAll();
    }

    /**
     * GET  /genel-firma-bilgileris/:id : get the "id" genelFirmaBilgileri.
     *
     * @param id the id of the genelFirmaBilgileri to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genelFirmaBilgileri, or with status 404 (Not Found)
     */
    @GetMapping("/genel-firma-bilgileris/{id}")
    @Timed
    public ResponseEntity<GenelFirmaBilgileri> getGenelFirmaBilgileri(@PathVariable Long id) {
        log.debug("REST request to get GenelFirmaBilgileri : {}", id);
        GenelFirmaBilgileri genelFirmaBilgileri = genelFirmaBilgileriRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genelFirmaBilgileri));
    }

    /**
     * DELETE  /genel-firma-bilgileris/:id : delete the "id" genelFirmaBilgileri.
     *
     * @param id the id of the genelFirmaBilgileri to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/genel-firma-bilgileris/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenelFirmaBilgileri(@PathVariable Long id) {
        log.debug("REST request to delete GenelFirmaBilgileri : {}", id);
        genelFirmaBilgileriRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
