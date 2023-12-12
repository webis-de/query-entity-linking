package de.webis.query.entitylinking.metrics;

public class EntityCommonness extends Metric{

    protected final static Metric INSTANCE = new EntityCommonness();

    public static Metric getInstance(){
        return INSTANCE;
    }

    private EntityCommonness(){
        super("./data/persistent/entity-commonness");
    }
}
