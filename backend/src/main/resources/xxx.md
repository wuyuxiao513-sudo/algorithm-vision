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

# 用户/文章

## GET 获取文章

GET /article/getById

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|query|string| 否 |none|
|lookUserId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "article_id": "文章id",
  "user_id": "作者id",
  "user_name": "作者用户名",
  "head_photo": "作者头像地址",
  "article_title": "文章标题",
  "create_time": "发布时间",
  "main_body": "正文",
  "pageview": "阅读数",
  "like_count": "赞数",
  "unlike_count": "睬数",
  "collect_count": "收藏数",
  "comment_count": "评论数",
  "comment": [
    {
      "comment_id": "评论id",
      "user_id": "评论用户id",
      "user_name": "评论用户名",
      "head_photo": "评论用户头像地址",
      "article_id": "文章id",
      "content": "评论内容",
      "createTime": "发布时间"
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 标题查询文章列表

GET /article/getAllByTitle

帖子数据的请求展示功能，用下拉刷新实现数据的展示，开始展示6条数据。
帖子中携带发布“用户的头像信息”，“用户名”，“帖子标题”，“发布时间”，“摘要”，“阅读数”，“赞数“，”睬数“，”收藏数“，”评论数“

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleTitle|query|string| 否 |none|

> 返回示例

> 200 Response

```json
[
  {
    "article_id": "文章id",
    "user_id": "发布文章用户名",
    "user_name": "发布文章用户名",
    "head_photo": "头像地址",
    "article_title": "文章标题",
    "paratext": "文章摘要副文",
    "create_time": "发布时间",
    "pageview": "阅读数",
    "like_count": "赞数",
    "unlike_count": "睬数",
    "collect_count": "收藏数",
    "comment_count": "评论数"
  },
  {},
  {}
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 发布文章

POST /article/add

> Body 请求参数

```json
{
  "userId": 1,
  "articleTitle": "如何使用WangEditor自定义按",
  "paratext": "这是一篇关于如何使用WangEditor自定义按钮的文章...",
  "createTime": "2024-08-29T16:45:24.723Z",
  "mainBody": "e",
  "pageview": "0",
  "likeCount": "0",
  "unlikeCount": "0",
  "collectCount": "0"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## PUT 修改文章

PUT /article/edit

> Body 请求参数

```json
{
  "articleId": 69,
  "userId": 1,
  "articleTitle": "呜呜呜呜",
  "paratext": "这是一篇关于如何使用WangEditor自定义按钮的文章...",
  "createTime": "2024-08-29T16:45:24.723Z",
  "mainBody": "e",
  "pageview": "0",
  "likeCount": "0",
  "unlikeCount": "0",
  "collectCount": "0"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## DELETE 删除文章

DELETE /article/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|query|string| 否 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 文章的图片上传

POST /article/uploadImage

> Body 请求参数

```yaml
file: file://D:\壁纸\壁纸3.jpg

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» file|body|string(binary)| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 更新阅读数

GET /article/updatePageview

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 查询ID用户发布的文章

GET /article/getByUserId

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "articleTitle": "string",
      "paratext": "string",
      "createTime": "string",
      "mainBody": "string",
      "pageview": 0,
      "likeCount": 0,
      "unlikeCount": 0,
      "collectCount": 0,
      "commentCount": 0,
      "userName": "string",
      "headPhoto": "string"
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
|»» articleId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» articleTitle|string|true|none||none|
|»» paratext|string|true|none||none|
|»» createTime|string|true|none||none|
|»» mainBody|string|true|none||none|
|»» pageview|integer|true|none||none|
|»» likeCount|integer|true|none||none|
|»» unlikeCount|integer|true|none||none|
|»» collectCount|integer|true|none||none|
|»» commentCount|integer|true|none||none|
|»» userName|string|true|none||none|
|»» headPhoto|string|true|none||none|

## POST 文章点赞

POST /articleLikes/add

> Body 请求参数

```json
{
  "articleId": 1,
  "userId": 1
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": {
    "likeId": 0,
    "userId": 0,
    "articleId": 0,
    "createTime": null
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
|»» likeId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» articleId|integer|true|none||none|
|»» createTime|null|true|none||none|

## DELETE 取消点赞

DELETE /articleLikes/del

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|query|string| 否 |none|

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

## GET 用户点赞文章

GET /articleLikes/getLikeArt

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|string| 否 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "msg": "string",
  "code": "string",
  "data": [
    {
      "articleId": 0,
      "userId": 0,
      "articleTitle": "string",
      "paratext": "string",
      "createTime": "string",
      "mainBody": "string",
      "pageview": 0,
      "likeCount": 0,
      "likeId": 0,
      "likeTime": "string",
      "likeFlag": true,
      "unlikeCount": null,
      "collectCount": 0,
      "collectTime": null,
      "collectId": null,
      "collectFlag": true,
      "commentCount": 0,
      "userName": "string",
      "headPhoto": "string"
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
|»» articleId|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» articleTitle|string|false|none||none|
|»» paratext|string|false|none||none|
|»» createTime|string|false|none||none|
|»» mainBody|string|false|none||none|
|»» pageview|integer|false|none||none|
|»» likeCount|integer|false|none||none|
|»» likeId|integer|false|none||none|
|»» likeTime|string|false|none||none|
|»» likeFlag|boolean|false|none||none|
|»» unlikeCount|null|false|none||none|
|»» collectCount|integer|false|none||none|
|»» collectTime|null|false|none||none|
|»» collectId|null|false|none||none|
|»» collectFlag|boolean|false|none||none|
|»» commentCount|integer|false|none||none|
|»» userName|string|false|none||none|
|»» headPhoto|string|false|none||none|

# 数据模型

