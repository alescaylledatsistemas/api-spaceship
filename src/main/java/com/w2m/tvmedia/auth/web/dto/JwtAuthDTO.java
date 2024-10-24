package com.w2m.tvmedia.auth.web.dto;

import lombok.Data;

@Data
public class JwtAuthDTO {

	private String accessToken;
	private String tokenType = "Bearer";

	public JwtAuthDTO() {
		super();
	}

	public JwtAuthDTO(String accessToken, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
