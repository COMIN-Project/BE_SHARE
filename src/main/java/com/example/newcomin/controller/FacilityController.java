package com.example.newcomin.controller;

import com.example.newcomin.service.FacilityService;
import com.example.newcomin.entity.Facility;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/facilities")
public class FacilityController {

    private FacilityService facilityService;

    // 등록
    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility){
        Facility savedFacility = facilityService.createFacility(facility);
        return new ResponseEntity<>(savedFacility, HttpStatus.CREATED);
    }

    // 조회
    @GetMapping("/{facilityId}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable("facilityId") Long facilityId){
        Facility facility = facilityService.getFacilityById(facilityId);
        return new ResponseEntity<>(facility,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities(){
        List<Facility> facilities = facilityService.getAllFacilities();
        return new ResponseEntity<>(facilities,HttpStatus.OK);
    }

    @PutMapping("/{facilityId}")
    public ResponseEntity<Facility> updateFacility(@PathVariable("facilityId") Long facilityId,
                                                   @RequestBody Facility facility){
        facility.setFacilityId(facilityId);
        Facility updatedFacility = facilityService.updateFacility(facility);
        return new ResponseEntity<>(updatedFacility,HttpStatus.OK);
    }
}