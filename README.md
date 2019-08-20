### natu
基于无服务器架构的动态脚本服务框架，支持多种语言(包括java/groovy/python/javascript 以及基于这几种语言的DSL)。主要分成以下几个部分
* natu-base 基础依赖
* natu-core 核心模块
* natu-aggron 脚本加载模块
* natu-bonsly 脚本处理模块
* natu-lileep 触发器模块

### feature
* 脚本支持spring bean注入/调用
* 支持自定义脚本加载/脚本处理/触发器
* 支持服务刷新（hot reload）
* 项目使用kotlin开发，可以支持kotlin协程。 插件定制的话，可以使用java/kotlin 但涉及到协程的推荐使用kotlin

### built-in
已有插件
* __natu-aggron-file__ 文件系统脚本加载 
* __natu-aggron-git__   git仓库文件加载
* __natu-aggron-persist__ 持久化脚本加载
* __natu-bonsly-asyncjava__ 支持async/await的java/groovy脚本处理器
* __natu-bonsly-groovy__ groovy脚本处理器
* __natu-bonsly-onix__ python脚本处理器  （Experimental)
* __natu-bonsly-shit__ javascript脚本处理器 (Experimental)
* __natu-lileep-http__ http触发器
* __natu-lileep-scheduler__ 定时任务触发器
* __natu-lileep-shell__ 基于spring shell交互式命令行
