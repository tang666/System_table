package com.solo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solo.model.DoctorLimit;
import com.solo.service.DoctorLimitService;

@Controller
@RequestMapping("/doctorLimit")
public class DoctorLimitController {

	@Resource
	private DoctorLimitService doctorLimitService;
	
	@RequestMapping("/")
	public String index(){
		return "redirect:/doctorLimit/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model,String managerName){
		List<DoctorLimit> doctorLimitList = doctorLimitService.getDoctorLimitList();
		model.addAttribute("doctorLimits", doctorLimitList);
		model.addAttribute("managerName", managerName);
		return "doctorLimit/doctorLimitList";
	}
	
	@RequestMapping("/toAdd")
	public String toadd(Model model,String name,DoctorLimit doctorLimit){
		
		boolean exist = false;
		
		List<DoctorLimit> doctorLimitList = doctorLimitService.getDoctorLimitList();
		for(DoctorLimit limit:doctorLimitList){
			if(limit.getDoctorName().equals(name)){
				model.addAttribute("doctorLimit",limit);
				exist = true;
			}
		}
		
		if(exist){
			model.addAttribute("name", name);
			return "doctorLimit/doctorLimitEditUnique";
		}
		
		model.addAttribute("name", name);
		return "doctorLimit/doctorLimitAdd";
	}
	
	@RequestMapping("/add")
	public String add(Model model,@Validated DoctorLimit doctorLimit,BindingResult bindingResult,String name){
		
		if(bindingResult.hasErrors()){
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			model.addAttribute("name", name);
			return "doctorLimit/doctorLimitAdd";
		}else{
			doctorLimitService.save(doctorLimit);
			model.addAttribute("name", name);
			model.addAttribute("doctorLimit", doctorLimit);
		}
		
		return "doctorLimit/doctorLimitEditUnique";
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(Model model,Long id,String managerName){
		DoctorLimit doctorLimit = doctorLimitService.findDoctorLimitById(id);
		model.addAttribute("doctorLimit", doctorLimit);
		model.addAttribute("managerName", managerName);
		return "doctorLimit/doctorLimitEdit";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,@Validated DoctorLimit doctorLimit,BindingResult bindingResult,String managerName,RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("managerName", managerName);
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			return "doctorLimit/doctorLimitEdit";
		}else{
			doctorLimitService.save(doctorLimit);
			attributes.addAttribute("managerName", managerName);
		}
		
		return "redirect:/doctorLimit/list";
	}
	
	@RequestMapping("/toEditDoctor")
	public String toEditDoctor(Model model,String name,DoctorLimit doctorLimit){
		List<DoctorLimit> doctorLimitList = doctorLimitService.getDoctorLimitList();
		for(DoctorLimit limit:doctorLimitList){
			if(limit.getDoctorName().equals(name)){
				model.addAttribute("doctorLimit",limit);
			}
		}
		model.addAttribute("name", name);
		
		return "doctorLimit/doctorLimitEditUnique";
	}
	
	@RequestMapping("/editDoctor")
	public String editDoctor(Model model,@Validated DoctorLimit doctorLimit,BindingResult bindingResult,String name,RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			model.addAttribute("name", name);
			
			return "doctorLimit/doctorLimitEditUnique";
		}else{
			doctorLimitService.save(doctorLimit);
			model.addAttribute("name", name);
			model.addAttribute("doctorLimit", doctorLimit);
		}
		
		return "doctorLimit/doctorLimitEditUnique";
	}
	
	@RequestMapping("/delete")
	public String edit(Long id,String managerName,RedirectAttributes attributes){
		doctorLimitService.delete(id);
		attributes.addAttribute("managerName", managerName);
		
		return "redirect:/doctorLimit/list";
	}
	
	@RequestMapping("/back")
	public String back(Model model,String currentManager){
		model.addAttribute("currentManager", currentManager);
		
		return "login/managerAction";
	}
	
	
}
