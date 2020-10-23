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
	// Add a LineItem
	@PostMapping("/")
	public LineItem addLineItem(@RequestBody LineItem li)
	{
		
		
		if(li != null)
		{
			Request r = li.getRequest();
			Product p = li.getProduct();
			double price = p.getPrice();
			price *= li.getQuantity();
			r.setTotal(r.getTotal() + price);
			
			requestRepo.save(r);
			
			return lineItemRepo.save(li);
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
			Product p = li.getProduct();
			double price = p.getPrice() * li.getQuantity();
			LineItem li2 = lineItemRepo.findById(id).get();
			Product p2 = li2.getProduct();
			double price2 = p2.getPrice() * li2.getQuantity();
			price2 -= price;
			Request r = li.getRequest();
			r.setTotal(r.getTotal() + price2);
			requestRepo.save(r);
			
			return lineItemRepo.save(li);
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
			Product p = li.getProduct();
			double price = p.getPrice();
			price *= li.getQuantity();
			r.setTotal(r.getTotal() - price);
			
			requestRepo.save(r);
			
			lineItemRepo.deleteById(id);
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found.");
		
		return oli;
	}
}