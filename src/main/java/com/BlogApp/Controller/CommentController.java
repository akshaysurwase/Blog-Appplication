package com.BlogApp.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApp.Payload.CommentDto;
import com.BlogApp.Service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment( @PathVariable(value="postId") long postId,@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public List<CommentDto> getCommentByPostId(@PathVariable(value="postId") Long postId){
		return commentService.getCmmentByPostId(postId);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId") Long postId, @PathVariable(value = "id") Long commentId){
		CommentDto commentDto=commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	
	@PutMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment( @PathVariable(value="postId") Long postId,@PathVariable(value="id") long commentId,@Valid @RequestBody CommentDto commentDto){
		CommentDto updatedcomment= commentService.updaComment(postId, commentId, commentDto);
		return new ResponseEntity<CommentDto>(updatedcomment, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId,@PathVariable(value="id") Long commentId){
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment Deleted Sucessfully", HttpStatus.OK);
	}
		
}
