package com.helper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.Table;



@Entity
@Data
@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String tag;
    private Integer solved_problem_count;
    private Integer wrong_answer_count;

    @ManyToOne
    @JoinColumn(name = "codeforces_id")
    private Codeforces codeforces;

    public Problem(Integer solved_problem_count, String tag, Integer wrong_answer_count) {
        this.solved_problem_count = solved_problem_count;
        this.tag = tag;
        this.wrong_answer_count = wrong_answer_count;
    }
    public Problem(){}

}
