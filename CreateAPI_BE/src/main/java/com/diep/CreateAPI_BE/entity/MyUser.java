package com.diep.CreateAPI_BE.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyUser {
    @Id
    String uid;
    String email;
    String name;
    @Column(unique = true)
    String apiKey;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<MyCollection> collections = new HashSet<>();
    String provider; // google, github, ...

    public void generateApiKey() {
        this.apiKey = UUID.randomUUID().toString().replaceAll("-", "");
    }
}
