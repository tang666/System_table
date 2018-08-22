package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solo.model.Doctor;

public interface DoctorDao extends JpaRepository<Doctor,Long>{

	Doctor findById(long id);
    void deleteById(Long id);
	
}
