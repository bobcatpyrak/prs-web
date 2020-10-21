package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.Vendor;
import com.prs.db.VendorRepo;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController 
{
	@Autowired
	private VendorRepo vendorRepo;
	
	// List all Vendors
	@GetMapping("/")
	public List<Vendor> getAllVendors() {
		return vendorRepo.findAll();
	}
	
	// Get a Vendor by id
	@GetMapping("/{id}")
	public Optional<Vendor> getVendor(@PathVariable int id) {
		Optional<Vendor> m = vendorRepo.findById(id);
		if(m.isPresent())
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found");
	}
	// Add a Vendor
	@PostMapping("/")
	public Vendor addVendor(@RequestBody Vendor p)
	{
		if(p != null)
			return vendorRepo.save(p);
		else
		{
			System.out.println("No vendor given");
			return null;
		}
	}
	
	// Edit a Vendor
	@PutMapping("/")
	public Vendor updateVendor(@RequestBody Vendor p)
	{
		if(p != null)
			return vendorRepo.save(p);
		else
		{
			System.out.println("No vendor given");
			return null;
		}
	}
	
	// Delete a Vendor
	@DeleteMapping("/")
	public Vendor deleteVendor(@RequestBody Vendor p)
	{
		if(p != null)
			vendorRepo.delete(p);
		else
			System.out.println("No Vendor given");
		
		return p;
	}
}