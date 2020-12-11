## 1 部署方案

本节将介绍代码开发成功以后开始部署项目时一些关键性流程。

首先，需要明确的是开发时项目使用的服务地址是本地地址，即localhost；而部署时则应该根据具体情况设置合理的服务器地址和端口。

其次，需要明确的是各模块之间的关系：

  * eye-common-api模块会包含eye-core模块、eye-db模块、eye-storage模块、eye-mail模块、eye-express模块、eye-sms模块
  * eye-admin-api模块会包含eye-core模块、eye-db模块、eye-storage模块、eye-mail模块、eye-express模块、eye-sms模块
  * eye-brand-api模块会包含eye-core模块、eye-db模块、eye-storage模块
  * eye-cms-api模块会包含eye-core模块、eye-db模块、eye-storage模块
  * eye-tool-api模块会包含eye-core模块、eye-db模块、eye-mail模块
  * eye-all模块则会包装所有模块，部署在服务器中

最后，**如果项目部署云服务器，则根据开发者的部署环境在以下文件中或代码中修改相应的配置。**

1. MySQL数据库设置合适的用户名和密码信息；
2. 后端服务模块设置合适的配置信息；

实际上，最终的部署方案是灵活的：

* 可以是同一云服务器中安装一个Spring Boot服务，同时提供eye-admin、eye-all两种服务
* 可以单一云服务器中仅安装一个tomcat/nginx服务器部署eye-admin静态页面分发服务，
  然后部署一个Spring Boot的后端服务；
* 当然，甚至多个服务器，采用集群式并发提供服务。

注意
> 1. `本机`指的是是当前的开发机
> 2. `云服务器`指的是开发者购买并部署的远程服务器

以下简单列举几种方案。

### 1.1 单机单服务部署方案

本节介绍基于腾讯云的单机单服务部署方案，面向的是服务器数据和应用部署在云服务器单机中用于演示的场景。
其他云应该也是可行的。

主要流程是：创建云服务器，安装ubuntu操作系统，按照JDK和MySQL应用运行环境，部署单一Spring Boot服务。

![](./pics/project/develop-stage.png)

#### 1.1.1 云服务器

1. 创建云服务器

   请参考腾讯云、阿里云或者其他云平台的官方文档进行相关操作。
   建议最低配置是**1核2G**。

2. 安装操作系统

   本项目采用Cnetos8，但是并不限制其他操作系统。

3. 创建安全组

    ![](./pics/project/security-group.png)

    目前允许的端口：8080，80，443，22，3306

    注意：
    这里其实只需要8080端口，允许其他端口只是方便开发阶段的测试和调试。
    特别是3306端口，作为MySQL的远程访问端口，请在上线阶段关闭。

4. 设置SSH密钥（可选）

    建议开发者设置SSH密钥，可以免密码登录云服务器，以及用于脚本自动上传应用。

5. 使用PuTTY远程登录云服务器

    如果开发者设置SSH密钥，可以采用免密码登录；否则采用账号和密码登录。

#### 1.1.2 OpenJDK11

这里可以安装openjdk-11.0.2


```
1. 下载openjdk11安装包 下载地址：https://jdk.java.net/archive/
2. 将下载好的tar安装包上传到服务器的指定位置。
3. 解压tar包 解压命令：tar -zxvf openjdk-11.0.2_linux-x64_bin.tar.gz
4. 解压后的文件夹名称jdk-11.0.2修改为jdk11 执行命令：mv jdk-11.0.2/ jdk11
5. 复制jdk11的路径，我的路径为/usr/local/java/jdk11
6. 修改系统级环境变量配置文件. 文件是: /etc/profile   执行命令：vim /etc/profile
7. 复制以下内容到文件的最后，退出并保存。执行命令：：wq
    export JAVA_HOME=/usr/local/java/jdk11
    export 
    CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    export PATH=$JAVA_HOME/bin:$PATH
8. 重启linux 执行命令：shutdown -r now
9. 验证jdk是否安装成功 执行命令：java –version
```

