package com.aio.portable.swiss.sugar.type;

import org.springframework.data.util.Streamable;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class StreamSugar {
    /**
     * reverse a Stream
     *
     * <pre>
     * {@code
     * assertThat(StreamSugar.reverse(Stream.of(1,2,3)).collect(CyclopsCollectors.toList())
     * ,equalTo(Arrays.asList(3,2,1)));
     * }
     * </pre>
     *
     * @param stream Stream to reverse
     * @return Reversed stream
     */
    public static final <T> Stream<T> reverse(final Stream<T> stream) {
        List<T> reverseList = stream.collect(Collectors.toList());
        java.util.Collections.reverse(reverseList);
        return reverseList.stream();
    }

    public static final <T> Stream<T> except(final Stream<T> stream1, final Stream<T> stream2) {
        final List<T> collection2 = stream2.collect(Collectors.toList());
        Stream<T> stream = stream1.filter(item -> !collection2.contains(item));
        return stream;
    }

    public static <T> Stream<T> intersect(final Stream<T> stream1, final Stream<T> stream2) {
        final List<T> collection2 = stream2.collect(Collectors.toList());
        Stream<T> stream = stream1.filter(c -> collection2.contains(c));
        return stream;
    }

    /**
     * tail
     * @param stream
     * @param <T>
     * @return
     */
    public static <T> Optional<T> tail(final Stream<T> stream) {
        long count = stream.count();
        Optional<T> optional = stream.skip(count-1).findFirst();
        return optional;
    }

    public static <T> boolean anyEquals(final Stream<T> source, final Stream<T> target) {
        final List<T> targetCollection = target.collect(Collectors.toList());
        boolean anyMatch = source.anyMatch(src -> targetCollection.contains(src));
        return anyMatch;
    }






    /**
     * Create a Stream that finitely repeat the provided Streamable, provided number of times
     *
     * <pre>
     * {@code
     * assertThat(StreamSugar.repeat(3,Streamable.of(1,2,2))
     * .collect(CyclopsCollectors.toList()),
     * equalTo(Arrays.asList(1,2,2,1,2,2,1,2,2)));
     * }
     * </pre>
     *
     * @param stream Streamable to repeat
     * @return New cycling stream
     */
    public static <T> Stream<T> repeat(final Streamable<T> stream, final int times) {
        return Stream.iterate(stream.stream(), s1 -> stream.stream())
                .limit(times)
                .flatMap(Function.identity());
    }

    /**
     * increase
     * @param seed : 1
     * @param f : n -> n + 1
     * @param limit 1000
     * @param <T> {1, 2, 3, 4, ..., 1000}
     */
    public static <T> Stream<T> increase(final T seed, final UnaryOperator<T> f, long limit) {
        return Stream.iterate(seed, f).limit(limit);
    }

    /**
     * Projects an immutable toX of this stream. Initial iteration over the toX is not thread safe
     * (can't be performed by multiple threads concurrently) subsequent iterations are.
     *
     * @return An immutable toX of this stream.
     */
    public static final <T> Collection<T> toLazyCollection(final Stream<T> stream) {
        return toLazyCollection(stream.iterator());
    }

    public static final <T> Collection<T> toLazyCollection(final Iterator<T> iterator) {
        return toLazyCollection(iterator, false);
    }

    /**
     * Lazily constructs a Collection from specified Stream. Collections iterator may be safely used
     * concurrently by multiple threads.
     */
    public static final <T> Collection<T> toConcurrentLazyCollection(final Stream<T> stream) {
        return toConcurrentLazyCollection(stream.iterator());
    }

    public static final <T> Collection<T> toConcurrentLazyCollection(final Iterator<T> iterator) {
        return toLazyCollection(iterator, true);
    }

    private static final <T> Collection<T> toLazyCollection(final Iterator<T> iterator, final boolean concurrent) {
        return createLazyCollection(iterator, concurrent);

    }

    private static final <T> Collection<T> createLazyCollection(final Iterator<T> iterator, final boolean concurrent) {
        return new AbstractCollection<T>() {

            @Override
            public boolean equals(final Object o) {
                if (o == null || !(o instanceof Collection))
                    return false;
                Iterator<T> it1 = iterator();
                Iterator<T> it2 = ((Collection) o).iterator();
                while (it1.hasNext()) {
                    if (!it2.hasNext() || !Objects.equals(it1.next(), it2.next()))
                        return false;
                }
                if (it2.hasNext())
                    return false;
                return true;
            }

            @Override
            public int hashCode() {
                Iterator<T> it1 = iterator();
                List<T> arrayList = new ArrayList<>();
                while (it1.hasNext()) {
                    arrayList.add(it1.next());
                }
                return Objects.hashCode(arrayList.toArray());
            }

            List<T> data = new ArrayList<>();

            volatile boolean complete = false;

            Object lock = new Object();
            ReentrantLock rLock = new ReentrantLock();

            @Override
            public Iterator<T> iterator() {
                if (complete)
                    return data.iterator();
                return new Iterator<T>() {
                    int current = -1;

                    @Override
                    public boolean hasNext() {

                        if (concurrent) {

                            rLock.lock();
                        }
                        try {

                            if (current == data.size() - 1 && !complete) {
                                boolean result = iterator.hasNext();
                                complete = !result;


                                return result;
                            }
                            if (current + 1 < data.size()) {

                                return true;
                            }
                            return false;
                        } finally {
                            if (concurrent)
                                rLock.unlock();
                        }
                    }

                    @Override
                    public T next() {

                        if (concurrent) {

                            rLock.lock();
                        }
                        try {
                            if (current < data.size() && !complete) {
                                if (iterator.hasNext())
                                    data.add(iterator.next());

                                return data.get(++current);
                            }
                            current++;
                            return data.get(current);
                        } finally {

                            if (concurrent)
                                rLock.unlock();
                        }

                    }

                };

            }

            @Override
            public int size() {
                if (complete)
                    return data.size();
                Iterator<T> it = iterator();
                while (it.hasNext())
                    it.next();

                return data.size();
            }
        };
    }





}