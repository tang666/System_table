package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solo.model.DoctorLimit;

public interface DoctorLimitDao extends JpaRepository<DoctorLimit,Long>{

	DoctorLimit findById(long id);
    void deleteById(Long id);
	
}
