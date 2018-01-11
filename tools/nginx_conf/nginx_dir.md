# 找出Nginx配置文件的位置

使用 `nginx -t`来检测配置文件, 以及查看配置文件目录:

```
nginx -t
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```

可以看到, yum 安装的 nginx 配置文件为 `/etc/nginx/nginx.conf`。

重新加载配置文件:

```
nginx -s reload
```

如果增加了 https 证书, 可能需要重启 nginx 服务。

```
service nginx restart
```

如果没权限, 可能需要再前面加上 `sudo`
