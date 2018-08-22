package com.solo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="t_doctorLimit")
public class DoctorLimit {

	@Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
	
	@NotBlank(message="name can not be empty")
	private String doctorName;
	private String numberId;
	private int numberWork;
	
	@NotNull(message="numberOvertime can not be empty")
	@Min(value = 0, message = "number must greater than 0")
	@Max(value = 5, message = "number must less than 5")
	private int numberOvertime;
	
	
	private int mondayPriority;
	
	
	private int tuesdayPriority;
	
	
	private int wednesdayPriority;
	
	
	private int thursdayPriority;
	
	
	private int fridayPriority;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getNumberId() {
		return numberId;
	}
	public void setNumberId(String numberId) {
		this.numberId = numberId;
	}
	public int getNumberWork() {
		return numberWork;
	}
	public void setNumberWork(int numberWork) {
		this.numberWork = numberWork;
	}
	public int getNumberOvertime() {
		return numberOvertime;
	}
	public void setNumberOvertime(int numberOvertime) {
		this.numberOvertime = numberOvertime;
	}
	public int getMondayPriority() {
		return mondayPriority;
	}
	public void setMondayPriority(int mondayPriority) {
		this.mondayPriority = mondayPriority;
	}
	public int getTuesdayPriority() {
		return tuesdayPriority;
	}
	public void setTuesdayPriority(int tuesdayPriority) {
		this.tuesdayPriority = tuesdayPriority;
	}
	public int getWednesdayPriority() {
		return wednesdayPriority;
	}
	public void setWednesdayPriority(int wednesdayPriority) {
		this.wednesdayPriority = wednesdayPriority;
	}
	public int getThursdayPriority() {
		return thursdayPriority;
	}
	public void setThursdayPriority(int thursdayPriority) {
		this.thursdayPriority = thursdayPriority;
	}
	public int getFridayPriority() {
		return fridayPriority;
	}
	public void setFridayPriority(int fridayPriority) {
		this.fridayPriority = fridayPriority;
	}
	
}
