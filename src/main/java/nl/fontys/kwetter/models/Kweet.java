package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Kweet implements Comparable<Kweet> {
    @Id
    @GeneratedValue
    private int id;

    @Max(value = 140)
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
