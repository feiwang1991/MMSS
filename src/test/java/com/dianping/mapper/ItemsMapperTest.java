package com.dianping.mapper;

import com.dianping.po.Items;
import com.dianping.po.ItemsExample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * created by IntelliJ IDEA
 */
public class ItemsMapperTest {

    private ApplicationContext applicationContext;
    private ItemsMapper itemsMapper;
    @Before
    public void setUp() throws  Exception{
        applicationContext=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        itemsMapper= (ItemsMapper) applicationContext.getBean("itemsMapper");
    }
    @Test
    public void testInsertByPrimaryKey() throws Exception {
        Items items=new Items();
        items.setId(16);
        items.setName("小米");
        items.setPrice(999F);
        itemsMapper.insert(items);

    }
    //根据自定义条件查询
    @Test
    public void testSelectByExample() throws Exception {
        //自定义查询就是把条件放到对象里面进行查询，类似hibernate以前那种
        //但是这个只能针对简单的一些条件，即内置的进行查询，但是对于设计到复杂的多表是不可以的
        ItemsExample itemsExample=new ItemsExample();
        ItemsExample.Criteria criteria=itemsExample.createCriteria();
        criteria.andNameEqualTo("苹果电脑");
        List<Items> itemsExampleList=itemsMapper.selectByExample(itemsExample);
        for (Items items : itemsExampleList) {
            System.out.println(items.getName()+"  "+items .getDetail());
        }
    }
    //根据主键查询
    @Test
    public void testSelectByPrimaryKey() throws Exception {
        Items items=itemsMapper.selectByPrimaryKey(1);
        System.out.println(items.getName());
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        //更新有两种情况，一种是直接把传入的对象更新，这种，如果有的属性没有赋值
        //则会把该列的空属性覆盖到数据库，非常危险，因此需要先查询，修改后再跟新
        Items items=new Items();
        items.setId(2);
        items.setName("水果电脑");
        //itemsMapper.updateByPrimaryKey(items);

        //另一种，是若更新对象有的属性为空，那么空的那个属性是不会覆盖到数据库中
        itemsMapper.updateByPrimaryKeySelective(items);
    }
}