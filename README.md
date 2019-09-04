### iotaki
基于无服务器架构的动态脚本服务框架，支持多种语言(包括java/groovy/python/javascript 以及基于这几种语言的DSL)。主要分成以下几个部分
* iotaki-base 基础依赖
* iotaki-core 核心模块
* iotaki-aggron 脚本加载模块
* iotaki-bonsly 脚本处理模块
* iotaki-lileep 触发器模块

### feature
* 脚本支持spring bean注入/调用
* 支持自定义脚本加载/脚本处理/触发器
* 支持服务刷新（hot reload）
* 项目使用kotlin开发，可以支持kotlin协程。 插件定制的话，可以使用java/kotlin 但涉及到协程的推荐使用kotlin

### built-in
已有插件
* __iotaki-aggron-file__ 文件系统脚本加载 
* __iotaki-aggron-git__   git仓库文件加载
* __iotaki-aggron-persist__ 持久化脚本加载
* __iotaki-bonsly-asyncjava__ 支持async/await的java/groovy脚本处理器
* __iotaki-bonsly-groovy__ groovy脚本处理器
* __iotaki-bonsly-onix__ python脚本处理器  （Experimental)
* __iotaki-bonsly-shit__ javascript脚本处理器 (Experimental)
* __iotaki-lileep-http__ http触发器
* __iotaki-lileep-scheduler__ 定时任务触发器
* __iotaki-lileep-shell__ 基于spring shell交互式命令行
* __iotaki-lileep-amqp__ rabbitmq消息触发器
