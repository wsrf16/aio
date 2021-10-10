package com.aio.portable.park.unit;

import com.aio.portable.swiss.design.specification.Specification;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@TestComponent
public class SpecificationTest {
    @Test
    public void foobar() {
        List<Melon> melons = new ArrayList<Melon>();
        melons.add(new Melon("Watermelon", 3, "europe"));
        melons.add(new Melon("Firemelon", 1, "china"));


        final Predicate<Melon> melonPredicate1 = (Melon m) -> "Watermelon".equals(m.getType());
        final Predicate<Melon> melonPredicate2 = (Melon m) -> m.getWeight() == 3;
        final Predicate<Melon> melonPredicate3 = (Melon m) -> m.getWeight() > 4;
        List<Predicate<Melon>> predicateList = new ArrayList<>();
        predicateList.add(melonPredicate1);
        predicateList.add(melonPredicate2);

        List<Melon> watermelons1 = Specification.filter(
                melons, (Melon m) -> "Watermelon".equals(m.getType()));


        List<Melon> watermelons2 = Specification.filterAllMatch(
                melons, predicateList);
        List<Melon> watermelons3 = Specification.filter(
                melons, melonPredicate1.and(melonPredicate2).or(melonPredicate3));
    }



    static class Melon {
        /**品种*/
        private final String type;
        /**重量*/
        private final int weight;
        /**产地*/
        private final String origin;

        public Melon(String type, int weight, String origin) {
            this.type = type;
            this.weight = weight;
            this.origin = origin;
        }
        // getters, toString()方法省略


        public String getType() {
            return type;
        }

        public int getWeight() {
            return weight;
        }

        public String getOrigin() {
            return origin;
        }


    }
}
