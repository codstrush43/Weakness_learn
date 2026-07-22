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
@Table(name = "tag_statistic")
public class TagStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private Integer solvedProblemCount;
    private Integer wrongAnswerCount;

    @ManyToOne
    @JoinColumn(name = "codeforces_id")
    private Codeforces codeforces;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public TagStatistic(Integer solvedProblemCount, Tag tag,Codeforces codeforces, Integer wrongAnswerCount) {
        this.solvedProblemCount = solvedProblemCount;
        this.tag = tag;
        this.wrongAnswerCount = wrongAnswerCount;
        this.codeforces=codeforces;
    }
    public TagStatistic(){}

}
