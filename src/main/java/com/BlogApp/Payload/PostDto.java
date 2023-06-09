package com.BlogApp.Payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

	private Long id;

	@NotEmpty
	@Size(min = 2, message = "Post Title Should have atleast Two Characters ")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Post Description Should have atleast Two Characters")
	private String description;

	@NotEmpty
	private String content;

	private Set<CommentDto> comments;

}
