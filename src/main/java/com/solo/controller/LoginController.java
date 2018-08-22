package com.solo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solo.model.Doctor;
import com.solo.model.Manager;
import com.solo.service.DoctorService;
import com.solo.service.ManagerService;

@Controller
public class LoginController {
	
	@Resource 
	private DoctorService doctorService;
	@Resource
	private ManagerService managerService;
	
	
	@RequestMapping("/")
	public String index(){
		return "login/index";
	}
	
	@RequestMapping("/managerLogin")
	public String managerLogin(Manager manager){
		return "login/managerLogin";
	}
	
	@RequestMapping("/managerCheck")
	public String managerCheck(Model model,Manager manager){
		
		List<Manager> managerList =  managerService.getManagerList();
		
		for(Manager managerl:managerList){
			if(managerl.getName().equals(manager.getName())){
				if(managerl.getPassword().equals(manager.getPassword())){
					System.out.println("manager login successful");
					model.addAttribute("managerName", managerl.getName());
					
					return "login/managerAction2";
				}
			}
		}
		
		System.out.println("manager login failure");
		return "login/managerLogin";
	}
	
	@RequestMapping("/doctorLogin")
	public String doctorLogin(Doctor doctor){
		return "login/doctorLogin";
	}
	
	@RequestMapping("/doctorCheck")
	public String doctorCheck(Model model,Doctor doctor){
		
		List<Doctor> doctorList = doctorService.getDoctorList();  
		
		for(Doctor doctorl:doctorList){
			if(doctorl.getName().equals(doctor.getName())){
				if(doctorl.getPassword().equals(doctor.getPassword())){
					System.out.println("doctor login successful");
					model.addAttribute("name",doctorl.getName() );
					
					return "login/doctorAction2";
				}
			}
		}
		
		System.out.println("doctor login failure");
		return "login/doctorLogin";
	}
	
	
	
	
	
	
	
	
	
	
	
}
