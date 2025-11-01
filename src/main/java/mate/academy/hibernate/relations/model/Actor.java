package mate.academy.hibernate.relations.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "actors")
public class Actor implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "country_id")
    private Country country;

    public Actor() {
    }

    public Actor(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Actor(String fullName) {
        String[] parts = fullName.split(" ", 2);
        this.name = parts.length > 0 ? parts[0] : "";
        this.lastName = parts.length > 1 ? parts[1] : "";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public Actor clone() {
        try {
            Actor actor = (Actor) super.clone();
            if (country != null) {
                actor.setCountry(country.clone());
            }
            return actor;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't make clone of " + this, e);
        }
    }

    @Override
    public String toString() {
        return "Actor{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", lastName='" + lastName + '\''
                + ", country=" + (country != null ? country.getName() : "null")
                + '}';
    }
}
