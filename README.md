# MMSS
It is a Demo web-project with maven-mabatis-spring-springMVC in intellij idea.

Tips:
1、#{}表示一个占位符，可以接收简单类型，pojo，hashmap,若接收简单类型，#{}里面可以写成value或其他名称
   ${}表示一个拼接符号，会引入sql注入，所以不建议使用${},${}可以接收简单类型，pojo,hashmap,若接收简单类型，${}里面只能写value

2、mybatis Dao开发
  以上是一些增删改查的小demo，因此每次需要走完完整流程。
  2.1 SqlSessionFactoryBuilder
    通过SqlSessionFactoryBuilder创建会话工厂，把SqlSessionFactoryBuilder当成一个工具类使用，不需要使用单例模式，会话工厂单例即可，

  2.2 SqlSessionFactory
    通过会话工厂创建SqlSession,使用单例模式管理SqlSessionFactory,在mybatis和spring整合时，默认Java对象使用单例。
    sqlsessionfactory需要注入到dao中产生会话来操作数据库
  2.3 SqlSession
     SqlSession是面向开发人员 操作数据库的接口，主要操作mappedStatement(封装了自己写的SQL)
     因为spring中DAO对象一般单例，因此sqlsession不能放在dao对象的成员变量，不然多个线程可以使用同一个数据库session,不安全。
     SqlSession最佳应用场合在方法体内，必定线程安全，定义成局部变量使用。

  原始DAO开发存在一些问题：
   1) 存在大量重复代码
   2）在sqlsession中的方法中，直接调用statementId，如test.findUserById等都是属于硬编码，代码在程序里

3、基于以上原因，需要使用mapper接口的方式开发(其实就是mybatis自动的生成一个DAO接口的实现类代理对象，不需要手动创建dao实现类，
   由mybatis自动生成，很方便) Mapper接口就是传统上的DAO接口
   1）开发人员需要编写mapper.xml文件
   2）编写mapper接口，该接口需要遵循一些开发规范，可以让Mybatis自动生成实现mapper接口的代理对象

   以下为重要开发规范：
     1）mapper.xml中的namespace值为mapper接口的全限定名称地址
     2）mapper.java接口中方法名字和mapper.xml中statementID一致
     3）mapper.java接口中方法中参数和mapper.xml中parameterType入参类型一致
     4）mapper.java接口中方法返回值类型和mapper.xml中resultType返回类型一致
     注意不要忘记在SqlMapConfig.xml中配置mapper.xml文件
    以上开发规范主要是对一下代码进行统一生成：
    User user = sqlSession.selectOne("test.findUserById", id);
    sqlSession.insert("test.insertUser",user);
    sqlSession.delete("test.deleteUserById",id);

    有两点需要注意：
    1）当输出为多条语句时，mybatis会使用selectList()
    2) mapper接口的方法参数只能有一个，一般来说一个即可。若想多个参数，可以使用pojo满足不同业务的需求。
    注意持久层方法参数可以为包装类型、map,   service方法中建议不要使用包装类型（不利于业务的扩展）

4、SqlMapConfig.xml中有许多属性需要配置
   1)properties属性
     需求：通常我们会把例如数据库的连接一些参数专门配置到特定的文件中，如db.properties,然后mybatis将该文件加载
     此时不需要对SqlMaConfig.xml中的数据库连接参数进行硬编码，方便其他的xml文件对统一管理的db文件再次使用
   2）settings
     重要用于配置mybatis的二级缓存，延迟加载等等特性
   3）typeAliases别名（重点）
     在mapper.xml中会使用Statement，其中有parameterType和resultType会使用全限定类名类型
     比较繁琐，因此可以使用别名，例如mybatis的默认内置别名int<==>java.lang.Integer，我们也可自定义别名
     分为单个定义和批量定义（更常用）
   4）typeHandlers 类型处理器
     用于处理jdbc类型和java类型之间的转换，一般不需要我们自定义，内置的已经够用
   5）mappers 映射
     一种为：单个映射文件的加载，通过mapper标签的resource属性，路径为classpath下
                              通过mapper标签的url属性，路径为绝对路径file:///D:\xxx
     一种为：通过mapper接口来加载mapper.xml文件
     一种为：package批量加载，推荐使用，使用条件和第二种一样