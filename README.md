# quick-builtin-quartz

模块提供一个基础的计划任务调度支持。底层基于Quartz实现，做了一层任务和执行的管理。



## 注解



### `@QuartzEnabled`

该注解标记一个Bean可以通过接口暴露给客户端，用于配置定时任务。



## 接口



### Java Bean管理 ： 获取可用BEAN


**接口地址**:`/_builtin/quartz/beans`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>根据条件列举出系统中可用于执行定时任务的BEAN</p>



**请求参数**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明         | schema                 |
| ------ | ------------ | ---------------------- |
| 200    | OK           | Result«List«BEAN实体»» |
| 401    | Unauthorized |                        |
| 403    | Forbidden    |                        |
| 404    | Not Found    |                        |


**响应参数**:


| 参数名称                                     | 参数说明     | 类型           | schema         |
| -------------------------------------------- | ------------ | -------------- | -------------- |
| code                                         |              | integer(int32) | integer(int32) |
| count                                        |              | integer(int64) | integer(int64) |
| data                                         |              | array          | BEAN实体       |
| &emsp;&emsp;description                      | 描述         | string         |                |
| &emsp;&emsp;methods                          | 方法列表     | array          | BEAN方法       |
| &emsp;&emsp;&emsp;&emsp;description          | 描述         | string         |                |
| &emsp;&emsp;&emsp;&emsp;name                 | 方法名称     | string         |                |
| &emsp;&emsp;&emsp;&emsp;parameters           | 参数列表     | array          | 方法调用参数   |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;name     | 参数名称     | string         |                |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;nullable | 是否可以为空 | boolean        |                |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;type     | 参数类型     | string         |                |
| &emsp;&emsp;name                             | 名称         | string         |                |
| error                                        |              | string         |                |
| msg                                          |              | string         |                |
| page                                         |              | integer(int64) | integer(int64) |
| pageSize                                     |              | integer(int64) | integer(int64) |
| total                                        |              | integer(int64) | integer(int64) |


**响应示例**:
```javascript
{
	"code": 0,
	"count": 0,
	"data": [
		{
			"description": "",
			"methods": [
				{
					"description": "",
					"name": "",
					"parameters": [
						{
							"name": "",
							"nullable": true,
							"type": ""
						}
					]
				}
			],
			"name": ""
		}
	],
	"error": "",
	"msg": "",
	"page": 0,
	"pageSize": 0,
	"total": 0
}
```

### 定时任务管理

遵循`restlet`增删查改规则。

#### 数据结构

```json
{
  "beanName": "",
  "created": "",
  "cronExpression": "",
  "description": "",
  "email": "",
  "id": 0,
  "jobName": "",
  "methodName": "",
  "params": "",
  "pauseAfterFailure": true,
  "paused": true,
  "personInCharge": "",
  "subTask": "",
  "updated": ""
}
```



#### 接口

- `GET` `/_builtin/quartz/jobs`
- `GET` `/_builtin/quartz/jobs/{id}`
- `POST` `/_builtin/quartz/jobs`
- `PUT` `/_builtin/quartz/jobs`
- `PUT` `/_builtin/quartz/jobs/{id}`
- `DELETE` `/_builtin/quartz/jobs`
- `DELETE` `/_builtin/quartz/jobs/{id}`

### 任务日志管理 

接口用于查询和管理定时任务执行产生的日志数据。遵循`restlet`增删查改规则。

#### 数据结构

```json
```



#### 接口

- `GET` `/_builtin/quartz/logs`
- `GET` `/_builtin/quartz/logs/{id}`
- `POST` `/_builtin/quartz/logs`
- `PUT` `/_builtin/quartz/logs`
- `PUT` `/_builtin/quartz/logs/{id}`
- `DELETE` `/_builtin/quartz/logs`
- `DELETE` `/_builtin/quartz/logs/{id}`



## 涉及数据库表



### `builtin_quartz_jobs`

```sql
CREATE TABLE IF NOT EXISTS `builtin_quartz_jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uuid` varchar(48) NOT NULL COMMENT '用于子任务唯一标识',
  `job_name` varchar(128) NOT NULL COMMENT '定时器名称',
  `bean_name` varchar(100) NOT NULL COMMENT 'Bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名称',
  `params` varchar(256) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(64) DEFAULT NULL COMMENT 'cron表达式',
  `paused` tinyint(1) NOT NULL COMMENT '状态，暂时或启动',
  `person_in_charge` varchar(32) DEFAULT NULL COMMENT '负责人',
  `email` varchar(64) DEFAULT NULL COMMENT '报警邮箱',
  `sub_task` varchar(100) DEFAULT NULL COMMENT '子任务',
  `pause_after_failure` tinyint(1) DEFAULT NULL COMMENT '失败后暂停',
  `description` varchar(128) DEFAULT NULL COMMENT '备注',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `builtin_quartz_jobs_UN` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='任务信息表';
```



### `builtin_quartz_logs`

```sql
CREATE TABLE IF NOT EXISTS `builtin_quartz_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(128) NOT NULL COMMENT '任务名称',
  `bean_name` varchar(100) NOT NULL COMMENT 'Bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名称',
  `params` varchar(256) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(64) DEFAULT NULL COMMENT 'cron表达式',
  `success` tinyint(1) NOT NULL COMMENT '状态',
  `exception_detail` text COMMENT '异常详情',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行耗时',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='任务日志记录';
```

