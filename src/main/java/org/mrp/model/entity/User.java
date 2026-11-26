package org.mrp.model.entity;

import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Setter(AccessLevel.NONE)
    private Long id;
    private String username;
    private String password;
    private String token;
    private String email;
    @Setter(AccessLevel.NONE)
    private Instant createdAt;
    private Instant tokenExpiresAt;

}