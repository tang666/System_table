package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solo.model.Doctor;
import com.solo.model.Manager;

public interface ManagerDao extends JpaRepository<Manager,Long>{
	Manager findById(long id);
    void deleteById(Long id);
}
