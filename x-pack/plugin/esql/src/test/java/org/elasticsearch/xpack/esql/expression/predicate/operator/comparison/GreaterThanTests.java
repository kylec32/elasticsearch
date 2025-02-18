/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.esql.expression.predicate.operator.comparison;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;

import org.elasticsearch.xpack.esql.evaluator.predicate.operator.comparison.GreaterThan;
import org.elasticsearch.xpack.ql.expression.Expression;
import org.elasticsearch.xpack.ql.expression.predicate.operator.comparison.BinaryComparison;
import org.elasticsearch.xpack.ql.tree.Source;
import org.elasticsearch.xpack.ql.type.DataTypes;
import org.hamcrest.Matcher;

import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.equalTo;

public class GreaterThanTests extends AbstractBinaryComparisonTestCase {
    public GreaterThanTests(@Name("TestCase") Supplier<TestCase> testCaseSupplier) {
        this.testCase = testCaseSupplier.get();
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() {
        return parameterSuppliersFromTypedData(List.of(new TestCaseSupplier("Int > Int", () -> {
            int rhs = randomInt();
            int lhs = randomInt();
            return new TestCase(
                List.of(new TypedData(lhs, DataTypes.INTEGER, "lhs"), new TypedData(rhs, DataTypes.INTEGER, "rhs")),
                "GreaterThanIntsEvaluator[lhs=Attribute[channel=0], rhs=Attribute[channel=1]]",
                DataTypes.BOOLEAN,
                equalTo(lhs > rhs)
            );
        })));
    }

    @Override
    protected <T extends Comparable<T>> Matcher<Object> resultMatcher(T lhs, T rhs) {
        return equalTo(lhs.compareTo(rhs) > 0);
    }

    @Override
    protected BinaryComparison build(Source source, Expression lhs, Expression rhs) {
        return new GreaterThan(source, lhs, rhs, ZoneOffset.UTC);
    }

    @Override
    protected boolean isEquality() {
        return false;
    }
}
