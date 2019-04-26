# messageServer
定时将每日的课程信息和天气状况以短信的形式发送给用户

如何使用：
1. 将该项目打包（jar）
2. 放置具有公网ip的服务器上，你总不能让自己电脑24小时运行吧
3. 使用crontab -e命令添加定时任务
4. 不会定时任务的，建议看这篇文章，讲的挺详细的https://www.cnblogs.com/p0st/p/9482167.html
5. 长期习惯了使用IDE，不会运行jar包的，看这里：java -jar test.jar，这里需要注意的是，项目使用的是JDK1.8，虽然我们安装了java环境并且配置了环境变量，但是在定时任务中无法使用java命令，解决方案：使用命令/usr/local/java/jdk1.8.0_201/bin/java -jar test.jar；就是使用java的绝对路径。这个是自己瞎试出来的，哈哈哈。
6. 项目中使用的天气接口和发送短信的接口是聚合数据的接口，课程信息嘛是自己简单的建了一个表放在服务器上的。聚合数据：https://www.juhe.cn
7. 模板中使用变量是联系客服申请的，个人是无法使用变量模板的。

文件配置：
1. msg_config.properties：mobile=发送短信的手机；tpl_id=短信模板的id；url=http\://v.juhe.cn/sms/send 短信API；key=用户接口key
2. sql.properties：jdbcUrl=数据库连接url；driverClass=com.mysql.jdbc.Driver；user=用户；pwd=密码
3. weather_config.properties：query_uri=http\://apis.juhe.cn/simpleWeather/query ；city=城市；key=用户接口key（接口不同，key不同）

表结构：
CREATE TABLE `class` (
  `id` int(11) NOT NULL,
  `dayof_week` int(11) NOT NULL,
  `timeofday` varchar(20) NOT NULL,
  `classname` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

效果图：
![](http://132.232.203.84/group1/M00/00/00/rBsADFzCpT6AAd5FAAJYZrsRlVk432_big.png)
![](http://132.232.203.84/group1/M00/00/00/rBsADFzCpUWANzphAAJyyFyfTcg307_big.png)
