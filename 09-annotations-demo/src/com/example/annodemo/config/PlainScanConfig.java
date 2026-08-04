package com.example.annodemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * No class-level @Lazy -> per-bean @Lazy annotations are the only thing
 * that matters. Expectation when this config is used:
 *   - EagerBean constructed immediately at context startup
 *   - ForcedEagerBean constructed immediately at context startup
 *   - LazyBean constructed only when first requested
 */
@Configuration
@ComponentScan("com.example.annodemo.lazyinit")
public class PlainScanConfig {
}
