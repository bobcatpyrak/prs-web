package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.*;
import com.prs.db.*;

@CrossOrigin
@RestController
@RequestMapping("/api/lines")
public class LineItemController 
{
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@Autowired
	private RequestRepo requestRepo;
	
	// List all LineItems
	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}
	
	// Get a LineItem by id
	@GetMapping("/{id}")
	public Optional<LineItem> getLineItem(@PathVariable int id) {
		Optional<LineItem> li = lineItemRepo.findById(id);
		if(li.isPresent())
		{
			return li;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found");
	}
	
	//Get all LineItems for a Request
	@GetMapping("/for-req/{id}")
	public List<LineItem> getLinesForPR(@PathVariable int id)
	{
		Request r = requestRepo.findById(id).get();
		List<LineItem> lis = lineItemRepo.findByRequest(r);
		return lis;
	}
	
	
	// Add a LineItem
	@PostMapping("/")
	public LineItem addLineItem(@RequestBody LineItem li)
	{
		
		
		if(li != null)
		{
			Request r = li.getRequest();

			lineItemRepo.save(li);
			recalculate(r);
			System.out.println("Total price of request: " + r.getTotal());
			return li;

		}
		else
		{
			System.out.println("No lineItem given");
			return null;
		}
	}
	
	// Edit a LineItem
	@PutMapping("/{id}")
	public LineItem updateLineItem(@RequestBody LineItem li, @PathVariable int id)
	{
		
		if(id == li.getId())
		{
			Request r = li.getRequest();

			lineItemRepo.save(li);
			recalculate(r);
			System.out.println("Total price of request: " + r.getTotal());
			return li;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem id does not match.");
	}
	
	// Delete a LineItem
	@DeleteMapping("/{id}")
	public Optional<LineItem> deleteLineItem(@PathVariable int id)
	{
		Optional<LineItem> oli = lineItemRepo.findById(id);
		
		if(oli.isPresent())
		{
			LineItem li = oli.get();
			Request r = li.getRequest();

			lineItemRepo.deleteById(id);
			recalculate(r);
			System.out.println("Total price of request: " + r.getTotal());

		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found.");
		
		return oli;
	}
	
	public void recalculate(Request r)
	{
		List<LineItem> lis = lineItemRepo.findByRequest(r);
		double newPrice = 0.0;
		for(LineItem li : lis)
		{
			double price = li.getProduct().getPrice() * li.getQuantity();
			newPrice += price;
		}
		
		r.setTotal(newPrice);
		r.setStatus("New");
		requestRepo.save(r);
		
	}
}