package com.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helper.entity.Tag;

public interface TagRepository extends JpaRepository<Tag,Integer> {

}
