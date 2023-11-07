package com.example.newcomin.service;

import com.example.newcomin.entity.Facility;
import java.util.List;


public interface FacilityService {
    Facility createFacility(Facility facility);
    Facility getFacilityById(Long facilityId);
    List<Facility> getAllFacilities();
    Facility updateFacility(Facility facility);
}

