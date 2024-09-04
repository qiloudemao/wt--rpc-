package com.wt.wtrpc.fault.retry;

import com.wt.wtrpc.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {
    RpcResponse doRetry(Callable<RpcResponse>callable) throws Exception;
}
