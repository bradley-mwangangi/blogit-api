package com.blogitapi.actors.role;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {

    // registered and logged-in user
    USER(
            // can read articles and manage own comments
            // permissions LIMITED to OWN account
            Set.of(
                    Privilege.read_article
            )
    ),
    // registered user with author status
    AUTHOR(
            // can create article content for publication
            // permissions LIMITED to OWN account
            Set.of(
                    Privilege.create_article,
                    Privilege.read_article,
                    Privilege.update_article,
                    Privilege.delete_article
            )
    ),
    // registered user with moderator status
    MODERATOR(
            // moderates user content. cannot create or delete articles
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    Privilege.read_article,
                    Privilege.update_article
            )
    ),
    ADMIN_CREATE_ENTITIES(
            Set.of(
                    Privilege.admin_read,
                    Privilege.admin_write,
                    Privilege.admin_update
            )
    ),
    ADMIN_READ_ENTITIES(
            Set.of(
                    Privilege.admin_read
            )
    ),
    ADMIN_UPDATE_ENTITIES(
            Set.of(
                    Privilege.admin_read,
                    Privilege.admin_update
            )
    ),
    ADMIN_DELETE_ENTITIES(
            Set.of(
                    Privilege.admin_read,
                    Privilege.admin_update,
                    Privilege.admin_delete
            )
    ),
    // super admin. has total control over system resources
    SUPER_ADMIN(
            Set.of(
                    Privilege.admin_write,
                    Privilege.admin_read,
                    Privilege.admin_update,
                    Privilege.admin_delete
            )
    );

    private final Set<Privilege> privileges;

    Role(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.toString()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
