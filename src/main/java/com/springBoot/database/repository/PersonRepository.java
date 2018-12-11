package com.springBoot.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.database.model.SpringBootDataModel;

public interface PersonRepository extends JpaRepository<SpringBootDataModel,Long>{

}
