package com.thinkit.virtualCoffee.server.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Chat;
import com.thinkit.virtualCoffee.server.models.Availability;
import com.thinkit.virtualCoffee.server.models.Thinkiteer;
import com.thinkit.virtualCoffee.server.repositories.AvailabilityRepository;
import com.thinkit.virtualCoffee.server.repositories.ThinkiteerRepository;

@Service
public class AvailabilityServiceImp implements AvailabilityService {
	AvailabilityRepository availabilityRepository;
	ThinkiteerRepository thinkiteerRepository;

	public AvailabilityServiceImp(AvailabilityRepository availabilityRepository,
			ThinkiteerRepository thinkiteerRepository) {
		this.availabilityRepository = availabilityRepository;
		this.thinkiteerRepository = thinkiteerRepository;
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
		// Standerdize Time
		int start_GMT = (start + offset) % 24;
		int end_GMT = (end + offset) % 24;
		// Get all possible common slots
		List<Availability> all_availabilities = getAll_availabilities(start_GMT, end_GMT);

		// Calculate Chat counting the desired number of participants with the largest
		// slots number
		List<Chat> chats = countCommonSlot(all_availabilities, start_GMT, end_GMT, numberOfParticipants);

		Chat chat = getChat(chats,offset);

		System.out.println("Chat: ");
		System.out.println("Start: "+chat.getStart());
		System.out.println("End: "+chat.getEnd());
		System.out.println("Chat: "+chat.getParticipants().toString());
		
		
		return chat;
	}

	private Chat getChat(List<Chat> chats, int offset) {
		// TODO Auto-generated method stub
		Collections.sort(chats, new Comparator<Chat>() {
            @Override
            public int compare(Chat c1, Chat c2) {
                int diff_p=	c2.getParticipants().size() -c1.getParticipants().size() ;
                int diff_duration=(c2.getEnd()-c2.getStart())-(c1.getEnd()-c1.getStart());
                return diff_p != 0? diff_p: diff_duration;
           
            }
        });
		
		//adjust time
		Chat chat=chats.get(0);
		chat.setEnd((chat.getEnd()-offset)%24);
		chat.setStart((chat.getStart()-offset)%24);
		return chat;
	}

	private List<Chat> countCommonSlot(List<Availability> all_availabilities, int start_GMT, int end_GMT,
			int numberOfParticipants) {

		List<Chat> chats = new ArrayList<Chat>();

		// for each starting time, count how many chat we can make
		for (int s_index = start_GMT; s_index < end_GMT; s_index++) {

			List<Availability> start_Availability = new ArrayList<Availability>();
			final int index = s_index;

			// add availabilities with matched slots
			all_availabilities.forEach(av -> {
				if (av.getStart() <= index && av.getEnd() > index) {
					start_Availability.add(av);
				}
			});

			/*
			 * for the given start time count the possible chat we can make within a defined
			 * end time
			 */
			Map<Integer, List<Long>> map_slot = new TreeMap<Integer, List<Long>>((a, b) -> a > b ? -1 : a < b ? 1 : 0);
			for (Availability slot_init : start_Availability) {
				int s_end = slot_init.getEnd();
				if (!map_slot.containsKey(s_end)) {
					List<Long> list = new ArrayList<Long>();
					list.add(slot_init.getId());
					map_slot.put(s_end, list);
					for (Availability slot_compare : start_Availability) {
						if (slot_compare != slot_init && slot_compare.getEnd() >= s_end) {
							list = map_slot.get(s_end);
							list.add(slot_compare.getId());
							map_slot.put(s_end, list);
						}

					}
				}
			}

			/*
			 * get common slots where number of thinkiteers equals to desired number of
			 * participants or to a closed number starting with the largest time duration
			 */
			int max_number = 0;
			int detected_end = 0;
			for (Integer end_slot : map_slot.keySet()) {

				List<Long> list_slot = map_slot.get(end_slot);
				int number = list_slot.size();
				if (number >= numberOfParticipants) {
					max_number = numberOfParticipants;
					detected_end = end_slot;
					break;
				}

				if (number > max_number) {
					max_number = number;
					detected_end = end_slot;
					max_number = number;
				}
			}

			// Chat creation
			Chat chat = new Chat();
			chat.setStart(s_index);
			chat.setEnd(detected_end);
			List<Long> list_id = map_slot.get(detected_end);
			List<String> _participants = new ArrayList<String>();
			for (int i = 0; i < max_number; i++) {
				Long id_av = list_id.get(i);
				Availability av = availabilityRepository.findById(id_av).get();
				_participants.add(av.getThinkiteer().getName());
			}
			chat.setParticipants(_participants);
			chats.add(chat);
		}

		return chats;
	}

	private List<Availability> getAll_availabilities(int start, int end) {

		List<Availability> common_availabilities = new ArrayList<Availability>();
		List<Availability> all_availabilities = getAvailabilities();

		all_availabilities.forEach(av -> {
			int start_av = (av.getStart() + av.getThinkiteer().getOffset()) % 24;
			int end_av = (av.getEnd() + av.getThinkiteer().getOffset()) % 24;

			// if common slot found, adjust the availability
			if (start_av < end && end_av > start) {
				int start_new = start_av < start ? start : start_av;
				int end_new = end_av > end ? end : end_av;

				Availability av_common = new Availability();
				av_common.setId(av.getId());
				av_common.setStart(start_new);
				av_common.setEnd(end_new);
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
