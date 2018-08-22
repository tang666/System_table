package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.solo.dao.ScheduleDao;
import com.solo.model.Schedule;


@Service
public class ScheduleServiceImpl implements ScheduleService{

	private ScheduleDao scheduleDao;
	@Autowired
	ScheduleServiceImpl(ScheduleDao scheduleDao){
		this.scheduleDao = scheduleDao;
	}
	
	@Override
	public List<Schedule> getScheduleList() {
		return scheduleDao.findAll();
	}

	@Override
	public Schedule findScheduleById(long id) {
		return scheduleDao.findById(id);
	}

	@Override
	public void save(Schedule schedule) {
		scheduleDao.save(schedule);
	}

	@Override
	public void edit(Schedule schedule) {
		scheduleDao.save(schedule);
	}

	@Override
	public void delete(long id) {
		scheduleDao.deleteById(id);
	}

}