如果验证没有安装成功，请详细查看哪一步没有按照安装步骤执行。

注意
> 如果用户想采用Oracle JDK8或者其他JDK环境，请查阅相关资料安装。

#### 1.1.3 MySQL

本示例使用的是yum安装方法

```
1. 下载mysql yum安装文件 mysql80-community-release-el8-1.noarch.rpm
   下载地址：https://dev.mysql.com/downloads/repo/yum/
2. 将下载好的yum文件上传至linux服务器的指定位置，我这里是/usr/local/mysql
3. 执行命令进行安装：yum localinstall mysql-community-release-el6-5.noarch.rpm
4. yum远程下载并安装，执行命令：yum install mysql-server
5. 中间提示Is this ok [y/N]:    输入y
6. 启动服务  执行命令：service mysqld start
7. 设置root用户密码，执行命令：/usr/bin/mysqladmin -u root password 'new-password'
   此时会报错：Access denied for user ‘root’@‘localhost’(using password: NO)
解决办法：
7.1 先停止mysql服务，执行命令：service mysqld stop
7.2 进入mysql安装目录bin/ 使用safe模式，进行重启：./mysqld_safe --skip-grant-tables
7.3 使用root账户，无密码登录，修改root用户密码
    mysql -u root 
    use mysql
    update user set password=PASSWORD("你的密码") where User = 'root';
    5.7版本下的mysql数据库下已经没有password这个字段了，password字段改成了authentication_string
    update user set authentication_string=PASSWORD("你的密码") where User = 'root';
    刷新权限：flush privileges;
    quit;
8. 重新登录：mysql -u 用户名  -p密码 
    -p 后直接输入密码,不能有任何其他字符.
9. 为root用户授权，执行命名：
    grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option;
   刷新授权信息，执行命令：flush privileges;
   退出：quit
10.登录测试 
```
#### 1.1.4 Git

本示例使用的是yum安装方法

```
1. yum安装Git,只需要一行命令:yum -y install git
2. 查看Git版本号,执行命令：git --version
```

#### 1.1.5 Elasticsearch

注意：
1.安装elasticsearch时linux内核版本必须是3.5+，查看内核版本的命令是： uname -a
2.jdk必须是jdk1.8.0_131以上版本
3.下载地址https://www.elastic.co/cn/downloads/elasticsearch

