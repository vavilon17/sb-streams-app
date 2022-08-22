package com.smartbrick.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DebeziumConfig {

    public static final Set<String> MUTATION_OPS = new HashSet<>(Arrays.asList("c", "u", "d", "r"));
    public static final Set<String> UPSERT_OPS = new HashSet<>(Arrays.asList("c", "u", "r"));
}
