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
@RequestMapping("/api/requests")
public class RequestController 
{
	@Autowired
	private RequestRepo requestRepo;
	
	// List all Requests
	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}
	
	// List all Requests in need of Review
	@GetMapping("/reviews")
	public List<Request> getAllRequestsInReview() {
		return requestRepo.findByStatus("Review");
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
	
	// Set a Request to be under review
	@PutMapping("/review")
	public Request SetRequests(@RequestBody Request p)
	{
		if(p.getTotal() > 50.00)
		{
			p.setStatus("Review");
			return requestRepo.save(p);
		}
		else
			return SetApproved(p);
	}
	
	// Set a Request to be approved
	@PutMapping("/approve")
	public Request SetApproved(@RequestBody Request p)
	{
		p.setStatus("Approved");
		return requestRepo.save(p);
	}
	
	// Set a Request to be rejected
	@PutMapping("/reject")
	public Request SetRejected(@RequestBody Request p)
	{
		p.setStatus("Rejected");
		return requestRepo.save(p);
	}
	
	// Edit a Request
	@PutMapping("/{id}")
	public Request updateRequest(@RequestBody Request p, @PathVariable int id)
	{
		if(id == p.getId())
			return requestRepo.save(p);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request id does not match.");
	}
	
	// Delete a Request
	@DeleteMapping("/{id}")
	public Optional<Request> deleteRequest(@PathVariable int id)
	{
		Optional<Request> p = requestRepo.findById(id);
		if(p.isPresent())
			requestRepo.deleteById(id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found.");
		
		return p;
	}
}