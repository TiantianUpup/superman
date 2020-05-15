# superman

### superman是什么
`superman`是自定义的`archetype`，通过使用`superman archetype`可以生成满足我们项目需求的工程模板，提高开发效率的同时可以统一团队内的项目结构风格

### 开发环境  
- maven3.6.1
- jdk1.8
- IntelliJ IDEA 2018
- win10 OS

### supmen archetype技术选型
- DB  
MySQL
- ORM  
MyBatisPlus
- Framework  
SpringBoot

### DONE
- [x] SpringBoot、MyBatisPlus框架的整合
- [x] 统一异常的处理
- [x] 统一结果封装
- [x] log4j日志的配置
- [x] swagger2整合
- [x] MybatisPlus
  - [x] 代码生成器 
  - [x] 分页
  - [x] 逻辑删除
  
### TODO
- [ ] 工具类的整合
- [ ] 常用中间件的封装

### Quick Start【快速使用】
- 1.下载源码
  ```
  git clone https://github.com/TiantianUpup/superman.git
  ```
- 2.打开superman工程，将其安装到本地仓库  
运行命令前先切换到20190815-v2update分支，master分支被我玩坏了【真丢人！！！】  
运行如下命令
  ```
  mvn clean install
  ```
- 3.使用自定义archetype初始化项目
  ```
  mvn archetype:generate 
  -DgroupId=com.h2t.test 
  -DartifactId=superman-demo 
  -Dversion=1.0.0-SNAPSHOT 
  -DarchetypeGroupId=com.h2t.study 
  -DarchetypeArtifactId=superman -DarchetypeVersion=0.0.1-SNAPSHOT -X -DarchetypeCatalog=local
  ```
  参数说明  
  `-DgroupId`组ID，默认项目的包名的组ID相同  
  `DartifactId`：项目唯一标识符，即项目名称  
  `-DarchetypeGroupId`：superman的组ID，值不需要进行修改  
  `-DarchetypeArtifactId`：superman的artifactId，值不需要进行改变

- 4.修改resource文件夹下的配置文件
修改该文件夹下`application.properties` ，`log4j2-spring.xml`两个配置文件
  - `application.properties`配置文件的修改
`application.properties` 主要是`Spring`、`MyBatisPlus`和数据库的配置信息
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database?characterEncoding=UTF8&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=your password
    ```
    修改数据库、密码，默认用户名为`root`
    ```
    mybatis-plus.mapper-locations=classpath*:/mapper/*.xml  
    # mybatis-plus.type-aliases-package=
    ```
    指定`MybatisPlus`实体类别名的包，即`model`模块的`po`层包名，默认`MybatiPlus`的`mapper`文件保存在`resource`下的`mapper`文件夹下，可自行修改

   - `log4j2-spring.xml`配置文件的修改  
   `log4j2-spring.xml`主要是日志输出规则的定义
    ```
    <properties>
        <property name="LOG_INFO_HOME">Your LOG_INFO_HOME</property>
        <property name="LOG_ERROR_HOME">Your LOG_ERROR_HOME</property>
        <property name="PATTERN">%d [%t] %-5p [%c] - %m%n</property>
    </properties>
    ```
    修改为你的日志存放路径
- 5 使用代码生成器生成`controller`、`service`、`dao`、`po`层代码
代码生成器类位于`service`模块下的`generator`包下，只需要初始化几个字段值运行就可以生成相应的代码。在运行前首先在项目根目录下创建一个`mp-generator-output`文件夹，该文件夹的名字和`OUTPUT_DIR`字段值保持一致
  - `PACKAGE_NAME`  
  生成代码的包名，和项目的包名一致，负责复制过去代码会有一些小问题
  -`OUTPUT_DIR `
  生成代码保存文件地址，默认保存在项目下的`mp-generator-output`文件夹下，可以修改为自定义保存地址
  - `AUTHOR`  
  注释中作者的名字
  - `DRIVER_NAME`  
  数据库驱动
  - `HOST`  
  数据库主机号
  - `PORT`  
  数据库端口
  - `DATABASE`  
  数据库名字
  - `USERNAME`  
  数据库用户名
  - `PASSWORD`  
  数据库密码
- 6.将生成的代码移动到对应模块对应包下
  - `controller`文件夹  
  实体类对应的`Controller`，将该目录下的类移到`web`模块下的`controller`包下
  - `mapper`文件夹
  实体类对应的`DAO`层，该目录下包含`xml`文件和对应实体的接口类，将`xml文`件移到`dao`模块`resource`  下的`mapper`文件夹下，需自行建立`mapper`文件夹，将接口移到`dao`模块下的`mapper`包下并在接口类上添加`@Mapper`注解，需自行建立  `mapper`包。同时将resource文件夹标记成`Resources root`
  - `service` 对应实体类接口
      - `impl` 对应实体类接口实现类
    
    将`service`目录下的接口移到`service`模块下的`service`包下，`impl`目录下的类移到`service`模块下的`service.impl`包下
  - po文件夹 
  将该目录下的类移到`model`模块下的`po`包下，并修改继承关系，统一继承`BasePO`类，因为`BasePO`类 包含了`id`、`gmtCreate`、`gmtModified`、`deleted`这些数据库基本字段，需将生成的实体类手动删除这些重复字段。同时自动生成的`po`类缺失了`@TableName`、`@TableField`注解需手动补充。注解的使用方式可参考`BasePO`类
- 7.修改`web`模块`aspect`包下的环绕通知
  ```
  @Around("execution(* yourpackage.controller..*(..))")
  ```
  该切面主要用于拦截controller层返回的结果，将其封装成统一结果返回
- 8 启动项目  
`web`模块下的`Runner`类为启动类，运行该类即可启动，默认端口为8081
- 9 swagger2使用  
该工程已整合swagger2框架，若想使用只需要修改`controller`模块`configuration`包下的`swagger2`配置类，然后在控制类中使用`swagger2`注解即可  
关于swagger2的使用可以参考：[springboot整合swagger2](https://github.com/TiantianUpup/springboot-demo/blob/master/springboot-swagger2/README.md)
- 10[新添] RequestLogAspect使用
新添RequestLogAspect类，打印请求信息，方便和上层甩锅，使用时只需替换`@Pointcut("execution(* your_package.controller..*(..))")`中的`your_package`即可


### 更新记录
- 2020.03.12 => 添加日志请求切面 RequestLogAspect.java


附：[superman archetype生成demo工程地址](https://github.com/TiantianUpup/superman-demo)<br>
附：与本代码配套文章 => [撸一个Java脚手架，一统团队项目结构风格](https://juejin.im/post/5d53c08ff265da03ed194974)，这篇文章讲解如何写一个archetype，目录结构该怎么建立
