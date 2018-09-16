package com.solo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solo.model.Schedule;
import com.solo.service.DoctorService;
import com.solo.service.ScheduleService;

@Controller
@RequestMapping("/table")
public class TableController {

	@Resource
	private DoctorService doctorService;
	@Resource
	private ScheduleService scheduleService;
	
	
	@RequestMapping("/")
	public String index(){
		return "table/tableList2";
	}
	
	@RequestMapping("/list")
	public @ResponseBody Map<String,Object> list(int limit,int offset){
		System.out.println("limit is:"+limit);
		System.out.println("offset is:"+offset);
		
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList = scheduleService.getScheduleList();
		int totalRecord = scheduleList.size();
		List<Schedule> resultSchedules = new ArrayList<>();
		for(int i=0;i<limit;i++){
			if(offset+i<totalRecord){
				resultSchedules.add(scheduleList.get(offset+i));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", totalRecord);
		map.put("data", resultSchedules);
		
		return map;
	}
	
	@RequestMapping("/selectDoctors")
	@ResponseBody
	public Map<String,Object> selectDoctors(@RequestParam("idList[]")List<Integer> idList){
		
		System.out.println("select list"+idList.size());
		
		for(int id:idList){
			Schedule schedule = new Schedule();
			Schedule scheduleTarget = scheduleService.findScheduleById((long)id);
			schedule.setDay(scheduleTarget.getDay());
			schedule.setDoctorName(schedule.getDoctorName());
			schedule.setTime(schedule.getTime());
			scheduleService.save(schedule);
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status","success");
		return map;
		
	}
	
	@RequestMapping("/selectList")
	@ResponseBody
	public Map<String,Object> selectList(int limit,int offset){
		
		System.out.println("limit is:"+limit);
		System.out.println("offset is:"+offset);
		
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList = scheduleService.getScheduleList();
		List<Schedule> resultSchedules = new ArrayList<>();
		if(scheduleList.size()>55){
			for(int i=55;i<scheduleList.size();i++){
				resultSchedules.add(scheduleList.get(i));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", resultSchedules.size()-55);
		map.put("data", resultSchedules);
		
		return map;
	}
	
	///////////////////////////////////////////////////////////////
	@RequestMapping("/list2")
	@ResponseBody
	public Map<String,Object> list2(int limit,int offset,String sortName,String sortOrder,String searchContent){
		System.out.println("limit is:"+limit);
		System.out.println("offset is:"+offset);
		System.out.println("sortName is:"+sortName);
		System.out.println("sortOrder is:"+sortOrder);
		System.out.println("search content is:"+searchContent);
		
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleList = scheduleService.getScheduleList();
		Collections.sort(scheduleList, new Comparator<Schedule>(){
			public int compare(Schedule s1, Schedule s2){
				if(sortOrder.equals("asc")){
					if(s1.getId().longValue() > s2.getId().longValue()){
						return 1;
					}else if(s1.getId().equals(s2.getId())){
						return 0;
					}
				}else if(sortOrder.equals("desc")){
					if(s1.getId().longValue() < s2.getId().longValue()){
						return 1;
					}else if(s1.getId().equals(s2.getId())){
						return 0;
					}
				}
				return -1;
			}
		});
		List<Schedule> scheduleSearchList = new ArrayList<>();
		if(searchContent.equals("")){
			scheduleSearchList = scheduleList;
		}else{
			for(Schedule schedule:scheduleList){
				if(searchContent.equals(schedule.getDay())){
					scheduleSearchList.add(schedule);
				}else if(searchContent.equals(schedule.getTime())){
					scheduleSearchList.add(schedule);
				}else if(searchContent.equals(schedule.getDoctorName())){
					scheduleSearchList.add(schedule);
				}
			}
		}
		
		//int totalRecord = scheduleList.size();
		int totalRecord = scheduleSearchList.size();
		System.out.println("the size of search list is:"+totalRecord);
		List<Schedule> resultSchedules = new ArrayList<>();
		for(int i=0;i<limit;i++){
			if(offset+i<totalRecord){
				//resultSchedules.add(scheduleList.get(offset+i));
				resultSchedules.add(scheduleSearchList.get(offset+i));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", totalRecord);
		map.put("data", resultSchedules);
		
		return map;
	}
	
	
	
}
