package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.dao.ManagerDao;

import com.solo.model.Manager;

@Service
public class ManagerServiceImpl implements ManagerService{

	private ManagerDao managerDao;
	@Autowired
	ManagerServiceImpl(ManagerDao managerDao){
		this.managerDao = managerDao;
	}
	
	@Override
	public List<Manager> getManagerList() {
		return managerDao.findAll();
	}

	@Override
	public Manager findManagerById(long id) {
		return managerDao.findById(id);
	}

	@Override
	public void save(Manager doctor) {
		managerDao.save(doctor);
	}

	@Override
	public void edit(Manager doctor) {
		managerDao.save(doctor);
	}

	@Override
	public void delete(long id) {
		managerDao.deleteById(id);
	}

}
