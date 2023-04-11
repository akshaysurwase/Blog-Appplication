package com.BlogApp.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.BlogApp.Entity.Comment;
import com.BlogApp.Entity.Post;
import com.BlogApp.Exception.BlogAPIException;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payload.CommentDto;
import com.BlogApp.Repository.CommentRepository;
import com.BlogApp.Repository.PostRepsoitory;
import com.BlogApp.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepsoitory postRepsoitory;
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepsoitory postRepsoitory, ModelMapper mapper) {
		this.commentRepository=commentRepository;
		this.postRepsoitory=postRepsoitory;
		this.mapper=mapper;
	}
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment=mapToEntity(commentDto);
		
		//retrive post Entity by id
		Post post=postRepsoitory.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//Set Post to comment entity
		comment.setPost(post);
		
		//save comment entity to database
		Comment newComment=commentRepository.save(comment);
		return mapToDTO(newComment);
	}
	
	private CommentDto mapToDTO(Comment comment) {
		
		CommentDto commentDto= mapper.map(comment, CommentDto.class);
//		CommentDto commentDto= new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setBody(comment.getBody());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment= mapper.map(commentDto, Comment.class);
		
//		Comment comment=new Comment();
//		comment.setId(commentDto.getId());
//		comment.setBody(commentDto.getBody());
//		comment.setEmail(commentDto.getEmail());
//		comment.setName(commentDto.getName());
		return comment;
		}

	@Override
	public List<CommentDto> getCmmentByPostId(Long postId) {
		
		//retrieve comment by post ID
		
		List<Comment> comments= commentRepository.findByPostId(postId);
		
		
		//Convert list of Comment entities to listof comment DTO's
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
	
		Post post=postRepsoitory.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve Comment By id
		Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Doesnot Belong to a post");
		}
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updaComment(Long postId, long commentId, CommentDto commentRequest) {
		Post post=postRepsoitory.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Commnet DoesNot Belong to Post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		Comment updatedComment=commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		Post post=postRepsoitory.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Commnet DoesNot Belong to Post");
		}
		
		commentRepository.delete(comment);
		
	}
}

	
	
	
	
	
	
	
	
	
	
	
	


