package com.github.benjaminbillet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PreferenceRepository extends MongoRepository<PreferenceEntity<?>, String> {
  List<PreferenceEntity<?>> findByOwnerAndScope(String owner, PreferenceScope scope);

  List<PreferenceEntity<?>> findByOwner(String owner);
}
