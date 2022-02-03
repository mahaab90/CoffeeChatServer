package com.thinkit.virtualCoffee.server.services;

import java.util.List;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;

public interface ThinkiteerService {
    List<Thinkiteer> getThinkiteers();
    Thinkiteer getThinkiteerByName(String name);
    Thinkiteer getThinkiteerByAvailability(Availability availability);
}
