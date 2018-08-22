package com.solo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.dao.MessageDao;
import com.solo.model.Message;



@Service
public class MessageServiceImpl implements MessageService{

	private MessageDao messageDao;
	@Autowired
	MessageServiceImpl(MessageDao messageDao){
		this.messageDao = messageDao;
	}
	
	@Override
	public List<Message> getMessageList() {
		return messageDao.findAll();
	}

	@Override
	public Message findMessageById(long id) {
		return messageDao.findById(id);
	}

	@Override
	public void save(Message message) {
		messageDao.save(message);
	}

	@Override
	public void edit(Message message) {
		messageDao.save(message);
	}

	@Override
	public void delete(long id) {
		messageDao.deleteById(id);
	}


	
}
