package com.accenture.gcp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.gcp.dao.RoomRepository;
import com.accenture.gcp.entity.Room;

@Service
public class RoomServiceImpl implements RoomService{
	
	private RoomRepository roomRepository;
	
	@Autowired
	public RoomServiceImpl(RoomRepository theRoomRepository) {
		roomRepository = theRoomRepository;
	}

	@Override
	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	@Override
	public Room findById(int theId) {
		Optional<Room> result = roomRepository.findById(theId);
		
		Room theRoom = null;
		
		if (result.isPresent()) {
			theRoom = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find room id - " + theId);
		}
		
		return theRoom;
	}

	@Override
	public void save(Room theRoom) {
		roomRepository.save(theRoom);		
	}

	@Override
	public void deleteById(int theId) {
		roomRepository.deleteById(theId);
		
	}

}
