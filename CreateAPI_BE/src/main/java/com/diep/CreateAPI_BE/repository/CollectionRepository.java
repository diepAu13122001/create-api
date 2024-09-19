package com.diep.CreateAPI_BE.repository;

import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<MyCollection, Long> {

    List<MyCollection> findByIsShared(Boolean isShared);

    @Query("from MyCollection where name like concat('%', :name, '%') and isShared=true")
    List<MyCollection> findByNameAndShared(String name);
}
