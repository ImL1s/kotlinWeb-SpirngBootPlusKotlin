# Kotlin Web 開發(1)-從零搭建kotlin與spring boot開發環境

## 引用
- [kotlin web开发教程【一】从零搭建kotlin与spring boot开发环境 ](https://kotlintc.com/articles/4526)
- [《Springboot极简教程》 Springboot plus Kotlin ：Hello,World](https://www.evernote.com/l/AsBfqh2Es7VJtrRvQUYKqiW1_YGxoP1Jhx4)

## 使用spring boot官網的工具構建工程

1. 打開：http://start.spring.io/
2. 選擇創建一個gradle的工程,使用kotlin編程語言,使用最高的2.0.0的spring boot框架
3. 工程的Group一般的格式如下：
	
		com.baidu.projectname

4. 工程的Artifact一般直接填寫工程的名字

		com.baidu.projectname
		
5. 依賴項很重要，我們的項目使用如下依賴項：
		
		Web：包含所有spring mvc,tomcat需要的東西（spring boot是基於spring mvc的）；
		DevTools：srping boot的開發工具，比如“熱部署”等
		JPA：ORM框架
		MySQL：mysql的JDBC驅動
		Actuator：應用配置及應用監控工具
		Freemarker：頁面模版引擎，有點類似ASP.NET MVC的razor

6. 最終目錄的結構如下：

		Project
		- jna
		- projectName
			- .gradle
			- .idea
			- build
			- gradle
				- wrapper
					- gradle-wrapper.jar
					- gradle-wrapper.properties
			- src
				- main
					- kotlin
						- com.company.projectName
							porjectNameApplication.kt
					- resources
				- test
				- .gitigonre
				- .build.gradle
				- gradlew
				- gradlew.bat
				- projectName.iml
		- External Libraries

注意：
運行上面的工程，IDEA會提示報錯信息

	Cannot determine embedded database driver class for database type NONE

代表還沒有設定DB相關的連接資訊,所以不能判斷要使用哪一種DB driver


## Gradle配置

先來看看gradle的配置文件：build.gradle（在項目根目錄下）
其中dependencies節是依賴項配置：
注意：以下代碼都是自動生成的不用做任何修改，此處只是解釋一下配置內容

	dependencies {
		compile('org.springframework.boot:spring-boot-starter-actuator')
		compile('org.springframework.boot:spring-boot-starter-data-jpa')
		compile('org.springframework.boot:spring-boot-starter-freemarker')
		compile('org.springframework.boot:spring-boot-starter-web')
		compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
		compile("org.jetbrains.kotlin:kotlin-reflect")
		runtime('org.springframework.boot:spring-boot-devtools')
		runtime('mysql:mysql-connector-java')
		testCompile('org.springframework.boot:spring-boot-starter-test')
	}

 我們簡單介紹一下：

    spring-boot-starter-actuator：SpringBoot的健康檢監控組件的啟動器
    spring-boot-starter-data-jpa：JPA的啟動器
    spring-boot-starter-freemarker：freemarker的啟動器
    kotlin-stdlib-jre8：kotlin基於jre8的標准庫
    kotlin-reflect：kotlin反射庫
    spring-boot-devtools：spring-boot開發者工具，比如“熱部署”等
    mysql-connector-java：java的mysql鏈接工具
    spring-boot-starter-test：spring-boot測試工具啟動器
 
## application.properties配置

接著要來設定DB的連接字串,打開application.properties（在src/main/resources目錄下）
添加如下代碼：

	#data source
	spring.datasource.url=jdbc:mysql://******.mysql.rds.aliyuncs.com:3306/yourDBName?characterEncoding=utf8&characterSetResults=utf8
	spring.datasource.username=******
	spring.datasource.password=******
	
	spring.jpa.database=mysql
	spring.jpa.show-sql=true
	spring.jpa.hibernate.ddl-auto=update
	
	server.port=8000
	spring.datasource.tomcat.test-while-idle=true
	spring.datasource.tomcat.validation-query= Select 1

前面四句主要是數據庫鏈接字符串
後面三句是為jpa設置的內容
server.port是說明服務將運行在什麼端口
再次運行[yourName]Application.kt
調試區會出現一大堆調試信息
如果沒有問題的話，最後一行調試信息如下

2017-12-23 12:24:43.220  INFO 460 --- [  restartedMain] com.ysl.jna.jna.JnaApplicationKt         : Started JnaApplicationKt in 7.447 seconds (JVM running for 8.208)

說明你的應用已經成功啟動了；
倒數第二行調試信息如下：

2017-12-23 13:30:39.642  INFO 6788 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

你會看到spring boot幫你啟動了tomcat，（你裝了spring boot，他附送你一個tomcat，省的你自己裝）


## 編寫controller
- 就算你做完上述配置，但你訪問http://localhost:8000/，依然得不到任何有用的信息
	
		Whitelabel Error Page
		
		This application has no explicit mapping for /error, so you are seeing this as a fallback.
		
		Sun Dec 24 17:14:43 CST 2017
		There was an unexpected error (type=Not Found, status=404).
		No message available

注意：你的[projectname]Application.kt這個文件一定要在包目錄的根目錄下：

		Project
		- jna
		- projectName
			...
			...
			- src
				- main
					- kotlin
						- com.company.projectName
							porjectNameApplication.kt <-
					- resources
				- test
				...
				...
		- External Libraries

		
要不然你連上述錯誤信息都得不到


- 在projectName.src.main.kotlin.com.company.projectName下創建controller包,並添加HelloController類，代碼如下：

		@RestController
		class HelloController {
		
		    @GetMapping(value = ["/hello", "/"])
		    fun hello(): Any {
		        return "hello"
		    }
		}

- 然後重新編譯一下工程,重新訪問http://localhost:8000/,你將得到你想要的輸出

		hello
				