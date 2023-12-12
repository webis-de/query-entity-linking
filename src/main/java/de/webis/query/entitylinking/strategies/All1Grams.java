package de.webis.query.entitylinking.strategies;

import de.webis.query.entitylinking.datastructures.Query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class All1Grams implements TokenizationStrategy {
    @Override
    public Set<String> apply(Query query) {
        return new HashSet<>(Arrays.asList(
                query.getText()
                        .trim()
                        .split("[\\s]+")));
    }
}
