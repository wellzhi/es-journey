package com.liuxz.es.test;

import com.liuxz.es.EsDemoApp;
import com.liuxz.es.entity.Item;
import com.liuxz.es.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApp.class)
public class TestQueryWrapper {
    @Autowired
    private ItemRepository itemRepository;


    /**
     * 普通查询
     */
    @Test
    public void testMathQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("brand", "小米"));
        Page<Item> items = itemRepository.search(builder.build());
        System.out.println("total = " + items.getTotalElements());
        System.out.println("pages = " + items.getTotalPages());
        items.forEach(o -> System.out.println(o));
    }

    /**
     * termQuery:功能更强大，除了匹配字符串以外，还可以匹配int/long/double/float/....
     */
    @Test
    public void testTermQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("brand", "华为"));
        itemRepository.search(builder.build()).forEach(o -> System.out.println(o));
    }

    /**
     * 布尔查询
     */
    @Test
    public void testBooleanQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title", "荣耀"))
                .must(QueryBuilders.matchQuery("brand", "华为"))
        );
        itemRepository.search(builder.build()).forEach(o -> System.out.println(o));
    }

    /**
     * 模糊查询
     */
    @Test
    public void testFuzzyQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("title", "7"));
        itemRepository.search(builder.build()).forEach(o -> System.out.println(o));
    }

    /**
     * 分页查询：Elasticsearch中的分页是从第0页开始。
     */
    @Test
    public void searchByPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 5;
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        showResult(items);
    }

    private void showResult(Page<Item> items) {
        // 总条数
        System.out.println("总条数：" + items.getTotalElements());
        // 总页数
        System.out.println("总页数：" + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());
        // 查询结果
        items.forEach(o -> System.out.println(o));
    }

    /**
     * 查询并排序
     */
    @Test
    public void searchAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        // 搜索获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        showResult(items);
    }


}
