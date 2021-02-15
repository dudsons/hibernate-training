package n1problem.one_to_one;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "hero_details")
public class HeroDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String skill;

    public HeroDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroDetails that = (HeroDetails) o;
        return id == that.id &&
                Objects.equals(skill, that.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skill);
    }

    @Override
    public String toString() {
        return "HeroDetails{" +
                "id=" + id +
                ", skill='" + skill + '\'' +
                '}';
    }
}
