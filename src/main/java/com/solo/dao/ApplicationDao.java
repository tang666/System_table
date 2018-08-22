package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solo.model.Application;

public interface ApplicationDao extends JpaRepository<Application,Long>{

	Application findById(long id);
    void deleteById(Long id);
	
}
