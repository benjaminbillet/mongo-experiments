package com.github.benjaminbillet;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PreferenceCatalog {
  private final Map<String, PreferenceSchema<?>> catalog;

  public PreferenceCatalog(Map<String, PreferenceSchema<?>> catalog) {
    this.catalog = Collections.unmodifiableMap(new ConcurrentHashMap<>(catalog));
  }

  public Optional<PreferenceSchema<?>> resolve(String preferenceName) {
    return Optional.ofNullable(catalog.get(preferenceName));
  }
}
