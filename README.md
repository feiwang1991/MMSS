# MMSS
一、Mybatis部分
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
     0) mapper.xml文件名字和mapper.java名字一样，且在同一个目录下
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

5、输入映射
   通过parameterType指定输入参数类型，类型可以是简单类型，hashmap（key-value,key作为查询参数），pojo的包装类型
   5）传递pojo的包装对象
      例如查询条件很复杂，因为mapper.java接口中只能有一个输入参数，故可以自定义一个包装类，进而查询
      po是持久层对象，vo是视图层对象，pojo是介于两者之间的一个javabean

6、输出映射
  resultType需要查询出的列名和pojo属性名称一致，可以直接自动映射
  resultMap不需要查询出的列名和poho属性名称一致同样可以映射，但需要自定义映射。
  1）resultType
    使用resultType进行输出映射，只有查询出来的列名和pojo中的属性名一致时，查询出的pojo该属性才有值。
    若查询出的列名和pojo中的属性名称全部不一样，则没有创建pojo对象
    若查询出的列名和poo中的属性名称有一个一样，就会创建pojo对象
    当然resultType也可以是简单类型。
    当查询出的结果集只有一行一列时，可以使用简单类型，当有多列多行是用包装类型

  2) resultMap
     Mybatis中使用resultMap完成高级的输出结果映射。
     若查询出来的列名和pojo的属性名不一致，则可以使用resultMap,用它对列名和pojo属性名之间做一个映射关系。
     有2步骤：
       [1]定义resultMap
       [2]使用resultMap作为statement的输出类型

7、动态sql
   1）if标签
     if 标签，用于判断条件
     where 标签，里面含if标签，判断是否加上and
   2）sql片段  方便开发
     可以把常用的sql代码块提取出来，其他的statement可以引用这个sql片段
     通常sql片段中基于单表定义，这样可重用性高，其他查询若用到这个表的sql片段也可以使用
     另外，sql片段中不要包括where,因为如果查询中有好几个sql片段，不能有好几个where,因此where不放在sql片段中
   3)foreach标签，通常，parameterType作为输入类型只能使用一个参数，若要多个参数可以把parameterType设置为包装类，
     若想输入多个简单类型如 id in (1,2,4)这种sql，可以使用foreach标签，在sql片段或者在select标签中进行拼接。
     需要再输入的包装类中定义list<Integer> ids,用ids进行匹配

8、高级映射
   实现一对一查询、一对多查询、多对多查询、延迟加载
   1)一对一查询： 例如查询订单（主）和对应购买用户的信息
     resultType方式：即在关联的一对一查询时，查询出两张表的字段内容，需要自己新建一个pojo类去封装查询出来的数据，返回类型的resultType就
     写这个类即可。方法比较简单。
     resultMap方式：在关联一对一查询时，查询出两张表的字段内容，不需要新建一个pojo,而是在原来主表po的基础上，加上一个从表Java对象字段，
     即在主表对象中加一个从表引用，其实这也是构造了一种pojo,在mapper.xml中配置时，除了配置主表的id,result之外，还需要配置一对一
     association（用于一对一配置关联对象），相对于resultType比较复杂一点，但是在一些特殊场合，比如需要使用延迟加载等等，只能使用resultMap.
   2)一对多查询： 例如查询订单（主）及订单明细信息
     resultType方式：因为是一对多查询，在查询出主表和从表的字段时，可能会出现多条主表字段相同但是从表字段不同的数据。用自己定义的一个pojo类
     去进行封装，只能全部查询的属性拿出来封装，不能形成一个主表对象中包含一个从表List的这种非常好的效果，这个效果需要我们自己后续手动处理。
     非常不好。
     resultMap方式： 在一对多中效果十分好。与一对一类似，mapper.xml文件中把association换成collection即可，同时在原始po类中加上从表
     List集合属性，这样mybatis可以根据配置文件自动进行resultMap映射。非常好用。注意resultMap配置时可以继承。减少冗余代码。
   3)多对多查询： 例如查询用户和用户所购买的商品信息，他们没有直接关联，而是通过两个表进行关联
     多对多实际上就是一对多，在mybatis中基本都是一对一和一对多，没什么大区别
     查询出的主表为用户表，关联表为order,orderdetail,items
