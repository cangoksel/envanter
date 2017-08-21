package tr.gov.sb.sygm.web.rest;

import com.codahale.metrics.annotation.Timed;
import tr.gov.sb.sygm.domain.Adres;

import tr.gov.sb.sygm.repository.AdresRepository;
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
 * REST controller for managing Adres.
 */
@RestController
@RequestMapping("/api")
public class AdresResource {

    private final Logger log = LoggerFactory.getLogger(AdresResource.class);

    private static final String ENTITY_NAME = "adres";

    private final AdresRepository adresRepository;

    public AdresResource(AdresRepository adresRepository) {
        this.adresRepository = adresRepository;
    }

    /**
     * POST  /adres : Create a new adres.
     *
     * @param adres the adres to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adres, or with status 400 (Bad Request) if the adres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adres")
    @Timed
    public ResponseEntity<Adres> createAdres(@Valid @RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to save Adres : {}", adres);
        if (adres.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new adres cannot already have an ID")).body(null);
        }
        Adres result = adresRepository.save(adres);
        return ResponseEntity.created(new URI("/api/adres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adres : Updates an existing adres.
     *
     * @param adres the adres to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adres,
     * or with status 400 (Bad Request) if the adres is not valid,
     * or with status 500 (Internal Server Error) if the adres couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adres")
    @Timed
    public ResponseEntity<Adres> updateAdres(@Valid @RequestBody Adres adres) throws URISyntaxException {
        log.debug("REST request to update Adres : {}", adres);
        if (adres.getId() == null) {
            return createAdres(adres);
        }
        Adres result = adresRepository.save(adres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adres.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adres : get all the adres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of adres in body
     */
    @GetMapping("/adres")
    @Timed
    public List<Adres> getAllAdres() {
        log.debug("REST request to get all Adres");
        return adresRepository.findAll();
    }

    /**
     * GET  /adres/:id : get the "id" adres.
     *
     * @param id the id of the adres to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adres, or with status 404 (Not Found)
     */
    @GetMapping("/adres/{id}")
    @Timed
    public ResponseEntity<Adres> getAdres(@PathVariable Long id) {
        log.debug("REST request to get Adres : {}", id);
        Adres adres = adresRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adres));
    }

    /**
     * DELETE  /adres/:id : delete the "id" adres.
     *
     * @param id the id of the adres to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adres/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdres(@PathVariable Long id) {
        log.debug("REST request to delete Adres : {}", id);
        adresRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
