package com.aio.portable.swiss.bean.node.relation;

import java.util.Objects;

@FunctionalInterface
public interface RelationEquals {
    <ID> boolean equals(ID a, ID b);

    RelationEquals OBJECTS_EQUALS = Objects::equals;
    RelationEquals DOUBLE_EQUALS = new RelationEquals() {
        @Override
        public <ID> boolean equals(ID a, ID b) {
            return a == b;
        }
    };
}
