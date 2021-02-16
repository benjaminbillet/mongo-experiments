package com.github.benjaminbillet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PreferenceRepository extends MongoRepository<PreferenceItem, String> {
  List<PreferenceItem> findByOwnerAndScope(String owner, PreferenceScope scope);
  List<PreferenceItem> findByOwner(String owner);
}
