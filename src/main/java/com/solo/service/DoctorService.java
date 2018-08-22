package com.solo.service;

import java.util.List;

import com.solo.model.Doctor;

public interface DoctorService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<Doctor> getDoctorList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @return
	 */
	public Doctor findDoctorById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(Doctor doctor);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(Doctor doctor);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
