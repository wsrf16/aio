package com.aio.portable.swiss.sugar.type;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FluxSugar {
    public static <T> List<T> flux2ProcessData(Flux<T> flux) {
        List<T> collect = flux.collectList().block();
        return collect;
    }

    public static <T> List<List<T>> fluxList2ProcessDataBatch(List<Flux<T>> fluxList) {
        List<List<T>> collect = fluxList.stream()
                .map(FluxSugar::flux2ProcessData).collect(Collectors.toList());
        return collect;
    }

    public static <T> T getFluxItem(Flux<T> flux, Function<List<T>, T> itemGetter) {
        T item = flux.collectList().flatMap(c -> Mono.just(itemGetter.apply(c))).block();
        return item;
    }

    public static <T> List<T> getFluxListItemBatch(List<Flux<T>> fluxList, Function<List<T>, T> itemGetter) {
        List<T> collect = fluxList.stream()
                .map(c -> getFluxItem(c, itemGetter)).collect(Collectors.toList());
        return collect;
    }

    public static <T> Flux<T> mergeSequential(List<Flux<T>> list) {
        return Flux.mergeSequential(list);
//
//        if (CollectionSugar.isEmpty(list))
//            return null;
//
//        if (list.size() == 1)
//            return list.get(0);
//
//        if (list.size() > 1) {
//            Flux<T> flux = list.stream()
//                    .reduce((last, current) -> Flux.mergeSequential(last, current))
//                    .get();
//            return flux;
//        }
//
//        return null;
    }

    public static <T> Flux<T> merge(List<Flux<T>> list) {
        if (CollectionSugar.isEmpty(list))
            return null;

        if (list.size() == 1)
            return list.get(0);

        if (list.size() > 1) {
            Flux<T> flux = list.stream()
                    .reduce((last, current) -> Flux.merge(last, current))
                    .get();
            return flux;
        }

        return null;
    }
}
