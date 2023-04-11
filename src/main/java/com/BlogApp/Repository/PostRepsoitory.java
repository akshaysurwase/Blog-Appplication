package com.BlogApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.Entity.Post;

 
public interface PostRepsoitory extends JpaRepository<Post, Long>{

}
