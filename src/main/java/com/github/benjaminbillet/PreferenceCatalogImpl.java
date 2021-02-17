package com.github.benjaminbillet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benjaminbillet.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PreferenceCatalogImpl extends PreferenceCatalog {
  public PreferenceCatalogImpl() {
    super(Map.ofEntries(
      Map.entry("pref1", new PreferenceSchema<>(String.class, StringPreference::new)),
      Map.entry("pref2", new PreferenceSchema<>(Number.class, NumberPreference::new)),
      Map.entry("pref3", new PreferenceSchema<>(Boolean.class, BooleanPreference::new)),
      Map.entry("pref4", new PreferenceSchema<>(new TypeReference<List<String>>() {
      }, StringListPreference::new)),
      Map.entry("pref5", new PreferenceSchema<>(new TypeReference<Map<String, String>>() {
      }, DictionaryPreference::new))
    ));
  }
}
