package com.sample.employee.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sample.employee.entity.Employee;
import com.sample.employee.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
	Logger logger =  LoggerFactory.getLogger(Employee.class);
	
	@Async
	public CompletableFuture<List<Employee>> saveEmployee(MultipartFile file) throws Exception
	{
		long start = System.currentTimeMillis();
		List<Employee> employees = parseCSVFile(file);
		logger.info("saving list of employee of size {}", employees.size(),""+Thread.currentThread().getName());
		employees = repo.saveAll(employees);
		long end = System.currentTimeMillis();
		logger.info("Total time {}", (end - start));
		return CompletableFuture.completedFuture(employees);
	}
	
	@Async
	public CompletableFuture<List<Employee>> findAllEmployees()
	{
		logger.info("get list of user by "+Thread.currentThread().getName());
		List<Employee> employees = repo.findAll();
		return CompletableFuture.completedFuture(employees);
	}
	
	
	private List<Employee> parseCSVFile(MultipartFile file) throws Exception {
		
		List<Employee> employee = new ArrayList<>();
		
		try
		{
			InputStreamReader streamReader = new InputStreamReader(file.getInputStream());
			BufferedReader bufReader = new BufferedReader(streamReader);
			String line;
			
			while ((line = bufReader.readLine()) != null) {
			  String[] data = line.split(",");
			  Employee emplDet = new Employee();
			  
			  emplDet.setId(0);
			  emplDet.setName(data[1]);
			  emplDet.setEmail(data[2]);
			  employee.add(emplDet);
			  
			}
			return employee;
		} catch(IOException e)
		{
			throw new Exception("Failed to parse file {}", e);
		}
		
		
	}

	

}
