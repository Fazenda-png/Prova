package com.example.demo;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.domain.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.resource.PostResource;
import com.example.demo.services.Postservices;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PostResource.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PostEndPointTeste {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private Postservices postservices;

	@Mock
	private PostRepository postRepository;

	@Test
	void insert() throws Exception {
		Post post = new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(post);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/posts/add").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("UTF-8")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void insertError() throws Exception {
		Post post = new Post("123456789", "", "", 0, "29/08/2021");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/posts/add").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andExpect(status().isBadRequest());
	}

	@Test
	void findById() throws Exception {
		String id = "123456789";
		Post post = new Post(id, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(post);
		mockMvc.perform(MockMvcRequestBuilders.get("/posts/" + post.getId().toString())
				.contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("UTF-8")).andExpect(status().isOk());
	}

	@Test
	void findAll() throws Exception {
		List<Post> datas = new ArrayList<Post>();
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));
		datas.add(new Post(null, "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021"));
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(datas);
		mockMvc.perform(MockMvcRequestBuilders.get("/posts").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("UTF-8"))
				.andExpect(status().isOk());
	}

//	@Test
//	void like() throws Exception {
//		Post post = new Post("123456789", "Teste", "Testes unitatios usando JUnit", 0, "29/08/2021");
//
//		given(postRepository.save(post)).willReturn(post);
//		
//		postservices.like(post);
//		
//		ObjectMapper mapper = new ObjectMapper();
//
//		String json = mapper.writeValueAsString(post);
//		
//		mockMvc.perform(MockMvcRequestBuilders.patch("/posts/update/123456789").contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("UTF-8")).andExpect(MockMvcResultMatchers.status().isOk());
//	}

}
