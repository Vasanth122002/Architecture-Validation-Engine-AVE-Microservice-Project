package com.ave.Architecture.Validation.Engine.AVE.repository;


import com.ave.Architecture.Validation.Engine.AVE.model.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {

    @Query("SELECT d.requester.id FROM Dependency d WHERE d.provider.id = :providerId")
    List<Long> findDownstreamServiceIds(Long providerId);
}
