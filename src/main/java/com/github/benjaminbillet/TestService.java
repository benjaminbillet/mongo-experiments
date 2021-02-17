package com.github.benjaminbillet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.benjaminbillet.dto.PreferenceSet;
import com.github.benjaminbillet.dto.PreferenceSetDeserializer;
import com.github.benjaminbillet.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {
  private final PreferenceRepository preferenceRepository;
  private final PreferenceCatalog preferenceCatalog;
  private final MongoTemplate mongoTemplate;

  @PostConstruct
  public void init() throws JsonProcessingException {
    preferenceRepository.save(StringPreference.builder().value("string").name("stringval").owner("user").build());
    preferenceRepository.save(StringListPreference.builder().value(Arrays.asList("string")).name("stringlistval").owner("user").build());
    preferenceRepository.save(NumberPreference.builder().value(10L).name("numberval").owner("pod").scope(PreferenceScope.POD).build());

    List<PreferenceEntity<?>> docs = preferenceRepository.findAll();
    System.out.println(docs);
    System.out.println(new ObjectMapper().writeValueAsString(docs.stream().collect(Collectors.toMap(PreferenceEntity::getName, PreferenceEntity::getValue))));

    docs = preferenceRepository.findByOwner("user");
    System.out.println(docs);

    docs = preferenceRepository.findByOwnerAndScope("pod", PreferenceScope.POD);
    System.out.println(docs);

    System.out.println(mongoTemplate.indexOps(PreferenceEntity.class).getIndexInfo());

    SimpleModule module = new SimpleModule().addDeserializer(PreferenceSet.class, new PreferenceSetDeserializer(preferenceCatalog));
    ObjectMapper mapper = new ObjectMapper().registerModule(module);

    PreferenceSet set = new PreferenceSet();
    set.put("pref1", "stringValue");
    set.put("pref2", 100);
    set.put("pref3", false);
    set.put("pref4", Arrays.asList("value1", "value2"));
    set.put("pref5", Map.of("k1", "v1", "k2", "v2"));

    String json = mapper.writeValueAsString(set);
    PreferenceSet parsed = mapper.readValue(json, PreferenceSet.class);
    System.out.println(json);
    System.out.println(parsed);
  }
}
