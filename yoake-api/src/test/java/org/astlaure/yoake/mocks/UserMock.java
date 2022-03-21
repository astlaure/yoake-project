package org.astlaure.yoake.mocks;

import org.astlaure.yoake.users.User;
import org.astlaure.yoake.users.enums.UserRole;

public class UserMock {
    public static User getInstance() {
        return User.builder()
                .id(1L)
                .name("Jasmine Karma")
                .username("jasmine@karma.io")
                .password("encrypted")
                .role(UserRole.ROLE_ADMIN)
                .enabled(true)
                .build();
    }
}
