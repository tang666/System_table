package com.solo.service;

import java.util.List;

import com.solo.model.Schedule;

public interface ScheduleService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<Schedule> getScheduleList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @return
	 */
	public Schedule findScheduleById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(Schedule schedule);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(Schedule schedule);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
