## mp-output文件夹说明  
### 文件夹目录
- controller  
实体类对应的`Controller`，将该目录下的类移到`web`模块下的`controller`包下
- mapper  
实体类对应的`DAO`层，该目录下包含`xml`文件和对应实体的接口类，将该目录下的文件移到`dao`模块下，自行建包
- service 对应实体类接口
    - impl 对应实体类接口实现类
    
  将`service`目录下的接口移到`service`模块下的`service`包下，`impl`目录下的类移到`service`模块下的`service.impl`包下
- po  
实体类。将该目录下的类移到`model`模块下的`po`包下，并修改继承关系，统一继承`BasePO`类，
因为`BasePO`类包含了`id`、`gmtCreate`、`gmtModified`、`deleted`字段，需将生成的实体类手动删除这些重复字段。
同时自动生成的`po`类缺失了`@TableName`、`@TableField`注解需手动补充。注解的使用方式可参考`BasePO`类