package com.ctos.dummy.library.repository;

import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AisleRepo extends JpaRepository<Aisle, Long> {
    @Query("SELECT a FROM Aisle a WHERE a.library = :library")
    List<Aisle> findAislesByLibrary(@Param("library") Library library);
}