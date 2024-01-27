package com.sample.employee.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sample.employee.entity.Employee;
import com.sample.employee.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@PostMapping(value = "/employees", consumes = (MediaType.MULTIPART_FORM_DATA_VALUE), produces ="application/json")
	public ResponseEntity saveEmployeeDet(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
		
		for(MultipartFile file: files) {
			service.saveEmployee(file);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
		
	@GetMapping(value = "/getEmployeeByThread", produces ="application/json")
	public ResponseEntity getEmployeeDetails()
	{
		CompletableFuture<List<Employee>> employee1 = service.findAllEmployees();
		CompletableFuture<List<Employee>> employee2 = service.findAllEmployees();
		CompletableFuture<List<Employee>> employee3 = service.findAllEmployees();
		CompletableFuture<List<Employee>> employee4 = service.findAllEmployees();
		CompletableFuture.allOf(employee1,employee2,employee3,employee4).join();
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
	
	
	

}
