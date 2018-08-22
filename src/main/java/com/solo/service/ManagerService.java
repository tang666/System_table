package com.solo.service;

import java.util.List;


import com.solo.model.Manager;

public interface ManagerService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<Manager> getManagerList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @return
	 */
	public Manager findManagerById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(Manager manager);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(Manager manager);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
