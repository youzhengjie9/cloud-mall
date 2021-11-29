# cloud-mall

<p align=center>
  <a href="#">
   <img src="https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/cloud-mall-logo.png" alt="cloud-mall" style="width:200px;height:200px">
  </a>
</p>
<p align=center>
   cloud-mall是一个基于分布式、微服务架构开发的商城系统,将由本人持续改进与维护！
</p>

<p align=center>
<img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
<img src="https://img.shields.io/badge/springboot-2.3.2.RELEASE-green"/>
<img src="https://img.shields.io/badge/SpringCloud-Hoxton.SR8-brightgreen"/>
<img src="https://img.shields.io/badge/SpringCloud alibaba-2.2.3.RELEASE-brightgreen"/>
</p>


### 开发环境

| 类别                |   名称     |   
| -----------------   | -------  |
| 编译器          |    IntelliJ IDEA 2019.3   |
| 系统1             |   Windows 11 dev版   |
| 系统2               |   CentOS 7   |
| CPU              |    Intel i5-9300H   |
| 内存                |    24GB         |

* 注：特别是内存需要>=16GB才能保证流畅运行,如果不足16GB运行将有点困难


### 版本对应
| 名称                | 版本    |
| -------------      | -------------  |
| Spring Boot             | 2.3.2.RELEASE      | 
| Spring Cloud            | Hoxton.SR8      | 
| Spring Cloud Alibaba    | 2.2.3.RELEASE   |
| Nacos                  | 1.4.1      |
| Sentinel               | 1.8.0     |
| Seata                  | 1.3.0      |    
| ZipKin                |  2.12.9       |
| elasticSearch         |  7.6.1             |
| Kibana               |   7.6.1         |
| MySQL                  |  5.7            |

* 剩下的版本可以随意，上面写出来的版本要严格对应，不然怕会出现兼容性问题！！！

### 技术栈
#### 后端

| 名称                | 官网                                                         |
| -----------------   | ------------------------------------------------------------ |
| Spring Boot             | https://spring.io/projects/spring-boot               | 
| Spring Cloud        |     https://spring.io/projects/spring-cloud                    |
| Spring Cloud Alibaba     |  https://spring.io/projects/spring-cloud-alibaba                       |
| Nacos      |     https://nacos.io/zh-cn/                    |
| Seata        |   http://seata.io/zh-cn/                      |
| Sentinel     |   https://github.com/alibaba/Sentinel                      |
| ZipKin            |    https://zipkin.io/                                            |
| Redis             | http://www.redis.cn/               | 
| RabbitMQ                   |  https://www.rabbitmq.com/                                  |
| elasticSearch           |    https://www.elastic.co/cn/elasticsearch/                  |
| Kibana               |   https://www.elastic.co/cn/kibana/        |
| MyBatis             | http://www.mybatis.org/mybatis-3/zh/index.html               |         
| Spring Security        | https://spring.io/projects/spring-security/                                   |
| PageHelper         | http://git.oschina.net/free/Mybatis_PageHelper               |
| Maven              | http://maven.apache.org/                                     |
| MySQL              | https://www.mysql.com/                                       |                                  |
| Swagger2                  | https://swagger.io/               |
| Druid                       |    https://github.com/alibaba/druid                    |
| fastjson                          |   https://github.com/alibaba/fastjson/                |
| sjf4j                     |   http://www.slf4j.org/  |
| Nginx                       |     http://nginx.org/en/download.html
#### 前端

| 名称            | 描述       | 官网                                                     |
| --------------- | ---------- | -------------------------------------------------------- |
| jQuery          | 函数库     | http://jquery.com/                                       |
| Bootstrap       | 前端框架   | https://v3.bootcss.com/                                |
| echarts         | 可视化图表库       | https://echarts.apache.org/zh/index.html        |                        |                             |
| Thymeleaf     | 模板引擎                | https://www.thymeleaf.org/      |
| TinyMCE        |  富文本编辑器         |  http://tinymce.ax-z.cn/  |
| layui           | 模块化前端UI框架        | https://www.layui.com/         |
| Angular        |   应用设计框架与开发平台    | https://angular.cn/        |


#### 怎么安装cloud-mall
* 配置Nginx反向代理服务器
```text
worker_processes  2;
worker_rlimit_nofile 10240;
events {
    worker_connections  10240;
	
}

http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
	
	server_tokens off;
     client_max_body_size    100m;
    #tcp_nopush     on;
    #keepalive_timeout  0;
     keepalive_timeout  65;
	client_header_buffer_size 4k;
	open_file_cache max=10240 inactive=20s;
	open_file_cache_valid 30s;
	open_file_cache_min_uses 1;
    #gzip  on;
     gzip  on; 
     gzip_http_version 1.1; 
     gzip_vary on; 
     gzip_comp_level 6; 
     gzip_proxied any; 
     gzip_types text/plain text/css application/json application/x-javascript text/xml application/xml application/xml+rss text/javascript application/x-httpd-php image/jpeg image/gif image/png; 
     gzip_buffers 16 8k; 
     gzip_disable "MSIE [1-6]\.";
	fastcgi_connect_timeout 300;
	fastcgi_send_timeout 300;
	fastcgi_read_timeout 300;
	fastcgi_buffer_size 64k;
	fastcgi_buffers 4 64k;
	fastcgi_busy_buffers_size 128k;
	fastcgi_temp_file_write_size 128k;
	

	upstream cloudmall.index.cn{
		server localhost:3201 weight=1;
	}
	
	#cloud-mall的web模块
    server {
        listen       80;
        server_name  localhost;
		
        location / {
            root   html;
            index  index.html index.htm;
			proxy_pass http://cloudmall.index.cn;
        }      
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    
    }
 	
}
```

##### 第三方登录
###### gitee
* 接入gitee第三方授权配置,先在gitee的第三方应用上对网站进行授权，获得Client ID和Client Secret和回调地址,并放到cloud-mall-web模块的application.yml中即可

###### GitHub
* GitHub第三方配置有点复杂，也弄了一两天，主要还是GitHub中国地区登录很慢
* 我们只需要把cloud-yblog-commons模块中的com.boot.GitHubConstant的CLIENT_ID和CLIENT_SECRET换成自己的
* 然后在login.html的GitHub第三方登录超链接中client_id=xxx换成自己的id，即可


### 图片演示
![01.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/01.png)
![02.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/02.png)
![03.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/03.png)
![04.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/04.png)
![05.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/05.png)
![06.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/06.png)
![07.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/07.png)
![08.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/08.png)
![09.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/09.png)
![10.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/10.png)
![11.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/11.png)
![12.png](https://gitee.com/youzhengjie/cloud-mall/raw/master/cloud-mall-doc/images/12.png)

