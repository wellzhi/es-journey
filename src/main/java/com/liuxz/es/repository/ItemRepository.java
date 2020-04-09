package com.liuxz.es.repository;

import com.liuxz.es.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;


/**
 * @Description:定义ItemRepository 接口
 * @Param: Item:为实体类
 * Long:为Item实体类中主键的数据类型
 * @Author: https://blog.csdn.net/chen_2890
 * @Date: 2020/04/09 10:50
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
    /**
     * 等于（模糊查询）
     */
    List<Item> findByTitle(String title);

    /**
     * 多字段or查询：Or拼接几个字段，方法就几个参数
     */
    List<Item> findByTitleOrBrand(String title, String brand);

    /**
     * 多字段and查询：Or拼接几个字段，方法就几个参数
     */
    List<Item> findByTitleAndBrand(String title, String brand);

    /**
     * 不等于
     */
    List<Item> findByTitleNot(String title);

    /**
     * 小于等于
     */
    List<Item> findByPriceLessThan(double price);

    /**
     * 大于等于
     */
    List<Item> findByPriceGreaterThan(double price);

    /**
     * 区间查询（含边界）
     */
    List<Item> findByPriceBetween(double price1, double price2);


    /**
     * 模糊查询，类似 %keyword% 查询
     */
    List<Item> findByTitleLike(String title);

    /**
     * 右模糊查询，类似 keyword% 查询
     */
    List<Item> findByTitleStartingWith(String title);

    /**
     * 左模糊查询，类似 %keyword 查询
     */
    List<Item> findByTitleEndingWith(String title);

    /**
     * contains查询
     */
    List<Item> findByTitleContaining(String title);


    /**
     * in查询
     */
    List<Item> findByBrandIn(Collection<String> brands);

    /**
     * not in查询
     */
    List<Item> findByBrandNotIn(Collection<String> brands);

}
