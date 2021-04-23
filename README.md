# jedis

使用Java语言简易实现一个单机Redis（开发阶段）

实现功能：
1. 完成基本网络通讯，实现string、list、hash、set、zet、bitmap六种基本类型；
2. 实现RDB、AOF持久化；
3. 实现发布订阅、命令流水、事务；


### 使用方法

* 服务端：入口位于jedis-server工程内JedisApplication启动文件，resources保存配置资源；

* 客户端：入口位于jedis-client工程内JedisCli启动文件，配置文件通过command参数指定；
