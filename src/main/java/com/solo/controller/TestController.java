package com.solo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solo.model.Schedule;
import com.solo.service.DoctorService;
import com.solo.service.ScheduleService;

@Controller
@RequestMapping("/test")
public class TestController {

	@Resource
	private DoctorService doctorService;
	@Resource
	private ScheduleService scheduleService;
	
	
	@RequestMapping("/")
	public String index(){
		return "test/testList";
	}
	
	
	@RequestMapping("/getTestList")
	@ResponseBody
	public  Map<String,Object> list(){
		
		List<String> tests = new ArrayList<>();
		tests.add("num1");
		tests.add("num2");
		tests.add("num3");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", tests);
		
		return map;
	}
	
	@GetMapping("/returnRes")
	public String returnRes(Integer id){
		
		System.out.println("the exist object is:"+id);
		
		return "test/testList";
	}
	
	
	
}
