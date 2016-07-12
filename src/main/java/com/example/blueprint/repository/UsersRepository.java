package com.example.blueprint.repository;

import com.example.blueprint.User;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface UsersRepository extends CrudRepository<User, Long> {

}
