# 知识库 / 博客协作平台后端

*简介*：**支持用户认证、文章管理、分类标签和评论互动的后端平台。  **

## 1.技术栈清单与版本

### 1.语言与框架
- Java 21：项目编译与运行使用的 JDK 版本。
- Spring Boot 3.5.16：应用配置、启动与依赖管理。
- Spring MVC：提供 REST API、拦截器和全局异常处理。
- Maven 3.10.0

### 2.数据库
- MySQL 8.0+：保存用户、文章、分类、标签和评论数据。
- MyBatis-Plus 3.5.17：基础 CRUD 与自定义 XML Mapper。
- MySQL Connector/J、HikariCP：数据库驱动和连接池，版本由 Spring Boot 管理。

### 3.认证、校验与测试
- JJWT 0.12.6：使用 HS256 签发、校验 JWT，有效期为 7 天。
- Spring Security Crypto：使用 BCrypt 保存密码哈希。
- Jakarta Validation：校验请求体和请求参数。
- Lombok：简化实体、DTO、VO 的代码。
- JUnit、Spring Boot Test、MockMvc：自动化测试支持。

- 其他依赖版本由 Spring Boot 3.5.16 统一管理，具体声明见 `pom.xml`。

## 2.项目架构

### 1.请求流转图
```text  
请求
  |
  v
中间件：LoginCheckInterceptor
（校验 Bearer JWT 并写入当前用户 ID
  注册、登录、健康检查路径免登录）
  |
  v
Controller + Jakarta Validation
（接收请求、校验参数）
  |
  v
Service
（执行业务逻辑、权限判断和事务管理）
  |
  v
DAO：Repository / XML Mapper
（使用 MyBatis-Plus 和自定义 SQL）
  |
  v
DB：MySQL（HikariCP 连接池）

响应返回方向：
DB -> DAO -> Service -> Controller -> VO -> Result -> JSON -> 客户端

业务异常 / 参数异常 -> GlobalExceptionHandler -> 统一错误响应
```  


### 2.目录

```text  
.  
|-- application.example.yml            # 环境变量与应用配置模板  
|-- database/  
|   |-- schema.sql                     # 建库建表、索引与外键  
|   `-- ER图.png                       # 数据库关系图  
|-- src/  
|   |-- main/  
|   |   |-- java/com/xrf/iotbackend/  
|   |   |   |-- common/api/            # 统一响应 Result、错误码 ErrorCode
|   |   |   |-- config/                # MVC、密码编码器和 JWT 配置  
|   |   |   |-- controller/            # HTTP 接口、参数校验与响应  
|   |   |   |-- exception/             # 业务异常与全局异常处理  
|   |   |   |-- middleware/            # JWT 登录校验拦截器  
|   |   |   |-- model/  
|   |   |   |   |-- dto/               # 请求参数对象  
|   |   |   |   |-- entity/            # 数据库实体  
|   |   |   |   `-- vo/                # 响应数据对象  
|   |   |   |-- repository/            # 数据访问接口  
|   |   |   |-- service/               # 服务接口及 impl/ 业务实现  
|   |   |   |-- utils/                 # 工具类（含JWT 签发与解析）  
|   |   |   `-- IotBackendApplication.java  
|   |   `-- resources/  
|   |       |-- application.yaml       # 数据源、MyBatis-Plus、JWT 配置  
|   |       `-- com/xrf/IOTBackend/mapper/  
|   |                                  # XML Mapper；路径大小写与配置一致  
|   `-- test/java/com/xrf/iotbackend/  # 自动化测试  
|-- .mvn/wrapper/                      # Maven Wrapper 配置  
|-- mvnw  
|-- mvnw.cmd  
|-- pom.xml  
`-- README.md  
```  

## 3.环境要求
- JDK 21
- MySQL 8.0+
## 4.详细启动步骤

### 1. 克隆仓库

```powershell  
git clone https://github.com/xwmsqwg/knowledge-blog-backend.git  
Set-Location knowledge-blog-backend  
java -version  
```  

### 2. 配置环境变量

- 在准备启动项目的同一个 PowerShell 窗口中设置：

```powershell  
$env:DB_USERNAME = "<数据库用户名>"  
$env:DB_PASSWORD = "<数据库密码>"  
  
# 生成随机密钥并仅保存到当前进程环境变量  
$jwtKeyBytes = New-Object byte[] 32  
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()  
$rng.GetBytes($jwtKeyBytes)  
$env:JWT_SECRET = [Convert]::ToBase64String($jwtKeyBytes)  
$rng.Dispose()  
```  

### 3. 初始化数据库

- 先确认 MySQL 已启动，再使用有建库、建表权限的账号进入 MySQL 客户端：

```powershell  
mysql --default-character-set=utf8mb4 -u "$env:DB_USERNAME" -p  
```  

- 在客户端内执行下列 SQL，将路径替换为当前克隆目录下 `database/schema.sql` 的实际绝对路径：
- 脚本会创建 `knowledge_blog` 数据库和六张表
```sql  
SOURCE C:/path/to/knowledge-blog-backend/database/schema.sql;  
USE knowledge_blog;  
SHOW TABLES;  
```  



### 4. 编译并运行

- 在项目根目录执行：

```powershell  
.\mvnw.cmd -q -DskipTests compile  
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.config.additional-location=file:./application.example.yml"  
```  

- 启动命令显式加载根目录的配置模板，数据库账号和 JWT 密钥从环境变量读取。首次运行 Wrapper 会下载 Maven 和依赖。服务默认地址为 `http://localhost:8080`。

## 5.环境变量配置说明

- 配置模板：[application.example.yml](application.example.yml)
- `DB_USERNAME`：MySQL 用户名。初始化数据库时所用账号需要有建库、建表权限。
- `DB_PASSWORD`：MySQL 密码。
- `JWT_SECRET`  ：HS256 签名密钥，UTF-8 编码后至少 32 字节。启动步骤中的命令会生成随机密钥。  

## 6. API 文档

待补充