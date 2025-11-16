package com.berryelle.core.service.userdetails;

import com.berryelle.core.domain.dto.user.UserDetailsResponseImp;
import com.berryelle.core.domain.model.user.User;
import com.berryelle.utils.exception.ApplicationBusinessException;
import com.berryelle.utils.response.DataListResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.List;

public interface IUserDetailsService {
    void fillAuditFields(UserDetailsResponseImp user);

    List<UserDetailsResponseImp> loadUserByIds(String token, String timezone, List<Long> ids) throws IOException;

    UserDetailsResponseImp loadUserByToken(String token, String timezone) throws UsernameNotFoundException, ApplicationBusinessException, IOException;

    User loadUserEntityByToken(String token, String timezone) throws UsernameNotFoundException, ApplicationBusinessException;

    void delete(Long id, String token, String timezone) throws ApplicationBusinessException, IOException;

    DataListResponse<UserDetailsResponseImp> list(String timezone, int page, int pageSize, String search, String token) throws UsernameNotFoundException, ApplicationBusinessException, IOException;
}