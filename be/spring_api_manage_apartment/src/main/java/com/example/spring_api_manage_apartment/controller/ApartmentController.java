package com.example.spring_api_manage_apartment.controller;

import com.example.spring_api_manage_apartment.entity.Apartment;
import com.example.spring_api_manage_apartment.repo.ApartmentRepository;
import com.example.spring_api_manage_apartment.repo.ResidentRepository;
import com.example.spring_api_manage_apartment.spec.ApartmentSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apartments")
public class ApartmentController {
    private final ApartmentRepository apartmentRepository;
    private final ResidentRepository residentRepository;

    @GetMapping
    private ResponseEntity<Page<Apartment>> getAll(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("resident_name") String residentName
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Apartment> apartments = apartmentRepository.findAll(ApartmentSpec.search(residentName), pageable);
        return ResponseEntity.ok().body(apartments);
    }

    @PostMapping
    private void create(@RequestBody Apartment apartment) {
        var resident = residentRepository.findByName(apartment.getResidentName());
        apartment.setResident(resident);
        apartmentRepository.save(apartment);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable("id") Long id, @RequestBody Apartment apartmentReq) {
        var apartment = apartmentRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(apartmentReq, apartment);
        apartmentRepository.save(apartment);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Apartment> get(@PathVariable("id") Long id) {
        var apartment = apartmentRepository.findById(id).orElseThrow();
        return ResponseEntity.ok().body(apartment);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable("id") Long id) {
        var apartment = apartmentRepository.findById(id).orElseThrow();
        apartmentRepository.delete(apartment);
    }
}
