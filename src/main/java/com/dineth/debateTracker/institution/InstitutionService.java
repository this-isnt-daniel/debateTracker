package com.dineth.debateTracker.institution;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution addInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }


}
