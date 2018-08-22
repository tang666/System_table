package com.solo.form;

public class DoctorScore implements Comparable<DoctorScore>{

	private String doctorName;
	private int ableWorkNumber;
	private int ableOverNumber;
	private int score;
	private String holiday;
	private int mondayPriority;
	private int tuesdayPriority;
	private int wednesdayPriority;
	private int thursdayPriority;
	private int fridayPriority;
	private boolean unableUse;
	private int eveningNum;
	private int age;
	private int priorityMultiplier;
	
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public int getAbleWorkNumber() {
		return ableWorkNumber;
	}
	public void setAbleWorkNumber(int ableWorkNumber) {
		this.ableWorkNumber = ableWorkNumber;
	}
	public int getAbleOverNumber() {
		return ableOverNumber;
	}
	public void setAbleOverNumber(int ableOverNumber) {
		this.ableOverNumber = ableOverNumber;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public boolean isUnableUse() {
		return unableUse;
	}
	public void setUnableUse(boolean unableUse) {
		this.unableUse = unableUse;
	}
	public int getEveningNum() {
		return eveningNum;
	}
	public void setEveningNum(int eveningNum) {
		this.eveningNum = eveningNum;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getPriorityMultiplier() {
		return priorityMultiplier;
	}
	public void setPriorityMultiplier(int priorityMultiplier) {
		this.priorityMultiplier = priorityMultiplier;
	}
	
	
	@Override
	public int compareTo(DoctorScore o){
		int i = o.getScore() - this.getScore();
		if(i==0){
			return o.getAbleWorkNumber()-this.getAbleWorkNumber();
		}
		return i;
	}
	
	
	
}
