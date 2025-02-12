package com.syuk27.springboot.pt_restful_hibernate.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syuk27.springboot.pt_restful_hibernate.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {


}
