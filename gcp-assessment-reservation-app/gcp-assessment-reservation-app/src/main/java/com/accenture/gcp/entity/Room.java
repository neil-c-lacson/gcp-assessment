package com.accenture.gcp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room")
public class Room {

	// define fields
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
			
	@Column(name="type")
	private String type;
			
	@Column(name="price")
	private double price;
			
	// define constructors
			
	public Room() {
				
	}

	public Room(int id, String type, double price) {
		this.id = id;
		this.type = type;
		this.price = price;
	}
			
	public Room(String type, double price) {
		this.type = type;
		this.price = price;
			}
		
	// define getter/setter

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
		
}











