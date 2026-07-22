package com.helper.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "codeforces")
public class Codeforces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cf_id;
    private Integer rating;

    @Column(nullable = false, unique = true)
    private String handle;

    @Column(name = "cf_rank")
    private String rank;

    private Integer maxRating;

    private String maxRank;

    @Column(unique = true)
    private String email;

    private Integer prevSubmission;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false, unique = true)
    private Users user;

    @OneToMany(mappedBy = "codeforces", cascade = CascadeType.ALL)
    private List<TagStatistic> tagStatistics=new ArrayList<>();

    public Codeforces(String email, String handle, String maxRank, Integer maxRating, String rank, Integer rating,Users user) {
        this.email = email;
        this.handle = handle;
        this.maxRank = maxRank;
        this.maxRating = maxRating;
        this.rank = rank;
        this.rating = rating;
        this.user=user;
    }
    public Codeforces(){}

}