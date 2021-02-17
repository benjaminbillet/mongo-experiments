package com.github.benjaminbillet.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Document(collection = "preferences")
@Data
@SuperBuilder
@NoArgsConstructor
@Valid
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BooleanPreference extends PreferenceEntity<Boolean> {
  @NotNull
  private Boolean value;
}
