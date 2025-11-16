package com.berryelle.core.service.auth;

import com.berryelle.core.domain.dto.login.LoginRequest;
import com.berryelle.core.domain.dto.register.RegisterRequest;
import com.berryelle.core.domain.dto.user.UserDetailsResponseImp;
import com.berryelle.utils.exception.ApplicationBusinessException;
import com.berryelle.utils.request.DataRequest;

import java.io.IOException;

public interface IAuthenticationService {
    default UserDetailsResponseImp login(DataRequest<LoginRequest> request, String locale) throws ApplicationBusinessException {
        return null;
    }

    UserDetailsResponseImp register(DataRequest<RegisterRequest> request, String locale, String token) throws ApplicationBusinessException, IOException;

    UserDetailsResponseImp edit(DataRequest<RegisterRequest> request, String timezone, String token) throws ApplicationBusinessException, IOException;
}