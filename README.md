
## 记账小程序服务端
- 基于 Springboot 2.x 版本，内置服务器使用 undertow
- ORM 框架选用 mybatis-plus 3.3.1，重写了生成实体类相关代码的模版
- 数据库 mysql， init.sql 为初始化表结构
- 接口文档框架选用 knife4j 3.0.2
- 配置加密使用 jasypt

### 账单列表
![账单列表](images/bill-1.jpeg, "账单列表页")

### 记账页
![记账页](images/bill-2.jpeg, "记账页")

### 用户首页
![我的](images/bill-3.jpeg, "用户首页")