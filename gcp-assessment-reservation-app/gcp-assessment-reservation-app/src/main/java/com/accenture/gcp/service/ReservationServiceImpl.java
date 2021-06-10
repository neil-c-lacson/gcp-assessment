package com.accenture.gcp.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.gcp.dao.ReservationRepository;
import com.accenture.gcp.entity.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	private ReservationRepository reservationRepository;
	
	@Autowired
	public ReservationServiceImpl(ReservationRepository theReservationRepository) {
		reservationRepository = theReservationRepository;
	}

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public Reservation findById(int theId) {
		Optional<Reservation> result = reservationRepository.findById(theId);
		
		Reservation theReservation = null;
		
		if (result.isPresent()) {
			theReservation = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find reservation id - " + theId);
		}
		
		return theReservation;
	}

	@Override
	public List<Reservation> findByUsername(String theUsername) {
		
		List<Reservation> results = null;
		
		if (theUsername != null && (theUsername.trim().length() > 0)) {
			results = reservationRepository.findByUsernameAllIgnoreCase(theUsername);
		}
		else {
			throw new RuntimeException("No reservations for" + theUsername);
		}
		
		return results;
	}

	@Override
	public void save(Reservation theReservation) {
		reservationRepository.save(theReservation);
		
	}

	@Override
	public void deleteById(int theId) {
		reservationRepository.deleteById(theId);
		
	}

	@Override
	public List<Reservation> findByDate(Date checkIn, Date checkOut) {
		List<Reservation> unavailableRooms = null;
		reservationRepository.findByDate(checkIn, checkOut);
		return unavailableRooms;
	}

	@Override
	public List<Reservation> searchBy(Date theDate) {
		List<Reservation> results = null;
		
		if (theDate != null) {
			results = reservationRepository.findByStartDateContainsOrEndDateContains(theDate, theDate);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

	@Override
	public List<Reservation> searchByJava(java.util.Date utilDate) {
		List<Reservation> results = null;
		
		if (utilDate != null) {
			results = reservationRepository.findByStartDateContainsOrEndDateContains(utilDate, utilDate);
		}
		else {
			results = findAll();
		}
		
		return results;
	}
	

}
