package com.ave.Architecture.Validation.Engine.AVE.controller;


import com.ave.Architecture.Validation.Engine.AVE.dto.DependencyRequest;
import com.ave.Architecture.Validation.Engine.AVE.model.Dependency;
import com.ave.Architecture.Validation.Engine.AVE.model.Service;
import com.ave.Architecture.Validation.Engine.AVE.repository.DependencyRepository;
import com.ave.Architecture.Validation.Engine.AVE.repository.ServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/dependencies")
public class DependencyController {
    private final DependencyRepository dependencyRepository;
    private final ServiceRepository serviceRepository;

    public DependencyController(DependencyRepository dependencyRepository, ServiceRepository serviceRepository) {
        this.dependencyRepository = dependencyRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping
    public ResponseEntity<Dependency> createDependency(@RequestBody DependencyRequest request) {

        Optional<Service> requesterOpt = serviceRepository.findById(request.getRequesterId());
        Optional<Service> providerOpt = serviceRepository.findById(request.getProviderId());

        if (requesterOpt.isEmpty() || providerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Dependency newDependency = new Dependency(requesterOpt.get(), providerOpt.get());
        Dependency savedDependency = dependencyRepository.save(newDependency);
        return ResponseEntity.ok(savedDependency);
    }
}
