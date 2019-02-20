package nl.fontys.kwetter.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

public class User {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Username can not be empty")
    private String username;

    @Getter
    @Setter
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Getter
    @Setter
    @Email(message = "Email should be valid")
    private String email;

    @Getter
    @Setter
    private Role role;

    @Getter
    @Setter
    private byte[] picture;

    @Getter
    @Setter
    @Max(value = 160)
    private String bio;

    @Getter
    @Setter
    @Max(value = 50)
    private String location;

    @Getter
    @Setter
    @Max(value = 50)
    private String website;

    @Getter
    @Setter
    private Set<User> following;

    @Getter
    @Setter
    private Set<User> followers;

    @Getter
    @Setter
    private Set<Kweet> kweets;

    @Getter
    @Setter
    private Set<Kweet> likedKweets;
}