映射思路：将用户信息映射到user中，即用户信息列映射到user属性中
        将一个用户产生的多个订单映射到用户类中的list<orders> orderslist中，即订单信息列映射到orderslist中的orders属性中
        将一个Order产生的多个订单明细映射到订单类中的list<orderdetails> ordertailslist中，即订单明细列映射到ordertailslist中的ordertails属性中
        将一个ordertail订单明细对应一个商品信息映射到订单明细类中的item属性中，即查询出的商品信息列映射到item类中属性
    注意Mybatis的一对一，一对多，多对多和Hibernate由很大不同，非常简单，在讨论java对象之间的包含关系时候
    只需要考虑对应一张表和另一张表之间的关系，一对一就包含另一张表的对象，一对多就包含另一张表的集合对象即可，非常简单。

    注意：不是任何时候都需要使用resultType,当我们需要查询所有情况的清单详情时候，需要把所有人购买的商品直接列出来即可，
    因此不需要层层的pojo包装，而是直接使用一个新的pojo列出所有的属性即可，因此是看情况，看需求，怎么方便怎么来。
    resultMap针对那些查询结果由特殊要求的情况。

9、延迟加载  查询缓存  一级缓存、二级缓存
   1)延迟加载
          resultMap可以做到延迟加载，association和collection支持延迟加载的功能
     需要的时候再同关联表的对象属性中查询，可以按需查询，因为查询单表速度比关联查询多张表快，所以需要延迟加载。
     以前写的sql语句，是把所有需要的列都查出来，然后一一进行映射，这样是做不到延迟加载的，因此sql语句使用子查询
          使用mybatis进行延迟加载有2个步骤：以查询订单及关联的用户信息为例
           【1】配置select statement只查询订单信息，注意使用resultMap
                在查询订单信息statement的resultmap中除了配置需要的订单属性外（不直接包含用户属性），加一个association,
                使用它进行关联延迟加载，所以需要再查一次，
           【2】在association中配置一个新的select statement，,用这个statement去查询用户信息，输入为关联的订单表的外键，输出为resultType
            的用户表，在association标签中需要制定另一个statement的id和订单表中的关联列user_id
        在进行测试的时候，我们从mapper.java方法中首先获得订单表的List<orders>,此时已经延迟，当我们需要关联的用户信息时，需要自己手动编写代码
      去遍历这个List中的order,调用getUser()方法时，mybatis会根据另一个association中的statement去发起另一个查询sql,进而获取User。
      注意：Mybatis默认没有开启延迟加载，需要我们手动在SqlMapConfig.xml中settting中进行配置：
          【1】lazyLoadingEnable 默认为false，需要设置为true
          【2】aggressiveLazyLoading 默认为true,表示不等getUser就可是发出sql,需要设置为false
      我们自己也是可以实现一个延迟加载，实现两个mapper,先查一个需要的表，然后对结果集进行遍历，再根据对应外键关联查另外一个表即可。
   2)查询缓存 （mybatis默认开启一级缓存）
     Mybatis为了减轻数据库压力，提供查询缓存，主要分为一级缓存和二级缓存。
     一级缓存是：sqlsession级别的缓存(hashmap结构)，不同的sqlsession一级缓存不同
     二级缓存是：XXXmapper级别（namespace级别）的缓存，它是跨sqlsession，多个sqlsession可以共用一个二级缓存,例如UserMapper.xml和OrderMapper.xml
     是两个不同的二级缓存。

     一级缓存工作流程：第一次查询,一级缓存sqlsession没有就到数据库中查询，然后放到sqlsession,
     第二次查询，若sqlsession有，直接查询即可，或想要增删改数据，则先删除sqlsession中的缓存，然后commit到数据库。不删除会读到脏数据。

     二级缓存工作流程：sqlsession1去查询用户ＩＤ为1的用户，并存到UserMapper的二级缓存
                     sqlsession2去查询用户id为1的用户,去缓存中查找是否存在数据，若存在，则从缓存中查询数据
     二级缓存比一级缓存范围大，他是跨sqlsession,多个sqlsession共享一个UserMapper二级缓存
     UserMapper缓存区域按照namespace分，其他mapper缓存有自己namespace的缓存
     即每一个namespace的mapper有一个自己的二级缓存区域
     若两个mapper的namespace相同，则它们的sql查询数据将缓存在同一个区域中

     开启二级缓存：
       1 在SqlMapConfig.xml中设置总开关
          在setting中设置cachedEnabled 为true,默认为true
       2 在各个子mapper.xml中开启二级缓存
     注意：一级缓存的介质一定是内存，二级缓存的介质可能是内存和硬盘等等，介质不一定，因此需要对持久化对象po实现序列化接口，
     方便以后反序列化，加入内存。故可对user实现serializable接口

     注意：只有当sqlsession关闭之后，才会写入到二级缓存中，因此想测试的话，必须先关闭第一次的sqlsession.
     如果增删改了并提交了数据，同样会清除当前namespace mapper下二级缓存中的内容。

     禁用二级缓存：
        注意：在statement中的useCache属性中设置为false,表示禁用当前statement语句的二级缓存，本mapper文件会缓存很多个statement二级缓存
     因此可以禁用设置为false的这个，默认是true,支持二级缓存，对于不需要缓存的sql，每次都要查最新的数据的语句，是可以禁用二级缓存的.

     刷新缓存（清除缓存）：
        当增删改之后commit后，默认是会清除二级缓存，防止数据库和缓存不一致，从而出现脏读现象。
        在statement中有flushCache属性，默认为true，即打开刷新（清除）缓存设置。
        若我们设置为false，则可能出现脏读，因此一般情况下，就使用默认值即可。

