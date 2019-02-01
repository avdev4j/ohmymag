package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Magasin;
import io.github.jhipster.application.service.MagasinService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
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
 * REST controller for managing Magasin.
 */
@RestController
@RequestMapping("/api")
public class MagasinResource {

    private final Logger log = LoggerFactory.getLogger(MagasinResource.class);

    private static final String ENTITY_NAME = "magasin";

    private final MagasinService magasinService;

    public MagasinResource(MagasinService magasinService) {
        this.magasinService = magasinService;
    }

    /**
     * POST  /magasins : Create a new magasin.
     *
     * @param magasin the magasin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new magasin, or with status 400 (Bad Request) if the magasin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/magasins")
    public ResponseEntity<Magasin> createMagasin(@Valid @RequestBody Magasin magasin) throws URISyntaxException {
        log.debug("REST request to save Magasin : {}", magasin);
        if (magasin.getId() != null) {
            throw new BadRequestAlertException("A new magasin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Magasin result = magasinService.save(magasin);
        return ResponseEntity.created(new URI("/api/magasins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /magasins : Updates an existing magasin.
     *
     * @param magasin the magasin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated magasin,
     * or with status 400 (Bad Request) if the magasin is not valid,
     * or with status 500 (Internal Server Error) if the magasin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/magasins")
    public ResponseEntity<Magasin> updateMagasin(@Valid @RequestBody Magasin magasin) throws URISyntaxException {
        log.debug("REST request to update Magasin : {}", magasin);
        if (magasin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Magasin result = magasinService.save(magasin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, magasin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /magasins : get all the magasins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of magasins in body
     */
    @GetMapping("/magasins")
    public ResponseEntity<List<Magasin>> getAllMagasins(Pageable pageable) {
        log.debug("REST request to get a page of Magasins");
        Page<Magasin> page = magasinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/magasins");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /magasins/:id : get the "id" magasin.
     *
     * @param id the id of the magasin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the magasin, or with status 404 (Not Found)
     */
    @GetMapping("/magasins/{id}")
    public ResponseEntity<Magasin> getMagasin(@PathVariable Long id) {
        log.debug("REST request to get Magasin : {}", id);
        Optional<Magasin> magasin = magasinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(magasin);
    }

    /**
     * DELETE  /magasins/:id : delete the "id" magasin.
     *
     * @param id the id of the magasin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/magasins/{id}")
    public ResponseEntity<Void> deleteMagasin(@PathVariable Long id) {
        log.debug("REST request to delete Magasin : {}", id);
        magasinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
