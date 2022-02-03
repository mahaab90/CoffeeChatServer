package com.thinkit.virtualCoffee.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkit.virtualCoffee.server.models.Availability;

@Repository
public interface AvailabilityRepository extends  JpaRepository<Availability, Long>{

	
}
