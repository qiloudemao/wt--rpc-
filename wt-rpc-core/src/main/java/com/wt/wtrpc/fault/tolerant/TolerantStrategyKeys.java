package com.wt.wtrpc.fault.tolerant;

public interface TolerantStrategyKeys {
    /**
     * 故障恢复
     */
    String FAIL_BACK="failBack";
    /**
     * 故障转移
     */
    String FAIL_OVER="failOver";
    /**
     * 快速失败
     */
    String FAIL_FAST="failFast";
    /**
     * 静默处理
     */
    String FAIL_SAFE="failSafe";
}
