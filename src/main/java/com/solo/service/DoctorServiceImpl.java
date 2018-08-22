package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.dao.DoctorDao;
import com.solo.model.Doctor;

@Service
public class DoctorServiceImpl implements DoctorService{

	private DoctorDao doctorDao;
	@Autowired
	DoctorServiceImpl(DoctorDao doctorDao){
		this.doctorDao = doctorDao;
	}
	
	@Override
	public List<Doctor> getDoctorList() {
		return doctorDao.findAll();
	}

	@Override
	public Doctor findDoctorById(long id) {
		return doctorDao.findById(id);
	}

	@Override
	public void save(Doctor doctor) {
		doctorDao.save(doctor);
	}

	@Override
	public void edit(Doctor doctor) {
		doctorDao.save(doctor);
	}

	@Override
	public void delete(long id) {
		doctorDao.deleteById(id);
	}

}
