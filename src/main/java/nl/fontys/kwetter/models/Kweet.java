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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Size(max = 140)
    @NotEmpty(message = "Text can not be empty")
    @Expose
    private String text;

    @Expose
    private Date time;

    @ManyToMany
    @Expose
    private List<User> likedBy;

    @ManyToMany
    @Expose
    private List<User> reportedBy;

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
        likedBy = new ArrayList<>();
        reportedBy = new ArrayList<>();
        trends = new ArrayList<>();
        mentions = new ArrayList<>();
    }

    @Override
    public int compareTo(Kweet o) {
        return time.compareTo(o.time);
    }

    public void addLike(User user) {
        if (!likedBy.contains(user)) {
            likedBy.add(user);
        }
    }

    public void removeLike(User user) {
        likedBy.remove(user);
    }
}
