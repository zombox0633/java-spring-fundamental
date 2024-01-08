package com.springframework.springfundamental.repository;

import com.springframework.springfundamental.entity.Keyboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KeyboardRepository extends JpaRepository<Keyboard, UUID> {

}
