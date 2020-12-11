# 项目配置
项目配置

当安装好Spring Boot开发环境、Vue开发环境和小程序开发环境以后，启动相应的模块，已经可以看到一些数据或者效果。
但是还有一些特性或者功能没有启动，这是因为需要开发者进行配置才能正确启用。

**项目配置结构**

管理后台后端和小商城后端，即多个Spring Boot模块，配置文件是每个模块的`eye-xx/src/main/java/resources`的
`application.yml`和`application-xx.yml`配置文件。这里会发现每个模块都会有两个配置文件，但是实际上当前模块的配置信息
都是在`application-xx.yml`文件中，而`application.yml`文件仅仅用于引入其他模块的配置文件。

例如eye-all模块的`application.yml`的内容是
```
spring:
  profiles:
    active: db, core, admin, common, storage, express, mail, sms
  messages:
    encoding: UTF-8
```
 因此启动eye-all模块时，程序首先加载eye-all的`application.yml`，然后通过`spring.profiles.active`信息
 再次依次加载`application-db.yml`,`application-core.yml`,`application-admin.yml`和`application-common.yml`,`application-express.yml`,`application-mail.yml`,`application-sms.yml`,`application-storage.yml`八个配置文件。

这里后端服务模块的配置如下所示。

#### 日志配置

如果开发者启动eye-all模块，则需要配置该模块的`logback-spring.xml`文件
```
    <logger name="org.mybatis" level="ERROR" />
    <logger name="org.springframework" level="ERROR" />
    <logger name="com.eye.core" level="ERROR" />
    <logger name="com.eye.db" level="DEBUG" />
    <logger name="com.eye.admin" level="DEBUG" />
    <logger name="com.eye.brand" level="DEBUG" />
    <logger name="com.eye.common" level="DEBUG" />
    <logger name="com.eye.express" level="DEBUG" />
    <logger name="com.eye.mail.notify" level="DEBUG" />
    <logger name="com.eye.search" level="DEBUG" />
    <logger name="com.eye.sms.notiry" level="DEBUG" />
    <logger name="com.eye.sms.storage" level="DEBUG" />
    <logger name="com.eye.tool" level="DEBUG" />
    <logger name="com.eye" level="DEBUG" />
```

具体如何配置，请自行学习Spring Boot的日志配置和logback日志配置。

`com.eye.core`定义eye-core模块的日志级别
`com.eye.db`定义eye-db模块的日志级别
`com.eye.common`定义eye-common-api模块的日志级别
`com.eye.admin`定义eye-admin-api模块的日志级别
`com.eye.brand`定义eye-common-brand模块的日志级别
`com.eye.express`定义eye-express模块的日志级别
`com.eye.mail.notify`定义eye-mail模块的日志级别
`com.eye.search`定义eye-search模块的日志级别
`com.eye.sms.notiry`定义eye-sms模块的日志级别
`com.eye.sms.storage`定义eye-storage模块的日志级别
`com.eye.tool`定义eye-tool-api模块的日志级别
`com.eye`而定义eye所有后端模块的日志级别

当然，如果开发者这里启动eye后端模块级别是DEBUG时，可能会发现并没有很多日志，
这是因为代码内部没有写很多日志，开发者可以根据需要添加。

注意：
> 如果开发者独立启动eye-common-api模块，那么则需要配置eye-common-api模块的
> 日志配置方式。

#### 数据库连接配置

在eye-db模块的`application-db.yml`文件中配置数据库连接和druid：

```

spring:
  datasource:
    druid:
      url:  jdbc:mysql://localhost:3306/eye?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false
      driver-class-name:  com.mysql.cj.jdbc.Driver
      username:  root
      password:  mysql#0122
      initial-size:  10
      max-active:  50
      min-idle:  10
      max-wait:  60000
      pool-prepared-statements:  true
      max-pool-prepared-statement-per-connection-size:  20
      validation-query:  SELECT 1 FROM DUAL
      test-on-borrow:  false
      test-on-return:  false
      test-while-idle:  true
      time-between-eviction-runs-millis:  60000
      filters:  stat,wall
```

#### 微信登录配置

微信登录需要配置地方，

后端eye-core模块的`application-core.yml`文件：

```bash
eye:
  wx:
    app-id: wxa5b486c6b918ecfb
    app-secret: e04004829d4c383b4db7769d88dfbca1
```

