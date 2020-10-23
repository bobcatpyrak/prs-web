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
		Optional<Request> r = requestRepo.findById(id);
		if(r.isPresent())
		{
			return r;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
	}
	// Add a Request
	@PostMapping("/")
	public Request addRequest(@RequestBody Request r)
	{
		if(r != null)
			return requestRepo.save(r);
		else
		{
			System.out.println("No request given");
			return null;
		}
	}
	
	// Set a Request to be under review
	@PutMapping("/review")
	public Request SetRequests(@RequestBody Request r)
	{
		if(r.getTotal() > 50.00)
		{
			r.setStatus("Review");
			return requestRepo.save(r);
		}
		else
			return SetApproved(r);
	}
	
	// Set a Request to be approved
	@PutMapping("/approve")
	public Request SetApproved(@RequestBody Request r)
	{
		r.setStatus("Approved");
		return requestRepo.save(r);
	}
	
	// Set a Request to be rejected
	@PutMapping("/reject")
	public Request SetRejected(@RequestBody Request r)
	{
		r.setStatus("Rejected");
		return requestRepo.save(r);
	}
	
	// Edit a Request
	@PutMapping("/{id}")
	public Request updateRequest(@RequestBody Request r, @PathVariable int id)
	{
		if(id == r.getId())
			return requestRepo.save(r);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request id does not match.");
	}
	
	// Delete a Request
	@DeleteMapping("/{id}")
	public Optional<Request> deleteRequest(@PathVariable int id)
	{
		Optional<Request> r = requestRepo.findById(id);
		if(r.isPresent())
			requestRepo.deleteById(id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found.");
		
		return r;
	}
}