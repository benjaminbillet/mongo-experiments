package com.github.benjaminbillet.repository;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Valid
@SuperBuilder
@NoArgsConstructor
@Document(collection = "preferences")
@CompoundIndexes({
  @CompoundIndex(name = "owner_scope", def = "{'owner': 1, 'scope': 1}"),
  @CompoundIndex(name = "owner_key", def = "{'owner': 1, 'name': 1}", unique = true)
})
public abstract class PreferenceItem {
  @Id
  private String id;

  @NotBlank
  private String name;

  @Builder.Default
  @NotNull
  private PreferenceScope scope = PreferenceScope.USER;

  @NotBlank
  private String owner;
}
