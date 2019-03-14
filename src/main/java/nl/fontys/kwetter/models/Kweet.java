package nl.fontys.kwetter.models;

import com.google.gson.annotations.Expose;
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
    @Expose
    private int id;

    @Size(max = 140)
    @NotEmpty(message = "Text can not be empty")
    @Expose
    private String text;

    @Expose
    private Date time;

    @OneToMany
    @Expose
    private List<User> likes;

    @OneToMany
    @Expose
    private List<User> reports;

    @ElementCollection
    @Expose
    private List<String> trends;

    @ManyToOne
    @Expose
    private User author;

    @OneToMany
    @Expose
    private List<User> mentions;

    public Kweet() {
        likes = new ArrayList<>();
        reports = new ArrayList<>();
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
