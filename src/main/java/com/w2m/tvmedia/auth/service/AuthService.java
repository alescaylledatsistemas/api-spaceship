package com.w2m.tvmedia.auth.service;

import com.w2m.tvmedia.auth.web.dto.LoginDTO;

public interface AuthService {

	String login(LoginDTO loginDto);

}
