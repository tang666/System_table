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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solo.model.Application;
import com.solo.model.Doctor;
import com.solo.service.ApplicationService;

@Controller
@RequestMapping("/application")
public class ApplicationController {

	@Resource
	private ApplicationService applicationService;
	
	@RequestMapping("/")
	public String index(){
		return "redirect:/application/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model,String managerName){
		List<Application> applicationList = applicationService.getApplicationList();
		model.addAttribute("applications", applicationList);
		model.addAttribute("managerName", managerName);
		
		return "application/applicationList";
	}
	
	@RequestMapping("/edita")
	public String edita(Model model,Long id,String managerName,RedirectAttributes attributes){
		
		Application app = applicationService.findApplicationById(id);
		app.setStatus("agree");
		applicationService.save(app);
		
		attributes.addAttribute("managerName", managerName);
		
		return "redirect:/application/list";
	}
	
	@RequestMapping("/editr")
	public String editr(Model model,Long id,String managerName,RedirectAttributes attributes){
		
		Application app = applicationService.findApplicationById(id);
		app.setStatus("reject");
		applicationService.save(app);
		attributes.addAttribute("managerName", managerName);
		
		return "redirect:/application/list";
	}
	
	
	@RequestMapping("/Doctorlist")
	public String Doctorlist(Model model,String name){
		
		//System.out.println("current user:"+name+"///////////////////////////////////////////////////");
		
		List<Application> applicationList = applicationService.getApplicationList();
		List<Application> listfordoctor = new ArrayList<>();
		for(Application application:applicationList){
			if(application.getDoctorName().equals(name)){
				//System.out.println("current user:"+name+" date:"+application.getDate()+" status:"+application.getStatus());
				listfordoctor.add(application);
			}
		}
		model.addAttribute("doctorList", listfordoctor);
		
		return "application/applicationListForDr";
	}
	
	@RequestMapping("/toAdd")
	public String toadd(Model model, String name,Application application){
		
		model.addAttribute("name",name);
		
		List<Application> applicationList = applicationService.getApplicationList();
		List<Application> listfordoctor = new ArrayList<>();
		for(Application app:applicationList){
			if(app.getDoctorName().equals(name)){
				//System.out.println("current user:"+name+" date:"+application.getDate()+" status:"+application.getStatus());
				listfordoctor.add(app);
			}
		}
		model.addAttribute("doctorList", listfordoctor);
		
		
		return "application/applicationAdd";
	}

	@RequestMapping("/add")
	public String add(Model model,@Validated Application application,BindingResult bindingResult,String name){
		
		if(bindingResult.hasErrors()){
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
            
			List<Application> applicationList = applicationService.getApplicationList();
			List<Application> listfordoctor = new ArrayList<>();
			for(Application app:applicationList){
				if(app.getDoctorName().equals(application.getDoctorName())){
					listfordoctor.add(app);
				}
			}
			model.addAttribute("doctorList", listfordoctor);
			model.addAttribute("name", name);
			
			return "application/applicationAdd";
		}else{
			application.setStatus("processing");
			applicationService.save(application);
			
			List<Application> applicationList = applicationService.getApplicationList();
			List<Application> listfordoctor = new ArrayList<>();
			for(Application app:applicationList){
				if(app.getDoctorName().equals(application.getDoctorName())){
					listfordoctor.add(app);
				}
			}
			model.addAttribute("doctorList", listfordoctor);
			model.addAttribute("name", name);
		}
		
		return "application/applicationAdd";
	}
	
	@RequestMapping("/back")
	public String back(Model model,String currentDoctor){
		model.addAttribute("currentDoctor",currentDoctor);
		
		return "login/doctorAction";
	}
	
	@RequestMapping("/backManager")
	public String backManager(Model model,String currentManager){
		model.addAttribute("currentManager", currentManager);
		
		return "login/managerAction";
	}
	
	
	
}
