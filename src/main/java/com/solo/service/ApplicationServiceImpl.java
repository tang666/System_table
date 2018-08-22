package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.dao.ApplicationDao;
import com.solo.model.Application;

@Service
public class ApplicationServiceImpl implements ApplicationService{

	private ApplicationDao applicationDao;
	@Autowired
	ApplicationServiceImpl(ApplicationDao applicationDao){
		this.applicationDao = applicationDao;
	}
	
	@Override
	public List<Application> getApplicationList() {
		return applicationDao.findAll();
	}

	@Override
	public Application findApplicationById(long id) {
		return applicationDao.findById(id);
	}

	@Override
	public void save(Application application) {
		applicationDao.save(application);
	}

	@Override
	public void edit(Application application) {
		applicationDao.save(application);
	}

	@Override
	public void delete(long id) {
		applicationDao.deleteById(id);
	}


	
}
