package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Kweet {
    @Id
    private Integer id;

    @Max(value = 140)
    private String text;

    private Calendar time;

    @PositiveOrZero
    private Integer likes;

    @ElementCollection
    private List<String> trends;

    @ManyToOne
    private User author;

    @OneToMany(targetEntity = User.class)
    private List<User> mentions;
}
