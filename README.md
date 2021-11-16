# cloud-mall

#### 介绍
cloud-mall是一个基于分布式、微服务架构开发的商城系统,将由本人持续维护！


#### 如何安装
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
#### 软件架构
软件架构后期将会加入
