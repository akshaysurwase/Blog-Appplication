package com.BlogApp.Service;

import java.util.List;

import com.BlogApp.Entity.Post;
import com.BlogApp.Payload.PostDto;
import com.BlogApp.Payload.PostResponse;

import lombok.extern.java.Log;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo,int pageSize, String sortBy,String sortDir);
	
	PostDto getPostbyId(Long id);
	
	PostDto updatePost(PostDto postDto, Long id);
	
	void deletePostById(long id);
 
}
