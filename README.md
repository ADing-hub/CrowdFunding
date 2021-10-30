# CrowdFunding

## 介绍

项目——尚筹网
帮助创业者发布创业项目，向大众募集启动资金的融资平台。

## 软件架构（未完善）
从单一架构阶段到分布式微服务架构阶段的过渡。后台管理员系统使用单一架构开发。前台会
员系统使用分布式微服务架构开发。

## 使用技术

### 搭建环境

* 使用Maven 作为构建管理和依赖管理工具。

* 使用SpringMVC 作为Web 层框架。

  * 普通请求：返回响应体通常为一个页面

  * Ajax 请求：返回响应体通常为一个JSON 数据
* 使用MyBatis 作为持久化层框架。
* 使用MyBatis 的PageHelper 插件实现数据的分页显示。
    * Admin 数据
    * Role 数据
* 使用Spring 提供的容器管理项目中的组件。
    * XxxHandler
    * XxxService
    * XxxMapper
    * XxxInterceptor
    * XxxExceptionResolve

**后端**

1. Spring
2. SpringMVC
3. Mybatis
4. Maven
5. SpringBoot
6. SpringSecurity 
7. SpringCloud

**前端** 

1. Bootstrap
2. zTree
3. layer

**数据库**

- MySQL5.x

**外部**

1. 阿里云市场短信验证码接口
2. 支付宝网页支付沙箱环境
3. 阿里云对象存储 OSS
