package com.example.demo.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.services.exception.ObjectEmpty;
import com.example.demo.services.exception.ObjectNotFoundException;

@Service
public class Postservices {

	@Autowired
	private PostRepository postRepository;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public Post findById(String id) {
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Post insert(Post obj) {
		Date data = new Date();
		String testData = sdf.format(data);
		obj.setDataPost(testData);
		if (obj.getAuthor() == "" || obj.getContent() == "") {
			throw new ObjectEmpty("Objeto incompleto");
		}

		return postRepository.save(obj);
	}

	public Post like(Post obj) {
		obj.setLike(obj.getLike() + 1);
		return postRepository.save(obj);
	}

}
