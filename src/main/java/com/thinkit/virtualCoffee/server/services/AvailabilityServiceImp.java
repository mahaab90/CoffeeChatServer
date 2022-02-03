package com.thinkit.virtualCoffee.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Chat;
import com.thinkit.virtualCoffee.server.models.Slots;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;
import com.thinkit.virtualCoffee.server.repositories.AvailabilityRepository;
import com.thinkit.virtualCoffee.server.repositories.ThinkiteerRepository;

@Service
public class AvailabilityServiceImp implements AvailabilityService{
	AvailabilityRepository availabilityRepository;
	ThinkiteerRepository thinkiteerRepository;
	
	public AvailabilityServiceImp(AvailabilityRepository availabilityRepository, ThinkiteerRepository thinkiteerRepository) {
		this.availabilityRepository=availabilityRepository;
		this.thinkiteerRepository=thinkiteerRepository;
	}
	@Override
	public List<Availability> getAvailabilityByThinkiteer(Thinkiteer thinkiteer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Availability> getCommonAvailability(Availability availability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InsertAvailability(Availability availability, String thinkiteerName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Chat getChat(int start, int end, int offset, int numberOfParticipants) {
		// TODO Auto-generated method stub
		int start_GMT=(start+offset)%24;
		int end_GMT=(end +offset)%24;
		List<Slots> all_availabilities=getAll_availabilities(start_GMT,end_GMT);
		countCommonSlot(all_availabilities,start_GMT,end_GMT,numberOfParticipants);
		
		all_availabilities.forEach(av->{
			
		});
		
		
		return null;
	}
	private void countCommonSlot(List<Slots> all_availabilities, int start_GMT, int end_GMT, int numberOfParticipants) {
		// TODO Auto-generated method stub
		List<Chat> chats=new ArrayList<Chat>();
		
		for(int s_index=start_GMT;s_index<end_GMT;s_index++) {
			//get slots where start <= s_index
			System.out.println("get slots where start <= s_index");
			System.out.println("s_index "+s_index);
			List<Slots> start_slots=new ArrayList<Slots>();
			final int index=s_index;
			all_availabilities.forEach(av->{
				if(av.getStart()<= 	index) {
					start_slots.add(av);
					System.out.println("start: "+av.getStart());
				}
			});
			
			//common slot recuperation
			System.out.println("common slot recuperation ");
			Map<Integer,List<Long>> map_slot=new TreeMap<Integer,List<Long>>(
					(a,b)->  a>b ?1:0);
			for(Slots slot_init:start_slots) {
				int s_end=slot_init.getEnd();

				System.out.println("s_end "+s_end);
				if(map_slot.get(s_end) == null) {
					List<Long> list=new ArrayList<Long>();
					list.add(slot_init.getId());
					map_slot.put(s_end, list);
					for(Slots slot_compare:start_slots) {
						if(slot_compare!= slot_init && slot_compare.getEnd() >= s_end) {
							list=map_slot.get(s_end);
							list.add(slot_compare.getId());
							map_slot.put(s_end, list);
							System.out.println("slot_compare s_end "+slot_compare.getEnd());
						}
							
					}
				 }
			}
			
			//get common slot from the current start
			int max_number=0;
			int detected_end=0;
			for(Integer end_slot:map_slot.keySet()) {
				
				List<Long> list_slot=map_slot.get(end_slot);
				int number=list_slot.size();
				max_number=Math.max(max_number, number);
				if(number>=numberOfParticipants) {
					max_number=numberOfParticipants;
					detected_end=end_slot;
					break;
				}
				
				if(number>max_number) {
					max_number=number;
					detected_end=end_slot;
					max_number=number;
				}
			}
			
			//Chat creation
			System.out.println("Chat creation");
			System.out.println("start "+s_index);
			System.out.println("end "+detected_end);
			Chat chat =new Chat();
			chat.setStart(s_index);
			chat.setEnd(detected_end);
			List<Long> list_id=map_slot.get(detected_end);
			List<String> _participants=new ArrayList<String>();
			for(int i=0;i<max_number;i++) {
				Long id_av=list_id.get(i);
				Availability av=availabilityRepository.findById(id_av).get();
				_participants.add(av.getThinkiteer().getName());
				System.out.println("participant "+_participants.get(i));
			}
			chats.add(chat);
		}
		
		
	}
	private List<Slots> getAll_availabilities(int start, int end) {
		// TODO Auto-generated method stub
		List<Slots> common_availabilities=new ArrayList<Slots>();
		List<Availability> all_availabilities=getAvailabilities();
		
		all_availabilities.forEach(av->{
			int start_av=(av.getStart()+av.getThinkiteer().getOffset())%24;
			int end_av=(av.getEnd()+av.getThinkiteer().getOffset())%24;
			
			//if common slot found, adjust the availability
			if(start_av < end && end_av > start) {
				int start_new=start_av <start ?start:start_av;
				int end_new= end_av > end ?end:end_av;
				
				Slots av_common=new Slots();
				av_common.setId(av.getId());
				av_common.setStart(start_new);
				av_common.setEnd(end_new);
				System.out.println(av.getThinkiteer().getName()+ "start "+start_new+" end "+end_new);			
				common_availabilities.add(av_common);
			}
		});
		
		return common_availabilities;
	}
	@Override
	public List<Availability> getAvailabilities() {
		// TODO Auto-generated method stub
		return availabilityRepository.findAll();
	}
	
}
