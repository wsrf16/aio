package com.aio.portable.swiss.module.nginx;

import com.github.odiszapc.nginxparser.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NginxConfig {
    static class Match {
        private final static BiFunction<NgxEntry, List<String>, Boolean> allInMatch = (entry, _condition) -> {
            if (entry instanceof NgxBlock) {
                NgxBlock ngxBlock = (NgxBlock) entry;
                boolean b = _condition.stream().allMatch(cond -> {
                    List<NgxEntry> subEntries = ((List) ngxBlock.getEntries());
                    return subEntries.stream().anyMatch(_entry -> Objects.equals(_entry.toString(), cond));
                });
                return b;
            } else if (entry instanceof NgxParam) {
                return false;
            } else
                return false;
        };
    }


    /**
     * findBlockWithAllInMatch
     *
     * @param ngxConfig
     * @param params     : {"http", "upstream"}
     * @param condition
     * @return
     */
    public final static List<NgxBlock> findBlockWithAllInMatch(NgxConfig ngxConfig, List<String> params, List<String> condition) {
        List<NgxBlock> all = ngxConfig.findAll(NgxBlock.class, params.toArray(new String[params.size()])).stream().map(c -> (NgxBlock) c).collect(Collectors.toList());
        List<NgxBlock> filter = all.stream().filter(entry -> Match.allInMatch.apply(entry, condition)).collect(Collectors.toList());
        return filter;
    }

    /**
     * findBlock
     *
     * @param ngxConfig
     * @param params    : {"http", "upstream"}
     * @param match
     * @return
     */
    public final static List<NgxBlock> findBlock(NgxConfig ngxConfig, List<String> params, Predicate<NgxEntry> match) {
        List<NgxBlock> all = ngxConfig.findAll(NgxBlock.class, params.toArray(new String[params.size()])).stream().map(c -> (NgxBlock) c).collect(Collectors.toList());
        List<NgxBlock> filter = all.stream().filter(entry -> match.test(entry)).collect(Collectors.toList());
        return filter;
    }

    /**
     * dumps
     *
     * @param ngxConfig
     * @return
     */
    public final static String dumps(NgxConfig ngxConfig) {
        String dump = new NgxDumper(ngxConfig).dump();
        return dump;
    }

    /**
     * dump
     *
     * @param ngxConfig
     * @param output
     * @throws IOException
     */
    public final static void dump(NgxConfig ngxConfig, Path output) throws IOException {
        String dump = new NgxDumper(ngxConfig).dump();
        byte[] bytes = dump.getBytes(StandardCharsets.UTF_8);
        Files.write(output, bytes, StandardOpenOption.CREATE);
    }
}
