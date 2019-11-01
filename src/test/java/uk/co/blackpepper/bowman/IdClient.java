package uk.co.blackpepper.bowman;

import java.net.URI;
import java.util.function.Function;

import com.banson.demo.client.model.CompositeId;
import com.banson.demo.client.model.Entity;

public class IdClient<ID extends CompositeId<ID>, T extends Entity<ID>> extends Client<T> {
    private final Function<ID, URI> idToUri;

    IdClient(Class<T> entityType, Configuration configuration, RestOperations restOperations, ClientProxyFactory proxyFactory,
        Function<ID, URI> idToUri) {
        super(entityType, configuration, restOperations, proxyFactory);
        this.idToUri = idToUri;
    }

    public void delete(ID id) {
        delete(idToUri.apply(id));
    }
}
