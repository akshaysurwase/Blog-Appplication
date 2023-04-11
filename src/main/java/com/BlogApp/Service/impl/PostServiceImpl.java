package com.BlogApp.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BlogApp.Entity.Post;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payload.PostDto;
import com.BlogApp.Payload.PostResponse;
import com.BlogApp.Repository.PostRepsoitory;
import com.BlogApp.Service.PostService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;


@Service
public class PostServiceImpl implements PostService{
	
	
	private PostRepsoitory postRepsoitory;
	private ModelMapper mapper;

	public PostServiceImpl(PostRepsoitory postRepsoitory,ModelMapper mapper){
		this.postRepsoitory=postRepsoitory;
		this.mapper=mapper;
	}
	
	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		//convert Dto to Entity
		Post post=mapToEntity(postDto);
		Post newPost=postRepsoitory.save(post);
		
		//convert Entity to DTO
		PostDto postResponse=mapToDto(newPost);
		return postResponse;
		}

	
	
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
				
		
		//create pageable instance
//		Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts= postRepsoitory.findAll(pageable);
		
		//get Content for page object 
		
		List<Post> listOfPosts= posts.getContent();
		
		List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalpage(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
		
	}
	
	

	@Override
	public PostDto getPostbyId(Long id) {
		Post post= postRepsoitory.findById(id).orElseThrow(()-> new ResourceNotFoundException("post" , "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		//get post by id  from database
		Post post= postRepsoitory.findById(id).orElseThrow(()-> new ResourceNotFoundException("post" , "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost=postRepsoitory.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
	Post post= postRepsoitory.findById(id).orElseThrow(()-> new ResourceNotFoundException("post" , "id", id));
	postRepsoitory.delete(post);
		
		
	}
	
	//Convert Entity in to Dto
	private PostDto mapToDto(Post post) {	
		PostDto postDto =mapper.map(post, PostDto.class);
		
		
		
//		PostDto postDto=new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
//		BeanUtils.copyProperties(post, postDto);
		
		return postDto;
	}

	
	//Convert Dto to Entity
	private Post mapToEntity(PostDto postDto) {
		
	      Post post=mapper.map(postDto, Post.class);
//		  Post post=new Post();
//		  post.setTitle(postDto.getTitle());
//		  post.setDescription(postDto.getDescription());
//		  post.setContent(postDto.getContent());
		  return post;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
