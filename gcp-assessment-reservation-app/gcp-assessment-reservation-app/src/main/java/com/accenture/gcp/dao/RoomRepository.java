package com.accenture.gcp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.gcp.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {


}