这里的`app-id`和`app-secret`需要开发者在[微信公众平台](https://mp.weixin.qq.com/)注册获取。

注意
> 这里开发者可能会疑惑：小商场后端应该配置在eye-common-api模块的`application-common.yml`文件更合适。
> 这里放置在`application-core.yml`文件中是因为eye-core模块也依赖小程序appid配置信息。

####  微信支付配置

在eye-core模块的`application-core.yml`文件中配置微信支付：
```
eye:
  wx:
    mch-id: 111111
    mch-key: xxxxxx
    notify-url: https://wwwapidev.6eye9.com/common/order/pay-notify
```

这里的`mch-id`和`mch-key`需要开发者在[微信商户平台](https://pay.weixin.qq.com/)注册获取。

而这里的notify-url则应该是项目上线以后微信支付回调地址，当微信支付成功或者失败， 微信商户平台将向回调地址发生成功或者失败的数据，因此需要确保该地址是 litemall-common-api模块的CommonOrderController类的payNotify方法所服务的API地址。
而此项目的notify-rul注释掉了，是因为项目有普通用户购买回调和会员回调，后期会都配置到eye-core.yml。中

开发阶段可以采用一些技术实现临时外网地址映射本地，开发者可以百度关键字“微信 内网穿透”自行学习。

#### 邮件通知配置

在eye-mail模块的`application-mail.yml`文件中配置邮件通知服务：
```
eye:
  notify:
    mail:
      # 邮件通知配置,邮箱一般用于接收业务通知例如收到新的订单，sendto 定义邮件接收者，通常为商城运营人员
      enable: false
      host: smtp.exmail.qq.com
      username: ex@ex.com.cn
      password: XXXXXXXXXXXXX
      sendfrom: ex@ex.com.cn
      sendto: ex@qq.com
```

配置方式：
1. 邮件服务器开启smtp服务
2. 开发者在配置文件中设置`enable`的值`true`，然后其他信息设置相应的值。
这里只测试过QQ邮箱，开发者需要自行测试其他邮箱。

应用场景：
目前邮件通知场景也很简单，就是用户下单以后系统会自动向`sendto`用户发送一封邮件，告知用户下单的订单信息。
以后可能需要继续优化扩展。当然，如果不需要邮件通知订单信息，可以默认关闭即可。

验证配置成功：
当配置好信息以后，开发者可以运行eye-core模块的`MailTest`测试类，
独立发送邮件，然后登录邮箱查看邮件是否成功接收。

####  短信通知配置

在eye-sms模块的`application-sms.yml`文件中配置短信通知服务：
```
eye:
  notify:
    # 短消息模版通知配置
    # 短信息用于通知客户，例如发货短信通知，注意配置格式；template-name，template-templateId 请参考 NotifyType 枚举值
    sms:
      enable: false
      # 如果是腾讯云短信，则设置active的值tencent
      # 如果是阿里云短信，则设置active的值aliyun
      active: tencent
      sign: eye
      template:
        - name: paySucceed
          templateId: 156349
        - name: captcha
          templateId: 156433
        - name: ship
          templateId: 158002
        - name: refund
          templateId: 159447
      tencent:
        appid: 111111111
        appkey: xxxxxxxxxxxxxx
      aliyun:
        regionId: xxx
        accessKeyId: xxx
        accessKeySecret: xxx
```

配置方式：
1. 腾讯云短信平台或者阿里云短信平台申请，然后设置四个场景的短信模板；
2. 开发者在配置文件设置`enable`的值`true`，设置`active`的值`tencent`或`aliyun`
3. 然后配置其他信息，例如腾讯云短信平台申请的appid等值。
这里只测试过腾讯云短信平台和阿里云短信平台，开发者需要自行测试其他短信云平台。

应用场景：
目前短信通知场景只支持支付成功、验证码、订单发送、退款成功四种情况。
以后可能需要继续优化扩展。

短信模板参数命名：
这里存在一个问题，即腾讯云短信的官方平台中申请短信模板格式的模板参数是数组，
例如“你好，验证码是{0}，时间是{1}"; 
而阿里云短信的官方平台中申请短信模板的模板参数是JSON,
例如“你好，验证码是{param1}，时间是{param2}"。
为了保持当前代码的通用性，本项目采用数组传递参数，而对阿里云申请模板的参数做了一定的假设：
1. 腾讯云模块参数，申请模板时按照官方设置即可，例如“你好，验证码是{0}，时间是{1}"; 
2. 阿里云模板参数，本项目假定开发者在官方申请的参数格式应该采用"{ code: xxx, code1: xxx, code2: xxx }"，
例如“你好，验证码是{code}，时间是{code1}"。开发者可以查看`AliyunSmsSender`类的`sendWithTemplate`方法的
源代码即可理解。如果觉得不合理，可以自行调整相关代码。

####  物流配置

物流配置是查询商品物流信息，这里主要是基于[第三方快递鸟服务](http://www.kdniao.com/api-track)。

在eye-express模块的`application-express.yml`文件中配置快递鸟物流服务：
```
eye:
  notify:
  # 快鸟物流查询配置
  express:
    enable: false
    appId: "XXXXXXXXX"
    appKey: "XXXXXXXXXXXXXXXXXXXXXXXXX"
    vendors:
    - code: "ZTO"
      name: "中通快递"
    - code: "YTO"
      name: "圆通速递"
    - code: "YD"
      name: "韵达速递"
    - code: "YZPY"
      name: "邮政快递包裹"
    - code: "EMS"
      name: "EMS"
    - code: "DBL"
      name: "德邦快递"
    - code: "FAST"
      name: "快捷快递"
    - code: "ZJS"
      name: "宅急送"
    - code: "TNT"
      name: "TNT快递"
    - code: "UPS"
      name: "UPS"
    - code: "DHL"
      name: "DHL"
    - code: "FEDEX"
      name: "FEDEX联邦(国内件)"
    - code: "FEDEX_GJ"
      name: "FEDEX联邦(国际件)"
```

配置方式：
1. [快递鸟平台](http://www.kdniao.com/)申请；
2. 开发者在配置文件设置`enable`的值`true`，然后其他信息设置
快递鸟平台中的appId和appKey。

应用场景：
小商场查询订单详情时，如果商品已发货，小商城后端会返回详细物流信息。

注意：
> 一部分快递公司（例如顺丰速运、申通快递等）的轨迹查询在开发环境下不支持，
> 具体支持情况或者使用限制请阅读[官方资料](http://www.kdniao.com/UserCenter/v2/UserHome.aspx)

####  对象存储配置

对象存储，即存储和下载文件。

在eye-storage模块的`application-storage.yml`文件中配置对象存储服务：

* 本地对象存储配置
如果开发者采用当前服务器保存上传的文件，则需要配置：
```
eye:
  storage:
    # 当前工作的对象存储模式，分别是local、aliyun、tencent、qiniu
    active: local
    # 本地对象存储配置信息
    local:
      storagePath: storage
      # 这个地方应该是common模块的CommonStorageController的fetch方法对应的地址
      address: http://localhost:8080/common/storage/fetch/
```

配置方式：
配置文件设置`active`的值`local`，表示当前对象存储模式是本地对象存储；
而`storagePath`是上传文件保存的路径；`address`则是访问文件的对外路径。

* 阿里云对象存储配置

```
eye:
  storage:
    # 当前工作的对象存储模式，分别是local、aliyun、tencent、qiniu
    active: aliyun
    aliyun:
      endpoint: oss-cn-shenzhen.aliyuncs.com
      accessKeyId: 111111
      accessKeySecret: xxxxxx
      bucketName: eye
```

配置方式：
1. 阿里云官网注册
2. 配置文件设置`active`的值`aliyun`，表示当前对象存储模式是阿里云对象存储；

* 腾讯云对象存储配置

```
eye:
  storage:
    # 当前工作的对象存储模式，分别是local、aliyun、tencent、qiniu
    active: tencent
    # 腾讯对象存储配置信息
    # 请参考 https://cloud.tencent.com/document/product/436/6249
    tencent:
      secretId: 111111
      secretKey: xxxxxx
      region: xxxxxx
      bucketName: eye
```

配置方式：
1. 腾讯云官网注册
2. 配置文件设置`active`的值`tencent`，表示当前对象存储模式是腾讯云对象存储；

* 七牛云对象存储配置

```
eye:
  storage:
    # 当前工作的对象存储模式，分别是local、aliyun、tencent、qiniu
    active: qiniu
    # 七牛云对象存储配置信息
    qiniu:
      endpoint: http://pd5cb6ulu.bkt.clouddn.com
      accessKey: 111111
      secretKey: xxxxxx
      bucketName: eye
```

配置方式：
1. 七牛云官网注册
2. 配置文件设置`active`的值`qiniu`，表示当前对象存储模式是七牛云对象存储；

####  其他配置

除上述配置信息，本项目还存在其他配置。
目前仅采用默认值即可，开发者可以自己实践或者扩展新的配置信息。

