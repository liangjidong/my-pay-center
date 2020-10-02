spring ssm：war包， tomcat下运行  
springboot: spring再简化，spring-boot jar包，内嵌tomcat    
微服务架构  
springcloud  

### spring

### springboot
约定大于配置
##### pom.xml
```text
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
    </parent>

    starter是启动器，导入后springboot会自动启动导入的组件
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```
##### Application.java
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

##### HelloController
```java
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "this is a test";
    }
}

```

##### springboot banner
resources目录下新建banner.txt就行  
https://www.bootschool.net/ascii-art 从这个网站上下载一个


##### yaml
给实体类赋值(**配置**)  
https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/configuration-metadata.html#configuration-metadata-annotation-processor  
pom.xml中添加依赖，方便在配置文件中提示
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
```
导入这个依赖后，application.yaml文件中就能自动提示了  
```text
@ConfigurationProperties(prefix = "dog")
参考Dog类
```
在application.yaml中配置dog
```yaml
dog:
  name: 旺财
  age: 3
```

##### springboot配置加载优先级
- 优先级1： 项目路径下的config文件夹配置文件
- 优先级2： 项目路径下的配置文件
- 优先级3： 资源路径下的config文件夹配置文件
- 优先级4： 资源路径下配置文件

##### 多环境配置
激活的概念：spring.profiles  

##### springboot配置自动生效的原因
@SpringBootApplication
    @EnableAutoConfiguration
        @Import({AutoConfigurationImportaSelector.class})  

AutoConfigurationImportSelector加载 META-INF/spring.factories 下的类  
这里拿org\springframework\boot\spring-boot-autoconfigure\2.3.3.RELEASE\spring-boot-autoconfigure-2.3.3.RELEASE.jar!\META-INF\spring.factories 里面配置的**BatchAutoConfiguration** 为例。

```java
@Configuration(proxyBeanMethods = false) 
// Conditional表示条件，即必须满足xxx条件才会自动装配
@ConditionalOnClass({ JobLauncher.class, DataSource.class })
@AutoConfigureAfter(HibernateJpaAutoConfiguration.class)
@ConditionalOnBean(JobLauncher.class)
// BatchProperties里面的配置才会被识别
@EnableConfigurationProperties(BatchProperties.class)
@Import(BatchConfigurerConfiguration.class)
public class BatchAutoConfiguration {
}

@ConfigurationProperties(prefix = "spring.batch")
public class BatchProperties {
    // ...
}
```

##### 查看哪些配置类生效
在application.yaml文件中配置  
**debug: true**


#### 员工管理系统
##### 静态资源
public, static, /**, resources --> localhost:8080  
resources>static（默认）>public  

放在static目录下  
http://localhost:8080/css/images/meteorshower2.jpg
##### thymeleaf
templates目录下的所有文件，只能通过controller访问,需要模板引擎的支持
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
thymeleaf模板文件默认**html**结尾

##### thymeleaf语法
- 命名空间：xmlns:th="http://www.thymeleaf.org"
- 所有html元素属性都可以被thymeleaf接管，加th前缀: <span th:text="${varName}"></span>
- Variable Expressions: ${...}
- Selection Variable Expressions: *{...}
- Message Expressions: #{...}
- **Link URL Expressions: @{...}**
- Fragment Expressions: ~{...}

##### 扩展springMVC
```java
/**
 * 扩展mvc
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
}
```

##### 国际化
AcceptHeaderLocaleContextResolver
AcceptHaderLocaleResolver
1,自定义组件LocaleResolver
```java
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(language)) {
            String[] s = language.split("_");
            locale = new Locale(s[0], s[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
```
2,注入bean
```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    // 国际化组件
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
```

3，resources目录下建立i18n目录，在该目录下创建  
login.properties  
login_en_US.properties  
login_zh_CN.properties  
分别配置对应语言的描述

4，前端页面增加切换按钮
```html
<a class="btn btn-sm" th:href="@{/login.html(l='zh_CN')}">中文</a>
<a class="btn btn-sm" th:href="@{/login.html(l='en_US')}">English</a>
```

##### url传参
th:fragment= ""  
th:replace="~{xxx/xxx::xxx(传参xx=xxxx)}"  


##### x-admin 后台模板


### JDBC

#### MyBatis
apollo用的是JPA
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.3</version>
</dependency>
```
```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver

# 整合mybatis
mybatis:
  type-aliases-package: com.kuaishou.ljd.model
  mapper-locations: classpath:mybatis/mapper/*.xml
```

```java
@Mapper
@Repository
public interface StudentMapper {
    List<Student> listAll();

    Student getById(int id);

    int add(Student student);

    int update(Student student);

    int delete(int id);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.kuaishou.ljd.mapper.StudentMapper">
    <select id="listAll" resultType="Student">
        select * from student
    </select>

    <select id="getById" resultType="Student">
        select * from student where id = #{id}
    </select>

    <insert id="add" parameterType="Student">
        insert into student (sid,name,age) values (#{sid}, #{name}, #{age})
    </insert>

    <update id="update" parameterType="Student">
        update student set sid=#{sid}, name=#{name}, age=#{age} where id = #{id}
    </update>

    <delete id="delete" parameterType="int">
        delete from student where id = #{id}
    </delete>
</mapper>
```

#### Druid

#### Shiro
   - subject
   - SecurityManager
   - Relam

#### Spring security


#### 异步任务

#### Swagger

#### Dubbo+Zookeeper


### kafka
#### 1，异步通信原理
##### 观察者模式
##### 生产者消费者模式
##### 缓冲区
##### 数据单元

#### 2，消息系统原理
##### 推送 push
推送的缺点：客户端的处理能力不同，而推送一般都是均匀推送，会导致处理能力强的无法最大化处理，而处理能力差的处理不过来。  
点对点
##### 拉取 poll
观察者模式：观察者发现数据变化，将变化通知给消费者，消费者再主动拉取变化的数据

一般使用推送推数据变化的消息，拉取会主动拉新数据。

#### 3，kafka简介
以O(1)复杂度持久化数据

   - 顺序保证
   - 异步通信  

#### 4，kafka系统架构
   - Broker：服务器节点
    除了数据存储在broker上之外，其他的元数据信息都存在zookeeper上
   - topic：相当于mysql的table
   - partition：相当于水平分表（根据分配策略）
    每条消息都有一个自增的编号。每个partition中的数据使用多个segment文件存储；单个partition消息有序，但是单个topic的多个parition消息无序，因此在需要保证单个topic消息严格有序的场景下，需要设置partition的数量为**1**
   - Leader：负责写入和读取数据
   - Follower：负责备份，保证可用性
   - replication：备份数量为N，表示Leader+Follower=N
   - Producer
   - Consumer
#### 5，kafka使用zookeeper


#### 6，搭建kafka
##### 搭建zk

   




