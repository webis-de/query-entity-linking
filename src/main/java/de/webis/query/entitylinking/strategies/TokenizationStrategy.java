package de.webis.query.entitylinking.strategies;

import de.webis.query.entitylinking.datastructures.Query;

import java.util.Set;

public interface TokenizationStrategy {
    Set<String> apply(Query query);
}
