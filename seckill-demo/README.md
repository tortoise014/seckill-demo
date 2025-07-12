# 秒杀系统 - 登录功能

## 项目简介

这是一个基于Spring Boot 3.x的秒杀系统演示项目，目前已实现用户登录功能，支持前端MD5加盐加密传输。

## 技术栈

- **后端框架**: Spring Boot 3.5.3
- **数据库**: MySQL 8.0
- **ORM**: MyBatis-Plus
- **模板引擎**: Thymeleaf
- **前端**: HTML + CSS + JavaScript + MD5加密
- **工具**: Lombok, Apache Commons

## 功能特性

- ✅ 用户登录/退出
- ✅ 前端MD5加盐加密传输
- ✅ 后端双重加密验证
- ✅ 会话管理
- ✅ 响应式登录界面
- ✅ 表单验证
- ✅ 错误提示
- ✅ 加密测试工具
- ⚠️ 商品列表展示（静态）
- ⚠️ 秒杀功能（开发中）

## 快速开始

### 1. 环境准备

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置

1. 创建数据库并导入数据：
```sql
-- 执行 src/main/resources/sql/init.sql 文件
```

2. 修改数据库连接配置（如需要）：
```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/seckill?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: Wr20050305  # 请修改为您的数据库密码
```

### 3. 启动应用

```bash
# 使用Maven启动
mvn spring-boot:run

# 或者使用IDE运行 SeckillDemoApplication.java
```

### 4. 访问应用

- 登录页面：http://localhost:8080/login/toLogin
- 健康检查：http://localhost:8080/health/check
- 加密测试：http://localhost:8080/test/encrypt

## 测试账号

系统已预置以下测试账号：

| 手机号 | 密码 | 用户名 |
|--------|------|--------|
| 13800000001 | 123456 | 测试用户1 |
| 13800000002 | 123456 | 测试用户2 |
| 13800000003 | 123456 | 测试用户3 |

## 安全特性

### 🔒 前端加密传输

**加密流程：**
1. 用户输入明文密码：`123456`
2. 前端JavaScript加盐加密：`MD5("123456" + "1a2b3c4d")`
3. 传输加密后的密码到后端，不传输明文
4. 后端进行第二次加密验证

**安全优势：**
- ✅ 防止密码明文传输
- ✅ 防止网络抓包获取明文密码
- ✅ 增加密码破解难度
- ✅ 支持明文密码兼容模式

### 🛡️ 双重加密机制

```javascript
// 前端加密（第一次）
function inputPassToFormPass(inputPass) {
    const saltedPassword = SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
    return md5(saltedPassword);
}

// 后端加密（第二次）
public static String formPassToDBPass(String formPass, String salt) {
    String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
    return md5(str);
}
```

## 使用说明

### 登录流程

1. 访问登录页面：http://localhost:8080/login/toLogin
2. 输入手机号码和密码
3. 前端自动进行MD5加盐加密
4. 点击"登录"按钮
5. 登录成功后跳转到商品列表页面

### 加密测试

访问 http://localhost:8080/test/encrypt 可以：
- 测试MD5加密功能
- 验证前端加盐加密
- 测试完整的双重加密流程
- 验证预置测试数据

### 密码加密机制

系统采用MD5双重加密：
1. **前端加密**：MD5(明文密码 + 固定盐值)
2. **后端加密**：MD5(前端结果 + 用户随机盐值)

**示例：**
```
原密码: 123456
固定盐值: 1a2b3c4d
用户盐值: 1a2b3c4d

第一次加密: MD5("1" + "2" + "123456" + "c" + "d") = d3b1294a61a07da9b49b6e22b2cbd7f9
第二次加密: MD5("1" + "2" + "d3b1294a61a07da9b49b6e22b2cbd7f9" + "c" + "d") = b7797cce01b4b131b433b6acf4add449
```

### 会话管理

- 登录成功后，用户信息存储在HttpSession中
- 支持退出登录功能
- 页面显示当前登录用户信息

## 项目结构

```
src/
├── main/
│   ├── java/com/seckilldemo/
│   │   ├── controller/          # 控制器层
│   │   │   ├── LoginController.java    # 登录控制器
│   │   │   ├── GoodsController.java    # 商品控制器
│   │   │   ├── TestController.java     # 测试控制器
│   │   │   └── ...
│   │   ├── service/             # 服务层
│   │   │   ├── UserService.java
│   │   │   └── impl/UserServiceImpl.java
│   │   ├── mapper/              # 数据访问层
│   │   │   └── UserMapper.java
│   │   ├── pojo/                # 实体类
│   │   │   └── User.java
│   │   └── utils/               # 工具类
│   │       └── MD5Util.java
│   └── resources/
│       ├── templates/           # 页面模板
│       │   ├── login.html       # 登录页面（支持前端加密）
│       │   ├── goods_list.html  # 商品列表页面
│       │   └── encrypt_test.html # 加密测试页面
│       ├── sql/                 # SQL脚本
│       │   └── init.sql         # 数据库初始化脚本
│       └── application.yml      # 应用配置
```

## 接口说明

### 登录相关接口

- `GET /login/toLogin` - 跳转到登录页面
- `POST /login/doLogin` - 处理登录请求
  - 支持参数：`mobile`, `password`（明文，兼容模式）
  - 支持参数：`mobile`, `encryptedPassword`（加密，推荐模式）
- `GET /login/logout` - 退出登录

### 商品相关接口

- `GET /goods/toList` - 跳转到商品列表页面

### 测试相关接口

- `GET /test/encrypt` - 跳转到加密测试页面

### 健康检查接口

- `GET /health/check` - 健康检查

## 开发计划

- [ ] 商品管理功能
- [ ] 库存管理
- [ ] 订单系统
- [ ] 秒杀核心功能
- [ ] Redis缓存
- [ ] 消息队列
- [ ] 高并发优化
- [ ] HTTPS支持
- [ ] 动态盐值

## 注意事项

1. 请确保MySQL数据库已启动并可连接
2. 请根据实际情况修改数据库连接配置
3. 登录界面支持响应式设计，适配移动端
4. 密码输入错误会显示相应提示信息
5. 前端加密需要网络连接以加载MD5库
6. 建议在生产环境中使用HTTPS

## 安全建议

1. **使用HTTPS**：在生产环境中启用HTTPS传输
2. **动态盐值**：考虑使用时间戳或随机数生成动态盐值
3. **密码策略**：实施强密码策略
4. **登录限制**：添加登录失败次数限制
5. **会话管理**：设置合理的会话超时时间

## 测试指南

### 前端加密测试

1. 访问：http://localhost:8080/test/encrypt
2. 测试各种加密功能
3. 验证加密结果是否正确

### 登录功能测试

1. 使用测试账号登录
2. 检查浏览器开发者工具，确认密码已加密传输
3. 查看后端日志，确认加密方式识别正确

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目！

## 许可证

MIT License 