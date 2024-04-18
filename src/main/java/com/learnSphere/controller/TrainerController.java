package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Course;
import com.learnSphere.entity.Lesson;
import com.learnSphere.entity.LessonDTO;
import com.learnSphere.services.TrainerService;

@Controller
public class TrainerController {
	@Autowired
	TrainerService tService;
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute Course course) {
		tService.addCourse(course);
		return "/trainerHome";
	}
	
	@PostMapping("/lesson")
	public String lesson(@ModelAttribute LessonDTO dto) {
		
		Course course=tService.getCourse(dto.getCourseId());
		
		Lesson lesson=new Lesson();
		lesson.setLessonName(dto.getLessonName());
		lesson.setTopics(dto.getTopics());
		lesson.setLink(dto.getLink());
		lesson.setCourse(course);
		tService.addLesson(lesson);
		
		course.getLessons().add(lesson);
		
		tService.saveCourse(course);
		
		return "/trainerHome";
	}
	@GetMapping("/showCourses")
	public String showCourses(Model model) {
		List<Course> courseList=tService.courseList();
		model.addAttribute("courseList",courseList);
		return "courses";
	}
	
	@GetMapping("/showCourses2")
	public String showCourses2(Model model) {
	    List<Course> courseList = tService.courseList(); // Fetch courses along with their lessons
	    model.addAttribute("courseList", courseList);
	    return "lesson";
	}

}
