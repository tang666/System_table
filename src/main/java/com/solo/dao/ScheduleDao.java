package com.solo.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.solo.model.Schedule;

public interface ScheduleDao extends JpaRepository<Schedule,Long>{

	Schedule findById(long id);
    void deleteById(Long id);
	
}
