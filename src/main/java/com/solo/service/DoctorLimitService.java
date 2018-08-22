package com.solo.service;

import java.util.List;

import com.solo.model.DoctorLimit;

public interface DoctorLimitService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<DoctorLimit> getDoctorLimitList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @return
	 */
	public DoctorLimit findDoctorLimitById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(DoctorLimit doctorLimit);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(DoctorLimit doctorLimit);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
