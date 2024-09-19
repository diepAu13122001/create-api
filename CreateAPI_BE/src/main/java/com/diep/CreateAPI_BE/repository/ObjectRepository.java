package com.diep.CreateAPI_BE.repository;

import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectRepository extends JpaRepository<MyObject, Long> {
}
