# Nginx配置文件服务器

server {
    listen       80;  
    server_name  file.6eye9.com; # server_name改成自己的ip或域名（需设置解析）

    location / {
        root   /root/download;   #目录文件服务器根目录
        autoindex on;   #允许nginx在浏览器以文件夹形式访问,打开目录浏览功能。当然nginx的规则配置还有很多
        autoindex_exact_size off;  #显示文件大小
        autoindex_localtime on;    #显示文件时间
        index index.html index.htm;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}