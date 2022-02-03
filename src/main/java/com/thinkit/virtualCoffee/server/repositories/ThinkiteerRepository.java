package com.thinkit.virtualCoffee.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkit.virtualCoffee.server.models.Thinkiteer;

@Repository
public interface ThinkiteerRepository extends  JpaRepository<Thinkiteer, String>{

}
