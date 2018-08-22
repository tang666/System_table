package com.solo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solo.model.Doctor;
import com.solo.model.Manager;
import com.solo.model.Message;
import com.solo.service.DoctorService;
import com.solo.service.ManagerService;
import com.solo.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Resource
	private MessageService messageService;
	@Resource
	private DoctorService doctorService;
	@Resource
	private ManagerService managerService;
	
	
	@RequestMapping("/doctorsIn")
	public String doctorsIn(Model model,String name,Message message){
		List<Manager> managerList = managerService.getManagerList();
		model.addAttribute("managers", managerList);
		model.addAttribute("name", name);
		
		List<Message> messagefromdoctor = listfromdoctors(name);
		model.addAttribute("messagefromdoctor", messagefromdoctor);
		
		List<Message> messagetodoctor = listtodoctors(name);
		model.addAttribute("messagetodoctor", messagetodoctor);
		
		return "message/doctorMessage"; 
	}
	
	@RequestMapping("/managersIn")
	public String managersIn(Model model,String managerName,Message message){
		List<Doctor> doctorList = doctorService.getDoctorList();
		model.addAttribute("doctors", doctorList);
		model.addAttribute("managerName", managerName);
		
		List<Message> messagefrommanager = listfrommanagers(managerName);
		model.addAttribute("messagefrommanager", messagefrommanager);
		
		List<Message> messagetomanager = listtomanagers(managerName);
		model.addAttribute("messagetomanager", messagetomanager);
		
		return "message/managerMessage"; 
	}
	
	@RequestMapping("/addDoctorMessage")
	public String addDoctorMessage(Model model,@Validated Message message,BindingResult bindingResult,String name){
		
		if(bindingResult.hasErrors()){
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			
			List<Manager> managerList = managerService.getManagerList();
			model.addAttribute("managers", managerList);
			model.addAttribute("name", message.getFromName());
			
			List<Message> messagefromdoctor = listfromdoctors(message.getFromName());
			model.addAttribute("messagefromdoctor", messagefromdoctor);
			
			List<Message> messagetodoctor = listtodoctors(message.getFromName());
			model.addAttribute("messagetodoctor", messagetodoctor);
			
			return "message/doctorMessage";
		}else{
			message.setStatus("processing");
			messageService.save(message);
			
			List<Manager> managerList = managerService.getManagerList();
			model.addAttribute("managers", managerList);
			model.addAttribute("name",message.getFromName());
			
			List<Message> messagefromdoctor = listfromdoctors(message.getFromName());
			model.addAttribute("messagefromdoctor", messagefromdoctor);
			
			List<Message> messagetodoctor = listtodoctors(message.getFromName());
			model.addAttribute("messagetodoctor", messagetodoctor);
		}
		
		return "message/doctorMessage";
	}
	
	@RequestMapping("/addManagerMessage")
	public String addManagerMessage(Model model,@Validated Message message, BindingResult bindingResult,String managerName){
		
		if(bindingResult.hasErrors()){
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			List<Doctor> doctorList = doctorService.getDoctorList();
			model.addAttribute("doctors", doctorList);
			model.addAttribute("managerName", message.getFromName());
			
			List<Message> messagefrommanager = listfrommanagers(message.getFromName());
			model.addAttribute("messagefrommanager", messagefrommanager);
			
			List<Message> messagetomanager = listtomanagers(message.getFromName());
			model.addAttribute("messagetomanager", messagetomanager);
			return "message/managerMessage";
		}else{
			message.setStatus("processing");
			messageService.save(message);
			List<Doctor> doctorList = doctorService.getDoctorList();
			model.addAttribute("doctors", doctorList);
			model.addAttribute("managerName", message.getFromName());
			
			List<Message> messagefrommanager = listfrommanagers(message.getFromName());
			model.addAttribute("messagefrommanager", messagefrommanager);
			
			List<Message> messagetomanager = listtomanagers(message.getFromName());
			model.addAttribute("messagetomanager", messagetomanager);
		}
		
		return "message/managerMessage";
	}
	
    public List<Message> listfromdoctors(String doctorName){
		
		List<Message> messageList = messageService.getMessageList();
		List<Message> messagefromdoctor = new ArrayList<>();
		
		for(Message message:messageList){
			if(message.getFromName().equals(doctorName)){
				messagefromdoctor.add(message);
			}
		}
		
		return messagefromdoctor;
	}
	
	public List<Message> listfrommanagers(String managerName){
		
		List<Message> messageList = messageService.getMessageList();
		List<Message> messagefrommanager = new ArrayList<>();
		
		for(Message message:messageList){
			if(message.getFromName().equals(managerName)){
				messagefrommanager.add(message);
			}
		}
		
		return messagefrommanager;
	}
	
	@RequestMapping("/deleteDoctorMessage")
	public String deleteDoctorMessage(Model model,Long id,String name,Message message){
		messageService.delete(id);
		
		List<Manager> managerList = managerService.getManagerList();
		model.addAttribute("managers", managerList);
		model.addAttribute("name", name);
		
		List<Message> messagefromdoctor = listfromdoctors(name);
		model.addAttribute("messagefromdoctor", messagefromdoctor);
		
		List<Message> messagetodoctor = listtodoctors(name);
		model.addAttribute("messagetodoctor", messagetodoctor);
		
		return "message/doctorMessage";
	}
	
	@RequestMapping("/deleteManagerMessage")
	public String deleteManagerMessage(Model model,Long id,String managerName,Message message){
		messageService.delete(id);
		List<Doctor> doctorList = doctorService.getDoctorList();
		model.addAttribute("doctors", doctorList);
		model.addAttribute("managerName", managerName);
		
		List<Message> messagefrommanager = listfrommanagers(managerName);
		model.addAttribute("messagefrommanager", messagefrommanager);
		
		List<Message> messagetomanager = listtomanagers(managerName);
		model.addAttribute("messagetomanager", messagetomanager);
		
		return "message/managerMessage"; 
	}

	public List<Message> listtodoctors(String doctorName){
		
		List<Message> messageList = messageService.getMessageList();
		List<Message> messagetodoctor = new ArrayList<>();
		
		for(Message message:messageList){
			if(message.getToName().equals(doctorName)){
				messagetodoctor.add(message);
			}
		}
		
		return messagetodoctor;
	}
	
	public List<Message> listtomanagers(String managerName){
		
		List<Message> messageList = messageService.getMessageList();
		List<Message> messagetomanager = new ArrayList<>();
		
		for(Message message:messageList){
			if(message.getToName().equals(managerName)){
				messagetomanager.add(message);
			}
		}
		
		return messagetomanager;
	}
	
	@RequestMapping("/editDoctorMessage")
	public String editDoctorMessage(Model model,Long id,String st,Message message){
		
		Message mess = messageService.findMessageById(id);
		mess.setStatus(st);
		messageService.save(mess);
		
		System.out.println("the change status:"+st+"*******************************************************");
		
		List<Manager> managerList = managerService.getManagerList();
		model.addAttribute("managers", managerList);
		model.addAttribute("name", mess.getToName());
		
		List<Message> messagefromdoctor = listfromdoctors(mess.getToName());
		model.addAttribute("messagefromdoctor", messagefromdoctor);
		
		List<Message> messagetodoctor = listtodoctors(mess.getToName());
		model.addAttribute("messagetodoctor", messagetodoctor);
		
		return "message/doctorMessage";
	}
	
	@RequestMapping("/editManagerMessage")
	public String editManagerMessage(Model model,Long id,String st,Message message){
		
		Message mess = messageService.findMessageById(id);
		mess.setStatus(st);
		messageService.save(mess);
		System.out.println("the change status:"+st+"*******************************************************");
		
		
		List<Doctor> doctorList = doctorService.getDoctorList();
		model.addAttribute("doctors", doctorList);
		model.addAttribute("managerName", mess.getToName());
		
		List<Message> messagefrommanager = listfrommanagers(mess.getToName());
		model.addAttribute("messagefrommanager", messagefrommanager);
		
		List<Message> messagetomanager = listtomanagers(mess.getToName());
		model.addAttribute("messagetomanager", messagetomanager);
		
		return "message/managerMessage";
	}
	
	@RequestMapping("/backManager")
	public String backManager(Model model,String currentManager){
		model.addAttribute("currentManager", currentManager);
		return "login/managerAction";
	}

	@RequestMapping("/backDoctor")
	public String backDoctor(Model model,String currentDoctor){
		model.addAttribute("currentDoctor", currentDoctor);
		return "login/doctorAction";
	}
	
	
	
}
