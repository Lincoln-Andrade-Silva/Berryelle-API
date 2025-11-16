package com.berryelle.core.mapper;

import com.berryelle.core.domain.dto.register.RegisterRequest;
import com.berryelle.core.domain.dto.user.UserDetailsResponseImp;
import com.berryelle.core.domain.model.user.User;
import com.berryelle.utils.date.DateUtils;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class AuthenticationMapper {

    public static UserDetailsResponseImp authToUserDetails(Authentication auth, String token, String timezone) {
        User user = (User) auth.getPrincipal();
        return UserDetailsResponseImp.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .admin(user.isAdmin())
                .email(user.getPassword())
                .nickname(user.getNickname())
                .authenticated(auth.isAuthenticated())
                .createdIn(DateUtils.formatLocalDateTimeToString(user.getCreatedIn(), timezone))
                .createdBy(user.getCreatedBy().toString())
                .changedIn(user.getChangedIn() != null ? DateUtils.formatLocalDateTimeToString(user.getChangedIn(),
                        timezone) : null)
                .changedBy(user.getChangedBy() != null ? user.getChangedBy().toString() : null)
                .build();
    }

    public static UserDetailsResponseImp userToUserDetails(String token, User user, String timezone) {
        return UserDetailsResponseImp.builder()
                .token(token)
                .id(user.getId())
                .authenticated(true)
                .name(user.getName())
                .admin(user.isAdmin())
                .email(user.getEmail())
                .deleted(user.isDeleted())
                .nickname(user.getNickname())
                .createdBy(user.getCreatedBy().toString())
                .createdIn(DateUtils.formatLocalDateTimeToString(user.getCreatedIn(), timezone))
                .changedIn(user.getChangedIn() != null ? DateUtils.formatLocalDateTimeToString(user.getChangedIn(),
                        timezone) : null)
                .changedBy(user.getChangedBy() != null ? user.getChangedBy().toString() : null)
                .build();
    }

    public static List<UserDetailsResponseImp> usersToUserDetails(List<User> users, String timezone) {
        List<UserDetailsResponseImp> list = new ArrayList<>();
        users.forEach(user -> list.add(userToUserDetails(null, user, timezone)));
        return list;
    }

    public static void edit(User entity, RegisterRequest request, Long editedBy) {
        if (!Objects.equals(entity.getName(), request.name())) {
            entity.setName(request.name());
        }

        if (!Objects.equals(entity.getEmail(), request.email())) {
            entity.setEmail(request.email());
        }

        if (!Objects.equals(entity.getNickname(), request.nickname())) {
            entity.setNickname(request.nickname());
        }

        if (request.password() != null && !request.password().isEmpty()) {
            entity.setPassword(new BCryptPasswordEncoder().encode(request.password()));
        }

        entity.setChangedBy(editedBy);
        entity.setChangedIn(LocalDateTime.now());
    }
}