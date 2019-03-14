package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Kweet implements Comparable<Kweet> {
    @Id
    @GeneratedValue
    private int id;

    @Size(max = 140)
    @NotEmpty(message = "Text can not be empty")
    private String text;

    private Date time;

    @OneToMany
    private List<User> likes;

    @OneToMany
    private List<User> reports;

    @ElementCollection
    private List<String> trends;

    @ManyToOne
    private User author;

    @OneToMany(targetEntity = User.class)
    private List<User> mentions;

    public Kweet() {
        likes = new ArrayList<>();
        trends = new ArrayList<>();
        mentions = new ArrayList<>();
    }

    @Override
    public int compareTo(Kweet o) {
        return time.compareTo(o.time);
    }

    public void addLike(User user) {
        if (!likes.contains(user)) {
            likes.add(user);
        }
    }

    public void removeLike(User user) {
        likes.remove(user);
    }
}