```
1. 安装elasticsearch
     ES是Java开发的应用，解压即安装，执行命令：tar –zxvf elasticsearch-7.9.3-linux-aarch64.tar.gz
2. 修改elasticsearch需要的系统配置
     Linux 默认来说，一般限制应用最多创建的文件是 65535 个。但是 ES 至少需要 65536 的文件创建权限。
     vi /etc/security/limits.conf增加下述内容：
     * soft nofile 65536
     * hard nofile 65536
3. 修改线程开启限制
     默认的 Linux 限制 root 用户开启的进程可以开启任意数量的线程，其他用户开启的进程可以开启 1024 个线程。必须修改限制数为 4096+。因为 ES 至少需要 4096 的线程池预备。
     Linux 低版本内核为线程分配的内存是 128K。4.x 版本的内核分配的内存更大。如果虚拟机的内存是 1G，最多只能开启 3000+个线程数。至少为虚拟机分配 1.5G 以上的内存。
     vi /etc/security/limits.d/90-nproc.conf 配置es启动时的线程池最低容量修改下述内容：
     *          soft    nproc     4096
     root       soft    nproc     unlimited
4. 修改系统控制权限
     ES 需要开辟一个 65536 字节以上空间的虚拟内存。Linux 默认不允许任何用户和应用直接开辟虚拟内存。
     vi /etc/sysctl.conf 新增下述内容：
     vm.max_map_count=655360
   
    使用命令，让sysctl配置生效：
    sysctl -p
5. 设置可访问的客户端
    修改elasticsearch的配置文件，设置可访问的客户端。0.0.0.0代表任意客户端访问。
    vi config/elasticsearch.yml修改下述内容：
    etwork.host: 0.0.0.0
    http.port: 9200
6. 创建用户
     从5.0开始，ElasticSearch 安全级别提高了，不允许采用root帐号启动，所以我们要添加一个用户。
     前面ES 是 root 用户解压缩的。所以解压后的 ES 应用属于 root 用户。所以我们需要将 ES 应用的所有者修改为其他用户：
     1.创建elk 用户组
     	  groupadd elk
     2.创建用户admin
     	  useradd admin
     	  passwd admin
     3.将admin用户添加到elk组
     	  usermod -G elk admin
     4.为用户分配权限
     	  chown -R admin:elk /usr/java/elasticsearch-6.2.3   
7. ElasticSearch启动与关闭
     1、启动
     		./elasticsearch -d		
     2、关闭
     		ps -ef|grep elasticsearch
         	kill -9 pid 
```
#### 1.1.6 elasticSearch-head
```
1. 下载node.js 下载地址：https://nodejs.org/en/download/ 
2. 下载elasticsearch-head 下载地址：https://github.com/mobz/elasticsearch-head
3. 解压node-v14.15.1-linux-x64.tar.xz
     执行命令：
     xz –d node-v14.15.1-linux-x64.tar.xz
     tar –xvf node-v14.15.1-linux-x64.tar
4. 复制以下代码到/etc/profile文件中
     export NODE_HOME= /usr/local/ node-v14.15.1-linux-x64
     export PATH=$PATH:$NODE_HOME/bin
     export NODE_PATH=$NODE_HOME/lib/node_modules
5. 使配置文件生效： source /etc/profile
6. 检测node是否安装成功
     执行命令：node –v
7. 将下载好的elasticsearch-head-master.zip上传至/usr/local/es-head
     执行解压命令：unzip elasticsearch-head-master.zip
     更改名为elasticsearch-head
     执行命令：mv elasticsearch-head-master elasticsearch-head
8. 编译
     执行命令：
     cd elasticsearch-head/
     npm install
9. 启动
     执行命令：npm run start
```
#### 1.1.7 kibana

```
1. 下载kibana，下载地址：https://artifacts.elastic.co/downloads/kibana/kibana-6.3.2-linux-x86_64.tar.gz
2. 上传至/usr/local/kibana文件夹下，解压下载好的安装包
   执行命令：tar –zxvf kibana-6.3.2-linux-x86_64.tar.gz
3. 修改配置文件
    执行命令：vim config/kibana.yml
    放开注释,将默认配置改成如下：
    server.port: 5601
    server.host: "0.0.0.0"
    elasticsearch.url: "http://localhost:9200"
    kibana.index: ".kibana"
4. 启动
     执行命令：./kibana
     后太启动：./kibana &
```
注意：
   如果是root角色会提示
   Kibana should not be run as root.  Use --allow-root to continue.
   所以需要切换角色并且开放文件夹给角色，具体可以查看安装Elasticsearch中的es的角色和权限：
   chown -R es /apps/tools/kibana-7.2.0/
   es为用户

#### 1.1.8 RbbitMQ

RabbitMQ是Erlang语言编写的，所以在安装RabbitMQ之前，需要先安装Erlang。但是在搭建
RabbitMQ环境过程中，会因为RabbitMQ 和 Erlang的版本问题导致环境一直搭建不起来， 可
以去官网查看RabbitMQ 和 Erlang的版本问题，
网址：https://www.rabbitmq.com/which-erlang.html#erlang-repositories
本例选择的RabbitMQ的版本为 3.7.16，Erlang的版本为22.0

