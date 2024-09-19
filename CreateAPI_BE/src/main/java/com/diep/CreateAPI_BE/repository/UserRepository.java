package com.diep.CreateAPI_BE.repository;

import com.diep.CreateAPI_BE.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, String> {

    MyUser findByEmail(String email);

    @Query(value = "from MyUser where name like concat('%', :query, '%') or email like concat('%', :query, '%')")
    List<MyUser> findAllByNameOrEmail(String query);

    Optional<MyUser> findByApiKey(String apiKey);
}
