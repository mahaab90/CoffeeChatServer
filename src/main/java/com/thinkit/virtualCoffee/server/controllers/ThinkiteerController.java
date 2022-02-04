package com.thinkit.virtualCoffee.server.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.thinkit.virtualCoffee.server.models.Thinkiteer;
import com.thinkit.virtualCoffee.server.services.ThinkiteerService;

@RestController
@RequestMapping("/thinkiteers")
public class ThinkiteerController {

	ThinkiteerService thinkiteerService;
	
	public ThinkiteerController(ThinkiteerService thinkiteerService) {
		this.thinkiteerService=thinkiteerService;
	}
	
	@GetMapping
	public ResponseEntity<List<Thinkiteer>> getThinkiteers() {
		return new ResponseEntity<>(thinkiteerService.getThinkiteers(), HttpStatus.OK);
	}
	
	@GetMapping({"/{thinkiteerName}"})
	public ResponseEntity<String> getThinkiteer(@PathVariable String thinkiteerName) {
		return new ResponseEntity<>(thinkiteerService.getThinkiteerByName(thinkiteerName).getName(), HttpStatus.OK);
	}
	@PostMapping({"/new"})
	public ResponseEntity<Thinkiteer> addThinkiteer(@RequestBody Thinkiteer t){
		thinkiteerService.addThinkiteer(t);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("new", "/thinkiteers" + t.getName());
		return new ResponseEntity<>(t, httpHeaders, HttpStatus.CREATED);
	}
}
