package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.domain.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.services.Postservices;
import com.example.demo.services.exception.ObjectEmpty;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PostRepositoryTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private Postservices postservices;

	@Test
	void findById() {
		String id = "123456789";
		Post post = new Post(id, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");

		given(postRepository.findById(id)).willReturn(Optional.of(post));

		Post expected = postservices.findById(id);

		assertThat(expected).isNotNull();
	}

	@Test
	void findAll() {
		List<Post> datas = new ArrayList<Post>();
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));

		given(postRepository.findAll()).willReturn(datas);

		List<Post> expected = postservices.findAll();

		assertEquals(expected, datas);
	}

	@Test
	void like() {
		Post post = new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");
		
		given(postRepository.save(post)).willReturn(post);
		
		Post expected = postservices.like(post);
		
		verify(postRepository).save(expected);
		
		assertEquals(expected, post);
	}

	@Test
	void insertPost() {
		Post post = new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");

		given(postRepository.save(post)).willAnswer(invocation -> invocation.getArgument(0));

		Post savedPost = postservices.insert(post);

		assertThat(savedPost).isNotNull();
		verify(postRepository).save(post);
	}

	@Test
	void insertPostError() {
		Post post = new Post(null, "", "", 0, "29/08/2021");

		try {
			Post savedPost = postservices.insert(post);
			verify(postRepository).save(savedPost);

		} catch (ObjectEmpty e) {
			assertEquals("Objeto incompleto", e.getMessage());

		}

	}

	@Test
	void findByIdError() {
		String id = "123456789";
		Post post = new Post(id, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");

		try {
			given(postRepository.findById(id)).willReturn(Optional.of(post));
			Post expected = postservices.findById(id);
			assertThat(expected).isNotNull();

		} catch (ObjectEmpty e) {
			assertEquals("postRepository", e.getMessage());

		}

	}
}
