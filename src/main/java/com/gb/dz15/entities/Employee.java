package com.gb.dz15.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "seqEmployee", sequenceName = "s_employee")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEmployee")
    @Column(name = "id")
    public Long id;
    @Column(name = "name")
    @NotNull
    public String name;
    @Column(name = "age")
    public Integer age;

    @Override
    public String toString() {
        return String.format("%s, %s, %s", this.id, this.name, this.age);
    }

}
