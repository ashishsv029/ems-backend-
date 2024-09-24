package net.meghasandesham.emsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

//DTO:- Data Transfer Object (Anything coming to our APIs and responses leaving from our APIs should be of this type only i.e instance of this dto class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Lombok's annotations like @AllArgsConstructor are simply convenience annotations that help reduce boilerplate code by automatically generating constructors, getters, setters, and other common Java methods.
public class EmployeeDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String emailId;

}
