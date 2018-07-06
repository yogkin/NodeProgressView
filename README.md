
## 关于 ##

本项目fork https://github.com/aesion/NodeProgressView 
## 主要加入一下内容：
### 1、加入jitpack 依赖
### 2、修复一个设置adapter不生效的bug
### 3、加入一个流程节点node类 详细看效果图

# NodeProgressView #
![](https://github.com/aesion/NodeProgressView/blob/master/image/S60628-152145.jpg)
![](https://github.com/yogkin/NodeProgressView/blob/master/image/Screenshot_20180706-110501.png)
![](https://github.com/aesion/NodeProgressView/blob/master/image/Screenshot_20180706-110542.png)




>用来显示物节点进度的自定义View，仿淘宝



在build.gradle 添加下面内容
```groovy
allprojects {
		repositories {
			maven { url 'https://www.jitpack.io' }
		}
	}
```
maven { url 'https://jitpack.io' }

##在dependency中添加依赖

```groovy
implementation 'com.github.yogkin:NodeProgressView:1.0.2'
```


在你的XMl文件中：

```xml
<com.tish0.NodeProgressView
        android:id="@+id/npv_NodeProgressView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:width="2dp"
        app:nodeRadius="5dp"
        />
```
# 使用 #
#### NodeProgressView属性说明
| 属性        | 说明   |类型   |
| --------   | --------- |--------- |
| nodeWidth |线条宽度|dimension|
| nodeRadius |圆心半径|dimension|

#### BaseNodeProgressView属性说明
| 属性        | 说明   |类型   |
| --------   | --------- |--------- |
| lineWidth |线条宽度|dimension|
| rotate |是否旋转内容|boolean|
| radius |圆心半径|dimension|

##step1 获取服务端数据 构造对应实体
```Java
        List<LogisticsData> logisticsDatas;
        logisticsDatas = new ArrayList<>();
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊市中转部】,上一站是【】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【潍坊市中转部】装车,正发往【潍坊奎文代派】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊】,上一站是【潍坊市中转部】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【武汉分拨中心】装车,正发往【晋江分拨中心】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
```

##step2，找到你的控件并为其设置NodeProgressAdapter适配器


```Java
        NodeProgressView nodeProgressView = (NodeProgressView) findViewById(R.id.npv_NodeProgressView);
        nodeProgressView.setNodeProgressAdapter(new NodeProgressAdapter() {

            @Override
            public int getCount() {
                return logisticsDatas.size();
            }

            @Override
            public List<LogisticsData> getData() {
                return logisticsDatas;
            }
        });
```
