package com.accenture.gcp.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.gcp.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	
	public List<Reservation> findByUsernameAllIgnoreCase(String username);
	
	@Query(value = "SELECT * FROM reservation \r\n"
			+ "WHERE room_number=?1 AND username=?2",
			nativeQuery = true)
	public List<Reservation> findByUserAndRoom(int num, String user);
	
//	@Query(value = "SELECT * FROM reservation \r\n"
//			+ "WHERE start_date=?1 BETWEEN start_date AND end_date",
//			nativeQuery = true)
	
	@Query(value = "SELECT * FROM reservation \r\n"
			+ "WHERE start_date BETWEEN ?1  AND ?2 OR end_date BETWEEN ?1  AND ?2", 
			nativeQuery = true)
	public List<Reservation> findByDate(Date checkIn, Date checkOut);
	
	@Query(value = "SELECT * FROM reservation \r\n"
			+ "WHERE start_date=?1 OR end_date=?2", 
			nativeQuery = true)
	public List<Reservation> findByStartDateContainsOrEndDateContains(Date checkIn, Date checkOut);

	public List<Reservation> findByStartDateContainsOrEndDateContains(java.util.Date utilDate,
			java.util.Date utilDate2);

}