```
1. 下载RabbitMQ所需要的安装包，即Erlang 和 RabbitMQ
     Erlang安装包官网下载地址：https://www.erlang.org/
     rabbitmq官网下载地址：https://www.rabbitmq.com/
2. 把下载好的RabbitMQ 和 Erlang上传到Linux服务器上
     将下载好的RabbitMQ 和 Erlang上传到目录，并解压上传的RabbitMQ 和 Erlang安装包，执行命令：
     tar -zxvf otp_src_22.0.tar.gz &>/dev/null
     xz -d rabbitmq-server-generic-unix-3.7.16.tar.xz
     tar -xvf rabbitmq-server-generic-unix-3.7.16.tar
3. 在/usr/local/software 目录下创建一个rabbitmq_software文件夹，便于我们管理安装的RabbitMQ软件，并把我们解压好的文件移动到
   rabbitmq_software目录下，执行命令：
     mkdir -p /usr/local/software/rabbitmq_software
     mv otp_src_22.0 /usr/local/software/rabbitmq_software/
     mv rabbitmq_server-3.7.16 /usr/local/software/rabbitmq_software/
4. 安装Erlang编译所依赖的环境
      执行命令：
      yum install make gcc gcc-c++ build-essential openssl openssl-devel unixODBC unixODBC-devel kernel-devel m4 ncurses-devel
5. 在 /usr/local 目录下创建一个erlang文件夹，因为erlang编译安装默认是装在/usr/local下的bin和lib中，这里我们将他统一装到
    /usr/local/erlang中，方便查找和使用，执行命令：
      mkdir -p /usr/local/erlang
6. 编译Erlang
     执行命令：
     cd /usr/local/software/rabbitmq_software/
     ./configure  --prefix=/usr/local/erlang --without-javac
7. 安装Erlang
     执行命令：
      cd /usr/local/software/rabbitmq_software/
      make && make install
8. 配置Erlang环境变量
     执行命令：vim /etc/profile
     添加如下配置信息
     export ERLANG_HOME=/usr/local/erlang 
     export PATH=${ERLANG_HOME}/bin:${PATH}
     重新读取配置文件，执行命令：source / etc/profile 
9. 创建软连
     执行命令：
     ln -s /usr/local/erlang/bin/erl /usr/local/bin/erl
10.测试erlang
     执行命令：erl
11.配置RabbitMQ环境变量
     执行命令：vim /etc/profile
     添加如下信息到profile文件
     export RABBITMQ_HOME=/usr/local/software/rabbitmq_software/rabbitmq_server-3.7.16   
     export PATH=${RABBITMQ_HOME}/sbin:${PATH}
     重新读取配置文件，执行命令：source / etc/profile 
12.开启Web管理界面插件，便于访问RabbitMQ
     执行命令：
     cd /usr/local/software/rabbitmq_software/rabbitmq_server-3.7.16/sbin
     ./rabbitmq-plugins enable rabbitmq_management
13.设置RabbitMQ开机启动
     添加如下代码到 /etc/rc.d/rc.local 中：
     source /etc/profile
     /usr/local/software/rabbitmq_software/rabbitmq_server-3.7.16/sbin/rabbitmq-server -detached
14.设置web插件用户名密码
     执行命令：
     rabbitmqctl add_user newadmin newpassword  
     rabbitmqctl set_user_tags newadmin administrator
     rabbitmqctl set_permissions -p / newadmin "." "." ".*"
     newadmin为新管理员账号，可以自行命名，newpassword为密码
15.后台启动、关闭RabbitMQ服务
     执行命令：
     ./rabbitmq-server –detached 
     ./rabbitmqctl stop
```

#### 1.1.9 Maven

```
1. 下载压缩包:
     官网地址: http://maven.apache.org/download.cgi
2. 上传到linux的/usr/local/maven目录
3. 解压文件
     执行命令：tar -zxvf apache-maven-3.6.3-bin.tar.gz
4. 配置环境变量
     执行命令：vi /etc/profile
     将以下代码复制到/etc/profile文件中
     export MAVEN_HOME=/usr/local/maven/apache-maven-3.6.3 
     export PATH=${MAVEN_HOME}/bin:$PATH 
5. 刷新环境变量
     执行命令：source /etc/profile
6. 检测版本号
    执行命令：mvn –v
```

