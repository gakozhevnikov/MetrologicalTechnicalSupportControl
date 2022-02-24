package com.kga.metrologicaltechnicalsupportcontrol.services.impl.maintenance;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.PurposeOperations;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance.PurposeOperationsRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.FindByDesignation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@Data
public class PurposeOperationsService implements FindByDesignation<PurposeOperations, Long> {

    private final PurposeOperationsRepository purposeOperationsRepository;

    @Autowired
    public PurposeOperationsService(PurposeOperationsRepository purposeOperationsRepository) {
        this.purposeOperationsRepository = purposeOperationsRepository;
    }

    @Override
    public PurposeOperations save(PurposeOperations entity) {
        return purposeOperationsRepository.saveAndFlush(entity);
    }

    @Override
    public List<PurposeOperations> saveAllAndFlush(List<PurposeOperations> purposeOperations) {
        return purposeOperationsRepository.saveAllAndFlush(purposeOperations);
    }

    @Override
    public void deleteAll() {
        purposeOperationsRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        purposeOperationsRepository.deleteById(id);
    }

    @Override
    public List<PurposeOperations> findAll() {
        return purposeOperationsRepository.findAll();
    }

    @Override
    public PurposeOperations findById(Long id) {
        Optional<PurposeOperations> optional = purposeOperationsRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public PurposeOperations findByDesignation(String designation) {
        return purposeOperationsRepository.findPurposeOperationsByDesignation(designation);
    }
}
