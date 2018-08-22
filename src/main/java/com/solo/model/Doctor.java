package com.solo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="t_doctor")
public class Doctor {

	@Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    
	
	@NotBlank(message="name can not be empty")
	private String name;
	@NotBlank(message="password can not be empty")
	@Size(min = 4, max = 15,message="the size of password is between 4 and 15")
    private String password;
    private String numberId;
    @NotNull(message="age can not be empty")
    @Min(value = 25, message = "age must greater than 25")
    private int age;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNumberId() {
		return numberId;
	}
	public void setNumberId(String numberId) {
		this.numberId = numberId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
    
	
}
