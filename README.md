# 基于mockito的自动化单元测试

## 背景
* 需要手动去mock数据, 流程简单, 但是很繁琐
* mock完数据的时候需要一个一个去returnValue

## 使用说明
1. 提供一个数据模板, 然后产生你要的数据, 通常是一个list
2. 指定你要测试的方法, 判断的格式(输出/断言)
3. mock数据返回的类型不允许使用import *这种格式, 需要一个一个来

## 局限
1. 只面向java, 但是可以根据接口具体实现
2. 面向的文本也比较少, 现在只针对网易下的owl的DB导出文本
3. mock数据list和单个对象分明, 如果是list必须是多个对象, 比如就是得是>=2个的一个数据集