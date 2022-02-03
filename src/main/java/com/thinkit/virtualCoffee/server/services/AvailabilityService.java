package com.thinkit.virtualCoffee.server.services;

import java.util.List;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Chat;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;

public interface AvailabilityService {
  List<Availability> getAvailabilityByThinkiteer(Thinkiteer thinkiteer);
  List<Availability> getAvailabilities();
  //get availabilities with common slots
  List<Availability> getCommonAvailability(Availability availability);
  void InsertAvailability(Availability availability,String thinkiteerName);
  Chat getChat(int start,int end,int offset, int numberOfParticipants);
}
