package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private int id;

    @NotEmpty(message = "Username can not be empty")
    @Column(unique = true)
    private String username;

    private transient String password;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @NotEmpty
    private Role role;

    private byte[] picture;

    @Size(max = 50)
    private String bio;

    @Size(max = 50)
    private String location;

    @Size(max = 50)
    private String website;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    private transient List<User> following;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    private transient List<User> followers;

    @OneToMany(targetEntity = Kweet.class, fetch = FetchType.LAZY)
    private transient List<Kweet> kweets;

    @OneToMany(targetEntity = Kweet.class, fetch = FetchType.LAZY)
    private transient List<Kweet> likedKweets;

    public User() {
        following = new ArrayList<>();
        followers = new ArrayList<>();
        kweets = new ArrayList<>();
        likedKweets = new ArrayList<>();
    }

    public void addFollowing(User followingUser) {
        following.add(followingUser);
    }

    public void addFollower(User follower) {
        followers.add(follower);
    }

    public void removeFollowing(User followingUser) {
        following.remove(followingUser);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
    }
}
