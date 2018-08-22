package com.solo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solo.form.DayForm;
import com.solo.form.DoctorScore;
import com.solo.form.ManagerRequire;
import com.solo.model.Application;
import com.solo.model.Doctor;
import com.solo.model.DoctorLimit;
import com.solo.model.Schedule;
import com.solo.service.ApplicationService;
import com.solo.service.DoctorLimitService;
import com.solo.service.DoctorService;
import com.solo.service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@Resource
	private DoctorLimitService doctorLimitService;
	@Resource 
	private ApplicationService applicationService;
	@Resource
    private ScheduleService scheduleService;	
	@Resource
	private DoctorService doctorService;
	
	
    public List<DoctorScore> calculateScore(String day,String time,List<DoctorScore> doctorScores ){
    	//List<DoctorScore> doctorCanUse = new ArrayList<>();
    	for(DoctorScore doctorScore:doctorScores){
    		if(day.equals("Monday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*doctorScore.getMondayPriority()*doctorScore.getPriorityMultiplier()+doctorScore.getAbleOverNumber());
    		}else if(day.equals("Tuesday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*doctorScore.getTuesdayPriority()*doctorScore.getPriorityMultiplier()+doctorScore.getAbleOverNumber());
    		}else if(day.equals("Wednesday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*doctorScore.getWednesdayPriority()*doctorScore.getPriorityMultiplier()+doctorScore.getAbleOverNumber());
    		}else if(day.equals("Thursday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*doctorScore.getThursdayPriority()*doctorScore.getPriorityMultiplier()+doctorScore.getAbleOverNumber());
    		}else if(day.equals("Friday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*doctorScore.getFridayPriority()*doctorScore.getPriorityMultiplier()+doctorScore.getAbleOverNumber());
    		}else if(day.equals("Saturday")||day.equals("Sunday")){
    			doctorScore.setScore(doctorScore.getAbleWorkNumber()*10+doctorScore.getAbleOverNumber()*2);
    		}
    		
    		if(day.equals(doctorScore.getHoliday())||doctorScore.getScore()<=0){
    			doctorScore.setUnableUse(false);
    		}else if(time.equals("Evening")&&doctorScore.getEveningNum()<=0){
    			doctorScore.setUnableUse(false);
    		}else{
    			doctorScore.setUnableUse(true);
    		}
    	}
    	Collections.sort(doctorScores);
    	return doctorScores;
    }
	
	
	@RequestMapping("/init")
	public String init(Model model,String managerName){
		
		List<Doctor> doctors = doctorService.getDoctorList();
		model.addAttribute("doctorNum", doctors.size());
		model.addAttribute("managerName", managerName);
		
		return "schedule/inputLimits";
	}
	
	@RequestMapping("/addLimit")
	public String addLimit(Model model,ManagerRequire managerRequire,String managerName){
		List<String> days = new ArrayList<>();
		List<String> times = new ArrayList<>();
		days.add("Monday"); days.add("Tuesday"); days.add("Wednesday");days.add("Thursday");days.add("Friday");days.add("Saturday");days.add("Sunday");
		times.add("Morning");times.add("Afternoon");times.add("Evening");
		List<DayForm> dayForms = new ArrayList<>();
		
		//获取医生的限制条件
		List<DoctorLimit> doctorLimits = doctorLimitService.getDoctorLimitList();
		List<Application> applications = applicationService.getApplicationList();
		List<Doctor> doctorList = doctorService.getDoctorList();
		//依次计算分数并进行排班
		List<DoctorScore> doctorScores = new ArrayList<>();
		
		for(DoctorLimit doctorLimit:doctorLimits){
			DoctorScore doctorScore = new DoctorScore();
			doctorScore.setDoctorName(doctorLimit.getDoctorName());
			//doctorScore.setAbleWorkNumber(doctorLimit.getNumberWork());
			doctorScore.setAbleOverNumber(doctorLimit.getNumberOvertime());
			doctorScore.setMondayPriority(doctorLimit.getMondayPriority());
			doctorScore.setTuesdayPriority(doctorLimit.getTuesdayPriority());
			doctorScore.setWednesdayPriority(doctorLimit.getWednesdayPriority());
			doctorScore.setThursdayPriority(doctorLimit.getThursdayPriority());
			doctorScore.setFridayPriority(doctorLimit.getFridayPriority());
			doctorScores.add(doctorScore);
		}
		for(Doctor dr:doctorList){
			for(DoctorScore doctorScore:doctorScores){
				if(doctorScore.getDoctorName().equals(dr.getName())){
					doctorScore.setAge(dr.getAge());
				}
			}
		}
		for(Application application:applications){
			for(DoctorScore doctorScore:doctorScores){
				if(application.getDoctorName().equals(doctorScore.getDoctorName())&&application.getStatus().equals("agree")){
					doctorScore.setHoliday(application.getDate());
				}
			}
		}
		int maxEvening = managerRequire.getMaxNumberforEveningWork();
		for(DoctorScore doctorScore:doctorScores){
			doctorScore.setEveningNum(maxEvening);
		}
		int youngDoctorWorkNum = managerRequire.getYoungDoctorWorkNum();
		int middleAgeDoctorWorkNum = managerRequire.getMiddleAgeDoctorWorkNum();
		int seniorDoctorWorkNum = managerRequire.getSeniorDoctorWorkNum();
		for(DoctorScore doctorScore:doctorScores){
			if(doctorScore.getAge()<40){
				doctorScore.setAbleWorkNumber(youngDoctorWorkNum);
				doctorScore.setPriorityMultiplier(seniorDoctorWorkNum);
			}else if(doctorScore.getAge()>=40&&doctorScore.getAge()<50){
				doctorScore.setAbleWorkNumber(middleAgeDoctorWorkNum);
				doctorScore.setPriorityMultiplier(middleAgeDoctorWorkNum);
			}else if(doctorScore.getAge()>=50){
				doctorScore.setAbleWorkNumber(seniorDoctorWorkNum);
				doctorScore.setPriorityMultiplier(youngDoctorWorkNum);
			}
			for(DoctorLimit doctorLimit:doctorLimits){
				if(doctorLimit.getDoctorName().equals(doctorScore.getDoctorName())){
					doctorLimit.setNumberWork(doctorScore.getAbleWorkNumber());
					doctorLimitService.save(doctorLimit);
					break;
				}
			}
		}
		
		int needNum=0;
		
		for(String day:days){
			for(String time:times){
				DayForm dayForm = new DayForm();
				dayForm.setDay(day);dayForm.setTime(time);
				List<String> doctors = new ArrayList<>();
				
				if(time.equals("Morning"))
				    needNum = managerRequire.getMorningDoctorNum();
				else if(time.equals("Afternoon"))
					needNum = managerRequire.getAfternoonDoctorNum();
				else if(time.equals("Evening"))
					needNum = managerRequire.getEveningDoctorNum();
				
				if(day.equals("Saturday")||day.equals("Sunday")){
					needNum = 1;
				}
				
				
				//对当前医生可用的医生计算分数
				doctorScores = calculateScore(day,time,doctorScores);
				for(DoctorScore doctor:doctorScores){
					if(doctor.isUnableUse()==true){
						doctors.add(doctor.getDoctorName());
						if(doctor.getAbleWorkNumber()>0){
							doctor.setAbleWorkNumber(doctor.getAbleWorkNumber()-1);
						}else if(doctor.getAbleWorkNumber()<=0&&doctor.getAbleOverNumber()>0){
							doctor.setAbleOverNumber(doctor.getAbleOverNumber()-1);
						}
						if(time.equals("Evening")){
							doctor.setEveningNum(doctor.getEveningNum()-1);
						}
						needNum--;
					}
					if(needNum<=0){
						break;
					}
				}
				
				System.out.println(day+" "+time+":"+needNum);
				
				while(needNum>0){
					doctors.add("Vacancy");
					needNum--;
				}
				dayForm.setDoctors(doctors);
				dayForms.add(dayForm);
			}
		}
		
		for(DayForm form:dayForms){
			System.out.println(form.getDay()+"---"+form.getTime());
			for(String name:form.getDoctors()){
				System.out.print(name+";");
			}
			System.out.println("###");
			
			if(form.getDay().equals("Monday")&&form.getTime().equals("Morning")){
				model.addAttribute("mondaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Monday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("mondayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Monday")&&form.getTime().equals("Evening")){
				model.addAttribute("mondayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Tuesday")&&form.getTime().equals("Morning")){
				model.addAttribute("tuesdaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Tuesday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("tuesdayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Tuesday")&&form.getTime().equals("Evening")){
				model.addAttribute("tuesdayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Wednesday")&&form.getTime().equals("Morning")){
				model.addAttribute("wednesdaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Wednesday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("wednesdayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Wednesday")&&form.getTime().equals("Evening")){
				model.addAttribute("wednesdayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Thursday")&&form.getTime().equals("Morning")){
				model.addAttribute("thursdaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Thursday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("thursdayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Thursday")&&form.getTime().equals("Evening")){
				model.addAttribute("thursdayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Friday")&&form.getTime().equals("Morning")){
				model.addAttribute("fridaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Friday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("fridayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Friday")&&form.getTime().equals("Evening")){
				model.addAttribute("fridayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Saturday")&&form.getTime().equals("Morning")){
				model.addAttribute("saturdaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Saturday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("saturdayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Saturday")&&form.getTime().equals("Evening")){
				model.addAttribute("saturdayeveningdrs", form.getDoctors());
			}else if(form.getDay().equals("Sunday")&&form.getTime().equals("Morning")){
				model.addAttribute("sundaymorningdrs", form.getDoctors());
			}else if(form.getDay().equals("Sunday")&&form.getTime().equals("Afternoon")){
				model.addAttribute("sundayafternoondrs", form.getDoctors());
			}else if(form.getDay().equals("Sunday")&&form.getTime().equals("Evening")){
				model.addAttribute("sundayeveningdrs", form.getDoctors());
			}
			
		}
		model.addAttribute("managerName", managerName);
		
		
		delete();
		add(dayForms);
		
		return "schedule/showSchedule";
	}
	
	
	public void add(List<DayForm> dayForms){
		for(DayForm form:dayForms){
			for(String doctorName:form.getDoctors()){
				Schedule schedule = new Schedule();
				schedule.setDay(form.getDay());
				schedule.setTime(form.getTime());
				schedule.setDoctorName(doctorName);
				scheduleService.save(schedule);
			}
		}
		
		
	}
	
	public void delete(){
		List<Schedule> scheduleList = scheduleService.getScheduleList();
		for(Schedule schedule:scheduleList){
			scheduleService.delete(schedule.getId());
		}
		
	}
	
	@RequestMapping("/list")
	public String list(Model model,String name){
		
		List<Schedule> scheduleList = scheduleService.getScheduleList();
		
		System.out.println("the length is:"+scheduleList.size()+"/////////////////////");
		
		List<String> mondaymorningdrs = new ArrayList<>();
		List<String> mondayafternoondrs = new ArrayList<>();
		List<String> mondayeveningdrs = new ArrayList<>();
		List<String> tuesdaymorningdrs = new ArrayList<>();
		List<String> tuesdayafternoondrs = new ArrayList<>();
		List<String> tuesdayeveningdrs = new ArrayList<>();
		List<String> wednesdaymorningdrs = new ArrayList<>();
		List<String> wednesdayafternoondrs = new ArrayList<>();
		List<String> wednesdayeveningdrs = new ArrayList<>();
		List<String> thursdaymorningdrs = new ArrayList<>();
		List<String> thursdayafternoondrs = new ArrayList<>();
		List<String> thursdayeveningdrs = new ArrayList<>();
		List<String> fridaymorningdrs = new ArrayList<>();
		List<String> fridayafternoondrs = new ArrayList<>();
		List<String> fridayeveningdrs = new ArrayList<>();
		List<String> saturdaymorningdrs = new ArrayList<>();
		List<String> saturdayafternoondrs = new ArrayList<>();
		List<String> saturdayeveningdrs = new ArrayList<>();
		List<String> sundaymorningdrs = new ArrayList<>();
		List<String> sundayafternoondrs = new ArrayList<>();
		List<String> sundayeveningdrs = new ArrayList<>();
		
		
		for(Schedule schedule:scheduleList){
			
			if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Morning")){
				mondaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Afternoon")){
				mondayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Evening")){
				mondayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Morning")){
				tuesdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Afternoon")){
				tuesdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Evening")){
				tuesdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Morning")){
				wednesdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Afternoon")){
				wednesdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Evening")){
				wednesdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Morning")){
				thursdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Afternoon")){
				thursdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Evening")){
				thursdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Morning")){
				fridaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Afternoon")){
				fridayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Evening")){
				fridayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Morning")){
				saturdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Afternoon")){
				saturdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Evening")){
				saturdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Morning")){
				sundaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Afternoon")){
				sundayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Evening")){
				sundayeveningdrs.add(schedule.getDoctorName());
			}
			
		}
		
		model.addAttribute("mondaymorningdrs", mondaymorningdrs);
		model.addAttribute("mondayafternoondrs", mondayafternoondrs);
		model.addAttribute("mondayeveningdrs", mondayeveningdrs);
		model.addAttribute("tuesdaymorningdrs", tuesdaymorningdrs);
		model.addAttribute("tuesdayafternoondrs", tuesdayafternoondrs);
		model.addAttribute("tuesdayeveningdrs", tuesdayeveningdrs);
		model.addAttribute("wednesdaymorningdrs", wednesdaymorningdrs);
		model.addAttribute("wednesdayafternoondrs", wednesdayafternoondrs);
		model.addAttribute("wednesdayeveningdrs", wednesdayeveningdrs);
		model.addAttribute("thursdaymorningdrs", thursdaymorningdrs);
		model.addAttribute("thursdayafternoondrs", thursdayafternoondrs);
		model.addAttribute("thursdayeveningdrs", thursdayeveningdrs);
		model.addAttribute("fridaymorningdrs", fridaymorningdrs);
		model.addAttribute("fridayafternoondrs", fridayafternoondrs);
		model.addAttribute("fridayeveningdrs", fridayeveningdrs);
		model.addAttribute("saturdaymorningdrs", saturdaymorningdrs);
		model.addAttribute("saturdayafternoondrs", saturdayafternoondrs);
		model.addAttribute("saturdayeveningdrs", saturdayeveningdrs);
		model.addAttribute("sundaymorningdrs", sundaymorningdrs);
		model.addAttribute("sundayafternoondrs", sundayafternoondrs);
		model.addAttribute("sundayeveningdrs", sundayeveningdrs);
		
		model.addAttribute("name", name);
		
		return "schedule/showSchedule2";
	} 
	
	
	@RequestMapping("/toEdit")
	public String toEdit(Model model,String managerName){
		
        List<Schedule> scheduleList = scheduleService.getScheduleList();
		
        List<DoctorLimit> doctorLimits = doctorLimitService.getDoctorLimitList();
		List<Application> applications = applicationService.getApplicationList();
		List<Doctor> doctorList = doctorService.getDoctorList();
        
		List<DoctorScore> doctorScores = new ArrayList<>();
		
		
		for(DoctorLimit doctorLimit:doctorLimits){
			DoctorScore doctorScore = new DoctorScore();
			doctorScore.setDoctorName(doctorLimit.getDoctorName());
			doctorScore.setAbleWorkNumber(doctorLimit.getNumberWork());
			doctorScore.setAbleOverNumber(doctorLimit.getNumberOvertime());
			doctorScore.setMondayPriority(doctorLimit.getMondayPriority());
			doctorScore.setTuesdayPriority(doctorLimit.getTuesdayPriority());
			doctorScore.setWednesdayPriority(doctorLimit.getWednesdayPriority());
			doctorScore.setThursdayPriority(doctorLimit.getThursdayPriority());
			doctorScore.setFridayPriority(doctorLimit.getFridayPriority());
			doctorScores.add(doctorScore);
		}
		for(Doctor dr:doctorList){
			for(DoctorScore doctorScore:doctorScores){
				if(doctorScore.getDoctorName().equals(dr.getName())){
					doctorScore.setAge(dr.getAge());
				}
			}
		}
		for(Application application:applications){
			for(DoctorScore doctorScore:doctorScores){
				if(application.getDoctorName().equals(doctorScore.getDoctorName())&&application.getStatus().equals("agree")){
					doctorScore.setHoliday(application.getDate());
				}
			}
		}
		
		
		List<String> mondaymorningdrs = new ArrayList<>();
		List<String> mondayafternoondrs = new ArrayList<>();
		List<String> mondayeveningdrs = new ArrayList<>();
		List<String> tuesdaymorningdrs = new ArrayList<>();
		List<String> tuesdayafternoondrs = new ArrayList<>();
		List<String> tuesdayeveningdrs = new ArrayList<>();
		List<String> wednesdaymorningdrs = new ArrayList<>();
		List<String> wednesdayafternoondrs = new ArrayList<>();
		List<String> wednesdayeveningdrs = new ArrayList<>();
		List<String> thursdaymorningdrs = new ArrayList<>();
		List<String> thursdayafternoondrs = new ArrayList<>();
		List<String> thursdayeveningdrs = new ArrayList<>();
		List<String> fridaymorningdrs = new ArrayList<>();
		List<String> fridayafternoondrs = new ArrayList<>();
		List<String> fridayeveningdrs = new ArrayList<>();
		List<String> saturdaymorningdrs = new ArrayList<>();
		List<String> saturdayafternoondrs = new ArrayList<>();
		List<String> saturdayeveningdrs = new ArrayList<>();
		List<String> sundaymorningdrs = new ArrayList<>();
		List<String> sundayafternoondrs = new ArrayList<>();
		List<String> sundayeveningdrs = new ArrayList<>();
		
		
		for(Schedule schedule:scheduleList){
			
			if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Morning")){
				mondaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Afternoon")){
				mondayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Monday")&&schedule.getTime().equals("Evening")){
				mondayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Morning")){
				tuesdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Afternoon")){
				tuesdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Tuesday")&&schedule.getTime().equals("Evening")){
				tuesdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Morning")){
				wednesdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Afternoon")){
				wednesdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Wednesday")&&schedule.getTime().equals("Evening")){
				wednesdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Morning")){
				thursdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Afternoon")){
				thursdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Thursday")&&schedule.getTime().equals("Evening")){
				thursdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Morning")){
				fridaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Afternoon")){
				fridayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Friday")&&schedule.getTime().equals("Evening")){
				fridayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Morning")){
				saturdaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Afternoon")){
				saturdayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Saturday")&&schedule.getTime().equals("Evening")){
				saturdayeveningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Morning")){
				sundaymorningdrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Afternoon")){
				sundayafternoondrs.add(schedule.getDoctorName());
			}else if(schedule.getDay().equals("Sunday")&&schedule.getTime().equals("Evening")){
				sundayeveningdrs.add(schedule.getDoctorName());
			}
			
			
			
			///////////////////////////////////////////////////////////
			for(DoctorScore ds:doctorScores){
				if(schedule.getDoctorName().equals(ds.getDoctorName())){
					if(ds.getAbleWorkNumber()>0){
						ds.setAbleWorkNumber(ds.getAbleWorkNumber()-1);
					}else if(ds.getAbleWorkNumber()<=0&&ds.getAbleOverNumber()>0){
						ds.setAbleOverNumber(ds.getAbleOverNumber()-1);
					}
					break;
				}
			}
			///////////////////////////////////////////////////////
			
		}
		
		model.addAttribute("mondaymorningdrs", mondaymorningdrs);
		model.addAttribute("mondayafternoondrs", mondayafternoondrs);
		model.addAttribute("mondayeveningdrs", mondayeveningdrs);
		model.addAttribute("tuesdaymorningdrs", tuesdaymorningdrs);
		model.addAttribute("tuesdayafternoondrs", tuesdayafternoondrs);
		model.addAttribute("tuesdayeveningdrs", tuesdayeveningdrs);
		model.addAttribute("wednesdaymorningdrs", wednesdaymorningdrs);
		model.addAttribute("wednesdayafternoondrs", wednesdayafternoondrs);
		model.addAttribute("wednesdayeveningdrs", wednesdayeveningdrs);
		model.addAttribute("thursdaymorningdrs", thursdaymorningdrs);
		model.addAttribute("thursdayafternoondrs", thursdayafternoondrs);
		model.addAttribute("thursdayeveningdrs", thursdayeveningdrs);
		model.addAttribute("fridaymorningdrs", fridaymorningdrs);
		model.addAttribute("fridayafternoondrs", fridayafternoondrs);
		model.addAttribute("fridayeveningdrs", fridayeveningdrs);
		model.addAttribute("saturdaymorningdrs", saturdaymorningdrs);
		model.addAttribute("saturdayafternoondrs", saturdayafternoondrs);
		model.addAttribute("saturdayeveningdrs", saturdayeveningdrs);
		model.addAttribute("sundaymorningdrs", sundaymorningdrs);
		model.addAttribute("sundayafternoondrs", sundayafternoondrs);
		model.addAttribute("sundayeveningdrs", sundayeveningdrs);
		
		
		model.addAttribute("doctorScores", doctorScores);
		model.addAttribute("managerName", managerName);
		
		
		
		
		
		return "schedule/editSchedule";
	}
	
	
	@PostMapping("/ajaxTest.action")
	public void saveEdit(@RequestBody List<DayForm> formList, HttpServletResponse resp)throws Exception{
		
		System.out.println("the length:"+formList.size());
		
		delete();
		
		add(formList);
		
		
	}
	
	
	
	
	
	
	
	
	
}
