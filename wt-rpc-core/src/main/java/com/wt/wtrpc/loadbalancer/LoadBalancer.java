package com.wt.wtrpc.loadbalancer;

import com.wt.wtrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

public interface LoadBalancer {
    /**
     * 选择服务调用
     * @param requestParams  请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String,Object>requestParams, List<ServiceMetaInfo>serviceMetaInfoList);
}
