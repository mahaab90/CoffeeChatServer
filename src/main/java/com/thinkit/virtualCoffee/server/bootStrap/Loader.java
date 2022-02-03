package com.thinkit.virtualCoffee.server.bootStrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;
import com.thinkit.virtualCoffee.server.repositories.AvailabilityRepository;
import com.thinkit.virtualCoffee.server.repositories.ThinkiteerRepository;

@Component
public class Loader implements CommandLineRunner {
	AvailabilityRepository availabilityRepository;
	ThinkiteerRepository thinkiteerRepository;

	public Loader(AvailabilityRepository availabilityRepository, ThinkiteerRepository thinkiteerRepository) {
		this.availabilityRepository=availabilityRepository;
		this.thinkiteerRepository=thinkiteerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		loadThinkiteers();
		loadAvailabilities();
	}

	private void loadAvailabilities() {
		// TODO Auto-generated method stub
		Thinkiteer t1=thinkiteerRepository.getById("Maha");
		availabilityRepository.save(Availability.builder().start(9).end(11).thinkiteer(t1).build());
		availabilityRepository.save(Availability.builder().start(15).end(16).thinkiteer(t1).build());
		Thinkiteer t2=thinkiteerRepository.getById("Amin");
		availabilityRepository.save(Availability.builder().start(9).end(12).thinkiteer(t2).build());
		Thinkiteer t3=thinkiteerRepository.getById("Wassim");
		availabilityRepository.save(Availability.builder().start(8).end(10).thinkiteer(t3).build());
		Thinkiteer t4=thinkiteerRepository.getById("Fatma");
		availabilityRepository.save(Availability.builder().start(11).end(12).thinkiteer(t4).build());
	
	}

	private void loadThinkiteers() {
		// TODO Auto-generated method stub
		if(thinkiteerRepository.count() == 0) {
			thinkiteerRepository.save(Thinkiteer.builder().name("Maha").offset(1).build());
			thinkiteerRepository.save(Thinkiteer.builder().name("Amin").offset(2).build());
			thinkiteerRepository.save(Thinkiteer.builder().name("Wassim").offset(1).build());
			thinkiteerRepository.save(Thinkiteer.builder().name("Fatma").offset(4).build());
			thinkiteerRepository.save(Thinkiteer.builder().name("Ali").offset(3).build());
		}
	}
}
