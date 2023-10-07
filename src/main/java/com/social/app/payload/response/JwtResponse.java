package com.social.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private boolean response;
	private String message;

	public JwtResponse(String message,Boolean response ,String accessToken, Long id, String username, String email) {
		this.response  = response;
		this.message = message;
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
	}

}
