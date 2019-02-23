package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    private List<String> trends;

    private User author;

    private List<User> mentions;
}
