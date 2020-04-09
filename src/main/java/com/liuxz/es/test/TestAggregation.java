package com.liuxz.es.test;

import com.liuxz.es.EsDemoApp;
import com.liuxz.es.entity.Item;
import com.liuxz.es.repository.ItemRepository;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 演示es中的聚合与嵌套聚合
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApp.class)
public class TestAggregation {
    @Autowired
    private ItemRepository itemRepository;

    /**
     * 聚合为桶：按照品牌brand进行分组
     */
    @Test
    public void aggregation() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        builder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) itemRepository.search(builder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        agg.getBuckets().forEach(bucket -> {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        });
    }

    /**
     * 嵌套聚合，求平均值、最大值、最小值
     */
    @Test
    public void subAggregation() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        builder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        // 在品牌聚合桶内进行嵌套聚合，求平均值
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price"))
                        // 在品牌聚合桶内进行嵌套聚合，求最大值
                        .subAggregation(AggregationBuilders.max("priceMax").field("price"))
                        // 在品牌聚合桶内进行嵌套聚合，求最小值
                        .subAggregation(AggregationBuilders.min("priceMin").field("price"))
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) itemRepository.search(builder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        agg.getBuckets().forEach(bucket -> {
            // 3.4、获取桶中的key，即品牌名称-文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");
            // 3.5.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
            InternalMax priceMax = (InternalMax) bucket.getAggregations().asMap().get("priceMax");
            System.out.println("最高售价：" + priceMax.getValue());
            InternalMin priceMin = (InternalMin) bucket.getAggregations().asMap().get("priceMin");
            System.out.println("最低售价：" + priceMin.getValue());
        });
    }
}
