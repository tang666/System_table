package com.solo.service;

import java.util.List;

import com.solo.model.Application;


public interface ApplicationService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<Application> getApplicationList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @retur
	 */
	public Application findApplicationById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(Application application);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(Application application);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
