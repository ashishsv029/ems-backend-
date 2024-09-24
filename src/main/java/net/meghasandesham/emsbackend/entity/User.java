package net.meghasandesham.emsbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

// This is user entity i.e nothing but a User table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users") //A users table will be created if not present
// If the table is already there, ensure that data types and constraints are correctly mapped by using annotations
// If the names mismatch then also ensure to have a column annotation mapping
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //Creating a Many to Many mapping between Users and roles
    // If ORM is used, this mapping table logic is taken care by it out of the box

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}
