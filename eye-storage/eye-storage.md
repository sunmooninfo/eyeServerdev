#上传图片
## 接口映射：/storage/create
##参数说明：
| 含义 | 必填 | 参数名 | 类型|默认值|
| ------ | ------ | ------ |------|------|
| 请求方式|  |  |post||
| 参数 | 是 | file | MultipartFile |null|

##响应参数格式：
    {
      "errno": 0,
      "data": {
        "id": 3533,
        "key": "829rlylo5kigqq3vgai1.jpg",
        "name": "下载.jpg",
        "type": "image/jpeg",
        "size": 14642,
        "url": "https://devapis.oss-cn-zhangjiakou.aliyuncs.com/829rlylo5kigqq3vgai1.jpg",
        "addTime": "2020-11-16 15:43:36",
        "updateTime": "2020-11-16 15:43:36"
      },
      "errmsg": "成功"
    }