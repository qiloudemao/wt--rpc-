package com.wt.wtrpc.fault.tolerant;

import com.wt.wtrpc.model.RpcResponse;

import java.util.Map;

public interface TolerantStrategy {
    RpcResponse doTolerant(Map<String,Object> context,Exception e);
}
