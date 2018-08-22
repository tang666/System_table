package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.solo.dao.DoctorLimitDao;
import com.solo.model.DoctorLimit;


@Service
public class DoctorLimitServiceImpl implements DoctorLimitService{

	private DoctorLimitDao doctorLimitDao;
	@Autowired
	DoctorLimitServiceImpl(DoctorLimitDao doctorLimitDao){
		this.doctorLimitDao = doctorLimitDao;
	}
	
	@Override
	public List<DoctorLimit> getDoctorLimitList() {
		return doctorLimitDao.findAll();
	}

	@Override
	public DoctorLimit findDoctorLimitById(long id) {
		return doctorLimitDao.findById(id);
	}

	@Override
	public void save(DoctorLimit doctorLimit) {
		doctorLimitDao.save(doctorLimit);
	}

	@Override
	public void edit(DoctorLimit doctorLimit) {
		doctorLimitDao.save(doctorLimit);
	}

	@Override
	public void delete(long id) {
		doctorLimitDao.deleteById(id);
	}

}
