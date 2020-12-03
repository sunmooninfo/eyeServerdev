# 前后端接口文档

本项目前后端接口规范和接口文档。

注意：
> 1. 以下API部分基于nideshop开源项目的API设计；
> 2. 以下API是参考API，可能不是很合理，欢迎开发者交流。
> 3. 接口文档处于开发中，如果发现接口描述和接口实际不对应，欢迎PR或者报告。

## 1 前后端接口规范

### 1.1 请求格式

这里没有采用RESTful风格的接口，而是定义具体语义的接口。
目前只使用`GET`和`POST`来表示请求内容和更新内容两种语义。

#### 1.1.1 GET请求

    GET API_URL?params

例如
    
    GET /home/index

或者
    
    GET /goods/list?page=1&limit=10

#### 1.1.2 POST更新
    
    POST API_URL
    {
        body
    }

例如
    
    POST /cart/clear

或者    
    
    POST /goods/star
    {
        id: 1
    }

#### 1.1.3 分页请求参数

当GET请求后端获取数组数据时，需要传递分页参数。

例如

    GET /goods/list?page=1&limit=10&sort=add_time&order=desc
    
本项目的通用分页请求参数统一传递四个：

    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序

* page, 和通常计算机概念中数组下标从0开始不同，这里的page参数应该从1开始，1即代表第一页数据;
* limit, 分页大小；
* sort, 例如"add_time"或者"id";
* order, 只能是"desc"或者"asc"。

此外，这里四个参数是可选的，后端应该设置默认参数，因此即使前端不设置，
后端也会自动返回合适的对象数组响应数据。

注意:
> 这里的参数是需要后端支持的，在一些场景下，例如数组对象是组装而成，
> 有可能sort和order不支持。

讨论：
> 有些请求后端是所有数据，这里page和limit可能设置是无意义的。但是
> 仍然建议加上两个参数，例如page=1, limit=1000。

也就是说，请求后端数组数据时，同一传递四个分页参数，可能是比较良好的做法。

### 1.2 响应格式

    Content-Type: application/json;charset=UTF-8
    
    {
        body
    }
    

而body是存在一定格式的json内容：
    
    {
        errno: xxx,
        errmsg: xxx,
        data: {}
    }

#### 1.2.1 失败异常

    {
        errno: xxx,
        errmsg: xxx
    }

* errno是错误码，具体语义见1.3节。
* errmsg是错误信息。
    
#### 1.2.2 操作成功

    {
        errno: 0,
        errmsg: "成功",
    }

#### 1.2.3 普通对象

    {
        errno: 0,
        errmsg: "成功",
        data: {}
    }
    
#### 1.2.4 数组对象

    {
        errno: 0,
        errmsg: "成功",
        data: {
            list: [],
            total: XX，
            page: XX,
            limit: XX,
            pages: XX
        }
    }

list是对象数组，total是总的数量。

### 1.3 错误码

#### 1.3.1 系统通用错误码

系统通用错误码包括4XX和5XX

* 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
  * 401，参数错误，即前端没有传递后端需要的参数；
  * 402，参数值错误，即前端传递的参数值不符合后端接收范围。
  
* 5xx，后端系统错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端系统错误码：
  * 501，验证失败，即后端要求用户登录；
  * 502，系统内部错误，即没有合适命名的后端内部错误；
  * 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
  * 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
  * 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。

#### 1.3.2 商场业务错误码

* AUTH_INVALID_ACCOUNT = 700
* AUTH_CAPTCHA_UNSUPPORT = 701
* AUTH_CAPTCHA_FREQUENCY = 702
* AUTH_CAPTCHA_UNMATCH = 703
* AUTH_NAME_REGISTERED = 704
* AUTH_MOBILE_REGISTERED = 705
* AUTH_MOBILE_UNREGISTERED = 706
* AUTH_INVALID_MOBILE = 707
* AUTH_OPENID_UNACCESS = 708
* AUTH_OPENID_BINDED = 709
* GOODS_UNSHELVE = 710
* GOODS_NO_STOCK = 711
* GOODS_UNKNOWN = 712
* GOODS_INVALID = 713
* ORDER_UNKNOWN = 720
* ORDER_INVALID = 721
* ORDER_CHECKOUT_FAIL = 722
* ORDER_CANCEL_FAIL = 723
* ORDER_PAY_FAIL = 724
* ORDER_INVALID_OPERATION = 725
* ORDER_COMMENTED = 726
* ORDER_COMMENT_EXPIRED = 727
* GROUPON_EXPIRED = 730
* COUPON_EXCEED_LIMIT = 740
* COUPON_RECEIVE_FAIL= 741
* COUPON_CODE_INVALID= 742
    
#### 1.3.3 管理后台业务错误码

* ADMIN_INVALID_NAME = 601
* ADMIN_INVALID_PASSWORD = 602
* ADMIN_NAME_EXIST = 602
* ADMIN_ALTER_NOT_ALLOWED = 603
* ADMIN_DELETE_NOT_ALLOWED = 604
* ADMIN_INVALID_ACCOUNT = 605
* GOODS_UPDATE_NOT_ALLOWED = 610
* GOODS_NAME_EXIST = 611
* ORDER_CONFIRM_NOT_ALLOWED = 620
* ORDER_REFUND_FAILED = 621
* ORDER_REPLY_EXIST = 622
* USER_INVALID_NAME = 630
* USER_INVALID_PASSWORD = 631
* USER_INVALID_MOBILE = 632
* USER_NAME_EXIST = 633
* USER_MOBILE_EXIST = 634
* ROLE_NAME_EXIST = 640
* ROLE_SUPER_SUPERMISSION = 641
* ROLE_USER_EXIST = 642

### 1.4 Token

前后端采用token来验证访问权限。

#### 1.4.1 Header&Token

前后端Token交换流程如下：

1. 前端访问商场登录API或者管理后台登录API;

2. 成功以后，前端会接收后端响应的一个token，保存在本地；
    
3. 请求受保护API则，则采用自定义头部携带此token

4. 后端检验Token，成功则返回受保护的数据。

#### 1.4.2 商场自定义Header

访问受保护商场API采用自定义`X-eye-Token`头部

1. 小商城（或轻商场）前端访问小商城后端登录API`/common/auth/login`

        POST /common/auth/login
    
        {
            "username": "user123",
            "password": "user123"
        }

2. 成功以后，前端会接收后端响应的一个token，

        {
          "errno": 0,
          "data": {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0aGlzIGlzIGxpdGVtYWxsIHRva2VuIiwiYXVkIjoiTUlOSUFQUCIsImlzcyI6IkxJVEVNQUxMIiwiZXhwIjoxNTU3MzI2ODUwLCJ1c2VySWQiOjEsImlhdCI6MTU1NzMxOTY1MH0.XP0TuhupV_ttQsCr1KTaPZVlTbVzVOcnq_K0kXdbri0"
          },
          "errmsg": "成功"
        }    
    
3. 请求受保护API则，则采用自定义头部携带此token

        GET http://localhost:8080/common/address/list
        X-eye-Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0aGlzIGlzIGxpdGVtYWxsIHRva2VuIiwiYXVkIjoiTUlOSUFQUCIsImlzcyI6IkxJVEVNQUxMIiwiZXhwIjoxNTU3MzM2ODU0LCJ1c2VySWQiOjIsImlhdCI6MTU1NzMyOTY1NH0.JY1-cqOnmi-CVjFohZMqK2iAdAH4O6CKj0Cqd5tMF3M

#### 1.4.3 管理后台自定义Header

访问受保护管理后台API则是自定义`X-eye-Admin-Token`头部。

1. 管理后台前端访问管理后台后端登录API`/admin/auth/login`

        POST /admin/auth/login
    
        {
            "username": "admin123",
            "password": "admin123"
        }

2. 成功以后，管理后台前端会接收后端响应的一个token，

        {
            "errno": 0,
            "data": {
                "adminInfo": {
                    "nickName": "admin123",
                    "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
                },
                "token": "f2dbcae8-6e25-4f8e-bc58-aa81d512c952"
            },
            "errmsg": "成功"
        }
    
3. 请求受保护API时，则采用自定义头部携带此token

        GET http://localhost:8080/common/address/list
        X-eye-Admin-Token: f2dbcae8-6e25-4f8e-bc58-aa81d512c952

### 1.5 版本控制

API应该存在版本控制，以保证兼容性。

由于仍处于开发中，因此目前未引入版本控制。

### 1.6 API格式

这里定义一个API的格式：

应用场景

    xxx
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx

### 1.7 API预览

接下来会分别从用户层面和管理员层面构建商场API服务和管理后台API服务。

商场API服务涉及

* 安全服务
* 首页服务
* 类目服务
* 商品服务
* 购物车服务
* 订单服务
* 会员服务
* 收货地址服务
* 品牌商服务
* 收藏服务
* 评论服务
* 优惠券服务
* 反馈服务
* 足迹服务
* 团购服务
* 帮助服务
* 搜索服务
* 专题服务
* 对象存储服务


管理后台API服务涉及:
* 略

### 1.8 API测试

本节以GET、POST两种方式以及是否需要登录举例说明如何测试和使用本项目API。

开发者可以使用各种API测试命令或者工具，这里以Postman作为工具。

#### 1.8.1 GET 示例

如果一个API是GET方法，那么请求参数需要在访问链接后面：

例如测试2.4.2节商品详情API

![](./pics/admin/get.png)

#### 1.8.2 GET & Token 示例

如果需要登录才能访问数据，则需要先向后端请求登录，得到token，然后请求时携带token。

例如测试2.8.1节收货地址列表API

如果没有登录，则返回未登录信息

![](./pics/admin/get_no_token.png)

因此测试这些API，需要先登录

![](./pics/admin/login.png)

然后，采用自定义`X-eye-Token`来携带token访问商场API

![](./pics/admin/get_with_token.png)

注意：
> 访问受保护商场API是采用自定义`X-eye-Token`头部；
> 而访问受保护管理后台API则是自定义`X-eye-Admin-Token`头部。

#### 1.8.3 POST 示例

通常POST请求后端时，都需要先登录才能有权限上传数据，因此这里不举例说明。

#### 1.8.4 POST & Token 示例

如果需要登录才能提交数据，则需要先向后端请求登录，得到token，然后请求时携带token。

![](./pics/admin/post_no_token.png)

因此测试这些API，需要先登录

![](./pics/admin/login.png)

然后，采用自定义`X-eye-Token`来携带token访问商场API

![](./pics/admin/get_with_token.png)

注意：
> 访问受保护商场API是采用自定义`X-eye-Token`头部；
> 而访问受保护管理后台API则是自定义`X-eye-Admin-Token`头部。

### 1.9 API保护

为了保护API不被滥用，通常API需要引入保护机制，例如OAuth2。

本项目暂时无保护机制，因此实际上一旦开发者知道服务器，就很容易访问API。

### 1.10 API局限性

当前API还存在一些问题，后面需要继续优化和完善。

