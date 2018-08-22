package com.solo.service;

import java.util.List;

import com.solo.model.Message;


public interface MessageService {

	/**
	 * 返回医生列表
	 * @return  List
	 */
	public List<Message> getMessageList();
	
	/**
	 * 根据id返回医生
	 * @param id
	 * @retur
	 */
	public Message findMessageById(long id);
	
	/**
	 * 保存医生
	 * @param doctor
	 */
	public void save(Message message);
	
	/**
	 * 新增医生
	 * @param user
	 */
	public void edit(Message message);
	
	/**
	 * 删除医生
	 * @param id
	 */
	public void delete(long id);
	
	
}
