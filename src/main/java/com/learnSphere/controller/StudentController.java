package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Comments;
import com.learnSphere.entity.Course;
import com.learnSphere.entity.Lesson;
import com.learnSphere.entity.Users;
import com.learnSphere.services.CommentService;
import com.learnSphere.services.StudentServices;
import com.learnSphere.services.TrainerService;
import com.learnSphere.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {
	@Autowired
	StudentServices service;
	
	@Autowired
	UserService us;
	
	@Autowired
	TrainerService tService;
	
	@Autowired
	CommentService cService;
	@GetMapping("/viewLesson")
	public String viewLesson(@RequestParam("lessonId") int lessonId, Model model, HttpSession session) {
	    Lesson lesson = service.getLesson(lessonId);
	    
	    // Extract the video ID from the YouTube URL
	    String youtubeUrl = lesson.getLink();
	    String videoId;
	    if (youtubeUrl.contains("youtu.be/")) {
	        // Extract video ID from the shortened format
	        videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1);
	    } else {
	        // Extract video ID from the full URL format
	        videoId = youtubeUrl.substring(youtubeUrl.indexOf("?v=") + 3);
	    }
	    
	    lesson.setLink(videoId);
	    
	    model.addAttribute("lesson", lesson);
	    List<Comments> commentsList = cService.commentList();
	    model.addAttribute("comments", commentsList);
	    
	    return "myLesson";
	}

	
	@PostMapping("/addComment")
	public ResponseEntity<String> addComment(@RequestParam("comment") String comment) {
	    Comments c = new Comments();
	    c.setComment(comment);
	    cService.addComment(c);
	    return ResponseEntity.ok().build();
	}

	
	@GetMapping("/addComment2")
	public String commentsView(Model model) {
		
		List<Comments> commentsList=cService.commentList();
		model.addAttribute("comments",commentsList);
		
		return "myLesson";
	}

	@GetMapping("/purchase")
	public String showCourses(Model model,HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");

		List<Course> courseList=tService.courseList();
		model.addAttribute("courseList",courseList);
		model.addAttribute("loggedInUser",user);
		return "purchase";
	}
	
	
	@GetMapping("/fetchCourses")
	public String fetchCourses(Model model, HttpSession session) {
		
		Users loggedUser=(Users) session.getAttribute("loggedInUser");
		
		String email=loggedUser.getEmail();
				
		Users user=us.getUser(email);
			
		List<Course> courseList=user.getCourses();
		model.addAttribute("courseList",courseList);
		
		
		return "myCourses";
	}
	
	
}
