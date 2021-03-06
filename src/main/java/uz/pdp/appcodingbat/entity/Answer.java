package uz.pdp.appcodingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;

    private boolean isCorrect;

    @ManyToOne
    private User user;

    @ManyToOne
    private Task task;

    private boolean active=true;


    public Answer(String text, User user, Task task) {
        this.text = text;
        this.user = user;
        this.task = task;
    }
}
