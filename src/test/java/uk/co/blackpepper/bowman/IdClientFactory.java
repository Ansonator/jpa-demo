//package uk.co.blackpepper.bowman;
//
//public class IdClientFactory { 
//    private ClientFactory clientFactory;
//    
//    public IdClientFactory(ClientFactory clientFactory) {
//        this.clientFactory = clientFactory;
//    }
//    
//    /**
//     * Create an <code>IdClient</code> for the given annotated entity type.
//     * 
//     * @param <T> the entity type of the required client
//     * @param <ID> the entity's identifier type
//     * @param entityType the entity type of the required client
//     * @param idType the identifier type of the required client
//     * @return the created client
//     */
//    public <T, ID> IdClient<T, ID> create(Class<T> entityType, Class<ID> idType) {
//        return new IdClient<>(entityType, configuration, restOperations, proxyFactory);
//    }
//}
