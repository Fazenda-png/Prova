package com.example.demo.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post")
public class Post implements Serializable, Comparable<Post> {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String author;
	private String content;
	private Integer like = 0;
	private String dataPost;

	public Post() {

	}

	public Post(String id, String author, String content, Integer like, String dataPost) {
		this.id = id;
		this.author = author;
		this.content = content;
		this.like = like;
		this.dataPost = dataPost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public String getDataPost() {
		return dataPost;
	}

	public void setDataPost(String dataPost) {
		this.dataPost = dataPost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(Post outroPost) {
		if (this.like > outroPost.getLike()) {
			return -1;
		}
		if (this.like < outroPost.getLike()) {
			return 1;
		}
		return 0;
	}

}