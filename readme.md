# flagwind core

flagwind core 是一套java的基础类库，它提供一些流程设计模型、常用的工具类等内容

## 依赖

commons-lang3    3.5
persistence-api  1.0.2

## 发布

``` bash
mvn release:clean
mvn release:prepare
mvn release:perform
```

## 变更

2018-9-14 增加了联想注解的设计

2019-4-24 解决TimeSpan BUG 以及优化联想注解设计

2019-8-12 把 flagwind-persistent 的类设计提出并放置在本工程
