package com.ave.Architecture.Validation.Engine.AVE.service;

import com.ave.Architecture.Validation.Engine.AVE.repository.DependencyRepository;
import com.ave.Architecture.Validation.Engine.AVE.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Service
public class ImpactAnalysisService {
    @Autowired
    private final DependencyRepository dependencyRepository;
    @Autowired
    private final ServiceRepository serviceRepository; // Assumed to exist for Service CRUD

    public ImpactAnalysisService(DependencyRepository dependencyRepository, ServiceRepository serviceRepository) {
        this.dependencyRepository = dependencyRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<String> predictOutage(Long failedServiceId) {
        Set<Long> impactedIds = new HashSet<>();
        Stack<Long> stack = new Stack<>();

        stack.push(failedServiceId);
        impactedIds.add(failedServiceId);

        while (!stack.isEmpty()) {
            Long currentServiceId = stack.pop();

            List<Long> requesters = dependencyRepository.findDownstreamServiceIds(currentServiceId);

            for (Long requesterId : requesters) {
                if (!impactedIds.contains(requesterId)) {
                    impactedIds.add(requesterId);
                    stack.push(requesterId);
                }
            }
        }

        return serviceRepository.findAllById(impactedIds)
                .stream()
                .map(com.ave.Architecture.Validation.Engine.AVE.model.Service::getName)
                .toList();
    }
}
