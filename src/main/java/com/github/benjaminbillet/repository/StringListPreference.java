package com.github.benjaminbillet.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "preferences")
@Data
@SuperBuilder
@NoArgsConstructor
@Valid
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StringListPreference extends PreferenceItem {
  @NotNull
  private List<String> value;
}
