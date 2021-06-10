package com.accenture.gcp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.accenture.gcp.entity.Room;
import com.accenture.gcp.service.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	private RoomService roomService;
		
	public RoomController(RoomService theRoomService) {
			roomService = theRoomService;
	}
	
	@GetMapping("/listRoom")
	public String listRooms(Model theModel) {
		
		// get room from db
		List<Room> theRooms = roomService.findAll();
		
		// add to the spring model
		theModel.addAttribute("rooms", theRooms);
		
		return "/rooms/list-rooms";
	}
	
	@GetMapping("/showFormForAddRoom")
	public String showFormForAddRoom(Model theModel) {
		
		// create model attribute to bind form data
		Room theRoom = new Room();
		
		theModel.addAttribute("room", theRoom);
		
		return "/rooms/room-form";
	}
	
	@GetMapping("/showFormForUpdateRoom")
	public String showFormForUpdateRoom(@RequestParam("roomId") int theId,
									Model theModel) {
		
		// get the room from the service
		Room theRoom = roomService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("room", theRoom);
		
		// send over to our form
		return "/rooms/room-form";			
	}
	
	@PostMapping("/saveRoom")
	public String saveRoom(@ModelAttribute("room") Room theRoom) {
		
		// save the employee
		roomService.save(theRoom);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/rooms/listRoom";
	}
	
	@GetMapping("/deleteRoom")
	public String deleteRoom(@RequestParam("roomId") int theId) {
		
		roomService.deleteById(theId);
		
		return "redirect:/rooms/listRoom";
		
	}

}
