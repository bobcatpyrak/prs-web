package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prs.business.*;

public interface RequestRepo extends JpaRepository<Request, Integer> 
{
	List<Request> findByStatusAndNotUserId(String status, int userId);

}
