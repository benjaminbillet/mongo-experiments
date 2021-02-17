package com.github.benjaminbillet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benjaminbillet.repository.PreferenceEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Supplier;

@Getter
@EqualsAndHashCode
@ToString
public class PreferenceSchema<T> {

  private Class<T> classType;
  private TypeReference<?> typeReference;
  private final Supplier<PreferenceEntity<T>> entityConstructor;

  public PreferenceSchema(TypeReference<T> typeReference, Supplier<PreferenceEntity<T>> entityConstructor) {
    this.typeReference = typeReference;
    this.entityConstructor = entityConstructor;
  }

  public PreferenceSchema(Class<T> classType, Supplier<PreferenceEntity<T>> entityConstructor) {
    this.classType = classType;
    this.entityConstructor = entityConstructor;
  }

}
