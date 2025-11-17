package com.ave.Architecture.Validation.Engine.AVE.controller;


import com.ave.Architecture.Validation.Engine.AVE.dto.ServiceRequest;
import com.ave.Architecture.Validation.Engine.AVE.model.Service;
import com.ave.Architecture.Validation.Engine.AVE.repository.ServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody ServiceRequest request) {
        Service newService = new Service(request.getName(), request.getOwnerId());
        Service savedService = serviceRepository.save(newService);
        return ResponseEntity.ok(savedService);
    }

}
