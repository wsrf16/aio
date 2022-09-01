package com.aio.portable.swiss.suite.bean.node.relation;

import java.util.Objects;

@FunctionalInterface
public interface IDEquals {
    <ID> boolean equals(ID a, ID b);

    IDEquals OBJECTS_EQUALS = Objects::equals;
    IDEquals DOUBLE_EQUALS = new IDEquals() {
        @Override
        public <ID> boolean equals(ID a, ID b) {
            return a == b;
        }
    };
}
