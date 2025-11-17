package com.ave.Architecture.Validation.Engine.AVE.repository;

import com.ave.Architecture.Validation.Engine.AVE.model.Dependency;
import com.ave.Architecture.Validation.Engine.AVE.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
