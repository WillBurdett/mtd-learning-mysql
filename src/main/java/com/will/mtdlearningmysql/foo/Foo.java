package com.will.mtdlearningmysql.foo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;


@Entity(name = "foo")
@Table(name = "foo")
public class Foo {
    @Id
    @Column(
            nullable = false,
            name = "name"
    )
    private String name;
    @Column(
            nullable = false,
            name = "legs"
    )
    @Max(value = 100)
    @Min(value = 0)
    private Integer legs;
    @Column(
            nullable = false,
            name = "can_fly"
    )
    private Boolean canFly;

    public Foo() {
    }

    public Foo(String name, Integer legs, Boolean canFly) {
        this.name = name;
        this.legs = legs;
        this.canFly = canFly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

    public Boolean getCanFly() {
        return canFly;
    }

    public void setCanFly(Boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return Objects.equals(name, foo.name) && Objects.equals(legs, foo.legs) && Objects.equals(canFly, foo.canFly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, legs, canFly);
    }

    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", legs=" + legs +
                ", canFly=" + canFly +
                '}';
    }
}