10、ehcache分布式缓存
   mybatis无法实现分布式缓存（redis集群，memcache集群，ehcache），最多能做到在单机服务器上的mapper级别的二级缓存
   不能解决分布式集群服务中，多台机器的缓存问题。mybatis只能解决单机问题。需要和其他分布式缓存框架整合。
   因此有多个缓存级别：
   用户查询数据-->负载均衡-->先查找单机Mybatis sqlsession一级缓存-->没有就找跨sqlsession的mapper级别的二级缓存（
   这个mybatis二级缓存默认实现是单机的，可以使用ehcache等让二级缓存成为分布式缓存）

   Mybatis和所有缓存框架的整合方法：
   Mybatis提供了一个cache接口，如果要实现自己的缓存逻辑，实现cache接口即可。
   mybatis和ehcache整合，mybatis和ehcache整合包中提供了cache接口的实现类，mybatis直接调用接口方法即可，操作不同redis的数据结构

   Mybatis整合ehcachebu步骤：
   1）在mapper.xml中cache标签中 type设置为ehcache的cache接口实现类，mybatis默认实现是PerpetualCache
   2）添加mybatis-ehcache.jar ,ehcache-core.jar
   3）在classpath下添加ehcache.xml配置文件

   二级缓存应用场景：
      对于实时性要求不高的数据，可以用二级缓存缓存起来，通过设置flushinterval刷新间隔，来定时30分钟或者60分钟一次的清除缓存
   mybatis二级缓存的局限性：
         我们通过ehcache或redis或memcache对Mybatis的二级缓存进行分布式拓展，确实解决了缓存在多个应用集群中共享的问题，
      但是，查询仍然是通过Mybatis向缓存或者数据库中查询数据，因此分布式的二级缓存仍然具有mybatis的特点，即缓存区域是按照mapper
      进行划分，一个商品信息的变更提交后会将商品mapper的所有商品信息全部清空，即mybatis对细粒度的数据级别的缓存做的实现不好。
      解决这种问题一般是通过在业务层针对需求对数据有针对性的缓存。一个商品信息变更后，只清空该商品的缓存数据，其他商品不变，
      这个我们可以自己实现缓存，称之为三级缓存。


11、mybatis和spring的整合
    1）整合思路
      需要spring通过单例的方式管理sqlsessionfactory(这步需要我们自己做)
      spring和mybatis整合生成代理对象，使用sqlsessionfactory生成sqlsession，然后sqlsession产生代理对象（这步srping和mybatis自动整合完成）
      持久层的mapper或者dao由spring自动管理（这步我们自己做）
    2）环境
       需要mybatis jar包，spring jar包，Mybatis-spring jar包
    3）sqlsessionfactory
       在applicationContext.xml配置sqlsessionfactory（需要单例）
    4）原始dao的开发（和spring整合后）
       mapper.xml
          mapper.xml基本保持不变,写相关sql
       dao(实现类继承SqlSessionSupport)
          在spring applicationContext中配置Dao实现类，注入我们到dao的实现类中。原来没有使用spring整合时，我们到dao的实现类中写set方法注入sqlsessionfactory
       sqlsessionfactory,在整合时同样需要注入sqlsessionfactory,但是我们不需要手动写set方法注入，让自己的dao实现类继承sqlsessiondaosupport,
       这个类有sqlsessionfactory属性和对应的set方法，故我们在配置文件中可以直接注入，不需要再写一次set方法，获取sqlsession时候可以
       直接this.getSqlSession（原来是要先获取sqlsessionfactory,这里继承的类中直接有这个方法，同时注入了sqlsessionfacotry,
       由它产生sqlsession）,注意，因为getSqlSession被spring改写过，因此我们不需要显示的关闭进行sqlsession.close()方法。
       spring会默认调用关闭sqlsession的方法。
    5）Mappper代理的开发（和spring整合后） 和Dao两种方式都要掌握，都有可能用到


