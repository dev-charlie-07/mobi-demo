package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Student;
import com.example.service.StudentService;

@RestController
@RequestMapping("/")
public class StudentController {

	@Autowired
	private StudentService studentService;

//	@GetMapping("students")
//	public List<Student> getAllStudent(@RequestBody Student student) {
//		System.out.println(student.getName());
//
//		String name = studentService.getDemoResponse(student);
//
//		System.out.println(name);
//
//		return studentService.getAllStudent();
//	}

	@GetMapping("students")
	public List<Student> getAllStudent(@RequestBody Student student, HttpServletRequest request) {
		request.setAttribute("student", student);

		System.out.println(student.getName());

//		int demo = student.getAddress().length();
//		System.out.println(demo);
		String name = studentService.getDemoResponse(student);

		System.out.println(name);
		System.out.println("Vanakkam da mapla");
System.out.println("Vanakkam da mapla");
		return studentService.getAllStudent();
	}
}