* 无意义的通用字段

* 团购API完善

### 1.11 Not Like Swagger

本项目不是很接受Swagger，基于以下考虑：

* 前后端中立

在前后端分离项目中，依赖后端的Swagger来生成项目API似乎不是很理想，
这实际上把项目API设计工作过多地压在后端，同时前端也被迫依赖后端，
因为后端如果没有写好文档注解，前端不可能了解API的输入输出。

可能一种合理的做法应该这样：
项目初期前后端一起完成一个完整基本的API文档，定义好交互规范和具体API的行为，然后双方同时开始开发工作；
某个开发阶段，前端需要更多的数据或者新的API支持，此时也不需要立即联系后端（除非API产生破坏性变更），
而是暂时基于mock和自定义mock数据独立开发；之后，在合适阶段（可以按照项目规定，例如三天或者周五），
前后端再次沟通API的变更，后端了解需求后则可以接受、拒绝或者调整，当然变更必须要在API文档中体现和更新；
下一个开发阶段，前端和后端能够再次基于最新的API文档来调整自己代码。
最后项目测试时，只要前端对照API文档，后端也是对照API文档。

* 后端代码简洁

如果使用Swagger，为了得到完整的文档，需要在每一个方法前面加上多个文档注解，文档越是详尽，则注解越多，
造成代码不是很简洁。特别是具备代码属性的注解和Swagger文档注解混杂在一起，可能不是很好。

当然，本项目也简单地配置了Swagger(见`WxSwagger2Configuration`和`AdminSwagger2Configuration`)，
* 在线Swagger文档链接：http://122.51.199.160:8080/swagger-ui.html
* 本地Swagger文档链接：http://localhost:8080/swagger-ui.html

此外，也使用了swagger-bootstrap-ui对Swagger进一步增强了使用效果。
* 在线swagger-bootstrap-ui文档链接：http://122.51.199.160:8080/doc.html
* 本地swagger-bootstrap-ui文档链接：http://localhost:8080/doc.html

当然正如上文讨论，本项目不是很接受Swagger的理念，所以后端没有使用Swagger的相关文档注解，
这也导致了Swagger接口文档的不具可读性。如果开发者需要，可以自行在后端补充Swagger注解。

需要注意的是：
> 这里接口默认是公开的，因此项目一旦需要上线，请及时删除swagger和swagger-bootstrap-ui依赖和配置，
> 或者采取其他手段，防止接口对外暴露造成**安全隐患**。

例如
```
swagger:
  production: false
```

## 2 商城API服务

### 2.1 安全服务

#### 2.1.1 小程序微信登录

应用场景

    小程序环境下微信登录。
      
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.2 账号登录

应用场景

    基于用户名和密码的账号登录
    
接口链接

    POST /common/auth/login
    
请求参数

    {
        "username": "user123",
        "password": "user123"
    }    
    
