package com.liuxz.es.test;

import com.liuxz.es.EsDemoApp;
import com.liuxz.es.entity.Item;
import com.liuxz.es.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示基础入门
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApp.class)
public class TestFirst {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Item.class);
    }

    @Test
    public void delIndex() {
        elasticsearchTemplate.deleteIndex(Item.class);
    }

    /**
     * 新增一个对象
     */
    @Test
    public void insert() {
        // Item item = new Item()
        //         .setId(1L).setCategory("手机")
        //         .setBrand("小米10")
        //         .setPrice(2499.23)
        //         .setTitle("小米手机10")
        //         .setImages("http://aliyun.xiaomi.com/image/mi10");
        // Item item = new Item()
        //         .setId(3L).setCategory("笔记本")
        //         .setBrand("iPad Pro")
        //         .setPrice(9499.23)
        //         .setTitle("苹果笔记本系列")
        //         .setImages("http://aliyun.apple.com/image/iPadPro");
        Item item = new Item()
                .setId(4L).setCategory("电脑")
                .setBrand("mac pro")
                .setPrice(22499.63)
                .setTitle("苹果电脑系列")
                .setImages("http://aliyun.apple.com/image/MacPro");
        itemRepository.save(item);
    }

    /**
     * 批量新增对象
     */
    @Test
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米7", "手机", "小米", 1299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "小米8", "手机", "小米", 2299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "小米10", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));

        list.add(new Item(4L, "荣耀7", "手机", "华为", 1399.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀8", "手机", "华为", 2399.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(6L, "荣耀10", "手机", "华为", 3399.00, "http://image.baidu.com/13123.jpg"));

        list.add(new Item(7L, "苹果6p", "手机", "苹果", 4399.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(8L, "苹果7p", "手机", "苹果", 5399.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(9L, "苹果8p", "手机", "苹果", 6399.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    /**
     * elasticsearch中本没有修改，它的修改原理是该是先删除在新增。修改和新增是同一个接口，区分的依据就是id。
     */
    @Test
    public void update() {
        Item item = new Item()
                .setId(1L).setCategory("手机")
                .setBrand("小米10")
                .setPrice(2999.99)
                .setTitle("小米手机10")
                .setImages("http://aliyun.xiaomi.com/image/uuidssss");
        itemRepository.save(item);
    }


    @Test
    public void testQuery() {
        itemRepository.findAll(Sort.by("price").descending()).forEach(o -> System.out.println(o));
        Item item = itemRepository.findById(3L).get();
        System.out.println(item);
    }
}
