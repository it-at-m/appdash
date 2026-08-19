package de.muenchen.oss.appdash.backend.application.service.connector.s3;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class S3JsonFilter {
  private final ObjectMapper objectMapper;

  public S3JsonFilter(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String filterJson(
      final InputStream inputStream, final Set<String> fieldsToKeep, final Set<String> fieldsToDrop)
      throws IOException {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      final JsonFactory jsonFactory = objectMapper.getFactory();

      try (JsonParser parser = jsonFactory.createParser(inputStream);
          JsonGenerator generator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8)) {

        generator.setCodec(objectMapper);

        final JsonToken initialToken = parser.nextToken();
        if (initialToken != JsonToken.START_OBJECT) {
          throw new IllegalArgumentException("Expected JSON object, but found: " + initialToken);
        }

        generator.writeStartObject();
        processTokens(parser, generator, fieldsToKeep, fieldsToDrop);
        generator.writeEndObject();
      }

      return outputStream.toString(StandardCharsets.UTF_8);
    }
  }

  private void processTokens(
      final JsonParser parser,
      final JsonGenerator generator,
      final Set<String> fieldsToKeep,
      final Set<String> fieldsToDrop)
      throws IOException {
    while (true) {
      final JsonToken currentToken = parser.nextToken();
      if (currentToken == null || currentToken == JsonToken.END_OBJECT) {
        break;
      }

      final String fieldName = parser.currentName();
      parser.nextToken();

      if (fieldName != null && fieldsToKeep.contains(fieldName)) {
        final JsonNode sectionNode = parser.readValueAsTree();
        removeEmptyNodes(sectionNode, fieldsToDrop);

        if (!sectionNode.isEmpty()) {
          generator.writeFieldName(fieldName);
          generator.writeTree(sectionNode);
        }
      } else {
        parser.skipChildren();
      }
    }
  }

  private void removeEmptyNodes(final JsonNode node, final Set<String> fieldsToDrop) {
    if (node.isObject() && node instanceof ObjectNode objectNode) {
      cleanObjectNode(objectNode, fieldsToDrop);
    } else if (node.isArray() && node instanceof ArrayNode arrayNode) {
      cleanArrayNode(arrayNode, fieldsToDrop);
    }
  }

  private void cleanObjectNode(final ObjectNode objectNode, final Set<String> fieldsToDrop) {
    final List<String> keysToRemove = new ArrayList<>();

    for (final Map.Entry<String, JsonNode> entry : objectNode.properties()) {
      final String key = entry.getKey();

      if (fieldsToDrop != null && fieldsToDrop.contains(key)) {
        keysToRemove.add(key);
        continue;
      }

      final JsonNode child = entry.getValue();
      removeEmptyNodes(child, fieldsToDrop);

      if (child.isNull() || (child.isContainerNode() && child.isEmpty())) {
        keysToRemove.add(key);
      }
    }

    for (final String key : keysToRemove) {
      objectNode.remove(key);
    }
  }

  private void cleanArrayNode(final ArrayNode arrayNode, final Set<String> fieldsToDrop) {
    final Iterator<JsonNode> it = arrayNode.elements();
    while (it.hasNext()) {
      final JsonNode child = it.next();
      removeEmptyNodes(child, fieldsToDrop);

      if (child.isNull() || (child.isContainerNode() && child.isEmpty())) {
        it.remove();
      }
    }
  }
}