响应内容

    {
      "errno": 0,
      "data": {
        "userInfo": {
          "nickName": "user123",
          "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        },
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0aGlzIGlzIGxpdGVtYWxsIHRva2VuIiwiYXVkIjoiTUlOSUFQUCIsImlzcyI6IkxJVEVNQUxMIiwiZXhwIjoxNTU3MzI2ODUwLCJ1c2VySWQiOjEsImlhdCI6MTU1NzMxOTY1MH0.XP0TuhupV_ttQsCr1KTaPZVlTbVzVOcnq_K0kXdbri0"
      },
      "errmsg": "成功"
    }   
    
错误码

    略
    
#### 2.1.3 注册

应用场景

    xxx
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.4 退出

应用场景

    账号退出
    
接口链接

    POST /common/auth/logout
    
请求参数

    {
        "username": "user123",
        "password": "user123"
    }    
    
响应内容

    {
        "errno": 0,
        "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.1.5 注册验证码

应用场景

    用户未登录情况下，请求后端发送注册验证码用于注册。
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.6 操作验证码

应用场景

    用户已登录情况下，请求后端发送操作验证码用于相关操作。
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.7 账号密码修改

应用场景

    账号密码修改
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.8 微信手机号码绑定

应用场景

    微信手机号码绑定，仅用于小程序环境。
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.9 手机号码修改

应用场景

    手机号码修改
    
接口链接

    xxx
    
请求参数

    xxx
    
响应内容

    xxx
    
错误码

    xxx
    
#### 2.1.10 账号信息

应用场景

    账号信息
    
接口链接

    GET /common/auth/info
    
请求参数

    无
    
响应内容

    {
        "errno": 0,
        "data": {
            "gender": 1,
            "nickName": "user123",
            "mobile": "",
            "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        },
        "errmsg": "成功"
    }   
    
错误码

    略
    
#### 2.1.11 账号信息更新

应用场景

    账号信息更新。
    
接口链接

    POST /common/auth/profile
    
请求参数

    {
        "gender": 1,
        "nickName": "user123",
        "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
    }
    
响应内容

    {
        "errno": 0,
        "errmsg": "成功"
    }
    
错误码

    略
    
### 2.2 首页服务

#### 2.2.1 首页数据

应用场景

    首页数据
    
接口链接

    GET /common/home/index
    
请求参数

    无
    
响应内容

    {
    "errno": 0,
    "data": {
        "killGoodsList": [],
        "newGoodsList": [
            {
                "id": 1811344,
                "name": "常用200字精讲录播【粤语CE培训  10节课】",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/3rmrow304eie0ty4x2tp.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 38,
                "retailPrice": 8.9
            },
            {
                "id": 1811343,
                "name": "商务剧场49节课录播【粤语CE培训  49节课】",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/k0djk8drisb2eoyigz4m.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 38,
                "retailPrice": 6.9
            },
            {
                "id": 1811342,
                "name": "全程粤语教学录播【粤语CE培训  16节课】",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/0q5otwvzlfk7papotjo3.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 35,
                "retailPrice": 7.5
            },
            {
                "id": 1811341,
                "name": "粤语速成教程，日常用语学习、场景对话(10课时)",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/qgn19aow0nqzwlshjj5b.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 40,
                "retailPrice": 8.8
            },
            {
                "id": 1811340,
                "name": "经典老视频 学说广州话.(18集)",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/qsfxzi2jdq72bqan8b2t.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 39,
                "retailPrice": 8.5
            },
            {
                "id": 1811339,
                "name": "经典老视频 跟我学讲广东话.(28节课)",
                "brief": "",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pqfzs6fp6xdk1hex5xd8.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 41,
                "retailPrice": 7.5
            }
        ],
        "couponList": [],
        "channel": [
            {
                "id": 1036012,
                "name": "管理类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/zd2bp1y14ftpn9k0d7ic.png"
            },
            {
                "id": 1036013,
                "name": "市场类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/qs6xt8vdinesorq5huv4.png"
            },
            {
                "id": 1036014,
                "name": "经济类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/yy8k9qfvlj8wyotgjzp6.png"
            },
            {
                "id": 1036015,
                "name": "技术类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/b69wnli9f1wteib5q567.png"
            },
            {
                "id": 1036016,
                "name": "心理类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/7lg2vb433iijz2ev7icv.png"
            },
            {
                "id": 1036017,
                "name": "文史类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/yds2qzqrzdlhfyiab79a.png"
            },
            {
                "id": 1036018,
                "name": "兴趣类",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/7zb98256pkebwf8d36kq.png"
            },
            {
                "id": 1036019,
                "name": "鸦杀尽",
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/v74b14y4h6qtm57ouary.png"
            }
        ],
        "grouponList": [],
        "banner": [
            {
                "id": 1,
                "name": "智慧商城",
                "link": "/pages/details/details?id=1811098",
                "url": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pw2rtcw3wjiwnhy8n68q.jpg",
                "pcUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/orqmqoc9cliykou6c2oa.jpg",
                "appUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pw2rtcw3wjiwnhy8n68q.jpg",
                "position": 1,
                "content": "不一样的商城",
                "enabled": true,
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2020-10-15 14:14:17",
                "deleted": false
            },
            {
                "id": 2,
                "name": "浪博教育培训班",
                "link": "/pages/details/details?id=1811100",
                "url": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/bz41wo96sh5tmhjht08s.jpg",
                "pcUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/vdfcwpouavgsb6xwr7jz.jpg",
                "appUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/bz41wo96sh5tmhjht08s.jpg",
                "position": 1,
                "content": "浪博教育培训班",
                "enabled": true,
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2020-10-15 14:14:49",
                "deleted": false
            },
            {
                "id": 3,
                "name": "互联网教育课程推荐",
                "link": "/pages/details/details?id=1811099",
                "url": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/j4za26p9i3vghxgawe0u.jpg",
                "pcUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/utr45spqsndvyohua42w.jpg",
                "appUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/j4za26p9i3vghxgawe0u.jpg",
                "position": 1,
                "content": "互联网教育课程推荐",
                "enabled": true,
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2020-10-15 14:14:56",
                "deleted": false
            }
        ],
        "brandList": [
            {
                "id": 1001000,
                "name": "MUJI制造商",
                "desc": "严选精选了MUJI制造商和生产原料，\n用几乎零利润的价格，剔除品牌溢价，\n让用户享受原品牌的品质生活。",
                "picUrl": "http://yanxuan.nosdn.127.net/1541445967645114dd75f6b0edc4762d.png",
                "floorPrice": 12.9
            },
            {
                "id": 1001002,
                "name": "内野制造商",
                "desc": "严选从世界各地挑选毛巾，最终选择了为日本内野代工的工厂，追求毛巾的柔软度与功能性。品质比肩商场几百元的毛巾。",
                "picUrl": "http://yanxuan.nosdn.127.net/8ca3ce091504f8aa1fba3fdbb7a6e351.png",
                "floorPrice": 29
            },
            {
                "id": 1001003,
                "name": "Adidas制造商",
                "desc": "严选找到为Adidas等品牌制造商，\n选取优质原材料，与厂方一起设计，\n为你提供好的理想的运动装备。",
                "picUrl": "http://yanxuan.nosdn.127.net/335334d0deaff6dc3376334822ab3a2f.png",
                "floorPrice": 49
            },
            {
                "id": 1001007,
                "name": "优衣库制造商",
                "desc": "严选找到日本知名服装UNIQLO的制造商，\n选取优质长绒棉和精梳工艺，\n与厂方一起设计，为你提供理想的棉袜。",
                "picUrl": "http://yanxuan.nosdn.127.net/0d72832e37e7e3ea391b519abbbc95a3.png",
                "floorPrice": 29
            }
        ],
        "hotGoodsList": [
            {
                "id": 1181036,
                "name": "Java开发兴趣培养",
                "brief": "java试听课",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/fq03mlnvr00ggfv27586.jpg",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 0,
                "retailPrice": 0.01
            },
            {
                "id": 1181071,
                "name": "Python实战项目-坦克大战",
                "brief": "手把手教你完成坦克大战项目(视频+源码+资料)",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/qegajnk5xt5wrif28xa2.jpg",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": true,
                "counterPrice": 100,
                "retailPrice": 0.01
            },
            {
                "id": 1811156,
                "name": "Java核心技术 卷I 基础知识（原书第11版）",
                "brief": "Core Java 第11版，Jolt大奖获奖作品，针对Java SE9、10、11全面更新，与Java编程思想、Effective Java、深入理解Java虚拟机 堪称：Java四大名著",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/4t7ab4c3fg9hrv30bhrx.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 149,
                "retailPrice": 99
            },
            {
                "id": 1811159,
                "name": "Word/Excel/PPT办公应用从入门到精通",
                "brief": "办公应用三合一，专门为初学者量身定做。以图解办公实务案例为基础，深入浅出、直观形象地讲解了三大办公软件的基本操作以及繁琐命令快捷化的实用技巧。让你轻松实现高效办公，短时间内轻松成为商务办公能手。",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/o7scvzfsu19vl4f8tasz.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 55,
                "retailPrice": 20
            },
            {
                "id": 1811162,
                "name": "网店运营管理与营销推广",
                "brief": "淘宝微信营销推广策略 网店商品的管理与发布 移动营销的常用工具 互联网时代的营销体验",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/zj5c8xvsep4cysl3ik49.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 59,
                "retailPrice": 45
            },
            {
                "id": 1811165,
                "name": "UI设计其实很简单：Photoshop界面设计高手之道",
                "brief": "玩转移动UI界面设计原则，精通iOS、Android系统界面设计，掌握不同类型App应用设计，全彩印刷，配套丰富，告别菜鸟，走向设计高手之路！",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/eo3d16jeph99ceks8bl5.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 99,
                "retailPrice": 49
            }
        ],
        "isshownGoodsList": [
            {
                "id": 1181011,
                "name": "基于Java_swing的贪吃蛇的设计与实现(小型简单项目)",
                "brief": "java项目，可直接点击run.bat运行",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/h72s6fle98gw9yr4c8em.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 9,
                "retailPrice": 3
            },
            {
                "id": 1181012,
                "name": "基于Java_swing的俄罗斯方块的设计与实现(小型简单项目)",
                "brief": "俄罗斯方块项目，可直接点击run.bat运行",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/5agxgp5gdp1wd733449q.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 9,
                "retailPrice": 3
            },
            {
                "id": 1181036,
                "name": "Java开发兴趣培养",
                "brief": "java试听课",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/fq03mlnvr00ggfv27586.jpg",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 0,
                "retailPrice": 0.01
            },
            {
                "id": 1181062,
                "name": "Java开发从入门到精通",
                "brief": "java进阶",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/s6v6wlquyivtvx1xhyse.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 70,
                "retailPrice": 30
            }
        ],
        "topicList": [
            {
                "id": 316,
                "title": "使用python开发自己的游戏坦克大战",
                "subtitle": "让自己在娱乐中学习",
                "price": 0,
                "readCount": "152",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/2j1frde3g72tdq0dj7fy.jpg"
            },
            {
                "id": 1,
                "title": "Java开发兴趣培养",
                "subtitle": "兴趣培养",
                "price": 0.1,
                "readCount": "61",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/t2uee1vz2iar3mhwa8wg.jpg"
            }
        ],
        "floorGoodsList": [
            {
                "name": "管理类",
                "goodsList": [
                    {
                        "id": 1811273,
                        "name": "麦德好燕麦巧克力营养麦片巧克力棒牛奶燕麦片糖燕麦酥结婚喜糖果",
                        "brief": "巧克力",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/34v6a9p6d7wsqz376xmp.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 60,
                        "retailPrice": 12.5
                    },
                    {
                        "id": 1811255,
                        "name": "100支钢笔墨囊墨水胆纯蓝墨兰黑色小学生用换墨囊3.4mm通用可替换男女孩初学者儿童正姿练字用钢笔芯套装",
                        "brief": "3.4mm口径可配烂笔头/永生/白雪钢笔",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/tl9qzh1ssuedvvnbwq2d.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 9.75,
                        "retailPrice": 9.75
                    },
                    {
                        "id": 1181080,
                        "name": "常用办公软件学习之WPS2013",
                        "brief": "文档视频学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/el79yk2dn0d0uurjnlta.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 90,
                        "retailPrice": 45
                    },
                    {
                        "id": 1181079,
                        "name": "常用办公软件学习之office2016",
                        "brief": "办公软件学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/co3716ne0op1t6q0wym6.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 90,
                        "retailPrice": 45
                    }
                ],
                "id": 1036012
            },
            {
                "name": "市场类",
                "goodsList": [
                    {
                        "id": 1811286,
                        "name": "秋季经典帆布鞋男小白鞋休闲板鞋学生布鞋男低帮黑白色鞋子男潮鞋",
                        "brief": "运动鞋",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/u2j7702cijn9my4xko3u.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 20,
                        "retailPrice": 19.9
                    },
                    {
                        "id": 1811279,
                        "name": "夏季人字拖男士防滑夹脚轻便室外休闲沙滩凉拖鞋时尚韩版潮流外穿",
                        "brief": "没有中间商赚差价",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/dvrs9520heqo18ccnzmu.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 9.99,
                        "retailPrice": 9.99
                    },
                    {
                        "id": 1811269,
                        "name": "帆布鞋懒人鞋子休闲男鞋夏季中年老北京布鞋一脚蹬工作潮春季板鞋",
                        "brief": "",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/xg975m946lfxswspcwt3.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 153,
                        "retailPrice": 39
                    },
                    {
                        "id": 1811247,
                        "name": "小鹿妈妈舌苔清洁器除口臭硅胶刮舌器刮舌头板舌苔神器舌苔刷 2支",
                        "brief": "买一送一 共两支 A面刷 B面刮",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/devt8gtxnacltsdx4bxp.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 29.9,
                        "retailPrice": 9.9
                    }
                ],
                "id": 1036013
            },
            {
                "name": "经济类",
                "goodsList": [
                    {
                        "id": 1811280,
                        "name": "SENNHEISER/森海塞尔 cx275s手机运动耳机 入耳式重低音电脑耳麦",
                        "brief": "耳机",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ijlak7f67vjdhajubj0h.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 400,
                        "retailPrice": 399
                    },
                    {
                        "id": 1811275,
                        "name": "男士休闲运动套装潮牌卫衣两件套2020春秋季韩版潮流嘻哈帅气衣服",
                        "brief": "性价比超高 抢到就是赚到！",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/k1dm2cgiaqy92x2uqq34.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 120,
                        "retailPrice": 99
                    },
                    {
                        "id": 1811272,
                        "name": "套装男秋季初中生休闲潮流卫衣男士青少年一套衣服帅气运动春装潮",
                        "brief": "",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/g0o0rzizadrdxxoyjbfj.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": true,
                        "counterPrice": 249,
                        "retailPrice": 139
                    },
                    {
                        "id": 1811271,
                        "name": "宏源陈皮糖5斤散装糖果批发 酒店前台招待话梅糖办公室糖果小零食",
                        "brief": "陈皮糖",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/9qa0el6zdc8u3l9fz9m9.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 40,
                        "retailPrice": 18.8
                    }
                ],
                "id": 1036014
            },
            {
                "name": "技术类",
                "goodsList": [
                    {
                        "id": 1811321,
                        "name": "真人照片转手绘视频教学",
                        "brief": "视频学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pwl9sbbijdvv5typt5js.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 51,
                        "retailPrice": 14
                    },
                    {
                        "id": 1811320,
                        "name": "设计师接私单技巧教学",
                        "brief": "视频学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/9bp4i4pnc520szu94j3h.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 88,
                        "retailPrice": 15
                    },
                    {
                        "id": 1811319,
                        "name": "人物速写视频教学",
                        "brief": "视频学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/xhs0l2lxwhler27a5ca3.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 61,
                        "retailPrice": 9.9
                    },
                    {
                        "id": 1811318,
                        "name": "插画教学",
                        "brief": "视频学习",
                        "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ucgf45of1pm9demii1ov.jpg",
                        "isNew": true,
                        "isHot": false,
                        "isKill": false,
                        "isShown": false,
                        "counterPrice": 39,
                        "retailPrice": 14
                    }
                ],
                "id": 1036015
            }
        ]
    },
    "errmsg": "成功"
    }

错误码

    无
        
### 2.3 类目服务

### 2.4 商品服务

#### 2.4.1 商品列表

应用场景

    商品列表
    
接口链接

    GET /common/goods/list

请求参数
    
    isNew: 是否新品，true或者false
    isHot: 是否热卖商品，true或者false
    isKill: 是否秒杀商品，true或者false
    isShown: 是否在微信小程序上架商品，true或者false
    keyword: 关键字，如果设置则查询是否匹配关键字
    brandId: 品牌商ID，如果设置则查询品牌商所属商品
    categoryId: 商品分类ID，如果设置则查询分类所属商品
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
        
响应内容

     {
    "errno": 0,
    "data": {
        "total": 217,
        "pages": 22,
        "limit": 10,
        "page": 1,
        "list": [
            {
                "id": 1181071,
                "name": "Python实战项目-坦克大战",
                "brief": "手把手教你完成坦克大战项目(视频+源码+资料)",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/qegajnk5xt5wrif28xa2.jpg",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": true,
                "counterPrice": 100,
                "retailPrice": 0.01
            },
            {
                "id": 1181036,
                "name": "Java开发兴趣培养",
                "brief": "java试听课",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/fq03mlnvr00ggfv27586.jpg",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 0,
                "retailPrice": 0.01
            },
            {
                "id": 1811156,
                "name": "Java核心技术 卷I 基础知识（原书第11版）",
                "brief": "Core Java 第11版，Jolt大奖获奖作品，针对Java SE9、10、11全面更新，与Java编程思想、Effective Java、深入理解Java虚拟机 堪称：Java四大名著",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/4t7ab4c3fg9hrv30bhrx.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 149,
                "retailPrice": 99
            },
            {
                "id": 1811157,
                "name": "Python基础教程（第3版）",
                "brief": "Python3.5编程从入门到实践 Python入门佳作 机器学习 人工智能 数据处理 网络爬虫热门编程语言 累计销售20万册",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/sxqlz1ug4fqo0h2mpeb6.png",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 99,
                "retailPrice": 66
            },
            {
                "id": 1811158,
                "name": "Python学习手册（原书第5版）",
                "brief": "零基础学Python3，Python编程从入门到实践学习手册，详解数据分析，机器学习，网络爬虫的编程语言基础，完整覆盖Python核心技术，助你快速让入门并进行项目开发实战",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/5ld0qazp3jol20xz75do.png",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 219,
                "retailPrice": 150
            },
            {
                "id": 1811162,
                "name": "网店运营管理与营销推广",
                "brief": "淘宝微信营销推广策略 网店商品的管理与发布 移动营销的常用工具 互联网时代的营销体验",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/zj5c8xvsep4cysl3ik49.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 59,
                "retailPrice": 45
            },
            {
                "id": 1811154,
                "name": "Java从入门到精通（第5版）",
                "brief": "297个应用实例+37个典型应用+30小时教学视频+海量开发资源库，丛书累计销量200多万册",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/zhun96cfddg3fe0i98pa.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 40,
                "retailPrice": 30
            },
            {
                "id": 1811159,
                "name": "Word/Excel/PPT办公应用从入门到精通",
                "brief": "办公应用三合一，专门为初学者量身定做。以图解办公实务案例为基础，深入浅出、直观形象地讲解了三大办公软件的基本操作以及繁琐命令快捷化的实用技巧。让你轻松实现高效办公，短时间内轻松成为商务办公能手。",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/o7scvzfsu19vl4f8tasz.png",
                "isNew": true,
                "isHot": true,
                "isKill": false,
                "isShown": false,
                "counterPrice": 55,
                "retailPrice": 20
            },
            {
                "id": 1811160,
                "name": "微软办公软件国际认证MOS Office 2016七合一高分必看",
                "brief": "微软Office在线教育专家、MOS认证战略伙伴“答得喵”作品 随书附赠1DVD独密料：MOS 认证考试模拟系",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/wa8xtehddp9z4my6bs8q.png",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 129,
                "retailPrice": 60
            },
            {
                "id": 1811163,
                "name": "淘宝运营速成指南 电商军规 81 讲 鬼脚七 贾真 强强联合打造81条电商军规 电商运营 ",
                "brief": "前淘宝搜索负责人鬼脚七+知名实战天猫卖家贾真，网店规划+爆款技术+团队管理全覆盖，网上开店电商书籍新作！",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/34ml8nhs4z2h2rzblaj5.png",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 79,
                "retailPrice": 40
            }
        ],
        "filterCategoryList": [
            {
                "id": 1036020,
                "name": "Java",
                "keywords": "",
                "desc": "开启java开发者之路",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/7spg8yotwpj141fmz26t.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ie3ayiee757nb1pha2hh.png",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-09-08 16:25:11",
                "updateTime": "2020-10-13 09:12:45",
                "deleted": false
            },
            {
                "id": 1036021,
                "name": "Python",
                "keywords": "",
                "desc": "学会Python自动化，工作竟然如此简单。",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/1bufyxjwfffmokwkhsb2.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/mh4c2ddciomue81i1yi5.png",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-09-10 14:59:27",
                "updateTime": "2020-10-14 14:00:53",
                "deleted": false
            },
            {
                "id": 1036023,
                "name": "办公软件",
                "keywords": "",
                "desc": "常用办公软件学习",
                "pid": 1036012,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/1571nfpuev4z9yy2dxcz.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/n0s83js9jr6vs4tkf8ns.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-09-14 09:19:20",
                "updateTime": "2020-10-13 17:20:43",
                "deleted": false
            },
            {
                "id": 1036024,
                "name": "网店管理",
                "keywords": "",
                "desc": "网店管理学习",
                "pid": 1036012,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/iv877pogj61na64hjcl0.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/2ca13k9ehnt71apyqg60.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-09-14 09:20:07",
                "updateTime": "2020-10-13 17:20:57",
                "deleted": false
            },
            {
                "id": 1036029,
                "name": "UI",
                "keywords": "",
                "desc": "高端工程师深度讲解学习经验",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/8j4dwh7x5u03gvftqnic.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/0oqmyaw8o1zeeccx43on.jpg",
                "level": "L2",
                "sortOrder": 3,
                "addTime": "2020-09-25 13:58:44",
                "updateTime": "2020-10-14 14:11:45",
                "deleted": false
            },
            {
                "id": 1036034,
                "name": "C++",
                "keywords": "",
                "desc": "业界标准语言，编程之本。",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/hko3sncijvf5g3cnjea6.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/yv48m0nlnyny9x65mppz.jpg",
                "level": "L2",
                "sortOrder": 4,
                "addTime": "2020-10-14 08:48:42",
                "updateTime": "2020-10-14 14:14:12",
                "deleted": false
            },
            {
                "id": 1036035,
                "name": "PHP",
                "keywords": "",
                "desc": "少量的编程，就能建立一个交互的WEB站点。",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/kphz4l8mbq1wpuikndj8.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/564tv9h1p3ch9sgbxlz9.jpg",
                "level": "L2",
                "sortOrder": 5,
                "addTime": "2020-10-14 08:49:19",
                "updateTime": "2020-10-15 09:37:53",
                "deleted": false
            },
            {
                "id": 1036036,
                "name": "大数据",
                "keywords": "",
                "desc": "大数据分析，数据挖掘。",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/adncbc7o19266mgaprrw.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/bzzzijesuz5cnk9r9lix.jpg",
                "level": "L2",
                "sortOrder": 6,
                "addTime": "2020-10-14 08:49:59",
                "updateTime": "2020-10-14 15:05:32",
                "deleted": false
            },
            {
                "id": 1036037,
                "name": "前端技术",
                "keywords": "",
                "desc": "教你成为一个优秀的前端工程师",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/mz07psfbixsmkskdoygx.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/q35q4uac33yf7k3d5b5j.jpg",
                "level": "L2",
                "sortOrder": 7,
                "addTime": "2020-10-14 08:50:31",
                "updateTime": "2020-10-15 09:59:15",
                "deleted": false
            },
            {
                "id": 1036038,
                "name": "软件运维",
                "keywords": "",
                "desc": "学习信息安全运维服务",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ids8casyqjn55t8mc2u0.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/s5lk3i1uh3qwcjowj5az.jpg",
                "level": "L2",
                "sortOrder": 8,
                "addTime": "2020-10-14 08:50:54",
                "updateTime": "2020-10-14 16:58:25",
                "deleted": false
            },
            {
                "id": 1036039,
                "name": "软件开发",
                "keywords": "",
                "desc": "构建专业技能体系，体现技术优势。",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/dzkxyzn4n7al6rnyxpos.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/bubtcviu9ffbvl533z9i.jpg",
                "level": "L2",
                "sortOrder": 9,
                "addTime": "2020-10-14 08:52:53",
                "updateTime": "2020-10-15 09:10:38",
                "deleted": false
            },
            {
                "id": 1036040,
                "name": "艺术设计",
                "keywords": "",
                "desc": "将艺术的形式美感应用于日常生活紧密相关的设计中",
                "pid": 1036015,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/wqwq144zu9l2hpckay4b.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/debco11t4cv3s2021v1n.jpg",
                "level": "L2",
                "sortOrder": 10,
                "addTime": "2020-10-14 08:53:25",
                "updateTime": "2020-10-15 09:22:29",
                "deleted": false
            },
            {
                "id": 1036041,
                "name": "口才与演讲",
                "keywords": "",
                "desc": "实战语言组织能力，提升领导力质变。",
                "pid": 1036013,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/yqa50udfhrefywg7gr5o.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/cz4fovi2xymdxk2v0bmk.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:28:20",
                "updateTime": "2020-10-14 10:42:23",
                "deleted": false
            },
            {
                "id": 1036042,
                "name": "招投标",
                "keywords": "",
                "desc": "手把手教你招投标",
                "pid": 1036013,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/stvnwxfcdkh4ng8gxlcs.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ptycbek8ba0uyqagdm06.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-10-14 10:28:46",
                "updateTime": "2020-10-14 10:48:33",
                "deleted": false
            },
            {
                "id": 1036043,
                "name": "自媒体",
                "keywords": "",
                "desc": "网络时代人人都是自媒体",
                "pid": 1036013,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/90fr5p43volrstjwgubd.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/46tk6fnzfmoqwjjb4kw8.jpg",
                "level": "L2",
                "sortOrder": 3,
                "addTime": "2020-10-14 10:29:07",
                "updateTime": "2020-10-14 10:49:36",
                "deleted": false
            },
            {
                "id": 1036044,
                "name": "Matlab",
                "keywords": "",
                "desc": "学maltab要有耐心",
                "pid": 1036014,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/gbax096oblfrijwtxu1t.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/gu5w1kzyxx7opjjt2ru7.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:29:49",
                "updateTime": "2020-10-14 11:04:08",
                "deleted": false
            },
            {
                "id": 1036045,
                "name": "财务",
                "keywords": "",
                "desc": "财务技能实操的终身宝典",
                "pid": 1036014,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/i0d71kkn27s4d2rh5eyq.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/cqe5dmz1s2a2bawftogb.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-10-14 10:30:10",
                "updateTime": "2020-10-14 13:56:36",
                "deleted": false
            },
            {
                "id": 1036046,
                "name": "经济类书籍",
                "keywords": "",
                "desc": "各种经济类专业书籍",
                "pid": 1036014,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/l7yvn6onz5aix7kmvarc.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/tg2lp0ux79arye50vokr.jpg",
                "level": "L2",
                "sortOrder": 3,
                "addTime": "2020-10-14 10:30:31",
                "updateTime": "2020-10-14 13:57:30",
                "deleted": false
            },
            {
                "id": 1036047,
                "name": "考证",
                "keywords": "",
                "desc": "经济专业考证资料大全",
                "pid": 1036014,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/9bbs6tanle4oyxx10v6m.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/0s8izaatbci7fjvdte7y.jpg",
                "level": "L2",
                "sortOrder": 4,
                "addTime": "2020-10-14 10:30:51",
                "updateTime": "2020-10-14 13:58:10",
                "deleted": false
            },
            {
                "id": 1036048,
                "name": "心理学",
                "keywords": "",
                "desc": "",
                "pid": 1036016,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ym9eeh1lpsrwlheen9df.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/tmolrwfj6fqtbrnvis16.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:31:28",
                "updateTime": "2020-10-14 10:31:28",
                "deleted": false
            },
            {
                "id": 1036049,
                "name": "心理学书籍",
                "keywords": "",
                "desc": "",
                "pid": 1036016,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/58rdu0z6ttpuun73qhgx.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/5ywy4wl1dtr3nnh5wzrk.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-10-14 10:31:49",
                "updateTime": "2020-10-14 10:31:57",
                "deleted": false
            },
            {
                "id": 1036050,
                "name": "日语",
                "keywords": "",
                "desc": "",
                "pid": 1036017,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/q8wuq82ffy6njz2p153b.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/d26bzx1z6ao2xg1q7kwm.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:32:29",
                "updateTime": "2020-10-14 10:32:29",
                "deleted": false
            },
            {
                "id": 1036051,
                "name": "粤语",
                "keywords": "",
                "desc": "",
                "pid": 1036017,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/yynfalof8bl8iq5qitfw.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/y6af7nn1dmumqbfak0hk.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-10-14 10:32:55",
                "updateTime": "2020-10-14 10:32:55",
                "deleted": false
            },
            {
                "id": 1036052,
                "name": "茶道",
                "keywords": "",
                "desc": "",
                "pid": 1036018,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/va450cbdvs9ypxs8hqr9.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/xloz3i5zp6n554v4bx9f.png",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:33:35",
                "updateTime": "2020-10-14 10:33:35",
                "deleted": false
            },
            {
                "id": 1036054,
                "name": "商务礼仪",
                "keywords": "",
                "desc": "",
                "pid": 1036018,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/uxpd2l0yhe4og5mci6bd.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/ef5snwpf89ce8n5djf82.png",
                "level": "L2",
                "sortOrder": 3,
                "addTime": "2020-10-14 10:34:38",
                "updateTime": "2020-10-22 09:21:39",
                "deleted": false
            },
            {
                "id": 1036055,
                "name": "手机摄影",
                "keywords": "",
                "desc": "",
                "pid": 1036018,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/wjq26fwbennadx7xwi1g.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/0m7wt89cl9eol95fg0kn.png",
                "level": "L2",
                "sortOrder": 4,
                "addTime": "2020-10-14 10:34:59",
                "updateTime": "2020-10-14 10:34:59",
                "deleted": false
            },
            {
                "id": 1036056,
                "name": "现代艺术",
                "keywords": "",
                "desc": "",
                "pid": 1036018,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/za5j6ddqzz6fgv2x254j.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/u6wwhrps4qcvrp9spb5j.png",
                "level": "L2",
                "sortOrder": 5,
                "addTime": "2020-10-14 10:36:10",
                "updateTime": "2020-10-14 10:36:10",
                "deleted": false
            },
            {
                "id": 1036059,
                "name": "影音",
                "keywords": "",
                "desc": "",
                "pid": 1036018,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/4q48q4kjmguwj32z8bcx.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/fxqj1b65ph5iwjdqs3yq.png",
                "level": "L2",
                "sortOrder": 8,
                "addTime": "2020-10-14 10:37:49",
                "updateTime": "2020-10-14 10:37:49",
                "deleted": false
            },
            {
                "id": 1036060,
                "name": "彩妆教学",
                "keywords": "",
                "desc": "",
                "pid": 1036019,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/hvdvfm9v0gc9r9z0478b.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/r666s5keka70usup7m93.jpg",
                "level": "L2",
                "sortOrder": 1,
                "addTime": "2020-10-14 10:38:25",
                "updateTime": "2020-10-19 10:22:19",
                "deleted": false
            },
            {
                "id": 1036061,
                "name": "软件",
                "keywords": "",
                "desc": "",
                "pid": 1036019,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/keszx0tnw0fchyn2yxvh.jpg",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/c8dt1lpzrqqn8jtndeyo.jpg",
                "level": "L2",
                "sortOrder": 2,
                "addTime": "2020-10-14 10:38:46",
                "updateTime": "2020-10-14 10:38:46",
                "deleted": false
            },
            {
                "id": 1036062,
                "name": "其它",
                "keywords": "",
                "desc": "",
                "pid": 1036019,
                "iconUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/h2djy6sigz66w9r4stkk.png",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/soavjj1pqzqfymxbq07c.png",
                "level": "L2",
                "sortOrder": 3,
                "addTime": "2020-10-19 10:23:18",
                "updateTime": "2020-10-19 13:54:00",
                "deleted": false
            }
        ]
    },
    "errmsg": "成功"
    } 

错误码
    
    略   
    

#### 2.4.2 商品详情

应用场景

    商品详情
    
接口链接

    GET /common/goods/detail

请求参数
    
    id: 商品ID，例如id=1811112

响应内容

    {
    "errno": 0,
    "data": {
        "specificationList": [
            {
                "name": "规格",
                "valueList": [
                    {
                        "id": 374,
                        "goodsId": 1811112,
                        "specification": "规格",
                        "value": "标准",
                        "picUrl": "",
                        "addTime": "2020-09-27 09:09:16",
                        "updateTime": "2020-09-27 09:09:16",
                        "deleted": false
                    }
                ]
            }
        ],
        "groupon": [],
        "issue": [
            {
                "id": 1,
                "question": "购买运费如何收取？",
                "answer": "单笔订单金额（不含运费）满88元免邮费；不满88元，每单收取10元运费。\n(港澳台地区需满",
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2018-02-01 00:00:00",
                "deleted": false
            },
            {
                "id": 2,
                "question": "使用什么快递发货？",
                "answer": "严选默认使用顺丰快递发货（个别商品使用其他快递），配送范围覆盖全国大部分地区（港澳台地区除",
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2018-02-01 00:00:00",
                "deleted": false
            },
            {
                "id": 3,
                "question": "如何申请退货？",
                "answer": "1.自收到商品之日起30日内，顾客可申请无忧退货，退款将原路返还，不同的银行处理时间不同，",
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2018-02-01 00:00:00",
                "deleted": false
            },
            {
                "id": 4,
                "question": "如何开具发票？",
                "answer": "1.如需开具普通发票，请在下单时选择“我要开发票”并填写相关信息（APP仅限2.4.0及以",
                "addTime": "2018-02-01 00:00:00",
                "updateTime": "2018-02-01 00:00:00",
                "deleted": false
            }
        ],
        "userHasCollect": 0,
        "shareImage": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pjak4t4bscil52wm6i5g.jpg",
        "comment": {
            "data": [],
            "count": 0
        },
        "share": true,
        "attribute": [],
        "brand": {},
        "productList": [
            {
                "id": 371,
                "goodsId": 1811112,
                "specifications": [
                    "标准"
                ],
                "price": 10,
                "number": 999999,
                "url": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/81ejnungjr23ycv044bl.jpg",
                "addTime": "2020-09-27 09:09:16",
                "updateTime": "2020-09-27 09:09:16",
                "deleted": false
            }
        ],
        "goodskill": [
            {
                "id": 446,
                "goodsId": 1811112,
                "killPrice": 0,
                "killDate": [
                    "null",
                    "null"
                ],
                "isKill": false,
                "killStatus": 0,
                "addTime": "2020-09-27 09:09:15",
                "updateTime": "2020-09-27 09:09:15",
                "deleted": false
            }
        ],
        "info": {
            "id": 1811112,
            "goodsSn": "",
            "name": "网页设计视频教学",
            "categoryId": 1036029,
            "brandId": 0,
            "gallery": [
                "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/dkh3v1hfreaurj4308p7.jpg",
                "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/cj00unp8d1vr5nuzg16a.jpg",
                "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/38eu5jnr2reox4bs0sne.jpg"
            ],
            "keywords": "页面设计,UI,视频教学",
            "brief": "网页设计视频教学",
            "isOnSale": true,
            "sortOrder": 403,
            "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/0j8qa5kvv4pw54rn58p1.jpg",
            "shareUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/pjak4t4bscil52wm6i5g.jpg",
            "isNew": true,
            "isHot": false,
            "isKill": false,
            "isShown": false,
            "unit": "套",
            "counterPrice": 35,
            "retailPrice": 10,
            "commissionRate": 0.1,
            "addTime": "2020-09-27 09:09:15",
            "updateTime": "2020-09-27 09:09:16",
            "deleted": false,
            "detail": "<p><img src=\"https://devapis.oss-cn-zhangjiakou.aliyuncs.com/6fhof7s6a7upx7eenze8.jpg\" alt=\"\" width=\"750\" height=\"4207\" /></p>"
        }
    },
    "errmsg": "成功"
}

错误码
    
    略   


#### 2.4.3 商品推荐

应用场景

    针对某个商品推荐其他商品
    
接口链接

    GET /common/goods/related

请求参数
    
    id: 商品ID，例如id=1811112
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
        
响应内容

    {
    "errno": 0,
    "data": {
        "total": 13,
        "pages": 3,
        "limit": 6,
        "page": 1,
        "list": [
            {
                "id": 1811287,
                "name": "金士顿A400 120G固态硬盘 笔记本硬盘 台式电脑ssd sata接口2.5寸",
                "brief": "高速读写 快速启动",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/36v4rd1arm1vqcx5eqrm.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": true,
                "counterPrice": 169,
                "retailPrice": 169
            },
            {
                "id": 1811210,
                "name": "彩铅手绘",
                "brief": "视频学习",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/x9qu65s04k85jc4rlepi.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 33,
                "retailPrice": 6.6
            },
            {
                "id": 1811209,
                "name": "Sketch软件速成",
                "brief": "视频学习",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/dsb5bo5kxhn97btdv7pi.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 20,
                "retailPrice": 3.5
            },
            {
                "id": 1811208,
                "name": "AxureRP_基础到精通",
                "brief": "视频学习",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/op7xl5dpt489s0htd4hl.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 39,
                "retailPrice": 9.9
            },
            {
                "id": 1811207,
                "name": "Ae_完全自学教程",
                "brief": "视频学习",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/o9t7tztbwoa2s0do4rpx.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 46,
                "retailPrice": 13.5
            },
            {
                "id": 1811206,
                "name": "Ai_零基础到精通",
                "brief": "视频学习",
                "picUrl": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/gsehtbupu07nog6w87lt.jpg",
                "isNew": true,
                "isHot": false,
                "isKill": false,
                "isShown": false,
                "counterPrice": 45,
                "retailPrice": 13
            }
        ]
    },
    "errmsg": "成功"
}

错误码
    
    略   
            
#### 2.4.4 商品分类

应用场景

    针对某个商品推荐其他商品
    
接口链接

    GET /common/goods/related

请求参数
    
        
响应内容

错误码

#### 2.4.5 在售商品总数

应用场景

    在售商品总数
    
接口链接

    GET /common/goods/count

请求参数
    
    无
        
响应内容

    {
      "errno": 0,
      "data": 217,
      "errmsg": "成功"
    }
    
错误码
       
    无
         
### 2.5 购物车服务

#### 2.5.1 用户购物车

应用场景

    用户购物车
    
接口链接


请求参数
    
    无
        
响应内容

    
错误码
       
    略
    
### 2.6 订单服务
    
#### 2.6.1 订单列表

应用场景

    订单列表

接口链接

    GET /common/order/list
    
请求参数

    showType: 订单类型，0则全部，1则待付款，2则待发货，3则待收货，4则代评价
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
    
响应结果

    {
      "errno": 0,
      "data": {
        "total": 1,
        "pages": 1,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "orderStatusText": "未付款",
            "isGroupin": false,
            "orderSn": "20190509607545",
            "actualPrice": 3989.00,
            "goodsList": [
              {
                "number": 1,
                "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
                "id": 3,
                "goodsName": "云端沙发组合",
                "specifications": [
                  "标准"
                ]
              }
            ],
            "id": 3,
            "handleOption": {
              "cancel": true,
              "delete": false,
              "pay": true,
              "comment": false,
              "confirm": false,
              "refund": false,
              "rebuy": false
            }
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.6.2 订单详情

应用场景

    订单详情

接口链接

    GET /common/order/detail
    
请求参数
    
    orderId： 订单ID
    
响应结果

    {
      "errno": 0,
      "data": {
        "orderInfo": {
          "consignee": "d",
          "address": "北京市市辖区东城区 ddd",
          "addTime": "2019-05-09 15:30:29",
          "orderSn": "20190509607545",
          "actualPrice": 3989.00,
          "mobile": "13811111111",
          "orderStatusText": "未付款",
          "goodsPrice": 3999.00,
          "couponPrice": 10.00,
          "id": 3,
          "freightPrice": 0.00,
          "handleOption": {
            "cancel": true,
            "delete": false,
            "pay": true,
            "comment": false,
            "confirm": false,
            "refund": false,
            "rebuy": false
          }
        },
        "orderGoods": [
          {
            "id": 3,
            "orderId": 3,
            "goodsId": 1109008,
            "goodsName": "云端沙发组合",
            "goodsSn": "1109008",
            "productId": 140,
            "number": 1,
            "price": 3999.00,
            "specifications": [
              "标准"
            ],
            "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
            "comment": 0,
            "addTime": "2019-05-09 15:30:29",
            "updateTime": "2019-05-09 15:30:29",
            "deleted": false
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.6.3 创建订单

应用场景

    创建新订单

接口链接

    POST /common/order/submit

请求参数

    {
      "cartId": 0,
      "addressId": 3,
      "couponId": -1,
      "message": "",
      "grouponRulesId": 0,
      "grouponLinkId": 0
    }
    
响应结果

    {
      "errno": 0,
      "data": {
        "orderId": 4
      },
      "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.6.4 取消订单

应用场景

    取消订单

接口链接

    POST /common/order/cancel
    
请求参数

    orderId: 订单ID
    
响应结果

    {
      "errno": 0,
      "errmsg": "成功"
    }
    
错误码

    略

#### 2.6.5 微信预支付交易单

应用场景

    订单的微信预支付交易单

接口链接

    POST /common/order/prepay

说明

    具体微信支付交互流程和预支付使用方式，见官方文档: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_3&index=1

请求参数

    orderId: 订单ID

响应结果

    {
        errno: 0,
        errmsg: "成功",
        data: {
            appId: 'xxx',
            timeStamp: 'xxx',
            nonceStr: 'xxx',
            packageValue: 'xxx',
            signType: 'xxx',
            paySign: 'xxx'
        }
    }

错误码

    略
     
#### 2.6.6 确认收货

应用场景

    订单确认收货

接口链接

    POST /common/order/confirm
    
请求参数

    orderId: 订单ID
    
响应结果

    {
      "errno": 0,
      "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.6.7 订单删除

应用场景

    删除订单记录

接口链接

    POST /common/order/delete
    
请求参数

    orderId: 订单ID
    
响应结果

    {
      "errno": 0,
      "errmsg": "成功"
    }
    
错误码

    略
    
#### 2.6.8 订单退款

应用场景

    订单已经支付但是商家未发货，用户可以点击退款按钮申请退款取消订单。

说明

    退款请求发送以后，不会自动退款，仅仅是后端设置退款请求记录。
    管理员在管理后台看到退款请求以后会手动退款或者拒绝退款。
    
接口链接

    POST /common/order/refund
    
请求参数

    orderId: 订单ID
    
响应结果

    {
      "errno": 0,
      "errmsg": "成功"
    }
    
错误码

    略
           
  
#### 2.6.9 待评价商品

应用场景

    用户确认收货以后，可以待评价的订单商品。    

接口链接

    GET /common/order/goods
    
请求参数

    orderId: 订单ID
    goodsId: 商品ID
    
响应结果

    {
      "errno": 0,
      "data": {
        "id": 4,
        "orderId": 4,
        "goodsId": 1109008,
        "goodsName": "云端沙发组合",
        "goodsSn": "1109008",
        "productId": 140,
        "number": 1,
        "price": 3999.00,
        "specifications": [
          "标准"
        ],
        "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
        "comment": 0,
        "addTime": "2019-05-09 17:06:54",
        "updateTime": "2019-05-09 17:06:54",
        "deleted": false
      },
      "errmsg": "成功"
    }
    
错误码

    略
         
  
#### 2.6.10 订单评价

应用场景

    订单评价

接口链接

    POST /common/order/comment
    
请求参数

    orderGoodsId: 订单商品ID
    content: 评价内容
    star: 评分，1分至5分
    hasPicture: 是否有评价图片
    picUrls: 评价图片列表
    
例如

    {
      "orderGoodsId": 4,
      "content": "不错",
      "star": 5,
      "hasPicture": true,
      "picUrls": []
    }
        
响应结果

    {
      "errno": 0,
      "errmsg": "成功"
    }
    
错误码

    略
                                                  
### 2.7 会员服务

### 2.8 收货地址服务

#### 2.8.1 收货地址列表

应用场景

    用户收货地址列表

接口链接

    GET /common/address/list

请求参数

    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
        
响应结果

    {
      "errno": 0,
      "data": {
        "total": 1,
        "pages": 1,
        "limit": 1,
        "page": 1,
        "list": [
          {
            "id": 3,
            "name": "d",
            "userId": 2,
            "province": "北京市",
            "city": "市辖区",
            "county": "东城区",
            "addressDetail": "ddd",
            "areaCode": "110101",
            "tel": "13811111111",
            "isDefault": true,
            "addTime": "2019-05-06 14:17:32",
            "updateTime": "2019-05-06 14:17:32",
            "deleted": false
          }
        ]
      },
      "errmsg": "成功"
    }

错误码

    略
    
#### 2.8.2 收货地址详情

应用场景

    请求用户的收货地址详情

接口链接

    GET /common/address/detail

请求参数

    id: 收货地址ID
    
响应结果

    {
        errno: 0,
        errmsg: "成功",，
        data: {
            id: 收货地址ID，
            name: 收货人，
            tel: 手机号
            province: 省级行政区域,
            city: 市级行政区域,
            county: 区级行政区域,
            addressDetail: 具体地址,
            areaCode: 地址编码，
            postalCode: 邮政编码
            isDefault: 是否默认
        }
    }

错误码

    略

    
#### 2.8.3 保存收货地址

应用场景

    添加或者更新用户收货地址

接口链接

    POST /common/address/save

请求参数

    id: 收货地址ID，如果是0则是添加，否则是更新
    name: 收货人，
    tel: 手机号
    province: 省级行政区域,
    city: 市级行政区域,
    county: 区级行政区域,
    addressDetail: 具体地址,
    areaCode: 地址编码，
    postalCode: 邮政编码
    isDefault: 是否默认    

例如
    
    {
      "id": 0,
      "name": "xxx",
      "tel": "13811111111",
      "province": "北京市",
      "city": "市辖区",
      "county": "东城区",
      "areaCode": "110101",
      "addressDetail": "dddd",
      "isDefault": true
    }
    
响应结果

    {
        errno: 0,
        errmsg: "成功",，
        data: 3
    }

错误码

    略
    
    
#### 2.8.4 删除收货地址

应用场景

    删除用户的某个收货地址

接口链接

    POST /common/address/delete

请求参数

    id: 收货地址ID
    
响应结果

    {
        errno: 0,
        errmsg: "成功"
    }

错误码

    略
            	
### 2.9 品牌商服务

#### 2.9.1 品牌商列表

应用场景

    访问品牌商列表信息
    
接口链接

    GET /common/brand/list

请求参数
    
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 49,
        "pages": 5,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "id": 1024000,
            "name": "WMF制造商",
            "desc": "严选找寻德国百年高端厨具WMF的制造商，\n选择拥有14年经验的不锈钢生产工厂，\n为你甄选事半功倍的优质厨具。",
            "picUrl": "http://yanxuan.nosdn.127.net/2018e9ac91ec37d9aaf437a1fd5d7070.png",
            "floorPrice": 9.90
          },
          {
            "id": 1024001,
            "name": "OBH制造商",
            "desc": "严选寻找OBH品牌的制造商，打造精致厨具，\n韩国独资工厂制造，严格质检，品质雕琢\n力求为消费者带来全新的烹饪体验。",
            "picUrl": "http://yanxuan.nosdn.127.net/bf3499ac17a11ffb9bb7caa47ebef2dd.png",
            "floorPrice": 39.00
          },
          {
            "id": 1024003,
            "name": "Stoneline制造商",
            "desc": "严选找寻德国经典品牌Stoneline的制造商，\n追踪工艺，考量细节，亲自试用，\n为你甄选出最合心意的锅具和陶瓷刀，下厨如神。",
            "picUrl": "http://yanxuan.nosdn.127.net/3a44ae7db86f3f9b6e542720c54cc349.png",
            "floorPrice": 9.90
          },
          {
            "id": 1024006,
            "name": "KitchenAid制造商",
            "desc": "严选寻访KitchenAid品牌的制造商，\n采用德国LFGB认证食品级专用不锈钢，\n欧式简约设计，可靠安心，尽享下厨乐趣。",
            "picUrl": "http://yanxuan.nosdn.127.net/e11385bf29d1b3949435b80fcd000948.png",
            "floorPrice": 98.00
          },
          {
            "id": 1034001,
            "name": "Alexander McQueen制造商",
            "desc": "为制造精致实用的高品质包包，\n严选团队选择Alexander McQueen制造商，\n严格筛选，带来轻奢优雅体验。",
            "picUrl": "http://yanxuan.nosdn.127.net/db7ee9667d84cbce573688297586699c.jpg",
            "floorPrice": 69.00
          },
          {
            "id": 1023000,
            "name": "PetitBateau小帆船制造商",
            "desc": "为打造适合宝宝的婴童服装，\n严选团队寻找PetitBateau小帆船的品牌制造商，\n无荧光剂，国家A类标准，让宝宝穿的放心。",
            "picUrl": "http://yanxuan.nosdn.127.net/1a11438598f1bb52b1741e123b523cb5.jpg",
            "floorPrice": 36.00
          },
          {
            "id": 1001000,
            "name": "MUJI制造商",
            "desc": "严选精选了MUJI制造商和生产原料，\n用几乎零利润的价格，剔除品牌溢价，\n让用户享受原品牌的品质生活。",
            "picUrl": "http://yanxuan.nosdn.127.net/1541445967645114dd75f6b0edc4762d.png",
            "floorPrice": 12.90
          },
          {
            "id": 1001002,
            "name": "内野制造商",
            "desc": "严选从世界各地挑选毛巾，最终选择了为日本内野代工的工厂，追求毛巾的柔软度与功能性。品质比肩商场几百元的毛巾。",
            "picUrl": "http://yanxuan.nosdn.127.net/8ca3ce091504f8aa1fba3fdbb7a6e351.png",
            "floorPrice": 29.00
          },
          {
            "id": 1001003,
            "name": "Adidas制造商",
            "desc": "严选找到为Adidas等品牌制造商，\n选取优质原材料，与厂方一起设计，\n为你提供好的理想的运动装备。",
            "picUrl": "http://yanxuan.nosdn.127.net/335334d0deaff6dc3376334822ab3a2f.png",
            "floorPrice": 49.00
          },
          {
            "id": 1033003,
            "name": "Armani制造商",
            "desc": "严选团队携手国际标准化专业生产厂家，\n厂家长期为Armani、Alexander wang等知名品牌代工，\n专业进口设备，精密质量把控，精于品质居家体验。",
            "picUrl": "http://yanxuan.nosdn.127.net/981e06f0f46f5f1f041d7de3dd3202e6.jpg",
            "floorPrice": 199.00
          }
        ]
      },
      "errmsg": "成功"
    }

错误码
    
    略
    
#### 2.9.2 品牌商详情

应用场景

    访问单个品牌商详情信息
    
接口链接

    GET /common/brand/detail

请求参数
    
    id: 品牌商ID，例如1001020
    
响应内容

    {
      "errno": 0,
      "data": {
        "id": 1001020,
        "name": "Ralph Lauren制造商",
        "desc": "我们与Ralph Lauren Home的制造商成功接洽，掌握先进的生产设备，传承品牌工艺和工序。追求生活品质的你，值得拥有。",
        "picUrl": "http://yanxuan.nosdn.127.net/9df78eb751eae2546bd3ee7e61c9b854.png",
        "sortOrder": 20,
        "floorPrice": 29.00,
        "addTime": "2018-02-01 00:00:00",
        "updateTime": "2018-02-01 00:00:00",
        "deleted": false
      },
      "errmsg": "成功"
    }    

错误码
    
    略
    
### 2.10 收藏服务

#### 2.10.1 收藏列表

应用场景

    收藏列表
    
接口链接

    GET /common/collect/list
    
请求参数
    
    type： 收藏类型，如果是0则是商品收藏，如果是1则是专题收藏
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序   
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 2,
        "pages": 1,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "brief": "酥脆奶香，甜酸回味",
            "picUrl": "http://yanxuan.nosdn.127.net/767b370d07f3973500db54900bcbd2a7.png",
            "valueId": 1116011,
            "name": "蔓越莓曲奇 200克",
            "id": 3,
            "type": 0,
            "retailPrice": 36.00
          },
          {
            "brief": "MUJI供应商携手打造",
            "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
            "valueId": 1109008,
            "name": "云端沙发组合",
            "id": 2,
            "type": 0,
            "retailPrice": 3999.00
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码
    
    略
    

#### 2.10.2 收藏添加或删除

应用场景

    用户收藏添加或删除

说明

    如果用户已经收藏，则请求API会删除已收藏商品或专题；
    如果用户未收藏，则请求API会添加新的商品或专题收藏记录。
        
接口链接

    POST /common/collect/addordelete
    
请求参数
    
    type: 收藏类型，如果是0则是商品收藏，如果是1则是专题收藏
    valueId: 收藏对象ID，如果type=0则设置商品ID，如果type=1则设置专题ID
    
例如
    
    {
      "type": 0,
      "valueId": 1116011
    }

    
响应内容

    
错误码
    
    略
        
### 2.11 评论服务

#### 2.11.1 评论数量

应用场景

    某个商品或者专题的评论数量，包括总的评论数量和包含图片的评论数量
    
接口链接

    GET /common/comment/count

请求参数
    
    type: 评论类型，如果是0则是商品评论，如果是1则是专题评论
    valueId: 评论对象ID，如果type=0,则设置商品ID，如果type=0,则设置专题ID
    
响应内容

    {
      "errno": 0,
      "data": {
        "hasPicCount": 34,
        "allCount": 96
      },
      "errmsg": "成功"
    }

错误码
    
    无
    

#### 2.11.2 评论列表

应用场景

    某个商品或者专题的评论列表
    
接口链接

    GET /common/comment/list
    
请求参数
    
    valueId=1181000&type=0&limit=20&page=1&showType=0
    type: 评论类型，如果是0则是商品评论，如果是1则是专题评论
    valueId: 评论对象ID，如果type=0,则设置商品ID，如果type=0,则设置专题ID
    showType: 评论显示类型，如果是0则是所有评论，如果是1则是包含图片的评论
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
        
响应内容

    {
      "errno": 0,
      "data": {
        "total": 96,
        "pages": 20,
        "limit": 5,
        "page": 1,
        "list": [
          {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "addTime": "2018-02-01 00:00:00",
            "picList": [
              "https://yanxuan.nosdn.127.net/218783173f303ec6d8766810951d0790.jpg"
            ],
            "content": "布料很厚实，触感不错，洗过之后不缩水不掉色"
          },
          {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "addTime": "2018-02-01 00:00:00",
            "picList": [
              "https://yanxuan.nosdn.127.net/33978a0d6f56d94c45e4fc594b4b8606.jpg"
            ],
            "content": "料子很舒服，凉凉的，配合蚕丝被，夏天很凉快~"
          },
          {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "addTime": "2018-02-01 00:00:00",
            "picList": [
              "https://yanxuan.nosdn.127.net/d3975d1b6d88e9f9d762cd9a879d1a14.jpg"
            ],
            "content": "一直喜欢粗布的床上用品。冬暖夏凉。这套看起来非常漂亮。实际感觉有点粗布的感觉。很好！"
          },
          {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "addTime": "2018-02-01 00:00:00",
            "picList": [
              "https://yanxuan.nosdn.127.net/5fe1121396458cfe0dc1b25ec86f7ff9.jpg",
              "https://yanxuan.nosdn.127.net/d5a55abd6ced5c811d775b04929aaabc.jpg",
              "https://yanxuan.nosdn.127.net/f1764d820ba6ddaf51d297e3cf3826cd.jpg"
            ],
            "content": "太好了，舒服的不得了，腰，腿，脊柱，头，颈椎！\n无一处不舒服，真没想到这么优惠！\n搬了新家还要买！"
          },
          {
            "userInfo": {
              "nickName": "user123",
              "avatarUrl": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
            },
            "addTime": "2018-02-01 00:00:00",
            "picList": [
              "https://yanxuan.nosdn.127.net/f753f91430dfb56f574c737d4b2fde46.jpg"
            ],
            "content": "抱着试试的态度 先买了小的 果然感觉很舒服 深陷其中 把自己全身心都给了它 第二个床垫已经在路上"
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码
    
    无
        

#### 2.11.3 发表评论

应用场景

    针对某个商品或者专题的发表评论
    
接口链接


请求参数
    

响应内容


错误码
    
    略
            
### 2.12 优惠券服务


#### 2.12.1 优惠券列表

应用场景

    优惠券列表
    
接口链接

    GET  /common/coupon/list
    
请求参数
    
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序       

响应内容

    {
      "errno": 0,
      "data": {
        "total": 2,
        "pages": 1,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "id": 1,
            "name": "限时满减券",
            "desc": "全场通用",
            "tag": "无限制",
            "discount": 5.00,
            "min": 99.00,
            "days": 10
          },
          {
            "id": 2,
            "name": "限时满减券",
            "desc": "全场通用",
            "tag": "无限制",
            "discount": 10.00,
            "min": 99.00,
            "days": 10
          }
        ]
      },
      "errmsg": "成功"
    }

错误码
    
    略
    

#### 2.12.2 用户优惠券列表

应用场景

    用户优惠券列表
    
接口链接

    GET /common/coupon/mylist
    
请求参数
    
    status: 优惠券状态，如果0则未使用，如果1则已使用，如果2则已过期
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序    
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 2,
        "pages": 1,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "id": 1,
            "name": "限时满减券",
            "desc": "全场通用",
            "tag": "无限制",
            "min": "99.00",
            "discount": "5.00",
            "startTime": "2019-05-06 16:21:38",
            "endTime": "2019-05-16 16:21:38"
          },
          {
            "id": 3,
            "name": "新用户优惠券",
            "desc": "全场通用",
            "tag": "无限制",
            "min": "99.00",
            "discount": "10.00",
            "startTime": "2019-05-06 12:30:06",
            "endTime": "2019-05-16 12:30:06"
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码
    
    略
    

#### 2.12.3 下单可用优惠券

应用场景

    当前购物车下单商品订单可用优惠券
    
接口链接

    GET /common/coupon/selectlist

请求参数
    
    cartId: 购物车ID，如果0则是购物车商品，如果非0则是立即单一商品
    grouponRulesId: 团购规则ID，如果是团购商品则需要设置具体团购规则ID
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 1,
        "pages": 1,
        "limit": 1,
        "page": 1,
        "list": [
          {
            "id": 2,
            "name": "限时满减券",
            "desc": "全场通用",
            "tag": "无限制",
            "min": "99.00",
            "discount": "10.00",
            "startTime": "2019-05-09 15:27:29",
            "endTime": "2019-05-19 15:27:29"
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码
    
    略
            
#### 2.12.4 优惠券领取

应用场景

    领取优惠券

接口链接
    
    POST /common/coupon/receive

请求参数
    
    couponId： 可领取优惠券ID
    
例如

    {
        "couponId": 2
    }
    
响应内容

    {
      "errno": 0,
      "errmsg": "成功"
    }

错误码
    
    略

#### 2.12.5 优惠券兑换

应用场景

    通过兑换码兑换优惠券
    
接口链接

    POST /common/coupon/exchange

请求参数
    
    code: 优惠券兑换码
    
响应内容

    {
      "errno": 0,
      "errmsg": "成功"
    }

错误码
    
    略
                                    
### 2.13 反馈服务

### 2.14 足迹服务

#### 2.14.1 用户足迹列表

应用场景

    用户足迹列表
    
接口链接

    GET /common/footprint/list
    
请求参数
    
    page: 请求页码
    limit: 每一页数量 
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 22,
        "pages": 6,
        "limit": 4,
        "page": 1,
        "list": [
          {
            "brief": "酥脆奶香，甜酸回味",
            "picUrl": "http://yanxuan.nosdn.127.net/767b370d07f3973500db54900bcbd2a7.png",
            "addTime": "2019-05-09 10:10:01",
            "goodsId": 1116011,
            "name": "蔓越莓曲奇 200克",
            "id": 22,
            "retailPrice": 36.00
          },
          {
            "brief": "MUJI供应商携手打造",
            "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
            "addTime": "2019-05-09 10:09:49",
            "goodsId": 1109008,
            "name": "云端沙发组合",
            "id": 21,
            "retailPrice": 3999.00
          },
          {
            "brief": "酥脆奶香，甜酸回味",
            "picUrl": "http://yanxuan.nosdn.127.net/767b370d07f3973500db54900bcbd2a7.png",
            "addTime": "2019-05-08 22:40:55",
            "goodsId": 1116011,
            "name": "蔓越莓曲奇 200克",
            "id": 20,
            "retailPrice": 36.00
          },
          {
            "brief": "MUJI供应商携手打造",
            "picUrl": "http://yanxuan.nosdn.127.net/c5be2604c0e4186a4e7079feeb742cee.png",
            "addTime": "2019-05-07 14:35:41",
            "goodsId": 1109008,
            "name": "云端沙发组合",
            "id": 19,
            "retailPrice": 3999.00
          }
        ]
      },
      "errmsg": "成功"
    }
        
错误码
    
    略
    
#### 2.14.2 用户足迹删除

应用场景

    用户足迹删除
    
接口链接

    POST /common/footprint/delete
    
请求参数
    
    id: 用户足迹ID
    
响应内容

    {
      "errno": 0,
      "errmsg": "成功"
    }
        
错误码
    
    略
        
### 2.15 团购服务

注意
> 团购业务还不完善


#### 2.15.1 团购商品列表

应用场景

    参加团购的商品列表信息
    
接口链接


请求参数
    

响应内容


错误码
    
    略


#### 2.15.2 团购活动详情

应用场景

    团购活动详情
    
接口链接


请求参数
    

响应内容


错误码
    
    略               

#### 2.15.3 参加团购

应用场景

    参加团购的商品列表信息
    
接口链接


请求参数
    

响应内容


错误码
    
    略


#### 2.15.4 用户参团列表

应用场景

    用户参团列表
    
接口链接


请求参数
    

响应内容


错误码
    
    略
                                                                  
### 2.16 帮助服务

#### 2.16.1 帮助列表

应用场景

    帮助列表
    
接口链接

    GET /common/issue/list
    
请求参数
    
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序    

响应内容

    {
      "errno": 0,
      "data": {
        "total": 4,
        "pages": 1,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "id": 1,
            "question": "购买运费如何收取？",
            "answer": "单笔订单金额（不含运费）满88元免邮费；不满88元，每单收取10元运费。\n(港澳台地区需满",
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false
          },
          {
            "id": 2,
            "question": "使用什么快递发货？",
            "answer": "严选默认使用顺丰快递发货（个别商品使用其他快递），配送范围覆盖全国大部分地区（港澳台地区除",
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false
          },
          {
            "id": 3,
            "question": "如何申请退货？",
            "answer": "1.自收到商品之日起30日内，顾客可申请无忧退货，退款将原路返还，不同的银行处理时间不同，",
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false
          },
          {
            "id": 4,
            "question": "如何开具发票？",
            "answer": "1.如需开具普通发票，请在下单时选择“我要开发票”并填写相关信息（APP仅限2.4.0及以",
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false
          }
        ]
      },
      "errmsg": "成功"
    }
    
错误码
    
    无
              
### 2.17 搜索服务

### 2.18 专题服务

#### 2.18.1 专题列表

应用场景

    访问专题列表信息
    
接口链接

    GET /common/topic/list

请求参数
    
    page: 请求页码
    limit: 每一页数量
    sort: 排序字段
    order: 升序降序
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 20,
        "pages": 2,
        "limit": 10,
        "page": 1,
        "list": [
          {
            "id": 264,
            "title": "设计师们推荐的应季好物",
            "subtitle": "原创设计春款系列上新",
            "price": 29.90,
            "readCount": "77.7k",
            "picUrl": "https://yanxuan.nosdn.127.net/14918201901050274.jpg"
          },
          {
            "id": 266,
            "title": "一条丝巾就能提升时髦度",
            "subtitle": "不知道大家对去年G20时，严选与国礼制造商一起推出的《凤凰于飞》等几款丝巾是否还...",
            "price": 0.00,
            "readCount": "35.0k",
            "picUrl": "https://yanxuan.nosdn.127.net/14919007135160213.jpg"
          },
          {
            "id": 268,
            "title": "米饭好吃的秘诀：会呼吸的锅",
            "subtitle": "今年1月份，我们联系到了日本伊贺地区的长谷园，那里有着180年伊贺烧历史的窑厂。...",
            "price": 0.00,
            "readCount": "33.3k",
            "picUrl": "https://yanxuan.nosdn.127.net/14920623353130483.jpg"
          },
          {
            "id": 271,
            "title": "选式新懒人",
            "subtitle": "懒出格调，懒出好生活。",
            "price": 15.00,
            "readCount": "57.7k",
            "picUrl": "https://yanxuan.nosdn.127.net/14924199099661697.jpg"
          },
          {
            "id": 272,
            "title": "料理也要精细简单",
            "subtitle": "享受天然的味道，日子每天都好新鲜",
            "price": 69.00,
            "readCount": "125.6k",
            "picUrl": "https://yanxuan.nosdn.127.net/14925200530030186.jpg"
          },
          {
            "id": 274,
            "title": "没有软木拖，怎么过夏天",
            "subtitle": "刚入四月，杭州的气温就已升高至30度。店庆时买了软木拖的用户，陆续发回评价说，很...",
            "price": 0.00,
            "readCount": "46.4k",
            "picUrl": "https://yanxuan.nosdn.127.net/14925822213780237.jpg"
          },
          {
            "id": 277,
            "title": "治愈生活的满怀柔软",
            "subtitle": "太鼓抱枕的上架历程，是从失踪开始的。由于表面的绒感，最初它被安排在秋冬季上架。某...",
            "price": 0.00,
            "readCount": "19.6k",
            "picUrl": "https://yanxuan.nosdn.127.net/14926737925770587.jpg"
          },
          {
            "id": 281,
            "title": "条纹新风尚",
            "subtitle": "经典百搭，时尚线条",
            "price": 29.00,
            "readCount": "76.5k",
            "picUrl": "https://yanxuan.nosdn.127.net/14926859849200826.jpg"
          },
          {
            "id": 282,
            "title": "成就一室笋香",
            "subtitle": "三石哥办公室常备小食推荐",
            "price": 12.00,
            "readCount": "40.9k",
            "picUrl": "https://yanxuan.nosdn.127.net/14927695046601069.jpg"
          },
          {
            "id": 283,
            "title": "孩子成长中少不了的一双鞋",
            "subtitle": "说起毛毛虫鞋，好处实在太多了，作为一个2岁孩子的宝妈选品员，按捺不住想告诉大家，...",
            "price": 0.00,
            "readCount": "42.5k",
            "picUrl": "https://yanxuan.nosdn.127.net/14927748974441080.jpg"
          }
        ]
      },
      "errmsg": "成功"
    }

错误码
    
    略
    
#### 2.18.2 专题详情

应用场景

    单个专题详情信息
    
接口链接

    GET /common/topic/detail

请求参数
    
    id: 专题ID，例如 id=264
    
响应内容

    {
      "errno": 0,
      "data": {
        "topic": {
          "id": 264,
          "title": "设计师们推荐的应季好物",
          "subtitle": "原创设计春款系列上新",
          "price": 29.90,
          "readCount": "77.7k",
          "picUrl": "https://yanxuan.nosdn.127.net/14918201901050274.jpg",
          "sortOrder": 0,
          "goods": [],
          "addTime": "2018-02-01 00:00:00",
          "updateTime": "2018-02-01 00:00:00",
          "deleted": false,
          "content": ""
        },
        "goods": []
      },
      "errmsg": "成功"
    }  

错误码
    
    略

#### 2.18.3 专题推荐

应用场景

    基于某个专题推荐其他专题
    
接口链接

    GET /common/topic/related

请求参数
    
    id: 专题ID，例如 id=264
    
响应内容

    {
      "errno": 0,
      "data": {
        "total": 19,
        "pages": 5,
        "limit": 4,
        "page": 1,
        "list": [
          {
            "id": 266,
            "title": "一条丝巾就能提升时髦度",
            "subtitle": "不知道大家对去年G20时，严选与国礼制造商一起推出的《凤凰于飞》等几款丝巾是否还...",
            "price": 0.00,
            "readCount": "35.0k",
            "picUrl": "https://yanxuan.nosdn.127.net/14919007135160213.jpg",
            "sortOrder": 0,
            "goods": [],
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false,
            "content": "\u003cimg src\u003d\"//yanxuan.nosdn.127.net/75c55a13fde5eb2bc2dd6813b4c565cc.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/e27e1de2b271a28a21c10213b9df7e95.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/9d413d1d28f753cb19096b533d53418d.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/64b0f2f350969e9818a3b6c43c217325.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/a668e6ae7f1fa45565c1eac221787570.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/0d4004e19728f2707f08f4be79bbc774.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/79ee021bbe97de7ecda691de6787241f.jpg\"\u003e"
          },
          {
            "id": 268,
            "title": "米饭好吃的秘诀：会呼吸的锅",
            "subtitle": "今年1月份，我们联系到了日本伊贺地区的长谷园，那里有着180年伊贺烧历史的窑厂。...",
            "price": 0.00,
            "readCount": "33.3k",
            "picUrl": "https://yanxuan.nosdn.127.net/14920623353130483.jpg",
            "sortOrder": 0,
            "goods": [],
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false,
            "content": "\u003cimg src\u003d\"//yanxuan.nosdn.127.net/75c55a13fde5eb2bc2dd6813b4c565cc.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/e27e1de2b271a28a21c10213b9df7e95.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/9d413d1d28f753cb19096b533d53418d.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/64b0f2f350969e9818a3b6c43c217325.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/a668e6ae7f1fa45565c1eac221787570.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/0d4004e19728f2707f08f4be79bbc774.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/79ee021bbe97de7ecda691de6787241f.jpg\"\u003e"
          },
          {
            "id": 271,
            "title": "选式新懒人",
            "subtitle": "懒出格调，懒出好生活。",
            "price": 15.00,
            "readCount": "57.7k",
            "picUrl": "https://yanxuan.nosdn.127.net/14924199099661697.jpg",
            "sortOrder": 0,
            "goods": [],
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false,
            "content": "\u003cimg src\u003d\"//yanxuan.nosdn.127.net/75c55a13fde5eb2bc2dd6813b4c565cc.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/e27e1de2b271a28a21c10213b9df7e95.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/9d413d1d28f753cb19096b533d53418d.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/64b0f2f350969e9818a3b6c43c217325.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/a668e6ae7f1fa45565c1eac221787570.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/0d4004e19728f2707f08f4be79bbc774.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/79ee021bbe97de7ecda691de6787241f.jpg\"\u003e"
          },
          {
            "id": 272,
            "title": "料理也要精细简单",
            "subtitle": "享受天然的味道，日子每天都好新鲜",
            "price": 69.00,
            "readCount": "125.6k",
            "picUrl": "https://yanxuan.nosdn.127.net/14925200530030186.jpg",
            "sortOrder": 0,
            "goods": [],
            "addTime": "2018-02-01 00:00:00",
            "updateTime": "2018-02-01 00:00:00",
            "deleted": false,
            "content": "\u003cimg src\u003d\"//yanxuan.nosdn.127.net/75c55a13fde5eb2bc2dd6813b4c565cc.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/e27e1de2b271a28a21c10213b9df7e95.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/9d413d1d28f753cb19096b533d53418d.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/64b0f2f350969e9818a3b6c43c217325.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/a668e6ae7f1fa45565c1eac221787570.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/0d4004e19728f2707f08f4be79bbc774.jpg\"\u003e\n    \u003cimg src\u003d\"//yanxuan.nosdn.127.net/79ee021bbe97de7ecda691de6787241f.jpg\"\u003e"
          }
        ]
      },
      "errmsg": "成功"
    }
    
### 2.19 对象存储服务

### 2.20 其他服务


## 3 管理后台API服务

略

## 4 更新日志

略
