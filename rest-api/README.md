# akka-action

Akka使用实例

## 分散、聚合模式

**测试用例**

```
./sbt
akka-action > testOnly me.yangbajing.akkaaction.scattergather.ScatterGatherTest
```
## 构建REST风格的微服务

**运行**

```
./sbt
akka-action > runMain me.yangbajing.akkaaction.restapi.App
```

**测试脚本**

- ./get-book-aa.sh：正常返回ID为aa的书
- ./get-book-bb.sh：查找ID为bb的书返回404
- ./post-book.sh：创建一本ID为bb的书，返回201
- ./get-book-bb.sh：正确返回ID为bb的书
- ./put-book.hs：正确更新ID为bb的书
- ./put-book-invalid.sh：无效的更新ID为aa的书，返回409
- ./delete-book-aa.sh：成功的删除ID为aa的书
- ./get-book-aa.sh：再次查找ID为aa的书返回404
- ./delete-book-aa.sh：再次删除ID为aa的书时返回404
