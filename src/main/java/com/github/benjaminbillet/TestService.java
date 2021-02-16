package com.github.benjaminbillet;

import com.github.benjaminbillet.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
  private final PreferenceRepository preferenceRepository;
  private final MongoTemplate mongoTemplate;

  @PostConstruct
  public void init() {
    preferenceRepository.save(StringPreference.builder().value("string").name("stringval").owner("user").build());
    preferenceRepository.save(StringListPreference.builder().value(Arrays.asList("string")).name("stringlistval").owner("user").build());
    preferenceRepository.save(NumberPreference.builder().value(10L).name("numberval").owner("pod").scope(PreferenceScope.POD).build());

    List<PreferenceItem> docs = preferenceRepository.findAll();
    System.out.println(docs);

    docs = preferenceRepository.findByOwner("user");
    System.out.println(docs);

    docs = preferenceRepository.findByOwnerAndScope("pod", PreferenceScope.POD);
    System.out.println(docs);

    System.out.println(mongoTemplate.indexOps(PreferenceItem.class).getIndexInfo());
  }
}
