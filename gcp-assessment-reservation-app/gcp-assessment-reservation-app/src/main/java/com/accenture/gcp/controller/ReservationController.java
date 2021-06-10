package com.accenture.gcp.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.accenture.gcp.entity.Reservation;
import com.accenture.gcp.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
	
	Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	private ReservationService reservationService;
	
	public ReservationController(ReservationService theReservationService) {
			reservationService = theReservationService;
	}
	
	@GetMapping("/listReservation")
	public String listReservation(Model theModel) {
		
		String username;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		// get room from db
		List<Reservation> theReservations = reservationService.findByUsername(username);
		
		// add to the spring model
		theModel.addAttribute("reservations", theReservations);
		
		return "/reservations/list-reservations";
	}
	
	@GetMapping("/showFormForAddReservation")
	public String showFormForAddReservation(Model theModel) {
		
		// create model attribute to bind form data
		Reservation theReservation = new Reservation();
				
		theModel.addAttribute("reservation", theReservation);
		
		return "/reservations/reservation-form";
	}

	@GetMapping("/showFormForUpdateReservation")
	public String showFormForUpdateReservation(@RequestParam("reservationId") int theId,
									Model theModel) {
		
		// get the reservation from the service
		Reservation theReservation = reservationService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("reservation", theReservation);
		
		// send over to our form
		return "/reservations/reservation-form";			
	}
	
	@PostMapping("/saveReservation")
	public String saveReservation(@ModelAttribute("reservation") Reservation theReservation) {
		
		// save the employee
		reservationService.save(theReservation);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/reservations/listReservation";
	}
	
	@GetMapping("/deleteReservation")
	public String deleteRoom(@RequestParam("reservationId") int theId) {
		
		reservationService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/reservations/listReservation";
		
	}
	
//	@GetMapping("/search")
//	public String search(@RequestParam("checkIn") Date checkIn, @RequestParam("checkOut") Date checkOut,
//						 Model theModel) {
//		
//		List<Reservation> theReservations = reservationService.findByDate(checkIn, checkOut);
//		
//		// add to the spring model
//		theModel.addAttribute("reservations", theReservations);
//		logger.debug("A DEBUG Message");
//		
//		return "/reservations/list-reservations";
//		
//	}
	
	@GetMapping("/search")
	public String search(@RequestParam("theDate") Date theDate,
						 Model theModel) {
				
		// delete the employee
		List<Reservation> theReservations = reservationService.searchBy(theDate);
				
		// add to the spring model
		theModel.addAttribute("reservations", theReservations);
				
		// send to /employees/list
		return "/reservations/list-reservations";
		
	}
	

}
