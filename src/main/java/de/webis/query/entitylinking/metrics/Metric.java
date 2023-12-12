package de.webis.query.entitylinking.metrics;

import de.webis.query.entitylinking.datastructures.PersistentStore;

public abstract class Metric {
    protected PersistentStore<String, Double> storage;

    protected Metric(String storageDir){
        storage = new PersistentStore<>(storageDir);
    }

    public double get(String entity, String mention){
        final Double value = storage.get(mention + "->" + entity);

        if(value == null){
            return 0.0;
        }

        return value;
    }

    public void close(){
        if(storage != null){
            storage.close();
        }
    }
}
