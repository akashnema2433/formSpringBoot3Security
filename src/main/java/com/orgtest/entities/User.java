package com.orgtest.entities;

//import javax.persistence.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "userdata")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please write your first name")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
            message = "first name must be of 6 to 12 length with no special characters")
    private String userFirstName;

    @NotEmpty(message = "Please write your last name")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
            message = "last namemust be of 6 to 12 length with no special characters")
    private String userLastName;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private String password;

    private String profile;

    private String role;

    private boolean isEnabled;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles = new HashSet<>();
}
