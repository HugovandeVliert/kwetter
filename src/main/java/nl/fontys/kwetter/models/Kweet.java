package nl.fontys.kwetter.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Calendar;
import java.util.List;

public class Kweet {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Max(value = 140)
    private String text;

    @Getter
    @Setter
    private Calendar time;

    @Getter
    @Setter
    @PositiveOrZero
    private Integer likes;

    @Getter
    @Setter
    private List<String> trends;

    @Getter
    @Setter
    private User author;

    @Getter
    @Setter
    private List<User> mentions;
}
