package com.solo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_schedule")
public class Schedule {

	@Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
	
	private String day;
    private String time;
    private String doctorName;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
}
