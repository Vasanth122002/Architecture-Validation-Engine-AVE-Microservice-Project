package com.ave.Architecture.Validation.Engine.AVE.controller;


import com.ave.Architecture.Validation.Engine.AVE.service.ImpactAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/impact")
public class ImpactController {
    private final ImpactAnalysisService impactAnalysisService;

    public ImpactController(ImpactAnalysisService impactAnalysisService) {
        this.impactAnalysisService = impactAnalysisService;
    }

    @GetMapping("/predict/{serviceId}")
    public ResponseEntity<List<String>> predictOutage(@PathVariable Long serviceId) {
        if (serviceId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<String> impactedNames = impactAnalysisService.predictOutage(serviceId);
        return ResponseEntity.ok(impactedNames);
    }
}
