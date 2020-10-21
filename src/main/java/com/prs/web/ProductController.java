package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.Product;
import com.prs.db.ProductRepo;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController 
{
	@Autowired
	private ProductRepo productRepo;
	
	// List all Products
	@GetMapping("/")
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	// Get a Product by id
	@GetMapping("/{id}")
	public Optional<Product> getProduct(@PathVariable int id) {
		Optional<Product> m = productRepo.findById(id);
		if(m.isPresent())
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
	}
	// Add a Product
	@PostMapping("/")
	public Product addProduct(@RequestBody Product p)
	{
		if(p != null)
			return productRepo.save(p);
		else
		{
			System.out.println("No product given");
			return null;
		}
	}
	
	// Edit a Product
	@PutMapping("/")
	public Product updateProduct(@RequestBody Product p)
	{
		if(p != null)
			return productRepo.save(p);
		else
		{
			System.out.println("No product given");
			return null;
		}
	}
	
	// Delete a Product
	@DeleteMapping("/")
	public Product deleteProduct(@RequestBody Product p)
	{
		if(p != null)
			productRepo.delete(p);
		else
			System.out.println("No Product given");
		
		return p;
	}
}