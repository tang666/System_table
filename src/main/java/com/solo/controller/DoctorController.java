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

import com.solo.model.Doctor;
import com.solo.service.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Resource
	private DoctorService doctorService;
	
	@RequestMapping("/")
	public String index(){
		return "redirect:/doctor/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model,String managerName){
		List<Doctor> doctorList = doctorService.getDoctorList();
		model.addAttribute("doctors", doctorList);
		model.addAttribute("managerName", managerName);
		return "doctor/doctorList";
	}
	
	@RequestMapping("/toAdd")
	public String toadd(Model model,Doctor doctor,String managerName){
		model.addAttribute("managerName", managerName);
		
		return "doctor/doctorAdd";
	}
	
	@RequestMapping("/add")
	public String add(Model model,@Validated Doctor doctor, BindingResult bindingResult,String managerName,RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("managerName", managerName);
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			return "doctor/doctorAdd";
		}else{
			doctorService.save(doctor);
			attributes.addAttribute("managerName", managerName);
		}
		
		return "redirect:/doctor/list";
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(Model model,Long id,String managerName){
		
		Doctor doctor = doctorService.findDoctorById(id);
		model.addAttribute("doctor", doctor);
		model.addAttribute("managerName", managerName);
		return "doctor/doctorEdit";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,@Validated Doctor doctor, BindingResult bindingResult,String managerName,RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("managerName", managerName);
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError error:errorList){
				System.out.println(error.getCode()+"---"+error.getArguments());
			}
			return "doctor/doctorEdit";
		}else{
			doctorService.save(doctor);
			attributes.addAttribute("managerName", managerName);
		}
		
		return "redirect:/doctor/list";
	}
	
	@RequestMapping("/delete")
	public String edit(Long id,String managerName,RedirectAttributes attributes){
		doctorService.delete(id);
		attributes.addAttribute("managerName", managerName);
		
		return "redirect:/doctor/list";
	}
	
	@RequestMapping("/back")
	public String back(Model model,String currentManager){
		model.addAttribute("currentManager", currentManager);
		
		return "login/managerAction";
	}
	
	@RequestMapping("/testlist")
	public String testlist(Model model,String managerName){
		
		List<Doctor> doctorList = doctorService.getDoctorList();
		model.addAttribute("doctors", doctorList);
		managerName = "manager";
		model.addAttribute("managerName", managerName);
		
		return "test/doctorList";
	}
	
	
	
}
