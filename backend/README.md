# 数据结构管理系统

基于Spring Boot和MyBatis-Flex开发的数据结构管理系统，集成了AI智能算法分析、实时WebSocket通信等现代化功能，提供完整的用户管理、权限控制和算法学习平台。

## 项目特性

- ✅ 基于Spring Boot 3.x构建，采用现代化架构设计
- ✅ 使用MyBatis-Flex作为ORM框架，提供灵活的数据访问能力
- ✅ 集成LangChain4j AI智能体，支持算法问题解答和代码生成
- ✅ 支持WebSocket全双工实时通信
- ✅ Redis高性能缓存支持
- ✅ 完整的异常处理和安全机制
- ✅ 参数校验和权限控制
- ✅ 实时算法分析和复杂度评估
- ✅ 统一的代码注释风格，提高代码可读性和可维护性

## 技术栈

**前端技术栈**
- **框架**: Vue 3
- **图表库**: ECharts

**后端技术栈**
- **核心框架**: Spring Boot 3.x
- **数据持久层**: MyBatis-Flex
- **缓存系统**: Redis
- **实时通信**: WebSocket
- **AI智能体**: LangChain4j（集成通义千问模型）
- **参数校验**: Jakarta Validation API
- **日志框架**: log4j
- **单元测试**: JUnit
- **接口文档**: Knife4j
- **数据库**: MySQL
- **构建工具**: Maven
- **Java版本**: 17

## 项目结构

```
src/main/java/top/qtcc/data_structure/
├── annotation/     # 自定义注解
├── aspect/         # 切面编程
├── chat/           # AI算法分析模块
│   ├── ai/         # AI智能体服务
│   ├── algorithm/  # 算法分析服务
│   ├── config/     # AI配置
│   ├── controller/ # AI控制器
│   ├── model/      # AI数据模型
│   └── service/    # AI业务服务
├── common/         # 通用工具类
│   └── websocket/  # WebSocket通信
├── config/         # 配置类
├── constant/       # 常量定义
├── controller/     # 控制器层
├── domain/         # 领域模型
├── exception/      # 异常处理
├── filter/         # 过滤器
├── interceptor/    # 拦截器
├── manager/        # 业务管理层
├── mapper/         # 数据访问层
├── service/        # 服务层
└── utils/          # 工具类
```

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE qiutuan_all_powerful;
```

2. 修改配置文件 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/qiutuan_all_powerful
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

### 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

项目将在 http://localhost:8800 启动。

## 核心功能

### AI智能算法分析

- **算法问题解答**: 基于LangChain4j AI智能体，提供专业的算法问题解答服务
- **代码生成**: 根据算法名称自动生成高质量的代码实现，支持多种编程语言
- **复杂度分析**: 对算法代码进行时间复杂度、空间复杂度分析，提供详细推导过程
- **优化建议**: 为算法代码提供具体的优化方案和重构建议
- **实时流式响应**: 支持WebSocket流式传输，提供实时的AI算法分析体验

### 用户管理

- **用户注册**: 支持账户密码注册，包含参数校验和密码加密
- **用户登录**: 支持会话管理和状态保持
- **权限控制**: 支持管理员和普通用户角色区分
- **用户查询**: 支持多条件用户信息查询

### 实时通信

- **WebSocket全双工通信**: 支持实时算法分析和代码生成结果的流式传输
- **消息推送**: 实时推送算法分析进度和结果
- **会话管理**: 支持多用户并发实时通信

### 安全特性

- 密码使用MD5加盐加密
- 会话状态管理
- 参数校验和异常处理
- 全局异常处理器
- 接口限流和防重复提交

## API文档

项目启动后，可通过以下方式访问API文档：
- Swagger UI: http://localhost:8800/swagger-ui.html
- OpenAPI: http://localhost:8800/v3/api-docs

## 开发规范

### 代码规范

- 使用Lombok减少样板代码
- 统一的异常处理机制
- 参数校验使用Jakarta Validation
- 日志记录使用SLF4J

### 包结构规范

- `controller`: 处理HTTP请求和响应
- `service`: 业务逻辑实现
- `mapper`: 数据访问层
- `domain`: 实体类和DTO
- `common`: 通用工具和常量

## 部署说明

### 生产环境配置

1. 修改 `application.yml` 中的数据库连接信息
2. 配置Redis连接信息
3. 设置合适的JVM参数
4. 使用生产环境配置文件

### 监控和日志

- 集成Spring Boot Actuator进行应用监控
- 配置日志级别和输出格式
- 设置日志文件轮转策略

## 故障排除

### 常见问题

1. **数据库连接失败**: 检查数据库服务是否启动，连接信息是否正确
2. **Redis连接失败**: 检查Redis服务是否启动，密码是否正确
3. **端口占用**: 检查8800端口是否被其他应用占用

### 日志分析

项目使用SLF4J记录日志，日志文件位于 `logs/` 目录下，可通过日志分析问题原因。

## 贡献指南

欢迎提交Issue和Pull Request来改进项目。

## 许可证

本项目采用MIT许可证。