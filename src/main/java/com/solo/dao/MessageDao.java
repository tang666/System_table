package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.solo.model.Message;

public interface MessageDao extends JpaRepository<Message,Long>{

	Message findById(long id);
    void deleteById(Long id);
	
}
