package com.example.demo.resource;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Post;
import com.example.demo.services.Postservices;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private Postservices postservices;
	
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Post>> findAll() {
		List<Post> obj = postservices.findAll();
		Collections.sort(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id){
		Post obj = postservices.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@CrossOrigin
	@PostMapping("/add")
	public ResponseEntity<Post> createdPost(@RequestBody Post obj){
		obj = postservices.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	

	@CrossOrigin
	@PatchMapping("/update/{id}")
	public ResponseEntity<Integer> update(@PathVariable String id) {
		Post obj = postservices.findById(id);
		postservices.like(obj);
		return ResponseEntity.ok().body(obj.getLike());
	}
}
