package nl.fontys.kwetter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Kweet implements Comparable<Kweet> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private long id;

    @Size(max = 140)
    @NotEmpty(message = "Text can not be empty")
    @Expose
    private String text;

    private LocalDateTime time;

    @Expose()
    @SerializedName("time")
    private String timeAsString;

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

    @ManyToMany
    @Expose
    private List<User> mentions;

    public Kweet() {
        likedBy = new ArrayList<>();
        reportedBy = new ArrayList<>();
        trends = new ArrayList<>();
        mentions = new ArrayList<>();

        this.setTime(LocalDateTime.now());
    }

    public void addLike(User user) {
        if (!likedBy.contains(user)) {
            likedBy.add(user);
        }
    }

    public void removeLike(User user) {
        likedBy.remove(user);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
        this.timeAsString = DateTimeFormatter.ofPattern("HH:mm dd-MM-YYYY").format(time);
    }

    @Override
    public int compareTo(Kweet o) {
        return o.time.compareTo(time);
    }
}
