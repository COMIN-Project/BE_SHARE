package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Facility;
import com.example.newcomin.repository.FacilityRepository;
import com.example.newcomin.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public Facility createFacility(Facility facility){
        return facilityRepository.save(facility);
    }

    @Override
    public Facility getFacilityById(Long facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        return optionalFacility.orElse(null);
    }
    @Override
    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    @Override
    public Facility updateFacility(Facility facility){
        Facility existingFacility = facilityRepository.findById(facility.getFacilityId()).orElse(null);
        if (existingFacility != null) {
            existingFacility.setFacilityName(facility.getFacilityName());
            Facility updatedFacility = facilityRepository.save(existingFacility);
            return updatedFacility;
        }
        return null;
    }
}
