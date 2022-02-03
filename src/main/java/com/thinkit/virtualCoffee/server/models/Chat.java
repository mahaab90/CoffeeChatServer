package com.thinkit.virtualCoffee.server.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Chat {

	List<String> participants;
	int start;
	int end;
}
