package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.Request;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController 
{
	@Autowired
	private RequestRepo requestRepo;
	
	// List all Requests
	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}
	
	// Get a Request by id
	@GetMapping("/{id}")
	public Optional<Request> getRequest(@PathVariable int id) {
		Optional<Request> m = requestRepo.findById(id);
		if(m.isPresent())
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
	}
	// Add a Request
	@PostMapping("/")
	public Request addRequest(@RequestBody Request p)
	{
		if(p != null)
			return requestRepo.save(p);
		else
		{
			System.out.println("No request given");
			return null;
		}
	}
	
	// Edit a Request
	@PutMapping("/")
	public Request updateRequest(@RequestBody Request p)
	{
		if(p != null)
			return requestRepo.save(p);
		else
		{
			System.out.println("No request given");
			return null;
		}
	}
	
	// Delete a Request
	@DeleteMapping("/")
	public Request deleteRequest(@RequestBody Request p)
	{
		if(p != null)
			requestRepo.delete(p);
		else
			System.out.println("No Request given");
		
		return p;
	}
}