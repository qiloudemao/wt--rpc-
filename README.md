#### 框架基本介绍



**RPC定义**：RPC（Remote Procedure Call）远程调用，是一种计算机通信协议，允许程序在不同的计算机之间进行通信和交互，就像本地调用一样



**简易框架示意图**：

![image-20240925234543563](C:\Users\86178\AppData\Roaming\Typora\typora-user-images\image-20240925234543563.png)



## RPC：框架扩展



**1、支持自定义全局配置加载，支持在 [application.properties](example-comsumer\src\main\resources\application.properties) 内配置RPC相关值**

​	涉及相关的类为ConfigUtils。全局的配置对象，通过单例模式来维护。

**2、支持Mock接口，通过配置mock变量来开启和关闭**

**3、通过SPI动态支持多种序列化器（JSON、Kryo、Hessian  默认为JSON）**

​	使用工厂+单例来实现创建和获取序列化器对象的操作

**4、支持配置和扩展注册中心（目前只实现了Etcd）**

​	注册中心支持心跳检测和续期机制（CronUtils.schedule 定期续约）。

​	服务信息缓存机制。（主要使用Etcd watch监听器保证缓存一致）

**5、支持服务负载均衡**

​	轮询负载均衡器 RoundRobinLoadBalancer 

​	随机负载均衡器 RandomLoadBalancer

​	一致性Hash 负载均衡器 ConsistentHashLoadBalancer

**6、支持重试机制**

​	不重试 NoRetryStrategy

​	固定时间重试 FixedIntervalRetryStrategy

**7、支持容错机制**

​	

