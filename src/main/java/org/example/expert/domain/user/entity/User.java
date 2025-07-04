package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String profileImageUrl;

    public User(String email, String username, String password, UserRole userRole) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.profileImageUrl = null;
    }

    private User(Long id, String email, String username, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        User user = new User(authUser.getId(), authUser.getEmail(), authUser.getUsername(), authUser.getUserRole());
        user.profileImageUrl = null;
        return user;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
