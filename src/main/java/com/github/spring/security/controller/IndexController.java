package com.github.spring.security.controller;

import java.util.concurrent.StructuredTaskScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.spring.security.strategy.ScopedSecurityContextHolderStrategy;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    @ResponseBody
    public String get() {
        Assert.isInstanceOf(
                ScopedSecurityContextHolderStrategy.class,
                SecurityContextHolder.getContextHolderStrategy(),
                ScopedSecurityContextHolderStrategy.class.getSimpleName() +
                " not installed as Context Holder Strategy actual is " +
                SecurityContextHolder.getContextHolderStrategy().getClass().getSimpleName());
        var message = "Current authentication is: ";
        try (var scope = StructuredTaskScope.open()) {
            var authTask = scope.fork(() -> SecurityContextHolder.getContext().getAuthentication());
            scope.join();
            message += authTask.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info(message);
        return message;
    }

}
