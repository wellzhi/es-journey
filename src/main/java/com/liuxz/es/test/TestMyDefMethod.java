package com.liuxz.es.test;

import com.liuxz.es.EsDemoApp;
import com.liuxz.es.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;

/**
 * 自定义方法---遵循Spring Data的命名规范,无需自己实现接口实现方法
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApp.class)
public class TestMyDefMethod {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void find() {

        // itemRepository.findByTitle("苹果").forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleNot("华为").forEach(o -> System.out.println(o));

        // 区间查询
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByPriceLessThan(3000).forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByPriceGreaterThan(9499.23).forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByPriceBetween(1299, 2299).forEach(o -> System.out.println(o));

        // 多字段查询
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleOrBrand("荣耀","小米").forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleAndBrand("小米","小米").forEach(o -> System.out.println(o));

        // 模糊查询
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleLike("7").forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleStartingWith("荣耀").forEach(o -> System.out.println(o));
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleEndingWith("p").forEach(o -> System.out.println(o));

        // contain查询
        // System.out.println("-------------------------------------------------------------------");
        // itemRepository.findByTitleContaining("耀").forEach(o -> System.out.println(o));

        // in、notIn查询
        System.out.println("-------------------------------------------------------------------");
        Collection<String> in = new HashSet<>();
        in.add("华为");
        in.add("小米");
        itemRepository.findByBrandIn(in).forEach(o -> System.out.println(o));
        System.out.println("-------------------------------------------------------------------");
        itemRepository.findByBrandNotIn(in).forEach(o -> System.out.println(o));
    }
}
