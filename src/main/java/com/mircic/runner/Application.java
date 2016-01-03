package com.mircic.runner;

import com.mircic.config.SpringConfig;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by Ivan on 03-Jan-16.
 */
@SpringBootApplication
@Import(SpringConfig.class)
public class Application extends FatJarRouter{
}
