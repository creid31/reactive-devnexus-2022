package com.cecilipoole.reactiveexample;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private GuestStatus guestStatus;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Override
    public String toString() {
        return String.format(
                "Guest[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
