package com.example.spring_api_manage_apartment.controller;

import com.example.spring_api_manage_apartment.entity.Apartment;
import com.example.spring_api_manage_apartment.entity.Resident;
import com.example.spring_api_manage_apartment.repo.ResidentRepository;
import com.example.spring_api_manage_apartment.spec.ApartmentSpec;
import com.example.spring_api_manage_apartment.spec.ResidentSpec;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/residents")
public class ResidentController {
    private final ResidentRepository residentRepository;

    @GetMapping
    private ResponseEntity<Page<Resident>> getAll(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("name") String name
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Resident> residents = residentRepository.findAll(ResidentSpec.search(name), pageable);
        return ResponseEntity.ok().body(residents);
    }

    @PostMapping
    private void create(@RequestBody Resident resident) {
        residentRepository.save(resident);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable("id") Long id, @RequestBody Resident residentReq) {
        var resident = residentRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(residentReq, resident);
        residentRepository.save(resident);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Resident> get(@PathVariable("id") Long id) {
        var resident = residentRepository.findById(id).orElseThrow();
        return ResponseEntity.ok().body(resident);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable("id") Long id) {
        var resident = residentRepository.findById(id).orElseThrow();
        residentRepository.delete(resident);
    }
}
