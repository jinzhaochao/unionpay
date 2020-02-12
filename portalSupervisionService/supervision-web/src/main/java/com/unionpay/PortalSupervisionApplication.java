package com.unionpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.unionpay.common.*","com.unionpay.*"})
@ServletComponentScan
@EnableScheduling
public class PortalSupervisionApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(com.unionpay.PortalSupervisionApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(com.unionpay.PortalSupervisionApplication.class, args);
    }
}
