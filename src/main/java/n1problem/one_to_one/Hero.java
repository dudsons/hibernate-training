package n1problem.one_to_one;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hero")
public class Hero {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hero_id")
    private HeroDetails heroDetails;

    public Hero() {
    }


    public HeroDetails getHeroDetails() {
        return heroDetails;
    }

    public void setHeroDetails(HeroDetails heroDetails) {
        this.heroDetails = heroDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return id == hero.id &&
                Objects.equals(name, hero.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", heroDetails=" + heroDetails +
                '}';
    }
}
