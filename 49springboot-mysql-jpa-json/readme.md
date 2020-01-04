# 1.Spring Boot 做了哪些配置

* DataSourceAutoConfiguration
  配置 DataSource

* DataSourceTransactionManagerAutoConfiguration
  配置 DataSourceTransactionManager

* JdbcTemplateAutoConfiguration
  配置 JdbcTemplate

  符合条件时才进⾏行行配置

# 2.数据源相关配置属性

## 2.1 通用

spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver(可选)

## 2.2 初始化内嵌数据库

spring.datasource.initialization-mode=embedded|always|never
spring.datasource.schema与spring.datasource.data确定初始化SQL⽂文件
spring.datasource.platform=hsqldb | h2 | oracle | mysql | postgresql（与前者对应）

# 3.配置多数据源的注意事项

## 3.1 不同数据源的配置要分开

## 3.2 关注每次使⽤用的数据源

### 1) 有多个DataSource时系统如何判断

### 2) 对应的设施（事务、ORM等）如何选择DataSource