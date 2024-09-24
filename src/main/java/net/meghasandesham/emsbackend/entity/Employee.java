package net.meghasandesham.emsbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

// Employee is one of the entity (class - table) in the application
// These are POJO Classes (Plain Old Java Objects)
// All such other entities in the application are stored under this entity package as individual classes
// for all the private properties we need to expose getters and setters. We can do this by adding explicit code or using annotations provided by lombok dependency
// As we are using JPA Repository as our DAL, we need to define all these entities as JPA entities (using annotations)
@Getter
@Setter
@NoArgsConstructor  // creates a basic constructor without any arguments
@AllArgsConstructor // creates parameterized constructor
@Entity
@Table(name="employees") // it maps this class to the corresponding table in db
public class Employee {

    @Id //specifying this id as the main primary key of the table
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email_id", nullable = false, unique = true)
    private String emailId;
}