12、逆向工程，须会用
    从数据库表--》自动生成Java相关代码和xml配置文件，这也是企业开发中最常见的逆向工程方式
    Mybatis官方提供了逆向工程的工具，可以针对单表自动生成mybatis执行所需要的代码，包括mapper.java,mapper.xml,po等等

    工具：mybatis-generator-core
    1)官网下载zip压缩包后，里面有index网页，可以查看多种使用方式
      它有很多IDE的插件运行方式，但是推荐使用java程序运行逆向工程，不依赖开发工具
    2）复制配置文件和java代码创建
    3）最好新建一个工程进行创建生成，不用自己的工程直接生成，这样危险性高

二、springMVC部分
    springMVC基本流程：
    用户发起request请求，到springMVC的前端控制器，类似struts2中的filter，springMVC中的前端控制器是DispatcherServlet(接受用户请求，响应)，
    然后，springMVC中的前端控制器是DispatcherServlet告诉处理器映射器(HandlerMapping)，让它根据请求的url把请求交给对应的Handler处理器
    （平常我们叫做controller），具体对应的方式，可能是根据xml文件配置的方式查找URL对应的Handler,处理器映射器找到了之后，向DispatcherServlet
    返回一个执行链HandlerExecutionChain.这个是一个封装对象，里面封装了前后许多HandlerInterceptor还有指定的Handler。 然后前端控制器
    请求处理器适配器HandlerAdapter去执行Handler,Handler执行完给处理器适配器返回ModelAndView，处理器适配器再把
    ModelAndView（ModelAndView是springMVC的底层对象）交给前端控制器；然后前端控制器请求视图解析器(ViewResolver)
    去解析得到的ModelAndView，根据逻辑视图名解析成真正的视图（jsp,pdf,excel,freemaker），然后把视图向前端控制器返回，前端控制器
    对视图进行渲染，视图渲染将模型数据（在ModelAndView对象中）填充到request域，最后有前端控制器response相应用户。

    组件：
    1、前端控制器DispatcherServlet（一般不需要程序员开发）
       作用接受请求，响应结果，相当于转发器
       有了DispatcherServlet就减少了其他组件之间的耦合性
    2、处理器映射器HandlerMapping （不需要程序员要开发）
       作用根据请求的url查找Handler,xml或者注解
    3、处理器Handler
    4、处理器适配器HandlerAdapter
       作用按照特定的规则（HandlerAdapter要求的规则）去执行Handler
       注意：编写Handler时候，要按照HandlerAdapter的要求去做，这样适配器才可以正确的执行Handler
    5、视图解析器View Resolver   （不需要程序员要开发）
       作用进行视图解析，根据逻辑视图名解析成真正的视图（View）
    6、视图View
       View是一个接口，实现类支持不同的实现类型(jsp,freemaker,pdf)

    环境准备：
    加入spring所有jar包，一定要加入spring-webmvc包
    步骤：
    1、配置前端控制器
    在web.xml中配置前端控制器，这个是一个servlet
    同时需要在web.xml中初始化时，加载一些初始化参数，这些参数指明需要同时加载的处理器映射器、适配器的具体配置文件地址。
    2、配置处理器适配器
    在classpath下的springmvc.xml中配置处理器映射器，处理器适配器，视图解析器，这三个器的配置都在springmvc.xml中。
    先配置哪个没有固定顺序，但是逻辑上，最好先配置处理器适配器，适配器定义好规则之后，配置处理器
    观察适配器原码发现，适配器能够适配实现了Controller接口的处理器
    3、编写处理器Handler
    Handler需要实现Controller接口才能由SimpleControllerHandlerAdapter处理器适配器执行
    4、视图的编写
    就是写jsp页面
    5、配置处理器映射器
    6、配置处理器Handler，都比较简单，参考实际代码
    7、我们这里使用jsp，故要配置jsp的视图解析器







