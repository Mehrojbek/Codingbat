package uz.pdp.appcodingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","language_id"}))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Language language;

    private boolean active=true;

    public Category(String name, String description, Language language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }
}
