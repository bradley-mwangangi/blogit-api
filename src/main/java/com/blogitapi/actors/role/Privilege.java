package com.blogitapi.actors.role;

import lombok.Getter;

@Getter
public enum Privilege {

    // article-related privileges
    create_article("can_create_articles"),
    read_article("can_read_articles"),
    update_article("can_update_articles"),
    delete_article("can_delete_articles"),

    // admin privileges. applies to all resources
    // super_admin has all privileges
    admin_write("admin_privilege_create"),
    admin_read("admin_privilege_read"),
    admin_update("admin_privilege_update"),
    admin_delete("admin_privilege_delete");

    private final String description;

    Privilege(String description) {
        this.description = description;
    }
}
