package com.thinkit.virtualCoffee.server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;
import com.thinkit.virtualCoffee.server.repositories.ThinkiteerRepository;

@Service
public class ThinkiteerServiceImp  implements ThinkiteerService{
	ThinkiteerRepository thinkiteerRepository;
	
	public ThinkiteerServiceImp(ThinkiteerRepository thinkiteerRepository) {
		this.thinkiteerRepository=thinkiteerRepository;
	}
	
	@Override
	public List<Thinkiteer> getThinkiteers() {
		// TODO Auto-generated method stub
		return thinkiteerRepository.findAll();
	}

	@Override
	public Thinkiteer getThinkiteerByName(String name) {
		// TODO Auto-generated method stub
		return thinkiteerRepository.getById(name);
	}

	@Override
	public Thinkiteer getThinkiteerByAvailability(Availability availability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addThinkiteer(Thinkiteer thinkiteer) {
		// TODO Auto-generated method stub
		thinkiteerRepository.save(thinkiteer);
	}

}
