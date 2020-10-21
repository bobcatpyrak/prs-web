package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.business.User;
import com.prs.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController 
{
	@Autowired
	private UserRepo userRepo;
	
	// List all Users
	@GetMapping("/")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	// Get a User by id
	@GetMapping("/{id}")
	public Optional<User> getUser(@PathVariable int id) {
		Optional<User> m = userRepo.findById(id);
		if(m.isPresent())
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	}
	// Add a User
	@PostMapping("/")
	public User addUser(@RequestBody User p)
	{
		if(p != null)
			return userRepo.save(p);
		else
		{
			System.out.println("No user given");
			return null;
		}
	}
	
	// Edit a User
	@PutMapping("/")
	public User updateUser(@RequestBody User p)
	{
		if(p != null)
			return userRepo.save(p);
		else
		{
			System.out.println("No user given");
			return null;
		}
	}
	
	// Delete a User
	@DeleteMapping("/")
	public User deleteUser(@RequestBody User p)
	{
		if(p != null)
			userRepo.delete(p);
		else
			System.out.println("No User given");
		
		return p;
	}
}