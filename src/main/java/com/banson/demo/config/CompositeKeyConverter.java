package com.banson.demo.config;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.entity.Node;
import com.banson.demo.model.entity.PersonId;
import com.banson.demo.model.entity.Person;

/**
 * (De)serializes composite keys to a single string so that spring-data-rest can (de)construct URLs with them.
 * 
 * @author BA030483
 *
 */
@Component
public class CompositeKeyConverter implements BackendIdConverter {
    private static final String DELIMITER = ":";

    @Override
    public Serializable fromRequestId(String id, Class<?> entityType) {
        if (id != null) {
            String[] parts = id.split(DELIMITER);
            return PersonId.of(parts[0], parts[1]);
        }
        return BackendIdConverter.DefaultIdConverter.INSTANCE.fromRequestId(id, entityType);
    }

    @Override
    public String toRequestId(Serializable source, Class<?> entityType) {
        if (source instanceof PersonId) {
            PersonId id = (PersonId) source;
            return id.getFirstName() + DELIMITER + id.getLastName();
        }
        return BackendIdConverter.DefaultIdConverter.INSTANCE.toRequestId(source, entityType);
    }

    @Override
    public boolean supports(Class<?> type) {
        return Node.class.equals(type) || CollectionNode.class.equals(type) || Person.class.equals(type);
    }
}
