package com.thinkit.virtualCoffee.server.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Thinkiteer {

	@Id
	@Column(updatable = false, nullable = false)
	String name;
	@Column(name="_offset") 
	int offset;
	
	@OneToMany(mappedBy = "thinkiteer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Availability> availibilities;
	
	public void setAvailibilities(Set<Availability> availibilities) {
		this.availibilities = availibilities;
		for (Availability _availability : availibilities) {
			_availability.setThinkiteer(this);
		}
	}
}
