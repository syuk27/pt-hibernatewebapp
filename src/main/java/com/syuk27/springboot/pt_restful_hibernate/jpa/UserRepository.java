package com.syuk27.springboot.pt_restful_hibernate.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syuk27.springboot.pt_restful_hibernate.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
