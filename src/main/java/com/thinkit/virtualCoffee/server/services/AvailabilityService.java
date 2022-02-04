package com.thinkit.virtualCoffee.server.services;

import java.util.List;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Chat;

public interface AvailabilityService {
  List<Availability> getAvailabilityByThinkiteer(String thinkiteer);
  List<Availability> getAvailabilities();
  //get availabilities with common slots
  List<Availability> getCommonAvailability(Availability availability);
  void insertAvailability(Availability availability,String thinkiteerName);
  void updateAvailability(Long id,Availability availability);
  Chat getChat(int start,int end,int offset, int numberOfParticipants);
}
