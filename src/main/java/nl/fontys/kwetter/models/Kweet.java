package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Kweet implements Comparable<Kweet> {
    @Id
    @GeneratedValue
    private int id;

    @Max(value = 140)
    private String text;

    private Calendar time;

    @PositiveOrZero
    private int likes;

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
}
