package dev.notypie.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.CommonErrorCodeImpl;
import dev.notypie.global.error.exceptions.JsonConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Converter
public class JsonStringConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper;

    public JsonStringConverter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (ObjectUtils.isEmpty(attribute)) {
            return null;
        }
        try {
            return this.objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            ArgumentError argumentError = new ArgumentError(attribute.toString(),e.getLocation().toString(),e.getMessage());
            List<ArgumentError> errors = new ArrayList<>();
            errors.add(argumentError);
            throw JsonConvertException.builder()
                    .errorCode(CommonErrorCodeImpl.JSON_CONVERT_ERRORS)
                    .argumentErrors(errors)
                    .build();
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if(StringUtils.hasText(dbData)){
            Class<?> classType = GenericTypeResolver.resolveTypeArgument(getClass(), JsonStringConverter.class);
            try {
                return (T) this.objectMapper.readValue(dbData, classType);
            } catch (JsonProcessingException e) {
                ArgumentError argumentError = new ArgumentError(dbData,e.getLocation().toString(),e.getMessage());
                List<ArgumentError> errors = new ArrayList<>();
                errors.add(argumentError);
                throw JsonConvertException.builder()
                        .errorCode(CommonErrorCodeImpl.JSON_CONVERT_ERRORS)
                        .argumentErrors(errors)
                        .build();
            }
        }
        else return null;
    }
}
