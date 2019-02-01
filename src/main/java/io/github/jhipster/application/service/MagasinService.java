package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Magasin;
import io.github.jhipster.application.repository.MagasinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Magasin.
 */
@Service
@Transactional
public class MagasinService {

    private final Logger log = LoggerFactory.getLogger(MagasinService.class);

    private final MagasinRepository magasinRepository;

    public MagasinService(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }

    /**
     * Save a magasin.
     *
     * @param magasin the entity to save
     * @return the persisted entity
     */
    public Magasin save(Magasin magasin) {
        log.debug("Request to save Magasin : {}", magasin);
        return magasinRepository.save(magasin);
    }

    /**
     * Get all the magasins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Magasin> findAll(Pageable pageable) {
        log.debug("Request to get all Magasins");
        return magasinRepository.findAll(pageable);
    }


    /**
     * Get one magasin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Magasin> findOne(Long id) {
        log.debug("Request to get Magasin : {}", id);
        return magasinRepository.findById(id);
    }

    /**
     * Delete the magasin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Magasin : {}", id);        magasinRepository.deleteById(id);
    }
}
