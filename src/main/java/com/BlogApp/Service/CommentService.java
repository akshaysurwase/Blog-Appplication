package com.BlogApp.Service;

import java.util.List;

import com.BlogApp.Entity.Comment;
import com.BlogApp.Payload.CommentDto;

import lombok.extern.java.Log;

public interface CommentService {
		
		CommentDto createComment(long postId, CommentDto commentDto);

		List<CommentDto> getCmmentByPostId(Long postId);
		
		CommentDto getCommentById(Long postId, Long commentId);
			
		CommentDto updaComment(Long postId, long commentId, CommentDto commentRequest);
		
		public void deleteComment(Long postId, Long CommentId);
}
