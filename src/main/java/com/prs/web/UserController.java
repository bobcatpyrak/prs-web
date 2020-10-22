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
@RequestMapping("/api/users")
public class UserController 
{
	@Autowired
	private UserRepo userRepo;
	
	// List all Users
	@GetMapping("/")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	// Get a User by username/password
	@GetMapping("/{username}/{password}")
	public User getUserByLogin(@PathVariable String username, @PathVariable String password) 
	{
		User m = userRepo.findByUsernameAndPassword(username, password); 
		if(m != null)
		{
			return m;
		}
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
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
		return userRepo.save(p);
	}
	
	// Edit a User
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User p, @PathVariable int id)
	{
		if(id == p.getId())
			return userRepo.save(p);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id does not match.");
	}
	
	// Delete a User
	@DeleteMapping("/{id}")
	public Optional<User> deleteUser(@PathVariable int id)
	{
		Optional<User> p = userRepo.findById(id);
		if(p.isPresent())
			userRepo.deleteById(id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		
		return p;
	}
}