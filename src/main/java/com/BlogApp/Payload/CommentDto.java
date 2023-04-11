package com.BlogApp.Payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data

public class CommentDto {
	
	
	private long id;
	
	@NotEmpty(message = "Name Should Not be null or Empty")
	private String name;
	
	@NotEmpty(message = "Email Should not be Null or Empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=10, message = "Commet Body Must Contain Atleast 10 characters")
	private String Body;
	

}
