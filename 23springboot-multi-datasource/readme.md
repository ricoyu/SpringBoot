# 1.配置多数据源的注意事项

不同数据源的配置要分开

关注每次使⽤用的数据源

* 有多个DataSource时系统如何判断
* 对应的设施（事务、ORM等）如何选择DataSource

手工配置两组 DataSource 及相关内容

与Spring Boot协同⼯工作（二选一）

* 配置@Primary类型的Bean

* 排除Spring Boot的自动配置

  DataSourceAutoConfiguration
  DataSourceTransactionManagerAutoConfiguration
  JdbcTemplateAutoConfiguration

