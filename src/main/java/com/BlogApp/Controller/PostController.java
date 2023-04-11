package com.BlogApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.Payload.PostDto;
import com.BlogApp.Payload.PostResponse;
import com.BlogApp.Service.PostService;
import com.BlogApp.Utils.AppConstants;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService=postService;
	}
	
	//create blog post 
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	//Get all post
//	@GetMapping
//	public List<PostDto> getAllPosts(){
//		return postService.getAllPosts();
//	}
	
	//change Get All post to add pagination support
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required=false) int pageNo,
			@RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value ="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required= false) String sortDir
			){
		return postService.getAllPosts(pageNo,pageSize,sortBy, sortDir );
	}
	
	
	//getBy id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name ="id") long id){
		return ResponseEntity.ok(postService.getPostbyId(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
			PostDto postResponseDto=postService.updatePost(postDto, id);
			return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> DeletePost(@PathVariable(name="id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<>("Post Entity Deleted Sucessfully", HttpStatus.OK);
	} 
	
	
	
}
