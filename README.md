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
    -----
    8、 非注解处理器映射器，适配器：
      8.1下面是非注解的处理器映射器（beanname方式）
     <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" />
     非注解处理器映射器（简单URL映射）
      8.2<!--简单url映射-->
         <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
             <property name="mappings">
                 <props>
                     <!--key就是url,value就是处理器的ID,可以多个url对应同一个handler -->
                     <prop key="/queryItems1.action">itemsController1</prop>
                     <prop key="/queryItems2.action">itemsController1</prop>
                 </props>
             </property>
         </bean>
      配置文件中可以同时出现多个处理器映射器，springmvc能让传进来的url给谁处理就让哪个处理器映射器处理
      8.3非注解的处理器适配器
       <!--配置处理器适配器，要求编写的Handler实现Controller接口-->
          <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" />
       <!--另一个非注解处理器适配器，要求编写的Handler实现HHttpRequestHandler接口
           注意两种适配器都可以使用，实现要求编写的Handler实现HHttpRequestHandler接口
           的Handler相当于另外一种struts2的action-->
           <bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"/>
            //使用此方式可以通过response修改响应数据格式，比如相应json数据
                   /*httpServletResponse.setCharacterEncoding("utf-8");
                   httpServletResponse.setContentType("application/json;charset=utf-8");
                   httpServletResponse.getWriter().write("json串");*/
      9 配置注解适配器、映射器
          9.1 DispatcherServlet.properties
              这个文件是在springmvc的jar包中的配置文件，里面配置了springmvc默认的处理器映射器，适配器以及视图解析器
              因此，即使在xml中不进行配置以上几种，仍然是可以起作用。
              其中在该文件中关于处理器映射器的配置中，
              org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping，这个注解处理器是spring3.1之前的
              默认注解方式处理器映射器，因此如果我们不设置的话，使用的就是过期的处理器映射器
              org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping是3.1之后的注解方式处理器映射器
              ---
              3.1之前使用的默认注解适配器是：
              org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter
              3.1之后使用的默认注解适配器是：
              org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
           9.2配置注解适配器、映射器
              <!--注解处理器映射器 -->
              <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
              <!--注解处理器适配器 -->
              <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>
              <!--springmvc还提供了非常方面的功能，即使用mvc:annotation-driven就可以代替上面2个注解类
               同时mvc:annotation-driven还默认加载了很多参数绑定方法，比如json转换解析器就默认加载了
               故使用mvc:annotation-driven的话就不需要配置上面两个，实际开发也用这个-->
              <mvc:annotation-driven></mvc:annotation-driven>

           注意：注解的映射器和适配器必须配对使用
            context:component-scan可以扫描controller service repository注解的类，直接在spring容器中进行注册
            <context:component-scan base-package="com.dianping.controller"/>
      10、原码分析
         看前端控制器，分析springMVC的执行过程
         1）前端控制器接收请求，调用doDispatch方法，对请求进行处理，入参是httpservletrequest,httpservletresponse，返回空
         2) 前端控制器，调用处理器映射器查找handler，调用HandlerExecutionChain方法，入参是httpservletrequest,返回handler执行链
         3）前端控制器调用前置的拦截器，然后调用处理器适配器去执行handler，利用适配器的handle方法，入参request,respons,以及查到的handler
            返回modelAndView,之后前端控制器执行后面的拦截器
         4）视图渲染
            调用视图处理器，首先获得view,根据modelandview中的view名字获得view,这里是jsp页面，然后调用view的渲染方法，把model（是个map）
            中的值设置到request中去，等到对jsp文件进行解释成java类（内含el表达式对象）之后，若jsp中有el表达式，则表达式对象就从最小的
            request域开始，找到就把属性值out.println到html输出流中
      11、springMVC和mybatis整合
          spring将各层进行整合
          通过spring管理持久层的mapper(相当于dao接口)
          通过Spring管理业务层service,service中可以调用Mapper接口，可以对service进行事物控制
          通过spring管理表现层handler,handler中可以调用service接口
          mapper的代理对象(spring配置文件中产生，传入sessionfactory和扫描的mapper包),service对象，handler对象都是Javabean
        第一步：整合dao层
         mybatis和spring整合，通过spring管理Mapper接口，使用Spring的扫描器自动扫描mappe接口在spring中进行注册
        第二步：管理service层
         通过spring管理service接口，可以通过注解@service或者通过声明式的方式在配置文件中配置
         实现事务控制，一般事务控制是在service中完成
        第三步：整个springMVC
         springMVC是spring的模块，不需要整合，在没有Springmvc时候，我们使用spring是通过单元测试中加载applicationConfig.xml
         配置文件，加上jar包，对springMVC容器进行启动，在springmvc时候，启动入口改为web,故需要在web.xml中配置springmvc
         的前端控制器，也就是最终要的servlet，并且把springmvc的配置文件位置作为初始化参数进行加载，同时在web.xml中对
         spring的配置文件进行加载，从而启动spring容器，在spring容器启动时候会在配置文件中
         同时加载mybatis(配置了mybatis配置文件的位置)
    12、参数绑定
       从客户端请求的key-value数据，经过springmvc参数绑定，绑定到controller方法形参上，即springmvc中，接受客户端
       传递进来的参数主要是靠方法形参，而不是类似struts2中的在controller方法的成员变量中定义

       处理器适配器通过参数绑定组件把key-value数据转换成controller方法的形参，其中Springmvc提供了很多这种组件叫converter
       一般情况不需要我们自定义，但是有时候也是需要，比如对日期数据的需求。

       参数绑定默认支持的类型，即在controller方法的形参的类型，我们可以自己定义随便用：
       httpservletrequest,httpservletresponse,httpsession,model/modelMap (model是接口，modelmap是他的接口实现，定义哪一个都可以)
       同样支持简单类型，即在方法形参中定义简单int之类的类型，
       若不使用注解@RequestParam,controller方法形参默认名称和传入url中参数名称一致
       若使用注解@RequestParam,不用限制controller方法形参默认名称和传入url中参数名称一致

       如果controller形参pojo中有时间属性，那么需要对传入的时间进行自定义绑定
       目标是把输入参数绑定输出成和数据库匹配的时间类型
       需要向处理器适配器中注入自定义绑定组件

    13、小结
    springmvc url和controller映射成功后，默认产生一个单例的handler对象，并且这个对象中只有，映射的一个方法，
    没有其他没映射到的方法。
    springmvc因为传递的参数都是在方法形参中，因此多线程之间不影响，可以做到单例多线程
    可以改成多例，但是建议使用单例开发。

    14、包装类型Pojo参数绑定
    有时候对于复杂的查询条件，一个调价中有很多信息，比如用户信息查询条件和订单信息查询条件，
    所以对于一个包装类（包装所有查询条件）中的属性比较多，比较杂，因此可以在包装类中使用包装类，而不是简单的属性
    在页面的标签name中，可以写属性.属性的方式进行匹配。
    类里面包一个类还有一个好处，就是如果出现同名属性的时候，类里面有不同的类，可以进行区分开来

    15、对于校验的理解
      一般校验都是前端进行的校验，但是我们这里所说的校验针对服务端，且服务端校验是必要的
      因为客户端可能是浏览器，可能是手机客户端，因此，需要在服务端进行统一的校验处理，保证安全性，不能说浏览器
      做了js校验就完事了
      服务端校验：
         controller校验：针对从页面传过来的参数进行校验，不针对客户端类型而做统一校验
         service校验（使用较多）：主要针对业务层的参数进行校验
         dao：基本不做校验
      springMVC使用的是hibernate的校验框架，Validation(和hibernate没有任何关系)
       1）准备jar报环境
       2）写校验配置文件
       3)把校验器注入到处理器适配器中，因为是处理器适配器去执行处理器的
       一般都是针对Pojo编校验信息，因为从页面传参数之后，都是直接匹配具体的pojo信息。

    16、分组检验
      一般校验规则是在pojo中写，不同的Controller可能有不同的校验规则，而pojo是公用的，因此这就产生了问题，
      解决方法：定义多个校验分组，其实是一个java接口，分组中顶一个不同的校验规则，
      controller使用不同的校验分组

    17、数据回显
    回显意思是如果提交发生了 错误，那么提交的数据再次在原来编辑的页面中显示
    springMVC默认就支持了Pojo类型的数据回显
    数据传入controller之后，springMVC自动的把pojo数据放到了request域之中，其中request中的key就是pojo类型，首字母小写 。
    还有一种用在controller中的形参之前设置注解@ModelAttribute可以设置回显到request域中key
    18、异常处理
     异常处理的思路，系统的dao,service,controller,都向上层抛出exception,最后由springmvc前端控制器交由异常处理器进行统一处理。
     异常分为两种，一种是检查异常，这种异常我们都是捕获异常，处理异常
     但是对于运行时异常，这种主要通过代码的规范，和后期的测试来发现

     全局异常处理器的处理思路：
     判断异常的类型，看是否是我们自定义的异常类型
     如果该异常是自定义异常，那么直接取出异常信息，在错误页面 进行显示
     如果该异常不是自定义异常，基本就是运行时异常，那么此时构造一个自定义异常对象，信息为“未知错误”

     抛出异常的位置：
     如果异常和业务系统相关，则在service中抛出异常
     如果异常和业务系统没有关系，则在controller中抛出异常
     DAO层面一般不进行异常处理，查询到什么就是什么，Null就是null，一般在业务层面进行异常处理，
     比如查询一个商品的信息，如果在service中调用dao后查出来是null,那么此时在service中进行判断，Null的话
     就需要抛出商品不存在的 自定义异常，controller之后接着抛，由异常处理器对异常统一处理。

   19、上传图片
   在页面提交enctype="multipart/form-data"类型数据时候，需要SpringMVC对multipart/form-data这种数据进行解析
   在电商的正式项目中，一般会有一台专门的图片服务器，这里我们使用tomcat即作为应用服务器也作为图片服务器
   在tomcat中建立一个虚拟目录，专门用于存放图片。注意数据库中的图片存放放的是图片的名称

   20、json
     1)请求json，输出json,所以在前端页面中需要将请求的内容转成json,不太方便
     但是有的前端框架即使基于json的，比如ext，因此我们自己写的时候，就使用普通的key-value数据即可
     2）请求的是key-value,输出的是json,这也是常用也是我们推荐的
     @RequestBody把json串转换为java对象
     @ResponseBody把java对象转换成json串

   21、RESTful软件架构
     这是一个开发理念，是对http的很好的诠释
      1、对url进行规范，写RESTful格式的URL
         非RESTful格式的url: http://..../queryItems.action?id=1&type=t1
         RESTful风格的url: http://..../items/1
         特点：url简单，将参数通过url传递到服务端
      2、不管是删除、添加、还是更新、使用的url都是一样的，如果进行删除，需要设置http方法为delete,同理添加
         后台controller方法，判断http的方法，如果是delete执行删除，如果是post，执行添加
      3、对http的contentType进行规范
         请求时候，指定contentType,要json数据，设置成json格式的Type
   22、拦截器
      springmvc针对handlermapping进行拦截，
      如果在某个handlermapping中配置拦截，结果handlermapping映射成功的handler最终使用该拦截器
      springmvc也可以配置全局性的拦截器 ，此时框架把全局拦截器注入到每一个handlermapping中（推荐使用全局的）
























