package nl.fontys.kwetter.models;

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    @Expose
    private int id;

    @NotEmpty(message = "Username can not be empty")
    @Column(unique = true)
    @Expose
    private String username;

    private String password;

    @NotEmpty(message = "Name can not be empty")
    @Expose
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email can not be empty")
    @Expose
    private String email;

    @NotNull
    private Role role;

    @Expose
    private byte[] picture;

    @Size(max = 50)
    @Expose
    private String bio;

    @Size(max = 50)
    @Expose
    private String location;

    @Size(max = 50)
    @Expose
    private String website;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> following;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> followers;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Kweet> kweets;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Kweet> likedKweets;

    public User() {
        following = new ArrayList<>();
        followers = new ArrayList<>();
        kweets = new ArrayList<>();
        likedKweets = new ArrayList<>();
    }

    public void addFollowing(User followingUser) {
        if (!following.contains(followingUser)) {
            following.add(followingUser);
        }
    }

    public void addFollower(User follower) {
        if (!followers.contains(follower)) {
            followers.add(follower);
        }
    }

    public void removeFollowing(User followingUser) {
        following.remove(followingUser);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
    }
}
