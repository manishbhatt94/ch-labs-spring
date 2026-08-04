package com.example.annodemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * XML equivalent:
 *   <context:component-scan base-package="com.example.annodemo.stereotypes"/>
 *
 * @ComponentScan tells Spring which package(s) to scan for classes carrying
 * @Component (or any annotation meta-annotated with @Component, such as
 * @Service/@Repository/@Controller/@Configuration) and register them as beans.
 */
@Configuration
@ComponentScan("com.example.annodemo.stereotypes")
public class StereotypeConfig {
}
