package com.cecilipoole.reactiveexample;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByLastName(String lastName);

    Guest findById(long id);

    @Query("select g from Guest g where g.createdAt > ?1")
    List<Guest> findByCreatedAt(Instant createdAt);

    @Query("select g from Guest g where g.updatedAt > ?1 and g.updatedAt <> g.createdAt")
    List<Guest> findByUpdatedAt(Instant updatedAt);
}
