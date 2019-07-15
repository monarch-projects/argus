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

<br/>

## 3. 技术实现 ？ （暂定，可以讨论出更好的方案）
- 在Client收集数据这块，是通过spring boot actuator提供的endpoint来进行采集，具体可看[spring boot actuator官方文档](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/production-ready-endpoints.html),为什么还加入了agent模块呢？是因为后面spring boot actuator提供的十几个endpoint肯定满足不了，所以就需要通过agent这个模块来进行一个采集，然后通过自定义endpoint来对外暴露接口
- Client端如何获取服务实例的jar包依赖呢？这里其实还没有想出一个很好的方案，目前是想直接去暴力的获取lib目录里面的jar包。
- 在Sever端这里，其实就是一个简单普通的spring boot web项目，这里可以用传统的servlet方式，也可以采用reactive的方式，然后其实这里是涉及到数据库层面的操作，目前就先弄mysql的存储方式就行了。
- Server端如何获取注册中心上面的服务实例呢？这里以Eureka为例，我们是把Server端注册到eureka上面去，这样子，Server端就可以定时的每次去eureka拉取其他注册实例，我们通过这样的方式就可以获取所有在eureka上面的实例。那如何做到动态切换、添加注册中心呢？其实这里就是涉及到注册中心内部源码的一个操作，其实eureka一个注册是通过eureka-client先生成所有的配置，然后进行eureka-server提供对应的接口，然后生成的配置类就是EurekaClientConfig，我们只需要获取到EurekaClientConfigBean，然后修改里面一个serviceUrl的map结构里面的value。那如何通过我们的服务治理平台进行动态注册实例、或者优雅的下线服务实例，其实这个更简单，eureka-server提供了很多个endpoint出来，我们直接调用访问即可
- 如何做服务降级、熔断这块呢？其实就是一个push和pull的问题，push就是我们server端主动通知给配置的服务，但是这里会存在一个问题，如果我一个服务有很多个实例，那相当于我需要推送n（n为服务实例数）次，这里其实是有点问题的，如果是pull，就是client端主动去server端去拉取，那这里的实现，就可以参考eureka的一个实现来进行实现，但是pull的方式，就是在我想要灰度发布，或者我只是对某个实例进行熔断处理，但pull的方式决定了肯定在某一个时间会去主动的拉取对应的配置策略，这里就要在server端做很多的控制实现。
- 如何动态的进行扩容缩容呢？这里其实方式有很多，其实是否需要这个功能，我觉得这个没太大意义，因为一般情况下，动态扩容都是在不同机器上进行的，那么如果需要，就需要做到手动填写服务器地址(可以ssh)、用户、密码,然后我们通过代码层面是把之前那个jar包ssh copy到另一台机器上，然后nohup java -jar，这里我觉得这个功能有点鸡肋。
- 如何接入第三方apm平台? 假如我使用了trace平台，skywalking、zipkin之类的，那我们应该对接这个出来，去获取里面的数据，做到跟这些apm系统的无暇连接呢？其实这里还没去思考方案，后面可以再去设计这块

<br/>

上面大概的说了一下，各个主要功能的设计，但是只是一个初步方案，具体的应该再详细讨论确认每个功能的实现方式，最后在落地。

<br/>

 
## 4. 补充

大概的一个设计文档就是目前上面说的，有什么需要补充的，就在这里加上
 
