package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    private Integer id;

    @NotEmpty(message = "Username can not be empty")
    private String username;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private Role role;

    private byte[] picture;

    @Max(value = 160)
    private String bio;

    @Max(value = 50)
    private String location;

    @Max(value = 50)
    private String website;

    @OneToMany(targetEntity = User.class)
    private Set<User> following;

    @ManyToOne(targetEntity = User.class)
    private Set<User> followers;

    @OneToMany(targetEntity = Kweet.class)
    private Set<Kweet> kweets;

    @OneToMany(targetEntity = Kweet.class)
    private Set<Kweet> likedKweets;
}
