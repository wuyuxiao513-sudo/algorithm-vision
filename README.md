# 算法视界

算法视界是一个面向数据结构与算法学习的前后端项目，包含在线内容、用户交互与代码评测相关功能。

## 项目结构

- `backend/`：Spring Boot 后端服务
- `frontend/`：Vue 3 + Vite 前端应用

## 本地开发

### 后端

1. 安装 JDK 与 MySQL，并按需启动 Redis。
2. 参考 `.env.example` 配置数据库、Redis 与模型服务环境变量。
3. 在 `backend` 目录运行 `./mvnw spring-boot:run`；Windows 可运行 `mvnw.cmd spring-boot:run`。

### 前端

1. 进入 `frontend` 目录。
2. 执行 `npm install`。
3. 执行 `npm run dev`。

## 安全说明

仓库不会提交真实密码、API Key、本地构建产物或包含初始化用户数据的数据库导出文件。敏感配置统一通过环境变量注入。
