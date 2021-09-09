package com.omkar.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.omkar.backend.model.Associate;

public interface AssociateRepository extends MongoRepository<Associate, String>{

}
