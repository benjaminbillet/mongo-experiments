package com.github.benjaminbillet.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benjaminbillet.PreferenceCatalog;
import com.github.benjaminbillet.PreferenceSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreferenceSetDeserializer extends JsonDeserializer<PreferenceSet> {

  private final PreferenceCatalog preferenceCatalog;

  @Override
  public PreferenceSet deserialize(JsonParser parser, DeserializationContext deserializationContext) {
    try {
      ObjectMapper mapper = (ObjectMapper) parser.getCodec();
      JsonNode node = mapper.readTree(parser);
      if (node != null && node.isObject()) {
        PreferenceSet preferenceSet = new PreferenceSet();
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        while (iterator.hasNext()) {
          Map.Entry<String, JsonNode> entry = iterator.next();
          parsePreference(mapper, entry).ifPresent(value -> preferenceSet.put(entry.getKey(), value));
        }
        return preferenceSet;
      }
    } catch (Exception e) {
      log.debug("Could not parse json object to PreferenceSet", e);
      throw new RuntimeException("cannot parse json object to PreferenceSet");
    }
    return null;
  }

  private Optional<Object> parsePreference(ObjectMapper mapper, Map.Entry<String, ? extends TreeNode> node) throws IOException {
    PreferenceSchema<?> schema = preferenceCatalog.resolve(node.getKey())
      .orElseThrow(() -> new IOException("unknown preference: " + node.getKey()));

    Object value = null;
    if (schema.getClassType() != null) {
      value = mapper.convertValue(node.getValue(), schema.getClassType());
    } else if (schema.getTypeReference() != null) {
      value = mapper.convertValue(node.getValue(), schema.getTypeReference());
    }
    return Optional.ofNullable(value);
  }

  @Override
  public Class<?> handledType() {
    return PreferenceSet.class;
  }
}
