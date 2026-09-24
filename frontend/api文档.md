---
title: 默认模块
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

# Authentication

# 文件接口

## POST 文件上传

POST /file/upload

> Body 请求参数

```yaml
file: file://C:\Users\linyxhe\Desktop\PixPin_2025-11-20_16-43-17.png

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|biz|query|string| 否 |业务|
|body|body|object| 否 |none|
|» file|body|string(binary)| 是 |文件|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": "",
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseString](#schemabaseresponsestring)|

# 用户接口

## POST 用户注册

POST /user/register

> Body 请求参数

```json
{
  "userAccount": "string",
  "userPassword": "string",
  "checkPassword": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserRegisterRequest](#schemauserregisterrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": 0,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseLong](#schemabaseresponselong)|

## POST 用户登录

POST /user/login

> Body 请求参数

```json
{
  "userAccount": "admin",
  "userPassword": "123456789"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserLoginRequest](#schemauserloginrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "userId": 0,
    "userName": "",
    "userAvatar": "",
    "userProfile": "",
    "userRole": "",
    "createTime": "",
    "updateTime": "",
    "token": ""
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseLoginUserVO](#schemabaseresponseloginuservo)|

## POST 用户注销

POST /user/logout

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 获取当前登录用户

GET /user/get/login

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "id": 0,
    "userName": "",
    "userAvatar": "",
    "userProfile": "",
    "userRole": "",
    "createTime": "",
    "updateTime": ""
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseLoginUserVO](#schemabaseresponseloginuservo)|

## POST 创建用户

POST /user/add

> Body 请求参数

```json
{
  "userName": "暴浩轩",
  "userAccount": "commodo eu voluptate",
  "userAvatar": "https://avatars.githubusercontent.com/u/57357671",
  "userRole": "id sint qui proident"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserAddRequest](#schemauseraddrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": 0,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseLong](#schemabaseresponselong)|

## POST 删除用户

POST /user/delete

> Body 请求参数

```json
{
  "id": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[DeleteRequest](#schemadeleterequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 更新用户

POST /user/update

> Body 请求参数

```json
{
  "userId": 1857700903633555500,
  "userName": "厍沐辰",
  "userAvatar": "https://avatars.githubusercontent.com/u/41596440",
  "userProfile": "nostrud dolore veniam",
  "userRole": "eu"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserUpdateRequest](#schemauserupdaterequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 根据 id 获取用户（仅管理员）

GET /user/get

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 否 |用户 id|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "id": 0,
    "userAccount": "",
    "userPassword": "",
    "userName": "",
    "userAvatar": "",
    "userProfile": "",
    "userRole": "",
    "createTime": 0,
    "updateTime": 0,
    "isDelete": 0
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseUser](#schemabaseresponseuser)|

## GET 根据 id 获取包装类

GET /user/get/vo

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 否 |用户 id|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "id": 0,
    "userName": "",
    "userAvatar": "",
    "userProfile": "",
    "userRole": "",
    "createTime": ""
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseUserVO](#schemabaseresponseuservo)|

## POST 分页获取用户列表（仅管理员）

POST /user/list/page

> Body 请求参数

```json
{
  "current": 1,
  "pageSize": 10,
  "sortField": "string",
  "sortOrder": "ascend",
  "id": 0,
  "unionId": "string",
  "mpOpenId": "string",
  "userName": "string",
  "userProfile": "string",
  "userRole": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserQueryRequest](#schemauserqueryrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 0,
        "userAccount": "",
        "userPassword": "",
        "userName": "",
        "userAvatar": "",
        "userProfile": "",
        "userRole": "",
        "createTime": 0,
        "updateTime": 0,
        "isDelete": 0
      }
    ],
    "pageNumber": 0,
    "pageSize": 0,
    "maxPageSize": 0,
    "totalPage": 0,
    "totalRow": 0,
    "optimizeCountQuery": false
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponsePageUser](#schemabaseresponsepageuser)|

## POST 分页获取用户封装列表

POST /user/list/page/vo

> Body 请求参数

```json
{
  "current": 1,
  "pageSize": 10,
  "sortField": "string",
  "sortOrder": "ascend",
  "id": 0,
  "unionId": "string",
  "mpOpenId": "string",
  "userName": "string",
  "userProfile": "string",
  "userRole": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserQueryRequest](#schemauserqueryrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 0,
        "userName": "",
        "userAvatar": "",
        "userProfile": "",
        "userRole": "",
        "createTime": ""
      }
    ],
    "pageNumber": 0,
    "pageSize": 0,
    "maxPageSize": 0,
    "totalPage": 0,
    "totalRow": 0,
    "optimizeCountQuery": false
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponsePageUserVO](#schemabaseresponsepageuservo)|

## POST 更新个人信息

POST /user/update/my

> Body 请求参数

```json
{
  "userName": "稽乙萍",
  "userAvatar": "https://avatars.githubusercontent.com/u/1436285",
  "userProfile": "qui"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserUpdateMyRequest](#schemauserupdatemyrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 更新用户密码

POST /user/update/password

> Body 请求参数

```json
{
  "id": 0,
  "userAccount": "string",
  "oldPassword": "string",
  "newPassword": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[UserUpdatePasswordRequest](#schemauserupdatepasswordrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": "",
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseString](#schemabaseresponsestring)|

# 文章控制器

## GET 获取文章详情

GET /article/getById

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 否 |文章ID|
|lookUserId|query|integer| 否 |查看用户ID（可选，用于记录浏览历史）|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "articleId": 0,
    "userId": 0,
    "userName": "",
    "headPhoto": "",
    "articleTitle": "",
    "paratext": "",
    "articleCover": "",
    "categoryId": 0,
    "categoryName": "",
    "tags": "",
    "status": 0,
    "isTop": 0,
    "isOriginal": 0,
    "sourceUrl": "",
    "sourceAuthor": "",
    "wordCount": 0,
    "createTime": "",
    "updateTime": "",
    "mainBody": "",
    "pageview": 0,
    "likeCount": 0,
    "unlikeCount": 0,
    "collectCount": 0,
    "commentCount": 0,
    "likeId": 0,
    "likeTime": "",
    "likeFlag": false,
    "collectId": 0,
    "collectTime": "",
    "collectFlag": false
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseArticleVO](#schemabaseresponsearticlevo)|

## GET 根据标题查询文章列表

GET /article/title

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleTitle|query|string| 否 |文章标题|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "userName": "",
      "headPhoto": "",
      "articleTitle": "",
      "paratext": "",
      "articleCover": "",
      "categoryId": 0,
      "categoryName": "",
      "tags": "",
      "status": 0,
      "isTop": 0,
      "isOriginal": 0,
      "sourceUrl": "",
      "sourceAuthor": "",
      "wordCount": 0,
      "createTime": "",
      "updateTime": "",
      "mainBody": "",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "likeId": 0,
      "likeTime": "",
      "likeFlag": false,
      "collectId": 0,
      "collectTime": "",
      "collectFlag": false
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListArticleVO](#schemabaseresponselistarticlevo)|

## POST 发布文章

POST /article/add

> Body 请求参数

```json
{
  "articleId": 0,
  "articleTitle": "string",
  "paratext": "string",
  "articleCover": "string",
  "categoryId": 0,
  "tags": "string",
  "status": 0,
  "isTop": 0,
  "isOriginal": 0,
  "sourceUrl": "string",
  "sourceAuthor": "string",
  "mainBody": "string",
  "deleted": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[ArticleRequest](#schemaarticlerequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## PUT 修改文章

PUT /article/edit

> Body 请求参数

```json
{
  "articleId": 0,
  "articleTitle": "string",
  "paratext": "string",
  "articleCover": "string",
  "categoryId": 0,
  "tags": "string",
  "status": 0,
  "isTop": 0,
  "isOriginal": 0,
  "sourceUrl": "string",
  "sourceAuthor": "string",
  "mainBody": "string",
  "deleted": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[ArticleRequest](#schemaarticlerequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## DELETE 删除文章

DELETE /article/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 更新阅读数

POST /article/updatePageview

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 根据用户ID查询文章列表

GET /article/getByUserId

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|integer| 是 |用户ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "userName": "",
      "headPhoto": "",
      "articleTitle": "",
      "paratext": "",
      "articleCover": "",
      "categoryId": 0,
      "categoryName": "",
      "tags": "",
      "status": 0,
      "isTop": 0,
      "isOriginal": 0,
      "sourceUrl": "",
      "sourceAuthor": "",
      "wordCount": 0,
      "createTime": "",
      "updateTime": "",
      "mainBody": "",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "likeId": 0,
      "likeTime": "",
      "likeFlag": false,
      "collectId": 0,
      "collectTime": "",
      "collectFlag": false
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListArticleVO](#schemabaseresponselistarticlevo)|

## GET 获取文章列表（分页）- 兼容旧版本

GET /article/list

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|pageSize|query|integer| 是 |每页大小|
|pageNum|query|integer| 是 |页码|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "userName": "",
      "headPhoto": "",
      "articleTitle": "",
      "paratext": "",
      "articleCover": "",
      "categoryId": 0,
      "categoryName": "",
      "tags": "",
      "status": 0,
      "isTop": 0,
      "isOriginal": 0,
      "sourceUrl": "",
      "sourceAuthor": "",
      "wordCount": 0,
      "createTime": "",
      "updateTime": "",
      "mainBody": "",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "likeId": 0,
      "likeTime": "",
      "likeFlag": false,
      "collectId": 0,
      "collectTime": "",
      "collectFlag": false
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListArticleVO](#schemabaseresponselistarticlevo)|

## GET 获取文章分页列表（包含总条数）- 新版本

GET /article/page

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|pageSize|query|integer| 是 |每页大小|
|pageNum|query|integer| 是 |页码|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "pageNum": 0,
    "pageSize": 0,
    "total": 0,
    "totalPages": 0,
    "records": [
      {
        "articleId": 0,
        "userId": 0,
        "userName": "",
        "headPhoto": "",
        "articleTitle": "",
        "paratext": "",
        "articleCover": "",
        "categoryId": 0,
        "categoryName": "",
        "tags": "",
        "status": 0,
        "isTop": 0,
        "isOriginal": 0,
        "sourceUrl": "",
        "sourceAuthor": "",
        "wordCount": 0,
        "createTime": "",
        "updateTime": "",
        "mainBody": "",
        "pageview": 0,
        "likeCount": 0,
        "unlikeCount": 0,
        "collectCount": 0,
        "commentCount": 0,
        "likeId": 0,
        "likeTime": "",
        "likeFlag": false,
        "collectId": 0,
        "collectTime": "",
        "collectFlag": false
      }
    ]
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponsePageResponseArticleVO](#schemabaseresponsepageresponsearticlevo)|

# 文章点赞控制器

## POST 点赞文章

POST /article/like/{articleId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|path|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## DELETE 取消点赞文章

DELETE /article/like/{articleId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|path|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 切换点赞状态

POST /article/like/toggle/{articleId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|path|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 查询用户是否点赞了某篇文章

GET /article/like/{articleId}/status

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|path|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 获取文章点赞数量

GET /article/like/count/{articleId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|path|integer| 是 |文章ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": 0,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseInteger](#schemabaseresponseinteger)|

## GET 获取用户点赞的文章列表

GET /article/like/user

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    0
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListLong](#schemabaseresponselistlong)|

# 分类控制器

## GET 获取分类详情

GET /category/getById

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |分类ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "categoryId": 0,
    "categoryName": "",
    "description": "",
    "parentId": 0,
    "parentName": "",
    "sortOrder": 0,
    "status": 0,
    "statusDesc": "",
    "iconUrl": "",
    "color": "",
    "articleCount": 0,
    "createTime": "",
    "updateTime": "",
    "children": [
      {
        "categoryId": 0,
        "categoryName": "",
        "description": "",
        "parentId": 0,
        "parentName": "",
        "sortOrder": 0,
        "status": 0,
        "statusDesc": "",
        "iconUrl": "",
        "color": "",
        "articleCount": 0,
        "createTime": "",
        "updateTime": "",
        "children": [
          {}
        ]
      }
    ]
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseCategoryVO](#schemabaseresponsecategoryvo)|

## GET 获取所有分类列表

GET /category/list

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "categoryId": 0,
      "categoryName": "",
      "description": "",
      "parentId": 0,
      "parentName": "",
      "sortOrder": 0,
      "status": 0,
      "statusDesc": "",
      "iconUrl": "",
      "color": "",
      "articleCount": 0,
      "createTime": "",
      "updateTime": "",
      "children": [
        {
          "categoryId": 0,
          "categoryName": "",
          "description": "",
          "parentId": 0,
          "parentName": "",
          "sortOrder": 0,
          "status": 0,
          "statusDesc": "",
          "iconUrl": "",
          "color": "",
          "articleCount": 0,
          "createTime": "",
          "updateTime": "",
          "children": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCategoryVO](#schemabaseresponselistcategoryvo)|

## GET 获取树形分类列表

GET /category/tree

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "categoryId": 0,
      "categoryName": "",
      "description": "",
      "parentId": 0,
      "parentName": "",
      "sortOrder": 0,
      "status": 0,
      "statusDesc": "",
      "iconUrl": "",
      "color": "",
      "articleCount": 0,
      "createTime": "",
      "updateTime": "",
      "children": [
        {
          "categoryId": 0,
          "categoryName": "",
          "description": "",
          "parentId": 0,
          "parentName": "",
          "sortOrder": 0,
          "status": 0,
          "statusDesc": "",
          "iconUrl": "",
          "color": "",
          "articleCount": 0,
          "createTime": "",
          "updateTime": "",
          "children": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCategoryVO](#schemabaseresponselistcategoryvo)|

## POST 添加分类

POST /category/add

> Body 请求参数

```json
{
  "categoryId": 0,
  "categoryName": "string",
  "description": "string",
  "parentId": 0,
  "sortOrder": 0,
  "status": 0,
  "iconUrl": "string",
  "color": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[CategoryRequest](#schemacategoryrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 修改分类

POST /category/edit

> Body 请求参数

```json
{
  "categoryId": 0,
  "categoryName": "string",
  "description": "string",
  "parentId": 0,
  "sortOrder": 0,
  "status": 0,
  "iconUrl": "string",
  "color": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[CategoryRequest](#schemacategoryrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## POST 删除分类（逻辑删除）

POST /category/delete

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |分类ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 根据分类名称查询分类

GET /category/name

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|categoryName|query|string| 否 |分类名称|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "categoryId": 0,
      "categoryName": "",
      "description": "",
      "parentId": 0,
      "parentName": "",
      "sortOrder": 0,
      "status": 0,
      "statusDesc": "",
      "iconUrl": "",
      "color": "",
      "articleCount": 0,
      "createTime": "",
      "updateTime": "",
      "children": [
        {
          "categoryId": 0,
          "categoryName": "",
          "description": "",
          "parentId": 0,
          "parentName": "",
          "sortOrder": 0,
          "status": 0,
          "statusDesc": "",
          "iconUrl": "",
          "color": "",
          "articleCount": 0,
          "createTime": "",
          "updateTime": "",
          "children": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCategoryVO](#schemabaseresponselistcategoryvo)|

# 文件访问控制器

## GET 访问上传的文件

GET /files/**

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "inputStream": {},
  "readable": false,
  "open": false,
  "file": {
    "path": "",
    "name": "",
    "parent": "",
    "parentFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": false,
    "absolutePath": "",
    "absoluteFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "",
    "canonicalFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": false,
    "file": false,
    "hidden": false,
    "lastModified": 0,
    "writable": false,
    "readable": false,
    "executable": false,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "uRL": "",
  "uRI": {
    "string": "",
    "absolute": false,
    "opaque": false,
    "rawSchemeSpecificPart": "",
    "rawAuthority": "",
    "rawUserInfo": "",
    "rawPath": "",
    "rawQuery": "",
    "rawFragment": ""
  },
  "contentAsByteArray": [
    0
  ],
  "filename": "",
  "description": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityResource](#schemaresponseentityresource)|

## GET 下载文件（强制下载）

GET /files/download/**

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "inputStream": {},
  "readable": false,
  "open": false,
  "file": {
    "path": "",
    "name": "",
    "parent": "",
    "parentFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": false,
    "absolutePath": "",
    "absoluteFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "",
    "canonicalFile": {
      "path": "",
      "name": "",
      "parent": "",
      "parentFile": {},
      "absolute": false,
      "absolutePath": "",
      "absoluteFile": {},
      "canonicalPath": "",
      "canonicalFile": {},
      "directory": false,
      "file": false,
      "hidden": false,
      "lastModified": 0,
      "writable": false,
      "readable": false,
      "executable": false,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": false,
    "file": false,
    "hidden": false,
    "lastModified": 0,
    "writable": false,
    "readable": false,
    "executable": false,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "uRL": "",
  "uRI": {
    "string": "",
    "absolute": false,
    "opaque": false,
    "rawSchemeSpecificPart": "",
    "rawAuthority": "",
    "rawUserInfo": "",
    "rawPath": "",
    "rawQuery": "",
    "rawFragment": ""
  },
  "contentAsByteArray": [
    0
  ],
  "filename": "",
  "description": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityResource](#schemaresponseentityresource)|

# 收藏控制器

## POST 添加收藏

POST /collect/add

> Body 请求参数

```json
{
  "userId": 0,
  "articleId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[CollectRequest](#schemacollectrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## DELETE 取消收藏

DELETE /collect/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 获取用户收藏的文章列表

GET /collect/getCollectArt

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "collectId": 0,
      "userId": 0,
      "articleId": 0,
      "createTime": "",
      "articleTitle": "",
      "paratext": "",
      "mainBody": "",
      "pageview": 0,
      "likeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "userName": "",
      "headPhoto": "",
      "collectFlag": false
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCollectVO](#schemabaseresponselistcollectvo)|

# 评论控制器

## POST 添加评论

POST /comment/add

> Body 请求参数

```json
{
  "commentId": 0,
  "userId": 0,
  "articleId": 0,
  "content": "string",
  "parentId": 0,
  "replyUserId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[CommentRequest](#schemacommentrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## DELETE 删除评论

DELETE /comment/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## GET 根据文章ID获取评论列表

GET /comment/getByArticleId

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|articleId|query|integer| 是 |none|
|userId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "commentId": 0,
      "userId": 0,
      "articleId": 0,
      "content": "",
      "parentId": 0,
      "replyUserId": 0,
      "likeCount": 0,
      "createTime": "",
      "userName": "",
      "userAvatar": "",
      "replyUserName": "",
      "likeFlag": false,
      "repliesChildren": [
        {
          "commentId": 0,
          "userId": 0,
          "articleId": 0,
          "content": "",
          "parentId": 0,
          "replyUserId": 0,
          "likeCount": 0,
          "createTime": "",
          "userName": "",
          "userAvatar": "",
          "replyUserName": "",
          "likeFlag": false,
          "repliesChildren": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCommentVO](#schemabaseresponselistcommentvo)|

## GET 根据评论ID获取评论详情

GET /comment/getById

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |none|
|userId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": {
    "commentId": 0,
    "userId": 0,
    "articleId": 0,
    "content": "",
    "parentId": 0,
    "replyUserId": 0,
    "likeCount": 0,
    "createTime": "",
    "userName": "",
    "userAvatar": "",
    "replyUserName": "",
    "likeFlag": false,
    "repliesChildren": [
      {
        "commentId": 0,
        "userId": 0,
        "articleId": 0,
        "content": "",
        "parentId": 0,
        "replyUserId": 0,
        "likeCount": 0,
        "createTime": "",
        "userName": "",
        "userAvatar": "",
        "replyUserName": "",
        "likeFlag": false,
        "repliesChildren": [
          {}
        ]
      }
    ]
  },
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseCommentVO](#schemabaseresponsecommentvo)|

## GET 根据用户ID获取评论列表

GET /comment/getByUserId

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "commentId": 0,
      "userId": 0,
      "articleId": 0,
      "content": "",
      "parentId": 0,
      "replyUserId": 0,
      "likeCount": 0,
      "createTime": "",
      "userName": "",
      "userAvatar": "",
      "replyUserName": "",
      "likeFlag": false,
      "repliesChildren": [
        {
          "commentId": 0,
          "userId": 0,
          "articleId": 0,
          "content": "",
          "parentId": 0,
          "replyUserId": 0,
          "likeCount": 0,
          "createTime": "",
          "userName": "",
          "userAvatar": "",
          "replyUserName": "",
          "likeFlag": false,
          "repliesChildren": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCommentVO](#schemabaseresponselistcommentvo)|

## GET 获取用户收到的评论

GET /comment/getByUserIdArt

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": [
    {
      "commentId": 0,
      "userId": 0,
      "articleId": 0,
      "content": "",
      "parentId": 0,
      "replyUserId": 0,
      "likeCount": 0,
      "createTime": "",
      "userName": "",
      "userAvatar": "",
      "replyUserName": "",
      "likeFlag": false,
      "repliesChildren": [
        {
          "commentId": 0,
          "userId": 0,
          "articleId": 0,
          "content": "",
          "parentId": 0,
          "replyUserId": 0,
          "likeCount": 0,
          "createTime": "",
          "userName": "",
          "userAvatar": "",
          "replyUserName": "",
          "likeFlag": false,
          "repliesChildren": []
        }
      ]
    }
  ],
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseListCommentVO](#schemabaseresponselistcommentvo)|

# 评论点赞控制器

## POST 点赞

POST /commentLikes/add

> Body 请求参数

```json
{
  "userId": 0,
  "targetType": "string",
  "targetId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[CommentLikeRequest](#schemacommentlikerequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

## DELETE 取消点赞

DELETE /commentLikes/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "data": false,
  "message": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[BaseResponseBoolean](#schemabaseresponseboolean)|

# 关注

## POST 关注

POST /follow/addFollow

> Body 请求参数

```json
{
  "userId": 1,
  "followUserId": 4
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": {
    "id": 0,
    "userId": 0,
    "followUserId": 0,
    "createdAt": null
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» msg|string|true|none||none|
|» code|string|true|none||none|
|» data|object|true|none||none|
|»» id|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» followUserId|integer|true|none||none|
|»» createdAt|null|true|none||none|

## GET 是否关注

GET /follow/getFollowFlag

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|id|query|string| 否 |none|
|userId|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": true
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» msg|string|true|none||none|
|» code|string|true|none||none|
|» data|boolean|true|none||none|

## GET 关注列表

GET /follow/getFollow

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": [
    {
      "id": 0,
      "userId": 0,
      "followUserId": 0,
      "createdAt": "string",
      "userName": "string",
      "headPhoto": "string",
      "brief": "string"
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» msg|string|true|none||none|
|» code|string|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» followUserId|integer|true|none||none|
|»» createdAt|string|true|none||none|
|»» userName|string|true|none||none|
|»» headPhoto|string|true|none||none|
|»» brief|string|true|none||none|

## GET 粉丝列表

GET /follow/getFollower

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|userId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": [
    {
      "id": 0,
      "userId": 0,
      "followUserId": 0,
      "createdAt": "string",
      "userName": "string",
      "headPhoto": "string",
      "brief": null
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» msg|string|true|none||none|
|» code|string|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» followUserId|integer|false|none||none|
|»» createdAt|string|false|none||none|
|»» userName|string|false|none||none|
|»» headPhoto|string|false|none||none|
|»» brief|null|false|none||none|

# AI 编程助手控制器

## GET 流式对话接口

GET /ai/chat

使用Server-Sent Events (SSE) 实现实时流式响应

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|memoryId|query|integer| 否 |会话记忆ID，用于维护对话上下文|
|message|query|string| 否 |用户输入的消息内容|

> 返回示例

> 200 Response

```json
[
  {
    "id": "",
    "event": "",
    "retry": {
      "seconds": 0,
      "nanos": 0
    },
    "comment": "",
    "data": ""
  }
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*Server-Sent Events流，包含AI的实时回复*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|*anonymous*|[[ServerSentEventString](#schemaserversenteventstring)]|false|none||Server-Sent Events流，包含AI的实时回复|
|» id|string¦null|false|none||none|
|» event|string¦null|false|none||none|
|» retry|[Duration](#schemaduration)¦null|false|none||none|
|»» seconds|integer(int64)|false|none||The number of seconds in the duration.|
|»» nanos|integer|false|none||The number of nanoseconds in the duration, expressed as a fraction of the<br />number of seconds. This is always positive, and never exceeds 999,999,999.|
|» comment|string¦null|false|none||none|
|» data|string¦null|false|none||none|

# 算法服务控制器

## POST 解答算法问题

POST /algorithm/solve

> Body 请求参数

```json
{
  "problem": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmProblemRequest](#schemaalgorithmproblemrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "solution": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[AlgorithmSolutionResponse](#schemaalgorithmsolutionresponse)|

## POST 生成算法代码

POST /algorithm/generate

> Body 请求参数

```json
{
  "algorithmName": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmCodeRequest](#schemaalgorithmcoderequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[AlgorithmCodeResponse](#schemaalgorithmcoderesponse)|

## POST 分析算法复杂度

POST /algorithm/analyze/complexity

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[ComplexityAnalysisRequest](#schemacomplexityanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "analysis": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ComplexityAnalysisResponse](#schemacomplexityanalysisresponse)|

## POST 获取算法优化建议

POST /algorithm/optimize

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[OptimizationRequest](#schemaoptimizationrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "suggestions": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[OptimizationResponse](#schemaoptimizationresponse)|

## POST 生成完整算法分析报告

POST /algorithm/analyze/report

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "report": {
    "algorithmName": "",
    "timeComplexity": {
      "bestCase": "",
      "averageCase": "",
      "worstCase": "",
      "explanation": ""
    },
    "spaceComplexity": {
      "bestCase": "",
      "averageCase": "",
      "worstCase": "",
      "explanation": ""
    },
    "starRating": 0,
    "starRatingComment": "",
    "optimizationDirections": [
      ""
    ],
    "weaknesses": [
      ""
    ],
    "overallAssessment": "",
    "improvementSuggestions": "",
    "emotionalValue": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[AlgorithmAnalysisReportResponse](#schemaalgorithmanalysisreportresponse)|

## POST 评估算法星级

POST /algorithm/evaluate/star

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[StarRatingRequest](#schemastarratingrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "starRating": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[StarRatingResponse](#schemastarratingresponse)|

## POST 获取算法优化方向

POST /algorithm/optimize/directions

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[OptimizationDirectionsRequest](#schemaoptimizationdirectionsrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "directions": [
    ""
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[OptimizationDirectionsResponse](#schemaoptimizationdirectionsresponse)|

## POST 识别算法弱点

POST /algorithm/identify/weaknesses

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[WeaknessesRequest](#schemaweaknessesrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "weaknesses": [
    ""
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WeaknessesResponse](#schemaweaknessesresponse)|

## GET 获取算法模板统计信息

GET /algorithm/templates/stats

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
"string"
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|string|

## GET 获取特定算法的模板内容

GET /algorithm/templates/{algorithmName}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|algorithmName|path|string| 是 |算法名称|

> 返回示例

> 200 Response

```json
"string"
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|string|

## GET 获取算法分析的情绪价值统计

GET /algorithm/analysis/emotion-stats

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|

> 返回示例

> 200 Response

```json
"string"
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|string|

## POST 获取增强版算法分析报告（包含情绪价值）

POST /algorithm/analyze/enhanced-report

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "report": {
    "algorithmName": "",
    "timeComplexity": {
      "bestCase": "",
      "averageCase": "",
      "worstCase": "",
      "explanation": ""
    },
    "spaceComplexity": {
      "bestCase": "",
      "averageCase": "",
      "worstCase": "",
      "explanation": ""
    },
    "starRating": 0,
    "starRatingComment": "",
    "optimizationDirections": [
      ""
    ],
    "weaknesses": [
      ""
    ],
    "overallAssessment": "",
    "improvementSuggestions": "",
    "emotionalValue": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[AlgorithmAnalysisReportResponse](#schemaalgorithmanalysisreportresponse)|

# 流式算法服务控制器

## POST 流式解答算法问题

POST /streaming/algorithm/solve

> Body 请求参数

```json
{
  "problem": "exercitation adipisicing nulla",
  "language": "et culpa"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmProblemRequest](#schemaalgorithmproblemrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*算法解答的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式生成算法代码

POST /streaming/algorithm/generate

> Body 请求参数

```json
{
  "algorithmName": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmCodeRequest](#schemaalgorithmcoderequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*算法代码的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式分析算法复杂度

POST /streaming/algorithm/analyze/complexity

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[ComplexityAnalysisRequest](#schemacomplexityanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*复杂度分析的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式提供算法优化建议

POST /streaming/algorithm/optimize

> Body 请求参数

```json
{
  "code": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[OptimizationRequest](#schemaoptimizationrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*优化建议的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式生成算法分析报告

POST /streaming/algorithm/analyze/report

> Body 请求参数

```json
"{\r\n    \"code\": \"38\",\r\n    \"language\": \"occaecat officia\"\r\n}。"
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*算法分析报告的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式分析算法代码

POST /streaming/algorithm/analyze

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*算法分析结果的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式评估算法性能

POST /streaming/algorithm/evaluate/performance

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*性能评估结果的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式提供代码重构建议

POST /streaming/algorithm/suggest/refactoring

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*重构建议的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式比较算法实现

POST /streaming/algorithm/compare

> Body 请求参数

```json
{
  "code1": "string",
  "code2": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmComparisonRequest](#schemaalgorithmcomparisonrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*比较结果的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

## POST 流式生成学习路径

POST /streaming/algorithm/generate/learning-path

> Body 请求参数

```json
{
  "code": "string",
  "language": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|cookie|string| 否 |none|
|body|body|[AlgorithmAnalysisRequest](#schemaalgorithmanalysisrequest)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

*学习路径的流式返回*

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|

# 数据模型

<h2 id="tocS_BaseResponseString">BaseResponseString</h2>

<a id="schemabaseresponsestring"></a>
<a id="schema_BaseResponseString"></a>
<a id="tocSbaseresponsestring"></a>
<a id="tocsbaseresponsestring"></a>

```json
{
  "code": 0,
  "data": "string",
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|string|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseLong">BaseResponseLong</h2>

<a id="schemabaseresponselong"></a>
<a id="schema_BaseResponseLong"></a>
<a id="tocSbaseresponselong"></a>
<a id="tocsbaseresponselong"></a>

```json
{
  "code": 0,
  "data": 0,
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|integer(int64)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_ArticleVO">ArticleVO</h2>

<a id="schemaarticlevo"></a>
<a id="schema_ArticleVO"></a>
<a id="tocSarticlevo"></a>
<a id="tocsarticlevo"></a>

```json
{
  "articleId": 0,
  "userId": 0,
  "userName": "string",
  "headPhoto": "string",
  "articleTitle": "string",
  "paratext": "string",
  "articleCover": "string",
  "categoryId": 0,
  "categoryName": "string",
  "tags": "string",
  "status": 0,
  "isTop": 0,
  "isOriginal": 0,
  "sourceUrl": "string",
  "sourceAuthor": "string",
  "wordCount": 0,
  "createTime": "string",
  "updateTime": "string",
  "mainBody": "string",
  "pageview": 0,
  "likeCount": 0,
  "unlikeCount": 0,
  "collectCount": 0,
  "commentCount": 0,
  "likeId": 0,
  "likeTime": "string",
  "likeFlag": true,
  "collectId": 0,
  "collectTime": "string",
  "collectFlag": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|articleId|integer(int64)|false|none||文章id|
|userId|integer(int64)|false|none||作者id|
|userName|string|false|none||作者用户名|
|headPhoto|string|false|none||作者头像地址|
|articleTitle|string|false|none||文章标题|
|paratext|string|false|none||文章摘要/副文|
|articleCover|string|false|none||文章封面图URL|
|categoryId|integer(int64)|false|none||文章分类id|
|categoryName|string|false|none||文章分类名称|
|tags|string|false|none||文章标签|
|status|integer|false|none||文章状态：0-草稿，1-已发布，2-下架，3-审核中|
|isTop|integer|false|none||是否置顶：0-否，1-是|
|isOriginal|integer|false|none||是否原创：0-转载，1-原创|
|sourceUrl|string|false|none||转载来源URL|
|sourceAuthor|string|false|none||转载来源作者|
|wordCount|integer|false|none||文章字数|
|createTime|string|false|none||发布时间|
|updateTime|string|false|none||最后更新时间|
|mainBody|string|false|none||正文|
|pageview|integer|false|none||阅读量|
|likeCount|integer|false|none||赞量|
|unlikeCount|integer|false|none||踩量|
|collectCount|integer|false|none||收藏量|
|commentCount|integer|false|none||评论量|
|likeId|integer(int64)|false|none||点赞ID（用于判断用户是否点赞）|
|likeTime|string|false|none||点赞时间|
|likeFlag|boolean|false|none||点赞标志|
|collectId|integer(int64)|false|none||收藏ID|
|collectTime|string|false|none||收藏时间|
|collectFlag|boolean|false|none||收藏标志|

<h2 id="tocS_CategoryVO">CategoryVO</h2>

<a id="schemacategoryvo"></a>
<a id="schema_CategoryVO"></a>
<a id="tocScategoryvo"></a>
<a id="tocscategoryvo"></a>

```json
{
  "categoryId": 0,
  "categoryName": "string",
  "description": "string",
  "parentId": 0,
  "parentName": "string",
  "sortOrder": 0,
  "status": 0,
  "statusDesc": "string",
  "iconUrl": "string",
  "color": "string",
  "articleCount": 0,
  "createTime": "string",
  "updateTime": "string",
  "children": [
    {
      "categoryId": 0,
      "categoryName": "string",
      "description": "string",
      "parentId": 0,
      "parentName": "string",
      "sortOrder": 0,
      "status": 0,
      "statusDesc": "string",
      "iconUrl": "string",
      "color": "string",
      "articleCount": 0,
      "createTime": "string",
      "updateTime": "string",
      "children": [
        {
          "categoryId": 0,
          "categoryName": "string",
          "description": "string",
          "parentId": 0,
          "parentName": "string",
          "sortOrder": 0,
          "status": 0,
          "statusDesc": "string",
          "iconUrl": "string",
          "color": "string",
          "articleCount": 0,
          "createTime": "string",
          "updateTime": "string",
          "children": [
            {}
          ]
        }
      ]
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|categoryId|integer(int64)|false|none||分类id|
|categoryName|string|false|none||分类名称|
|description|string|false|none||分类描述|
|parentId|integer(int64)|false|none||父级分类id|
|parentName|string|false|none||父级分类名称|
|sortOrder|integer|false|none||分类排序|
|status|integer|false|none||分类状态：0-禁用，1-启用|
|statusDesc|string|false|none||分类状态描述|
|iconUrl|string|false|none||分类图标URL|
|color|string|false|none||分类颜色|
|articleCount|integer|false|none||文章数量|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|
|children|[[CategoryVO](#schemacategoryvo)]|false|none||子分类列表|

<h2 id="tocS_InputStream">InputStream</h2>

<a id="schemainputstream"></a>
<a id="schema_InputStream"></a>
<a id="tocSinputstream"></a>
<a id="tocsinputstream"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_Duration">Duration</h2>

<a id="schemaduration"></a>
<a id="schema_Duration"></a>
<a id="tocSduration"></a>
<a id="tocsduration"></a>

```json
{
  "seconds": 0,
  "nanos": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|seconds|integer(int64)|false|none||The number of seconds in the duration.|
|nanos|integer|false|none||The number of nanoseconds in the duration, expressed as a fraction of the<br />number of seconds. This is always positive, and never exceeds 999,999,999.|

<h2 id="tocS_AlgorithmSolutionResponse">AlgorithmSolutionResponse</h2>

<a id="schemaalgorithmsolutionresponse"></a>
<a id="schema_AlgorithmSolutionResponse"></a>
<a id="tocSalgorithmsolutionresponse"></a>
<a id="tocsalgorithmsolutionresponse"></a>

```json
{
  "solution": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|solution|string|false|none||none|

<h2 id="tocS_UserRegisterRequest">UserRegisterRequest</h2>

<a id="schemauserregisterrequest"></a>
<a id="schema_UserRegisterRequest"></a>
<a id="tocSuserregisterrequest"></a>
<a id="tocsuserregisterrequest"></a>

```json
{
  "userAccount": "string",
  "userPassword": "string",
  "checkPassword": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userAccount|string|false|none||none|
|userPassword|string|false|none||none|
|checkPassword|string|false|none||none|

<h2 id="tocS_BaseResponseArticleVO">BaseResponseArticleVO</h2>

<a id="schemabaseresponsearticlevo"></a>
<a id="schema_BaseResponseArticleVO"></a>
<a id="tocSbaseresponsearticlevo"></a>
<a id="tocsbaseresponsearticlevo"></a>

```json
{
  "code": 0,
  "data": {
    "articleId": 0,
    "userId": 0,
    "userName": "string",
    "headPhoto": "string",
    "articleTitle": "string",
    "paratext": "string",
    "articleCover": "string",
    "categoryId": 0,
    "categoryName": "string",
    "tags": "string",
    "status": 0,
    "isTop": 0,
    "isOriginal": 0,
    "sourceUrl": "string",
    "sourceAuthor": "string",
    "wordCount": 0,
    "createTime": "string",
    "updateTime": "string",
    "mainBody": "string",
    "pageview": 0,
    "likeCount": 0,
    "unlikeCount": 0,
    "collectCount": 0,
    "commentCount": 0,
    "likeId": 0,
    "likeTime": "string",
    "likeFlag": true,
    "collectId": 0,
    "collectTime": "string",
    "collectFlag": true
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[ArticleVO](#schemaarticlevo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseInteger">BaseResponseInteger</h2>

<a id="schemabaseresponseinteger"></a>
<a id="schema_BaseResponseInteger"></a>
<a id="tocSbaseresponseinteger"></a>
<a id="tocsbaseresponseinteger"></a>

```json
{
  "code": 0,
  "data": 0,
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|integer|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseCategoryVO">BaseResponseCategoryVO</h2>

<a id="schemabaseresponsecategoryvo"></a>
<a id="schema_BaseResponseCategoryVO"></a>
<a id="tocSbaseresponsecategoryvo"></a>
<a id="tocsbaseresponsecategoryvo"></a>

```json
{
  "code": 0,
  "data": {
    "categoryId": 0,
    "categoryName": "string",
    "description": "string",
    "parentId": 0,
    "parentName": "string",
    "sortOrder": 0,
    "status": 0,
    "statusDesc": "string",
    "iconUrl": "string",
    "color": "string",
    "articleCount": 0,
    "createTime": "string",
    "updateTime": "string",
    "children": [
      {
        "categoryId": 0,
        "categoryName": "string",
        "description": "string",
        "parentId": 0,
        "parentName": "string",
        "sortOrder": 0,
        "status": 0,
        "statusDesc": "string",
        "iconUrl": "string",
        "color": "string",
        "articleCount": 0,
        "createTime": "string",
        "updateTime": "string",
        "children": [
          {
            "categoryId": null,
            "categoryName": null,
            "description": null,
            "parentId": null,
            "parentName": null,
            "sortOrder": null,
            "status": null,
            "statusDesc": null,
            "iconUrl": null,
            "color": null,
            "articleCount": null,
            "createTime": null,
            "updateTime": null,
            "children": null
          }
        ]
      }
    ]
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[CategoryVO](#schemacategoryvo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_File">File</h2>

<a id="schemafile"></a>
<a id="schema_File"></a>
<a id="tocSfile"></a>
<a id="tocsfile"></a>

```json
{
  "path": "string",
  "name": "string",
  "parent": "string",
  "parentFile": {
    "path": "string",
    "name": "string",
    "parent": "string",
    "parentFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": true,
    "absolutePath": "string",
    "absoluteFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "string",
    "canonicalFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": true,
    "file": true,
    "hidden": true,
    "lastModified": 0,
    "writable": true,
    "readable": true,
    "executable": true,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "absolute": true,
  "absolutePath": "string",
  "absoluteFile": {
    "path": "string",
    "name": "string",
    "parent": "string",
    "parentFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": true,
    "absolutePath": "string",
    "absoluteFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "string",
    "canonicalFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": true,
    "file": true,
    "hidden": true,
    "lastModified": 0,
    "writable": true,
    "readable": true,
    "executable": true,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "canonicalPath": "string",
  "canonicalFile": {
    "path": "string",
    "name": "string",
    "parent": "string",
    "parentFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": true,
    "absolutePath": "string",
    "absoluteFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "string",
    "canonicalFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": true,
    "file": true,
    "hidden": true,
    "lastModified": 0,
    "writable": true,
    "readable": true,
    "executable": true,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "directory": true,
  "file": true,
  "hidden": true,
  "lastModified": 0,
  "writable": true,
  "readable": true,
  "executable": true,
  "totalSpace": 0,
  "freeSpace": 0,
  "usableSpace": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|path|string|false|none||This abstract pathname's normalized pathname string. A normalized<br />pathname string uses the default name-separator character and does not<br />contain any duplicate or redundant separators.|
|name|string|false|none||Returns the name of the file or directory denoted by this abstract<br />pathname.  This is just the last name in the pathname's name<br />sequence.  If the pathname's name sequence is empty, then the empty<br />string is returned.|
|parent|string|false|none||Returns the pathname string of this abstract pathname's parent, or<br />{@code null} if this pathname does not name a parent directory.<br /><br /><p> The <em>parent</em> of an abstract pathname consists of the<br />pathname's prefix, if any, and each name in the pathname's name<br />sequence except for the last.  If the name sequence is empty then<br />the pathname does not name a parent directory.|
|parentFile|[File](#schemafile)|false|none||Returns the abstract pathname of this abstract pathname's parent,<br />or{@code null} if this pathname does not name a parent<br />directory.<br /><br /><p> The <em>parent</em> of an abstract pathname consists of the<br />pathname's prefix, if any, and each name in the pathname's name<br />sequence except for the last.  If the name sequence is empty then<br />the pathname does not name a parent directory.|
|absolute|boolean|false|none||Tests whether this abstract pathname is absolute.  The definition of<br />absolute pathname is system dependent.  On UNIX systems, a pathname is<br />absolute if its prefix is{@code "/"}.  On Microsoft Windows systems, a<br />pathname is absolute if its prefix is a drive specifier followed by<br />{@code "\\"}, or if its prefix is{@code "\\\\"}.|
|absolutePath|string|false|none||Returns the absolute pathname string of this abstract pathname.<br /><br /><p> If this abstract pathname is already absolute, then the pathname<br />string is simply returned as if by the{@link #getPath}<br />method.  If this abstract pathname is the empty abstract pathname then<br />the pathname string of the current user directory, which is named by the<br />system property{@code user.dir}, is returned.  Otherwise this<br />pathname is resolved in a system-dependent way.  On UNIX systems, a<br />relative pathname is made absolute by resolving it against the current<br />user directory.  On Microsoft Windows systems, a relative pathname is made absolute<br />by resolving it against the current directory of the drive named by the<br />pathname, if any; if not, it is resolved against the current user<br />directory.|
|absoluteFile|[File](#schemafile)|false|none||Returns the absolute form of this abstract pathname.  Equivalent to<br /><code>new&nbsp;File(this.{@link #getAbsolutePath})</code>.|
|canonicalPath|string|false|none||Returns the canonical pathname string of this abstract pathname.<br /><br /><p> A canonical pathname is both absolute and unique.  The precise<br />definition of canonical form is system-dependent.  This method first<br />converts this pathname to absolute form if necessary, as if by invoking the<br />{@link #getAbsolutePath} method, and then maps it to its unique form in a<br />system-dependent way.  This typically involves removing redundant names<br />such as{@code "."} and{@code ".."} from the pathname, resolving<br />symbolic links (on UNIX platforms), and converting drive letters to a<br />standard case (on Microsoft Windows platforms).<br /><br /><p> Every pathname that denotes an existing file or directory has a<br />unique canonical form.  Every pathname that denotes a nonexistent file<br />or directory also has a unique canonical form.  The canonical form of<br />the pathname of a nonexistent file or directory may be different from<br />the canonical form of the same pathname after the file or directory is<br />created.  Similarly, the canonical form of the pathname of an existing<br />file or directory may be different from the canonical form of the same<br />pathname after the file or directory is deleted.|
|canonicalFile|[File](#schemafile)|false|none||Returns the canonical form of this abstract pathname.  Equivalent to<br /><code>new&nbsp;File(this.{@link #getCanonicalPath})</code>.|
|directory|boolean|false|none||Tests whether the file denoted by this abstract pathname is a<br />directory.<br /><br /><p> Where it is required to distinguish an I/O exception from the case<br />that the file is not a directory, or where several attributes of the<br />same file are required at the same time, then the{@link<br />    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br />    * Files.readAttributes} method may be used.|
|file|boolean|false|none||Tests whether the file denoted by this abstract pathname is a normal<br />file.  A file is <em>normal</em> if it is not a directory and, in<br />addition, satisfies other system-dependent criteria.  Any non-directory<br />file created by a Java application is guaranteed to be a normal file.<br /><br /><p> Where it is required to distinguish an I/O exception from the case<br />that the file is not a normal file, or where several attributes of the<br />same file are required at the same time, then the{@link<br />    * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])<br />    * Files.readAttributes} method may be used.|
|hidden|boolean|false|none||Tests whether the file named by this abstract pathname is a hidden<br />file.  The exact definition of <em>hidden</em> is system-dependent.  On<br />UNIX systems, a file is considered to be hidden if its name begins with<br />a period character ({@code '.'}).  On Microsoft Windows systems, a file is<br />considered to be hidden if it has been marked as such in the filesystem.|
|lastModified|integer|false|none||Sets the last-modified time of the file or directory named by this<br />abstract pathname.<br /><br /><p> All platforms support file-modification times to the nearest second,<br />but some provide more precision.  The argument will be truncated to fit<br />the supported precision.  If the operation succeeds and no intervening<br />operations on the file take place, then the next invocation of the<br />{@link #lastModified} method will return the (possibly<br />truncated){@code time} argument that was passed to this method.|
|writable|boolean|false|none||A convenience method to set the owner's write permission for this abstract<br />pathname. On some platforms it may be possible to start the Java virtual<br />machine with special privileges that allow it to modify files that<br />disallow write operations.<br /><br /><p> An invocation of this method of the form{@code file.setWritable(arg)}<br />behaves in exactly the same way as the invocation<br /><br /><pre>{@code<br />    *     file.setWritable(arg, true)<br />    * }</pre>|
|readable|boolean|false|none||A convenience method to set the owner's read permission for this abstract<br />pathname. On some platforms it may be possible to start the Java virtual<br />machine with special privileges that allow it to read files that are<br />marked as unreadable.<br /><br /><p>An invocation of this method of the form{@code file.setReadable(arg)}<br />behaves in exactly the same way as the invocation<br /><br /><pre>{@code<br />    *     file.setReadable(arg, true)<br />    * }</pre>|
|executable|boolean|false|none||A convenience method to set the owner's execute permission for this<br />abstract pathname. On some platforms it may be possible to start the Java<br />virtual machine with special privileges that allow it to execute files<br />that are not marked executable.<br /><br /><p>An invocation of this method of the form{@code file.setExcutable(arg)}<br />behaves in exactly the same way as the invocation<br /><br /><pre>{@code<br />    *     file.setExecutable(arg, true)<br />    * }</pre>|
|totalSpace|integer(int64)|false|none||Returns the size of the partition <a href="#partName">named</a> by this<br />abstract pathname. If the total number of bytes in the partition is<br />greater than{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be<br />returned.|
|freeSpace|integer(int64)|false|none||Returns the number of unallocated bytes in the partition <a<br />href="#partName">named</a> by this abstract path name.  If the<br />number of unallocated bytes in the partition is greater than<br />{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br /><br /><p> The returned number of unallocated bytes is a hint, but not<br />a guarantee, that it is possible to use most or any of these<br />bytes.  The number of unallocated bytes is most likely to be<br />accurate immediately after this call.  It is likely to be made<br />inaccurate by any external I/O operations including those made<br />on the system outside of this virtual machine.  This method<br />makes no guarantee that write operations to this file system<br />will succeed.|
|usableSpace|integer(int64)|false|none||Returns the number of bytes available to this virtual machine on the<br />partition <a href="#partName">named</a> by this abstract pathname.  If<br />the number of available bytes in the partition is greater than<br />{@link Long#MAX_VALUE}, then{@code Long.MAX_VALUE} will be returned.<br />When possible, this method checks for write permissions and other<br />operating system restrictions and will therefore usually provide a more<br />accurate estimate of how much new data can actually be written than<br />{@link #getFreeSpace}.<br /><br /><p> The returned number of available bytes is a hint, but not a<br />guarantee, that it is possible to use most or any of these bytes.  The<br />number of available bytes is most likely to be accurate immediately<br />after this call.  It is likely to be made inaccurate by any external<br />I/O operations including those made on the system outside of this<br />virtual machine.  This method makes no guarantee that write operations<br />to this file system will succeed.|

<h2 id="tocS_CollectRequest">CollectRequest</h2>

<a id="schemacollectrequest"></a>
<a id="schema_CollectRequest"></a>
<a id="tocScollectrequest"></a>
<a id="tocscollectrequest"></a>

```json
{
  "userId": 0,
  "articleId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||用户id|
|articleId|integer(int64)|false|none||文章id|

<h2 id="tocS_CommentRequest">CommentRequest</h2>

<a id="schemacommentrequest"></a>
<a id="schema_CommentRequest"></a>
<a id="tocScommentrequest"></a>
<a id="tocscommentrequest"></a>

```json
{
  "commentId": 0,
  "userId": 0,
  "articleId": 0,
  "content": "string",
  "parentId": 0,
  "replyUserId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|commentId|integer(int64)|false|none||评论id（编辑时使用）|
|userId|integer(int64)|false|none||用户id|
|articleId|integer(int64)|false|none||文章id|
|content|string|false|none||评论内容|
|parentId|integer(int64)|false|none||父评论id（0表示顶级评论）|
|replyUserId|integer(int64)|false|none||回复的用户id（如果是回复评论）|

<h2 id="tocS_CommentLikeRequest">CommentLikeRequest</h2>

<a id="schemacommentlikerequest"></a>
<a id="schema_CommentLikeRequest"></a>
<a id="tocScommentlikerequest"></a>
<a id="tocscommentlikerequest"></a>

```json
{
  "userId": 0,
  "targetType": "string",
  "targetId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||用户id|
|targetType|string|false|none||目标类型：comment-评论|
|targetId|integer(int64)|false|none||目标id（评论id）|

<h2 id="tocS_ServerSentEventString">ServerSentEventString</h2>

<a id="schemaserversenteventstring"></a>
<a id="schema_ServerSentEventString"></a>
<a id="tocSserversenteventstring"></a>
<a id="tocsserversenteventstring"></a>

```json
{
  "id": "string",
  "event": "string",
  "retry": {
    "seconds": 0,
    "nanos": 0
  },
  "comment": "string",
  "data": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|string¦null|false|none||none|
|event|string¦null|false|none||none|
|retry|[Duration](#schemaduration)|false|none||none|
|comment|string¦null|false|none||none|
|data|string¦null|false|none||none|

<h2 id="tocS_AlgorithmProblemRequest">AlgorithmProblemRequest</h2>

<a id="schemaalgorithmproblemrequest"></a>
<a id="schema_AlgorithmProblemRequest"></a>
<a id="tocSalgorithmproblemrequest"></a>
<a id="tocsalgorithmproblemrequest"></a>

```json
{
  "problem": "string",
  "language": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|problem|string|false|none||none|
|language|string|false|none||none|

<h2 id="tocS_LoginUserVO">LoginUserVO</h2>

<a id="schemaloginuservo"></a>
<a id="schema_LoginUserVO"></a>
<a id="tocSloginuservo"></a>
<a id="tocsloginuservo"></a>

```json
{
  "userId": 0,
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string",
  "userRole": "string",
  "createTime": "string",
  "updateTime": "string",
  "token": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||用户 id|
|userName|string|false|none||用户昵称|
|userAvatar|string|false|none||用户头像|
|userProfile|string|false|none||用户简介|
|userRole|string|false|none||用户角色|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|
|token|string|false|none||JWT令牌|

<h2 id="tocS_BaseResponseListArticleVO">BaseResponseListArticleVO</h2>

<a id="schemabaseresponselistarticlevo"></a>
<a id="schema_BaseResponseListArticleVO"></a>
<a id="tocSbaseresponselistarticlevo"></a>
<a id="tocsbaseresponselistarticlevo"></a>

```json
{
  "code": 0,
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "userName": "string",
      "headPhoto": "string",
      "articleTitle": "string",
      "paratext": "string",
      "articleCover": "string",
      "categoryId": 0,
      "categoryName": "string",
      "tags": "string",
      "status": 0,
      "isTop": 0,
      "isOriginal": 0,
      "sourceUrl": "string",
      "sourceAuthor": "string",
      "wordCount": 0,
      "createTime": "string",
      "updateTime": "string",
      "mainBody": "string",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "likeId": 0,
      "likeTime": "string",
      "likeFlag": true,
      "collectId": 0,
      "collectTime": "string",
      "collectFlag": true
    }
  ],
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[[ArticleVO](#schemaarticlevo)]|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseListLong">BaseResponseListLong</h2>

<a id="schemabaseresponselistlong"></a>
<a id="schema_BaseResponseListLong"></a>
<a id="tocSbaseresponselistlong"></a>
<a id="tocsbaseresponselistlong"></a>

```json
{
  "code": 0,
  "data": [
    0
  ],
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[integer]|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseListCategoryVO">BaseResponseListCategoryVO</h2>

<a id="schemabaseresponselistcategoryvo"></a>
<a id="schema_BaseResponseListCategoryVO"></a>
<a id="tocSbaseresponselistcategoryvo"></a>
<a id="tocsbaseresponselistcategoryvo"></a>

```json
{
  "code": 0,
  "data": [
    {
      "categoryId": 0,
      "categoryName": "string",
      "description": "string",
      "parentId": 0,
      "parentName": "string",
      "sortOrder": 0,
      "status": 0,
      "statusDesc": "string",
      "iconUrl": "string",
      "color": "string",
      "articleCount": 0,
      "createTime": "string",
      "updateTime": "string",
      "children": [
        {
          "categoryId": 0,
          "categoryName": "string",
          "description": "string",
          "parentId": 0,
          "parentName": "string",
          "sortOrder": 0,
          "status": 0,
          "statusDesc": "string",
          "iconUrl": "string",
          "color": "string",
          "articleCount": 0,
          "createTime": "string",
          "updateTime": "string",
          "children": [
            {}
          ]
        }
      ]
    }
  ],
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[[CategoryVO](#schemacategoryvo)]|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_URI">URI</h2>

<a id="schemauri"></a>
<a id="schema_URI"></a>
<a id="tocSuri"></a>
<a id="tocsuri"></a>

```json
{
  "string": "string",
  "absolute": true,
  "opaque": true,
  "rawSchemeSpecificPart": "string",
  "rawAuthority": "string",
  "rawUserInfo": "string",
  "rawPath": "string",
  "rawQuery": "string",
  "rawFragment": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|string|string|false|none||The string form of this URI.|
|absolute|boolean|false|none||Tells whether or not this URI is absolute.<br /><br /><p> A URI is absolute if, and only if, it has a scheme component. </p>|
|opaque|boolean|false|none||Tells whether or not this URI is opaque.<br /><br /><p> A URI is opaque if, and only if, it is absolute and its<br />scheme-specific part does not begin with a slash character ('/').<br />An opaque URI has a scheme, a scheme-specific part, and possibly<br />a fragment; all other components are undefined. </p>|
|rawSchemeSpecificPart|string|false|none||Returns the raw scheme-specific part of this URI.  The scheme-specific<br />part is never undefined, though it may be empty.<br /><br /><p> The scheme-specific part of a URI only contains legal URI<br />characters. </p>|
|rawAuthority|string|false|none||Returns the raw authority component of this URI.<br /><br /><p> The authority component of a URI, if defined, only contains the<br />commercial-at character ({@code '@'}) and characters in the<br /><i>unreserved</i>, <i>punct</i>, <i>escaped</i>, and <i>other</i><br />categories.  If the authority is server-based then it is further<br />constrained to have valid user-information, host, and port<br />components. </p>|
|rawUserInfo|string|false|none||Returns the raw user-information component of this URI.<br /><br /><p> The user-information component of a URI, if defined, only contains<br />characters in the <i>unreserved</i>, <i>punct</i>, <i>escaped</i>, and<br /><i>other</i> categories. </p>|
|rawPath|string|false|none||Returns the raw path component of this URI.<br /><br /><p> The path component of a URI, if defined, only contains the slash<br />character ({@code '/'}), the commercial-at character ({@code '@'}),<br />and characters in the <i>unreserved</i>, <i>punct</i>, <i>escaped</i>,<br />and <i>other</i> categories. </p>|
|rawQuery|string|false|none||Returns the raw query component of this URI.<br /><br /><p> The query component of a URI, if defined, only contains legal URI<br />characters. </p>|
|rawFragment|string|false|none||Returns the raw fragment component of this URI.<br /><br /><p> The fragment component of a URI, if defined, only contains legal URI<br />characters. </p>|

<h2 id="tocS_CollectVO">CollectVO</h2>

<a id="schemacollectvo"></a>
<a id="schema_CollectVO"></a>
<a id="tocScollectvo"></a>
<a id="tocscollectvo"></a>

```json
{
  "collectId": 0,
  "userId": 0,
  "articleId": 0,
  "createTime": "string",
  "articleTitle": "string",
  "paratext": "string",
  "mainBody": "string",
  "pageview": 0,
  "likeCount": 0,
  "collectCount": 0,
  "commentCount": 0,
  "userName": "string",
  "headPhoto": "string",
  "collectFlag": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|collectId|integer(int64)|false|none||收藏id|
|userId|integer(int64)|false|none||用户id|
|articleId|integer(int64)|false|none||文章id|
|createTime|string|false|none||创建时间|
|articleTitle|string|false|none||文章标题|
|paratext|string|false|none||文章摘要|
|mainBody|string|false|none||文章正文|
|pageview|integer|false|none||阅读量|
|likeCount|integer|false|none||赞量|
|collectCount|integer|false|none||收藏量|
|commentCount|integer|false|none||评论量|
|userName|string|false|none||用户名|
|headPhoto|string|false|none||用户头像|
|collectFlag|boolean|false|none||是否已收藏|

<h2 id="tocS_CommentVO">CommentVO</h2>

<a id="schemacommentvo"></a>
<a id="schema_CommentVO"></a>
<a id="tocScommentvo"></a>
<a id="tocscommentvo"></a>

```json
{
  "commentId": 0,
  "userId": 0,
  "articleId": 0,
  "content": "string",
  "parentId": 0,
  "replyUserId": 0,
  "likeCount": 0,
  "createTime": "string",
  "userName": "string",
  "userAvatar": "string",
  "replyUserName": "string",
  "likeFlag": true,
  "repliesChildren": [
    {
      "commentId": 0,
      "userId": 0,
      "articleId": 0,
      "content": "string",
      "parentId": 0,
      "replyUserId": 0,
      "likeCount": 0,
      "createTime": "string",
      "userName": "string",
      "userAvatar": "string",
      "replyUserName": "string",
      "likeFlag": true,
      "repliesChildren": [
        {
          "commentId": 0,
          "userId": 0,
          "articleId": 0,
          "content": "string",
          "parentId": 0,
          "replyUserId": 0,
          "likeCount": 0,
          "createTime": "string",
          "userName": "string",
          "userAvatar": "string",
          "replyUserName": "string",
          "likeFlag": true,
          "repliesChildren": [
            {}
          ]
        }
      ]
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|commentId|integer(int64)|false|none||评论id|
|userId|integer(int64)|false|none||用户id|
|articleId|integer(int64)|false|none||文章id|
|content|string|false|none||评论内容|
|parentId|integer(int64)|false|none||父评论id（0表示顶级评论）|
|replyUserId|integer(int64)|false|none||回复的用户id（如果是回复评论）|
|likeCount|integer|false|none||点赞数|
|createTime|string|false|none||创建时间|
|userName|string|false|none||用户名|
|userAvatar|string|false|none||用户头像|
|replyUserName|string|false|none||回复的用户名|
|likeFlag|boolean|false|none||是否已点赞|
|repliesChildren|[[CommentVO](#schemacommentvo)]|false|none||子评论列表|

<h2 id="tocS_AlgorithmCodeResponse">AlgorithmCodeResponse</h2>

<a id="schemaalgorithmcoderesponse"></a>
<a id="schema_AlgorithmCodeResponse"></a>
<a id="tocSalgorithmcoderesponse"></a>
<a id="tocsalgorithmcoderesponse"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

<h2 id="tocS_BaseResponseLoginUserVO">BaseResponseLoginUserVO</h2>

<a id="schemabaseresponseloginuservo"></a>
<a id="schema_BaseResponseLoginUserVO"></a>
<a id="tocSbaseresponseloginuservo"></a>
<a id="tocsbaseresponseloginuservo"></a>

```json
{
  "code": 0,
  "data": {
    "userId": 0,
    "userName": "string",
    "userAvatar": "string",
    "userProfile": "string",
    "userRole": "string",
    "createTime": "string",
    "updateTime": "string",
    "token": "string"
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[LoginUserVO](#schemaloginuservo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_ResponseEntityResource">ResponseEntityResource</h2>

<a id="schemaresponseentityresource"></a>
<a id="schema_ResponseEntityResource"></a>
<a id="tocSresponseentityresource"></a>
<a id="tocsresponseentityresource"></a>

```json
{
  "inputStream": {},
  "readable": true,
  "open": true,
  "file": {
    "path": "string",
    "name": "string",
    "parent": "string",
    "parentFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "absolute": true,
    "absolutePath": "string",
    "absoluteFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "canonicalPath": "string",
    "canonicalFile": {
      "path": "string",
      "name": "string",
      "parent": "string",
      "parentFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "absolute": true,
      "absolutePath": "string",
      "absoluteFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "canonicalPath": "string",
      "canonicalFile": {
        "path": "string",
        "name": "string",
        "parent": "string",
        "parentFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "absolute": true,
        "absolutePath": "string",
        "absoluteFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "canonicalPath": "string",
        "canonicalFile": {
          "path": null,
          "name": null,
          "parent": null,
          "parentFile": null,
          "absolute": null,
          "absolutePath": null,
          "absoluteFile": null,
          "canonicalPath": null,
          "canonicalFile": null,
          "directory": null,
          "file": null,
          "hidden": null,
          "lastModified": null,
          "writable": null,
          "readable": null,
          "executable": null,
          "totalSpace": null,
          "freeSpace": null,
          "usableSpace": null
        },
        "directory": true,
        "file": true,
        "hidden": true,
        "lastModified": 0,
        "writable": true,
        "readable": true,
        "executable": true,
        "totalSpace": 0,
        "freeSpace": 0,
        "usableSpace": 0
      },
      "directory": true,
      "file": true,
      "hidden": true,
      "lastModified": 0,
      "writable": true,
      "readable": true,
      "executable": true,
      "totalSpace": 0,
      "freeSpace": 0,
      "usableSpace": 0
    },
    "directory": true,
    "file": true,
    "hidden": true,
    "lastModified": 0,
    "writable": true,
    "readable": true,
    "executable": true,
    "totalSpace": 0,
    "freeSpace": 0,
    "usableSpace": 0
  },
  "uRL": "string",
  "uRI": {
    "string": "string",
    "absolute": true,
    "opaque": true,
    "rawSchemeSpecificPart": "string",
    "rawAuthority": "string",
    "rawUserInfo": "string",
    "rawPath": "string",
    "rawQuery": "string",
    "rawFragment": "string"
  },
  "contentAsByteArray": [
    0
  ],
  "filename": "string",
  "description": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|inputStream|[InputStream](#schemainputstream)|false|none||java.io.InputStream|
|readable|boolean|false|none||none|
|open|boolean|false|none||none|
|file|[File](#schemafile)|false|none||java.io.File|
|uRL|string|false|none||none|
|uRI|[URI](#schemauri)|false|none||java.net.URI|
|contentAsByteArray|[integer]|false|none||none|
|filename|string¦null|false|none||none|
|description|string|false|none||none|

<h2 id="tocS_BaseResponseListCollectVO">BaseResponseListCollectVO</h2>

<a id="schemabaseresponselistcollectvo"></a>
<a id="schema_BaseResponseListCollectVO"></a>
<a id="tocSbaseresponselistcollectvo"></a>
<a id="tocsbaseresponselistcollectvo"></a>

```json
{
  "code": 0,
  "data": [
    {
      "collectId": 0,
      "userId": 0,
      "articleId": 0,
      "createTime": "string",
      "articleTitle": "string",
      "paratext": "string",
      "mainBody": "string",
      "pageview": 0,
      "likeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "userName": "string",
      "headPhoto": "string",
      "collectFlag": true
    }
  ],
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[[CollectVO](#schemacollectvo)]|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_BaseResponseListCommentVO">BaseResponseListCommentVO</h2>

<a id="schemabaseresponselistcommentvo"></a>
<a id="schema_BaseResponseListCommentVO"></a>
<a id="tocSbaseresponselistcommentvo"></a>
<a id="tocsbaseresponselistcommentvo"></a>

```json
{
  "code": 0,
  "data": [
    {
      "commentId": 0,
      "userId": 0,
      "articleId": 0,
      "content": "string",
      "parentId": 0,
      "replyUserId": 0,
      "likeCount": 0,
      "createTime": "string",
      "userName": "string",
      "userAvatar": "string",
      "replyUserName": "string",
      "likeFlag": true,
      "repliesChildren": [
        {
          "commentId": 0,
          "userId": 0,
          "articleId": 0,
          "content": "string",
          "parentId": 0,
          "replyUserId": 0,
          "likeCount": 0,
          "createTime": "string",
          "userName": "string",
          "userAvatar": "string",
          "replyUserName": "string",
          "likeFlag": true,
          "repliesChildren": [
            {}
          ]
        }
      ]
    }
  ],
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[[CommentVO](#schemacommentvo)]|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_AlgorithmCodeRequest">AlgorithmCodeRequest</h2>

<a id="schemaalgorithmcoderequest"></a>
<a id="schema_AlgorithmCodeRequest"></a>
<a id="tocSalgorithmcoderequest"></a>
<a id="tocsalgorithmcoderequest"></a>

```json
{
  "algorithmName": "string",
  "language": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|algorithmName|string|false|none||none|
|language|string|false|none||none|

<h2 id="tocS_UserLoginRequest">UserLoginRequest</h2>

<a id="schemauserloginrequest"></a>
<a id="schema_UserLoginRequest"></a>
<a id="tocSuserloginrequest"></a>
<a id="tocsuserloginrequest"></a>

```json
{
  "userAccount": "string",
  "userPassword": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userAccount|string|false|none||none|
|userPassword|string|false|none||none|

<h2 id="tocS_ArticleRequest">ArticleRequest</h2>

<a id="schemaarticlerequest"></a>
<a id="schema_ArticleRequest"></a>
<a id="tocSarticlerequest"></a>
<a id="tocsarticlerequest"></a>

```json
{
  "articleId": 0,
  "articleTitle": "string",
  "paratext": "string",
  "articleCover": "string",
  "categoryId": 0,
  "tags": "string",
  "status": 0,
  "isTop": 0,
  "isOriginal": 0,
  "sourceUrl": "string",
  "sourceAuthor": "string",
  "mainBody": "string",
  "deleted": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|articleId|integer(int64)|false|none||主键，文章id|
|articleTitle|string|false|none||文章标题|
|paratext|string|false|none||文章摘要/副文|
|articleCover|string|false|none||文章封面图URL|
|categoryId|integer(int64)|false|none||文章分类id（代码层面约束关联分类表）|
|tags|string|false|none||文章标签（多个标签用逗号分隔，如：Java,MySQL,后端）|
|status|integer|false|none||文章状态：0-草稿，1-已发布，2-下架，3-审核中|
|isTop|integer|false|none||是否置顶：0-否，1-是|
|isOriginal|integer|false|none||是否原创：0-转载，1-原创|
|sourceUrl|string|false|none||转载来源URL（非原创时填写）|
|sourceAuthor|string|false|none||转载来源作者（非原创时填写）|
|mainBody|string|false|none||正文|
|deleted|integer|false|none||逻辑删除：0-未删除，1-已删除|

<h2 id="tocS_CategoryRequest">CategoryRequest</h2>

<a id="schemacategoryrequest"></a>
<a id="schema_CategoryRequest"></a>
<a id="tocScategoryrequest"></a>
<a id="tocscategoryrequest"></a>

```json
{
  "categoryId": 0,
  "categoryName": "string",
  "description": "string",
  "parentId": 0,
  "sortOrder": 0,
  "status": 0,
  "iconUrl": "string",
  "color": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|categoryId|integer(int64)|false|none||分类ID|
|categoryName|string|false|none||分类名称|
|description|string|false|none||分类描述|
|parentId|integer(int64)|false|none||父级分类ID|
|sortOrder|integer|false|none||分类排序|
|status|integer|false|none||分类状态：0-禁用，1-启用|
|iconUrl|string|false|none||分类图标URL|
|color|string|false|none||分类颜色|

<h2 id="tocS_BaseResponseCommentVO">BaseResponseCommentVO</h2>

<a id="schemabaseresponsecommentvo"></a>
<a id="schema_BaseResponseCommentVO"></a>
<a id="tocSbaseresponsecommentvo"></a>
<a id="tocsbaseresponsecommentvo"></a>

```json
{
  "code": 0,
  "data": {
    "commentId": 0,
    "userId": 0,
    "articleId": 0,
    "content": "string",
    "parentId": 0,
    "replyUserId": 0,
    "likeCount": 0,
    "createTime": "string",
    "userName": "string",
    "userAvatar": "string",
    "replyUserName": "string",
    "likeFlag": true,
    "repliesChildren": [
      {
        "commentId": 0,
        "userId": 0,
        "articleId": 0,
        "content": "string",
        "parentId": 0,
        "replyUserId": 0,
        "likeCount": 0,
        "createTime": "string",
        "userName": "string",
        "userAvatar": "string",
        "replyUserName": "string",
        "likeFlag": true,
        "repliesChildren": [
          {
            "commentId": null,
            "userId": null,
            "articleId": null,
            "content": null,
            "parentId": null,
            "replyUserId": null,
            "likeCount": null,
            "createTime": null,
            "userName": null,
            "userAvatar": null,
            "replyUserName": null,
            "likeFlag": null,
            "repliesChildren": null
          }
        ]
      }
    ]
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[CommentVO](#schemacommentvo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_ComplexityAnalysisResponse">ComplexityAnalysisResponse</h2>

<a id="schemacomplexityanalysisresponse"></a>
<a id="schema_ComplexityAnalysisResponse"></a>
<a id="tocScomplexityanalysisresponse"></a>
<a id="tocscomplexityanalysisresponse"></a>

```json
{
  "analysis": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|analysis|string|false|none||none|

<h2 id="tocS_BaseResponseBoolean">BaseResponseBoolean</h2>

<a id="schemabaseresponseboolean"></a>
<a id="schema_BaseResponseBoolean"></a>
<a id="tocSbaseresponseboolean"></a>
<a id="tocsbaseresponseboolean"></a>

```json
{
  "code": 0,
  "data": true,
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|boolean|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_PageResponseArticleVO">PageResponseArticleVO</h2>

<a id="schemapageresponsearticlevo"></a>
<a id="schema_PageResponseArticleVO"></a>
<a id="tocSpageresponsearticlevo"></a>
<a id="tocspageresponsearticlevo"></a>

```json
{
  "pageNum": 0,
  "pageSize": 0,
  "total": 0,
  "totalPages": 0,
  "records": [
    {
      "articleId": 0,
      "userId": 0,
      "userName": "string",
      "headPhoto": "string",
      "articleTitle": "string",
      "paratext": "string",
      "articleCover": "string",
      "categoryId": 0,
      "categoryName": "string",
      "tags": "string",
      "status": 0,
      "isTop": 0,
      "isOriginal": 0,
      "sourceUrl": "string",
      "sourceAuthor": "string",
      "wordCount": 0,
      "createTime": "string",
      "updateTime": "string",
      "mainBody": "string",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "likeId": 0,
      "likeTime": "string",
      "likeFlag": true,
      "collectId": 0,
      "collectTime": "string",
      "collectFlag": true
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|pageNum|integer|false|none||当前页码|
|pageSize|integer|false|none||每页大小|
|total|integer(int64)|false|none||总条数|
|totalPages|integer|false|none||总页数|
|records|[[ArticleVO](#schemaarticlevo)]|false|none||数据列表|

<h2 id="tocS_ComplexityAnalysisRequest">ComplexityAnalysisRequest</h2>

<a id="schemacomplexityanalysisrequest"></a>
<a id="schema_ComplexityAnalysisRequest"></a>
<a id="tocScomplexityanalysisrequest"></a>
<a id="tocscomplexityanalysisrequest"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

<h2 id="tocS_AlgorithmComparisonRequest">AlgorithmComparisonRequest</h2>

<a id="schemaalgorithmcomparisonrequest"></a>
<a id="schema_AlgorithmComparisonRequest"></a>
<a id="tocSalgorithmcomparisonrequest"></a>
<a id="tocsalgorithmcomparisonrequest"></a>

```json
{
  "code1": "string",
  "code2": "string",
  "language": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code1|string|false|none||none|
|code2|string|false|none||none|
|language|string|false|none||none|

<h2 id="tocS_UserAddRequest">UserAddRequest</h2>

<a id="schemauseraddrequest"></a>
<a id="schema_UserAddRequest"></a>
<a id="tocSuseraddrequest"></a>
<a id="tocsuseraddrequest"></a>

```json
{
  "userName": "string",
  "userAccount": "string",
  "userAvatar": "string",
  "userRole": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户昵称|
|userAccount|string|false|none||账号|
|userAvatar|string|false|none||用户头像|
|userRole|string|false|none||用户角色: user, admin|

<h2 id="tocS_BaseResponsePageResponseArticleVO">BaseResponsePageResponseArticleVO</h2>

<a id="schemabaseresponsepageresponsearticlevo"></a>
<a id="schema_BaseResponsePageResponseArticleVO"></a>
<a id="tocSbaseresponsepageresponsearticlevo"></a>
<a id="tocsbaseresponsepageresponsearticlevo"></a>

```json
{
  "code": 0,
  "data": {
    "pageNum": 0,
    "pageSize": 0,
    "total": 0,
    "totalPages": 0,
    "records": [
      {
        "articleId": 0,
        "userId": 0,
        "userName": "string",
        "headPhoto": "string",
        "articleTitle": "string",
        "paratext": "string",
        "articleCover": "string",
        "categoryId": 0,
        "categoryName": "string",
        "tags": "string",
        "status": 0,
        "isTop": 0,
        "isOriginal": 0,
        "sourceUrl": "string",
        "sourceAuthor": "string",
        "wordCount": 0,
        "createTime": "string",
        "updateTime": "string",
        "mainBody": "string",
        "pageview": 0,
        "likeCount": 0,
        "unlikeCount": 0,
        "collectCount": 0,
        "commentCount": 0,
        "likeId": 0,
        "likeTime": "string",
        "likeFlag": true,
        "collectId": 0,
        "collectTime": "string",
        "collectFlag": true
      }
    ]
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[PageResponseArticleVO](#schemapageresponsearticlevo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_OptimizationResponse">OptimizationResponse</h2>

<a id="schemaoptimizationresponse"></a>
<a id="schema_OptimizationResponse"></a>
<a id="tocSoptimizationresponse"></a>
<a id="tocsoptimizationresponse"></a>

```json
{
  "suggestions": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|suggestions|string|false|none||none|

<h2 id="tocS_DeleteRequest">DeleteRequest</h2>

<a id="schemadeleterequest"></a>
<a id="schema_DeleteRequest"></a>
<a id="tocSdeleterequest"></a>
<a id="tocsdeleterequest"></a>

```json
{
  "id": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||id|

<h2 id="tocS_UserUpdatePasswordRequest">UserUpdatePasswordRequest</h2>

<a id="schemauserupdatepasswordrequest"></a>
<a id="schema_UserUpdatePasswordRequest"></a>
<a id="tocSuserupdatepasswordrequest"></a>
<a id="tocsuserupdatepasswordrequest"></a>

```json
{
  "id": 0,
  "userAccount": "string",
  "oldPassword": "string",
  "newPassword": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||用户ID，主键，自增|
|userAccount|string|false|none||用户账号|
|oldPassword|string|false|none||旧密码|
|newPassword|string|false|none||新密码|

<h2 id="tocS_OptimizationRequest">OptimizationRequest</h2>

<a id="schemaoptimizationrequest"></a>
<a id="schema_OptimizationRequest"></a>
<a id="tocSoptimizationrequest"></a>
<a id="tocsoptimizationrequest"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

<h2 id="tocS_UserUpdateRequest">UserUpdateRequest</h2>

<a id="schemauserupdaterequest"></a>
<a id="schema_UserUpdateRequest"></a>
<a id="tocSuserupdaterequest"></a>
<a id="tocsuserupdaterequest"></a>

```json
{
  "userId": 0,
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string",
  "userRole": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||id|
|userName|string|false|none||用户昵称|
|userAvatar|string|false|none||用户头像|
|userProfile|string|false|none||简介|
|userRole|string|false|none||用户角色|

<h2 id="tocS_TimeComplexity">TimeComplexity</h2>

<a id="schematimecomplexity"></a>
<a id="schema_TimeComplexity"></a>
<a id="tocStimecomplexity"></a>
<a id="tocstimecomplexity"></a>

```json
{
  "bestCase": "string",
  "averageCase": "string",
  "worstCase": "string",
  "explanation": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|bestCase|string|false|none||none|
|averageCase|string|false|none||none|
|worstCase|string|false|none||none|
|explanation|string|false|none||none|

<h2 id="tocS_User">User</h2>

<a id="schemauser"></a>
<a id="schema_User"></a>
<a id="tocSuser"></a>
<a id="tocsuser"></a>

```json
{
  "id": 0,
  "userAccount": "string",
  "userPassword": "string",
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string",
  "userRole": "string",
  "createTime": 0,
  "updateTime": 0,
  "isDelete": -127
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||用户ID，主键，自增|
|userAccount|string|false|none||用户账号|
|userPassword|string|false|none||用户密码|
|userName|string|false|none||用户昵称|
|userAvatar|string|false|none||用户头像|
|userProfile|string|false|none||用户简介|
|userRole|string|false|none||用户角色，默认为 'user'<br />默认值为 'user'，表示普通用户|
|createTime|integer|false|none||创建时间，默认值为当前时间戳<br />记录用户创建的时间|
|updateTime|integer|false|none||更新时间，每次更新记录时自动更新为当前时间戳<br />记录用户最后更新的时间|
|isDelete|integer|false|none||是否删除，0表示未删除，非0表示已删除<br />用于逻辑删除，0表示未删除，1或其他值表示已删除|

<h2 id="tocS_SpaceComplexity">SpaceComplexity</h2>

<a id="schemaspacecomplexity"></a>
<a id="schema_SpaceComplexity"></a>
<a id="tocSspacecomplexity"></a>
<a id="tocsspacecomplexity"></a>

```json
{
  "bestCase": "string",
  "averageCase": "string",
  "worstCase": "string",
  "explanation": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|bestCase|string|false|none||none|
|averageCase|string|false|none||none|
|worstCase|string|false|none||none|
|explanation|string|false|none||none|

<h2 id="tocS_BaseResponseUser">BaseResponseUser</h2>

<a id="schemabaseresponseuser"></a>
<a id="schema_BaseResponseUser"></a>
<a id="tocSbaseresponseuser"></a>
<a id="tocsbaseresponseuser"></a>

```json
{
  "code": 0,
  "data": {
    "id": 0,
    "userAccount": "string",
    "userPassword": "string",
    "userName": "string",
    "userAvatar": "string",
    "userProfile": "string",
    "userRole": "string",
    "createTime": 0,
    "updateTime": 0,
    "isDelete": -127
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[User](#schemauser)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_AlgorithmAnalysisReport">AlgorithmAnalysisReport</h2>

<a id="schemaalgorithmanalysisreport"></a>
<a id="schema_AlgorithmAnalysisReport"></a>
<a id="tocSalgorithmanalysisreport"></a>
<a id="tocsalgorithmanalysisreport"></a>

```json
{
  "algorithmName": "string",
  "timeComplexity": {
    "bestCase": "string",
    "averageCase": "string",
    "worstCase": "string",
    "explanation": "string"
  },
  "spaceComplexity": {
    "bestCase": "string",
    "averageCase": "string",
    "worstCase": "string",
    "explanation": "string"
  },
  "starRating": 0,
  "starRatingComment": "string",
  "optimizationDirections": [
    "string"
  ],
  "weaknesses": [
    "string"
  ],
  "overallAssessment": "string",
  "improvementSuggestions": "string",
  "emotionalValue": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|algorithmName|string|false|none||none|
|timeComplexity|[TimeComplexity](#schematimecomplexity)|false|none||none|
|spaceComplexity|[SpaceComplexity](#schemaspacecomplexity)|false|none||none|
|starRating|integer|false|none||none|
|starRatingComment|string|false|none||星级评语|
|optimizationDirections|[string]|false|none||none|
|weaknesses|[string]|false|none||none|
|overallAssessment|string|false|none||none|
|improvementSuggestions|string|false|none||none|
|emotionalValue|string|false|none||情绪价值内容|

<h2 id="tocS_UserVO">UserVO</h2>

<a id="schemauservo"></a>
<a id="schema_UserVO"></a>
<a id="tocSuservo"></a>
<a id="tocsuservo"></a>

```json
{
  "id": 0,
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string",
  "userRole": "string",
  "createTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||id|
|userName|string|false|none||用户昵称|
|userAvatar|string|false|none||用户头像|
|userProfile|string|false|none||用户简介|
|userRole|string|false|none||用户角色|
|createTime|string|false|none||创建时间|

<h2 id="tocS_AlgorithmAnalysisReportResponse">AlgorithmAnalysisReportResponse</h2>

<a id="schemaalgorithmanalysisreportresponse"></a>
<a id="schema_AlgorithmAnalysisReportResponse"></a>
<a id="tocSalgorithmanalysisreportresponse"></a>
<a id="tocsalgorithmanalysisreportresponse"></a>

```json
{
  "report": {
    "algorithmName": "string",
    "timeComplexity": {
      "bestCase": "string",
      "averageCase": "string",
      "worstCase": "string",
      "explanation": "string"
    },
    "spaceComplexity": {
      "bestCase": "string",
      "averageCase": "string",
      "worstCase": "string",
      "explanation": "string"
    },
    "starRating": 0,
    "starRatingComment": "string",
    "optimizationDirections": [
      "string"
    ],
    "weaknesses": [
      "string"
    ],
    "overallAssessment": "string",
    "improvementSuggestions": "string",
    "emotionalValue": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|report|[AlgorithmAnalysisReport](#schemaalgorithmanalysisreport)|false|none||none|

<h2 id="tocS_BaseResponseUserVO">BaseResponseUserVO</h2>

<a id="schemabaseresponseuservo"></a>
<a id="schema_BaseResponseUserVO"></a>
<a id="tocSbaseresponseuservo"></a>
<a id="tocsbaseresponseuservo"></a>

```json
{
  "code": 0,
  "data": {
    "id": 0,
    "userName": "string",
    "userAvatar": "string",
    "userProfile": "string",
    "userRole": "string",
    "createTime": "string"
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[UserVO](#schemauservo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_AlgorithmAnalysisRequest">AlgorithmAnalysisRequest</h2>

<a id="schemaalgorithmanalysisrequest"></a>
<a id="schema_AlgorithmAnalysisRequest"></a>
<a id="tocSalgorithmanalysisrequest"></a>
<a id="tocsalgorithmanalysisrequest"></a>

```json
{
  "code": "string",
  "language": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|
|language|string|false|none||none|

<h2 id="tocS_PageUser">PageUser</h2>

<a id="schemapageuser"></a>
<a id="schema_PageUser"></a>
<a id="tocSpageuser"></a>
<a id="tocspageuser"></a>

```json
{
  "records": [
    {
      "id": 0,
      "userAccount": "string",
      "userPassword": "string",
      "userName": "string",
      "userAvatar": "string",
      "userProfile": "string",
      "userRole": "string",
      "createTime": 0,
      "updateTime": 0,
      "isDelete": -127
    }
  ],
  "pageNumber": 0,
  "pageSize": 0,
  "maxPageSize": 0,
  "totalPage": 0,
  "totalRow": 0,
  "optimizeCountQuery": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|records|[[User](#schemauser)]|false|none||none|
|pageNumber|integer(int64)|false|none||none|
|pageSize|integer(int64)|false|none||none|
|maxPageSize|integer(int64)|false|none||none|
|totalPage|integer(int64)|false|none||none|
|totalRow|integer(int64)|false|none||none|
|optimizeCountQuery|boolean|false|none||none|

<h2 id="tocS_StarRatingResponse">StarRatingResponse</h2>

<a id="schemastarratingresponse"></a>
<a id="schema_StarRatingResponse"></a>
<a id="tocSstarratingresponse"></a>
<a id="tocsstarratingresponse"></a>

```json
{
  "starRating": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|starRating|integer|false|none||none|

<h2 id="tocS_BaseResponsePageUser">BaseResponsePageUser</h2>

<a id="schemabaseresponsepageuser"></a>
<a id="schema_BaseResponsePageUser"></a>
<a id="tocSbaseresponsepageuser"></a>
<a id="tocsbaseresponsepageuser"></a>

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 0,
        "userAccount": "string",
        "userPassword": "string",
        "userName": "string",
        "userAvatar": "string",
        "userProfile": "string",
        "userRole": "string",
        "createTime": 0,
        "updateTime": 0,
        "isDelete": -127
      }
    ],
    "pageNumber": 0,
    "pageSize": 0,
    "maxPageSize": 0,
    "totalPage": 0,
    "totalRow": 0,
    "optimizeCountQuery": true
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[PageUser](#schemapageuser)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_StarRatingRequest">StarRatingRequest</h2>

<a id="schemastarratingrequest"></a>
<a id="schema_StarRatingRequest"></a>
<a id="tocSstarratingrequest"></a>
<a id="tocsstarratingrequest"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

<h2 id="tocS_UserQueryRequest">UserQueryRequest</h2>

<a id="schemauserqueryrequest"></a>
<a id="schema_UserQueryRequest"></a>
<a id="tocSuserqueryrequest"></a>
<a id="tocsuserqueryrequest"></a>

```json
{
  "current": 1,
  "pageSize": 10,
  "sortField": "string",
  "sortOrder": "ascend",
  "id": 0,
  "unionId": "string",
  "mpOpenId": "string",
  "userName": "string",
  "userProfile": "string",
  "userRole": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|current|integer|false|none||当前页号|
|pageSize|integer|false|none||页面大小|
|sortField|string|false|none||排序字段|
|sortOrder|string|false|none||排序顺序（默认升序）|
|id|integer(int64)|false|none||id|
|unionId|string|false|none||开放平台id|
|mpOpenId|string|false|none||公众号openId|
|userName|string|false|none||用户昵称|
|userProfile|string|false|none||简介|
|userRole|string|false|none||用户角色|

<h2 id="tocS_OptimizationDirectionsResponse">OptimizationDirectionsResponse</h2>

<a id="schemaoptimizationdirectionsresponse"></a>
<a id="schema_OptimizationDirectionsResponse"></a>
<a id="tocSoptimizationdirectionsresponse"></a>
<a id="tocsoptimizationdirectionsresponse"></a>

```json
{
  "directions": [
    "string"
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|directions|[string]|false|none||none|

<h2 id="tocS_PageUserVO">PageUserVO</h2>

<a id="schemapageuservo"></a>
<a id="schema_PageUserVO"></a>
<a id="tocSpageuservo"></a>
<a id="tocspageuservo"></a>

```json
{
  "records": [
    {
      "id": 0,
      "userName": "string",
      "userAvatar": "string",
      "userProfile": "string",
      "userRole": "string",
      "createTime": "string"
    }
  ],
  "pageNumber": 0,
  "pageSize": 0,
  "maxPageSize": 0,
  "totalPage": 0,
  "totalRow": 0,
  "optimizeCountQuery": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|records|[[UserVO](#schemauservo)]|false|none||none|
|pageNumber|integer(int64)|false|none||none|
|pageSize|integer(int64)|false|none||none|
|maxPageSize|integer(int64)|false|none||none|
|totalPage|integer(int64)|false|none||none|
|totalRow|integer(int64)|false|none||none|
|optimizeCountQuery|boolean|false|none||none|

<h2 id="tocS_OptimizationDirectionsRequest">OptimizationDirectionsRequest</h2>

<a id="schemaoptimizationdirectionsrequest"></a>
<a id="schema_OptimizationDirectionsRequest"></a>
<a id="tocSoptimizationdirectionsrequest"></a>
<a id="tocsoptimizationdirectionsrequest"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

<h2 id="tocS_BaseResponsePageUserVO">BaseResponsePageUserVO</h2>

<a id="schemabaseresponsepageuservo"></a>
<a id="schema_BaseResponsePageUserVO"></a>
<a id="tocSbaseresponsepageuservo"></a>
<a id="tocsbaseresponsepageuservo"></a>

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 0,
        "userName": "string",
        "userAvatar": "string",
        "userProfile": "string",
        "userRole": "string",
        "createTime": "string"
      }
    ],
    "pageNumber": 0,
    "pageSize": 0,
    "maxPageSize": 0,
    "totalPage": 0,
    "totalRow": 0,
    "optimizeCountQuery": true
  },
  "message": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|data|[PageUserVO](#schemapageuservo)|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_WeaknessesResponse">WeaknessesResponse</h2>

<a id="schemaweaknessesresponse"></a>
<a id="schema_WeaknessesResponse"></a>
<a id="tocSweaknessesresponse"></a>
<a id="tocsweaknessesresponse"></a>

```json
{
  "weaknesses": [
    "string"
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|weaknesses|[string]|false|none||none|

<h2 id="tocS_UserUpdateMyRequest">UserUpdateMyRequest</h2>

<a id="schemauserupdatemyrequest"></a>
<a id="schema_UserUpdateMyRequest"></a>
<a id="tocSuserupdatemyrequest"></a>
<a id="tocsuserupdatemyrequest"></a>

```json
{
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户昵称|
|userAvatar|string|false|none||用户头像|
|userProfile|string|false|none||简介|

<h2 id="tocS_WeaknessesRequest">WeaknessesRequest</h2>

<a id="schemaweaknessesrequest"></a>
<a id="schema_WeaknessesRequest"></a>
<a id="tocSweaknessesrequest"></a>
<a id="tocsweaknessesrequest"></a>

```json
{
  "code": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||none|

