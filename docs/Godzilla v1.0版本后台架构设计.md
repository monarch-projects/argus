# 一、Godzilla v1.0版本后台架构设计
## 1. 概览图
![服务治理平台架构图01](https://github.com/starboyate/Godzilla/blob/master/img/%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%E5%B9%B3%E5%8F%B0%E6%9E%B6%E6%9E%84%E5%9B%BE01.png)

<br/>

## 2. 具体架构设计
![服务治理平台架构图02](https://github.com/starboyate/Godzilla/blob/master/img/%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86%E5%B9%B3%E5%8F%B0%E6%9E%B6%E6%9E%84%E5%9B%BE02.png)

<br/>

其实整体主要分为Server端和Client端,下面详细介绍两者的设计

### 2.1 Server端的设计
Server端包括的模块
- application (提供对外的应用层服务)
    - controller
    - service
    - mapper
- core (提供注册实例信息的服务，具体就是这里去做代理去获取Client端插桩的信息)
   - adapter
- adapter (提供不同注册中心的不同处理)
   - eureka-adapter
   - zookeeper-adapter
   - etcd-adapter
   - configserver-adapter
   
<br/>

__**具体流程**__: 
- 注册到注册中心
- 通过core模块获取注册中心对应的实例信息
- 当用户进行查看对应信息时，通过core模块代理获取Client端插桩的信息
- 当用户想要对实例进行修改，例如熔断、降级这类操作的时候，通过core模块推送对应的配置给Client端

<br/>

### 2.2 Client端的设计
Client端包含的模块
- spring boot actuator (具体的注册实例健康检查信息)
- plugin (根据使用的spring boot版本做适配，为了以后扩展，因为不同spring boot版本肯定相应的源码、API尽不相同)
    - plugin-2.x
- agent (这里也是为了扩展用的一个插桩采集，后面可以通过这种方式获取更多不一样丰富的信息)
- extension (使用不同的组件进行不同的处理操作)

<br/>

 __**具体流程**__
 - 服务引入Client端依赖
 - Client开放endpoint提供给Server端调用
 - 当Server端推送数据过来，通过extension模块根据当前使用的不同组件进行不一样的处理。
 
## 3. 补充

大概的一个设计文档就是目前上面说的，有什么需要补充的，就在这里加上
 