#### 1.1.4 项目打包

1.现将项目在git上面克隆下来

```
git clone https://用户名:密码@项目地址

如:
	git clone https://xxx:xxx@git.xxx/eyeServerdev.git
```

2.在服务器或者开发机打包项目到deploy；

```
cd eye
cat ./eye-db/sql/eye_table.sql >> ./deploy/db/eye_table.sql
cat ./eye-db/sql/eye_data.sql >> ./deploy/db/eye_data.sql

cd ./eye-admin
cnpm install
cnpm run build:dep

cd ..
mvn clean package
cp -f ./eye-all/target/eye-all-*-exec.jar ./deploy/eye/eye.jar
```

这里脚本的作用是：

1. 把数据库文件拷贝到deploy/db文件夹；
2. 编译eye-admin项目；
3. 编译eye-all模块


此时deploy部署包结构如下：

* bin
  存放远程服务器运行的脚本，包括deploy.sh脚本和reset.sh脚本

* db
  存放eye数据库文件

* eye
  存放远程服务器运行的代码，包括eye-all二进制可执行包和eye外部配置文件

* util
  存放开发服务器运行的脚本，包括package.sh脚本和lazy.sh脚本。
  由于是本地开发服务器运行，因此开发者可以不用上传到远程服务器。

#### 1.1.5 项目部署

1. 远程服务器环境（MySQL和JDK1.8）已经安装好，请确保云服务器的安全组已经允许相应的端口。

2. 创建数据库

    ```
    create database eye;
    ```

    将SQL文件导入到数据库中

    ```bash
    cd /home/ubuntu/deploy/db
    按顺序导入
    mysql -uroot -proot eye < eye_table.sql;
    mysql -uroot -proot eye < eye_data.sql;
    或使用mysql可视化界面工具导入
    ```

3. 启动服务
    ```bash
    java -Dfile.encoding=UTF-8 -jar eye-all/target/eye-all-0.1.0-exec.jar
    ```

4. 测试是否部署成功(xxx.xxx.xxx.xxx是云服务器IP）：
    ```
    http://xxx.xxx.xxx.xxx:8080/common/index/index
    http://xxx.xxx.xxx.xxx:8080/admin/index/index
    ```

注意：
> 开发者访问以上三个地址都能成功，但是管理后台点击登录时会报错网络连接不成功。
> 这里很可能是开发者eye-admin模块的`config/dep.env.js`或者`condig/prod.env.js`
> 没有设置正确的管理后台后端地址，例如这里的`http://xxx.xxx.xxx.xxx:8080/admin`

#### 1.1.6 deploy部署脚本

在前面的项目打包和项目部署中都是采用手动命令来部署。
这里可以写一些脚本简化：

* util/packet.sh

在开发服务器运行可以自动项目打包

* util/lazy.sh

在开发服务器运行可以自动项目打包、项目上传远程服务器、自动登录系统执行项目部署脚本。
    
注意：
> 1. 开发者需要在util/lazy.sh中设置相应的远程服务器登录账号和密钥文件路径。
> 2. 开发者需要在bin/reset.sh设置远程服务器的MySQL的root登录账户。

* bin/deploy.sh

在远程服务器运行可以自动部署服务

* bin/reset.sh

在远程服务器运行可以自动项目导入数据、删除本地上传图片、再执行bin/deploy.sh部署服务。

注意：
> 开发者需要在bin/reset.sh设置远程服务器的MySQL的root登录账户。

总结，当开发者设置好配置信息以后，可以在本地运行lazy.sh脚本自动一键部署:
```bash
cd eye
./deploy/util/lazy.sh
```

不过由于需要设置的信息会包含敏感安全信息，强烈建议开发者参考这里的deploy文件夹，
然后实现自己的deploy文件夹，妥善处置外部配置文件和脚本中的敏感安全信息!!!

