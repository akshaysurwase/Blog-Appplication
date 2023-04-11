package com.BlogApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApp.Entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByPostId(Long postId);
	
	
	
}
