package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.LineItem;
import com.prs.db.LineItemRepo;

@CrossOrigin
@RestController
@RequestMapping("/lineItems")
public class LineItemController 
{
	@Autowired
	private LineItemRepo lineItemRepo;
	
	// List all LineItems
	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}
	
	// Get a LineItem by id
	@GetMapping("/{id}")
	public Optional<LineItem> getLineItem(@PathVariable int id) {
		Optional<LineItem> m = lineItemRepo.findById(id);
		if(m.isPresent())
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found");
	}
	// Add a LineItem
	@PostMapping("/")
	public LineItem addLineItem(@RequestBody LineItem p)
	{
		if(p != null)
			return lineItemRepo.save(p);
		else
		{
			System.out.println("No lineItem given");
			return null;
		}
	}
	
	// Edit a LineItem
	@PutMapping("/")
	public LineItem updateLineItem(@RequestBody LineItem p)
	{
		if(p != null)
			return lineItemRepo.save(p);
		else
		{
			System.out.println("No lineItem given");
			return null;
		}
	}
	
	// Delete a LineItem
	@DeleteMapping("/")
	public LineItem deleteLineItem(@RequestBody LineItem p)
	{
		if(p != null)
			lineItemRepo.delete(p);
		else
			System.out.println("No LineItem given");
		
		return p;
	}
}