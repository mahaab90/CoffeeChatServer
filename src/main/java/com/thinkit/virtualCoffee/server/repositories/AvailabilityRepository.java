package com.thinkit.virtualCoffee.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;

@Repository
public interface AvailabilityRepository extends  JpaRepository<Availability, Long>{

	List<Availability> findByThinkiteer(Thinkiteer thinkiteer);
}
