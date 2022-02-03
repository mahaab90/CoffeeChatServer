package com.thinkit.virtualCoffee.server.models;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Availability {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	int start;
	int end;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "thinkiteer_name", nullable = false)
	@JsonBackReference
	private Thinkiteer thinkiteer;
	
	
}
