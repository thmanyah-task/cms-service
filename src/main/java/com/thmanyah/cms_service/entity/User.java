package com.thmanyah.cms_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime createdDate;
    @Column(name = "user_name")
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @ManyToMany
    @JoinTable(name="users_roles" , joinColumns=@JoinColumn(name="user_id") ,
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private List<Role> roles;
}
