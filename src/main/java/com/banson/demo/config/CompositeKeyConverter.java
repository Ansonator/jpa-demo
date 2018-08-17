package com.banson.demo.config;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.entity.Node;
import com.banson.demo.model.entity.NodeId;
import com.banson.demo.model.entity.NodePk;

/**
 * (De)serializes composite keys to a single string so that spring-data-rest can (de)construct URLs with them.
 * 
 * @author BA030483
 *
 */
@Component
public class CompositeKeyConverter implements BackendIdConverter {
    private static final String delimiter = ":";

    @Override
    public Serializable fromRequestId(String id, Class<?> entityType) {
        if (id != null) {
            String[] parts = id.split(delimiter);
            return NodeId.of(parts[0].charAt(0), Long.parseLong(parts[1]));
        }
        return BackendIdConverter.DefaultIdConverter.INSTANCE.fromRequestId(id, entityType);
    }

    @Override
    public String toRequestId(Serializable source, Class<?> entityType) {
        if (source instanceof NodeId) {
            NodeId id = (NodeId) source;
            return id.getPk1() + delimiter + id.getPk2();
        }
        return BackendIdConverter.DefaultIdConverter.INSTANCE.toRequestId(source, entityType);
    }

    @Override
    public boolean supports(Class<?> type) {
        return Node.class.equals(type) || CollectionNode.class.equals(type) || NodePk.class.equals(type);
    }
}
