# myproject
项目分模块。core模块不用改动。实体类、service、mybaits的mapper都在entity模块下。如有扩展：新表的增加，
直接在entity模块下的resources下的generatorConfig.xml中配置（新增table标签即可），然后运行entity模块下的
com.weina.codegenerator.GeneratorClient.java。即可自动生成相应的实体类、mapper接口，mapper xml文件。
所有的提供给外部的接口（就是Controller),要么新建一个模块，要么都放到test模块下。
**项目中使用的redis参考自jfinal项目，集成到本项目中了**
