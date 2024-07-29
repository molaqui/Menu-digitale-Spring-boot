package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chefs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String designation;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image; // Store image as byte array

    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Add this line
}
