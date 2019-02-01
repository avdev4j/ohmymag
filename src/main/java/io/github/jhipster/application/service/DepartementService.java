package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Departement;
import io.github.jhipster.application.repository.DepartementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Departement.
 */
@Service
@Transactional
public class DepartementService {

    private final Logger log = LoggerFactory.getLogger(DepartementService.class);

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /**
     * Save a departement.
     *
     * @param departement the entity to save
     * @return the persisted entity
     */
    public Departement save(Departement departement) {
        log.debug("Request to save Departement : {}", departement);
        return departementRepository.save(departement);
    }

    /**
     * Get all the departements.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Departement> findAll() {
        log.debug("Request to get all Departements");
        return departementRepository.findAll();
    }


    /**
     * Get one departement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Departement> findOne(Long id) {
        log.debug("Request to get Departement : {}", id);
        return departementRepository.findById(id);
    }

    /**
     * Delete the departement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Departement : {}", id);        departementRepository.deleteById(id);
    }
}
