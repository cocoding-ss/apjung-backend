package me.apjung.backend.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String mobile;
}
