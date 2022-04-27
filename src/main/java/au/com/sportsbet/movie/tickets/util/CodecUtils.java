package au.com.sportsbet.movie.tickets.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public final class CodecUtils {
  private CodecUtils() {
  }

  public static final ObjectMapper OBJECT_MAPPER = basicMapper();

  /**
   * This method is used to serialize any Java value as a String.
   *
   * @param object Any object
   * @return An object of ObjectMapper
   */
  public static String writeValueAsString(final Object object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("message = {}", e.getMessage());
    }

    return null;
  }


  /**
   * This method is used to create an ObjectMapper for reading and writing JSON.
   *
   * @return An object of ObjectMapper
   */
  public static ObjectMapper basicMapper() {
    return new ObjectMapper()
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true)
        .registerModule(javaTimeModule());
  }

  /**
   *
   * @return An object of JavaTimeModule.
   */
  public static Module javaTimeModule() {
    return new JavaTimeModule().addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
      @Override
      public void serialize(final BigDecimal value, final JsonGenerator jsonGenerator,
                            final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(value.setScale(2,  RoundingMode.HALF_UP));
      }
    });
  }

}
