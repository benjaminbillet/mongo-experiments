package com.github.benjaminbillet.repository;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Valid
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "preferences")
@CompoundIndexes({
  @CompoundIndex(name = "scope_app_owner", def = "{'scope': 1, 'app': 1, 'owner': 1}"),
  @CompoundIndex(name = "scope_owner", def = "{'scope': 1, 'owner': 1}")
})
public abstract class PreferenceEntity<T> {
  @Id
  private String id;

  @NotBlank
  private String name;

  @Builder.Default
  @NotNull
  private PreferenceScope scope = PreferenceScope.USER;

  @NotBlank
  private String owner;

  private String app;

  public abstract T getValue();

  public abstract void setValue(T value);
}
