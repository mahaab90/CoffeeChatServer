package com.thinkit.virtualCoffee.server.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Chat;
import com.thinkit.virtualCoffee.server.services.AvailabilityService;

@RestController
@RequestMapping("/availabilities")
public class AvailibilityController {
	
	AvailabilityService availabilityService;

	public AvailibilityController(AvailabilityService availabilityService) {
		this.availabilityService=availabilityService;
	}
	
	@GetMapping({ "/{thinkiteerName}" })
	public ResponseEntity<List<Availability>> getAvailabilities() {
		return new ResponseEntity<>(availabilityService.getAvailabilityByThinkiteer(null), HttpStatus.OK);
	}
	
	@GetMapping({ "/chat" })
	public ResponseEntity<Chat> getChat(@RequestParam int start,@RequestParam int end,@RequestParam int offset,@RequestParam int numberOfParticipants) 
	{   Chat chat=availabilityService.getChat(start, end, offset, numberOfParticipants);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}
	
	@PostMapping({ "/{thinkiteerName}/availability" })
	public ResponseEntity<Availability> saveAvailabiliy(@PathVariable String thinkiteerName, @RequestBody Availability _Availability){
		availabilityService.InsertAvailability(_Availability, thinkiteerName);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("availability", "/{thinkiteerName}/availability" + _Availability.getId().toString());
		return new ResponseEntity<>(_Availability, httpHeaders, HttpStatus.CREATED);
	}
}
