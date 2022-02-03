package com.thinkit.virtualCoffee.server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Slots implements Comparable<Slots>{

	
	Long id;
	int start;
	int end;
	@Override
	public int compareTo(Slots av) {
		if( av.getStart() > start )
			return 1;
		if(av.getStart() == start && av.getEnd()> end)
			return 1;
		
		return 0;
	}
}
