# 飞书开放接口使用示例
* 针对多 API 串行调用场景，封装 API [组合函数](src/main/java/com/larksuite/oapi/composite_api)，减少开发者对接 API 个数，提高开发效率；
* 针对常见业务场景，封装可直接运行的 [Quick-Start](src/main/java/com/larksuite/oapi/quick_start)，帮助开发者快速上手 API 接入。

## 组合函数
目前提供以下组合函数：
* 消息
  * [发送文件消息](src/main/java/com/larksuite/oapi/composite_api/im/Im.java)
  * [发送图片消息](src/main/java/com/larksuite/oapi/composite_api/im/Im.java)
* 通讯录
  * [获取部门下所有用户列表](src/main/java/com/larksuite/oapi/composite_api/contact/Contact.java)
* 云空间
  * [分片上传大文件](src/main/java/com/larksuite/oapi/composite_api/drive/Drive.java)
* 多维表格
  * [创建多维表格同时添加数据表](src/main/java/com/larksuite/oapi/composite_api/base/Base.java)
* 电子表格
  * [复制粘贴某个范围的单元格数据](src/main/java/com/larksuite/oapi/composite_api/sheets/Sheets.java)

## Quick-Start
目前提供以下场景的运行示例：
* [机器人自动拉群报警](src/main/java/com/larksuite/oapi/quick_start/robot) ([开发教程](https://open.feishu.cn/document/home/message-development-tutorial/introduction))
  

## License
MIT

## 加入讨论群
[_单击_](https://applink.feishu.cn/client/chat/chatter/add_by_link?link_token=dc7p3b08-78ac-451b-855b-daf8156a4a11)加入讨论群
