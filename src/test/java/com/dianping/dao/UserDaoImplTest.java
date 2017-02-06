package com.dianping.dao;

import com.dianping.po.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * UserDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class UserDaoImplTest {

    /*使用spring就不用自己获取sqlsessionfactory了，而是直接加载spring容器即可
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before() throws Exception {
        String xml= "mybatis/SqlMapConfig.xml";
        InputStream input= Resources.getResourceAsStream(xml);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
    }*/
    private ApplicationContext applicationContext;
    /**
     * Method: findUserById(int id)
     */
    @Test
    public void testFindUserById() throws Exception {
        //UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        applicationContext=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserDao userDao= (UserDao) applicationContext.getBean("userDaoImpl");
        User u=userDao.findUserById(1);
        System.out.println(u);
    }

    /**
     * Method: insertUser(User user)
     */
    /*@Test
    public void testInsertUser() throws Exception {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User u=new User();
        u.setUsername("小王3");
        u.setBirthday(new Date());
        u.setSex("女");
        u.setAddress("非洲尼日尼亚3");
        userDao.insertUser(u);
        System.out.println(u.getId());
    }*/

    /**
     * Method: deleteUserById(int id)
     */
   /* @Test
    public void testDeleteUserById() throws Exception {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        userDao.deleteUserById(9);

    }*/


} 
