package com.lkl.springboot.hystrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 请求合并
 */
public class TestCommandCollapsing extends HystrixCollapser<List<String>, String, Integer> {

    private final Integer key;

    public TestCommandCollapsing(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {

        return new BatchCommond(requests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse,
                                         Collection<CollapsedRequest<String, Integer>> requests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }

    }

    private final static class BatchCommond extends HystrixCommand<List<String>> {
        private Collection<CollapsedRequest<String, Integer>> requests;

        protected BatchCommond(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).andCommandKey(
                HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            ArrayList<String> response = new ArrayList<String>();
            for (CollapsedRequest<String, Integer> request : requests) {
                // artificial response for each argument received in the batch
                response.add("ValueForKey: " + request.getArgument());
            }
            return response;
        }

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        System.out.println(new TestCommandCollapsing(1).queue().get());
        context.shutdown();
    }

}
