package com.helper.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helper.entity.Codeforces;
import com.helper.entity.Tag;
import com.helper.entity.TagStatistic;

public interface TagStatisticRepository extends JpaRepository<TagStatistic,Integer>{
    List<TagStatistic> findByCodeforces(Codeforces codeforces);
    Optional<TagStatistic> findByCodeforcesAndTag(Codeforces codeforces,Tag tag);
}
