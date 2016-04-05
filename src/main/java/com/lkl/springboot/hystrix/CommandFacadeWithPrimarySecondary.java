package com.lkl.springboot.hystrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class CommandFacadeWithPrimarySecondary extends HystrixCommand<String> {
    private final static DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty(
                                                               "primarySecondary.usePrimary", true);
    private final int                           id;

    public CommandFacadeWithPrimarySecondary(int id) {
        super(Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("PrimarySecondaryCommand"))
            .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
        this.id = id;
    }

    @Override
    protected String run() {
        if (usePrimary.get()) {
            return new PrimaryCommand(id).execute();
        } else {
            return new SecondaryCommand(id).execute();
        }
    }

    @Override
    protected String getFallback() {
        return "static-fallback-" + id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    private static class PrimaryCommand extends HystrixCommand<String> {
        private final int id;

        private PrimaryCommand(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand")).andCommandPropertiesDefaults(
                // we default to a 600ms timeout for primary
                    HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));
            this.id = id;
        }

        @Override
        protected String run() {
            // perform expensive 'primary' service call
            return "responseFromPrimary-" + id;
        }
    }

    private static class SecondaryCommand extends HystrixCommand<String> {
        private final int id;

        private SecondaryCommand(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("SecondaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SecondaryCommand")).andCommandPropertiesDefaults(
                // we default to a 100ms timeout for secondary
                    HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100)));
            this.id = id;
        }

        @Override
        protected String run() {
            // perform fast 'secondary' service call
            return "responseFromSecondary-" + id;
        }
    }

    public static class UnitTest {
        @Test
        public void testPrimary() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", true);
                assertEquals("responseFromPrimary-20", new CommandFacadeWithPrimarySecondary(20).execute());
            } finally {
                context.shutdown();
                ConfigurationManager.getConfigInstance().clear();
            }
        }

        @Test
        public void testSecondary() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", false);
                assertEquals("responseFromSecondary-20", new CommandFacadeWithPrimarySecondary(20).execute());
            } finally {
                context.shutdown();
                ConfigurationManager.getConfigInstance().clear();
            }
        }
    }
}