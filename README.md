# bh-dblock

#### 介绍
数据库实现的分布式锁

#### 软件架构
软件架构说明
基于springboot
使用springboot jdbc操作数据库


#### 安装使用教程
1.  建表
```
CREATE TABLE `lock_info` (
  `expiration_time` datetime NOT NULL,
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`tag`)
);
```
2.  引入maven依赖
```
    <dependency>
        <groupId>com.byhot</groupId>
        <artifactId>bh-dblock</artifactId>
        <version>1.0.0</version>
    </dependency>
 ```
3.  使用到的地方注入对象
```
    @Autowired
    private LockService lockService;
```
4.  使用分布式锁
```
      //tag是锁的唯一标识
      //expiredSeconds是锁的有效时间 单位是秒 传-1或者小于0则锁是永久锁
      if (lockService.tryLock(tag, expiredSeconds)) {
            try {
                //do something
            } catch (Exception e) {

            } finally {
                lockService.unlock(tag);
            }
        }else {
            throw new Exception("获取锁失败");
        }
```


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
