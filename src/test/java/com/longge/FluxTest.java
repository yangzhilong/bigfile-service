 package com.longge;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Flux;

/**
 * @author roger yang
 * @date 11/19/2019
 */
public class FluxTest {
    
    @Test
    public void test1() throws Exception {
        // range
        List<Integer> list = Flux.range(1, 5).collectList().block();
        System.out.println(JSONObject.toJSONString(list));

        System.out.println("------------");
        // just
        Flux.just(list).subscribe(System.err::println);
        
        System.out.println("------------");
        
        Flux.just("1", "2", "3").subscribe(System.out::println);
        
        System.out.println("------------");
        
        // fromIterable
        Flux.fromIterable(list).subscribe(item -> {
           System.err.println(item); 
        });
        Flux.fromIterable(list).subscribe(System.out::println);
        
        System.out.println("------------");
        
        // from 0 to max
        CountDownLatch latch = new CountDownLatch(1);
        Flux.interval(Duration.ofMillis(200))
            .doOnNext(val -> {
                if(val == 5) {
                    latch.countDown();
                }
            })
            .take(5)
            .doOnComplete(() -> latch.countDown())
            .subscribe(System.err::println);
        latch.await();
        
        System.out.println("------------");
        
        // empty
        // 如果不调用subscribe方法，doOnComplete将不会执行
        Flux.empty()
            .doOnComplete(() -> System.out.println("data no found"))
            .subscribe();
        
        System.out.println("------------");
        
        // error
        Flux.error(new NullPointerException("test excepton"))
        .doOnError(err -> {
            System.err.println(err.getMessage());
        }).subscribe();
        
        System.out.println("------------");
        
        // never
        // 啥事都不干
        Flux.never()
            .doOnComplete(() -> {
                System.out.println("never complete");
            })
            .subscribe();
        
        System.out.println("------------");
        
        // generate
        Flux.generate(i -> {
            i.next("1");
            i.complete();
        })
        .subscribe(System.out::println);
        
        System.out.println("------------");
        
        // create
        Flux.create(item -> {
            for(int i=1; i<=5; i++) {
                item.next(i);
                if(i==5) {
                    item.complete();
                }
            }
        }).subscribe(System.err::println);
        
        System.out.println("------------");
    }
    
    @Test
    public void test2() throws Exception {
        // buffer
        Flux.range(1, 5).buffer(2).subscribe(item -> {
            // ArrayList
            // 调用三次，每次2个元素的list
            System.out.println(item);
        });
        
        System.out.println("------------");
        
        Flux.range(1, 5).buffer(2).buffer(Duration.ofMillis(500)).subscribe(item -> {
            // List<List<Integer>>
            System.err.println(item);
        });
        
        Thread.sleep(1500L);
        
        System.out.println("------------");
        
        Flux.range(1, 5).bufferTimeout(3, Duration.ofSeconds(2)).subscribe(item -> {
            System.out.println(item);
        });
        
        System.out.println("------------");
        
        // zip
        // 木桶理论，最少元素的个数为最终生成的zip的个数
        Flux.range(1, 5).zipWithIterable(Arrays.asList(6,7,8)).subscribe(System.err::println);
        Thread.sleep(500L);
        
        System.out.println("------------");
        
        Flux.range(1, 5).zipWith(Flux.just(6, 7, 8)).zipWithIterable(Arrays.asList(9, 10, 11, 12)).subscribe(System.out::println);
    }
    
    @Test
    public void testTake() throws Exception {
        Flux.range(1, 10).take(3).subscribe(System.out::println);
        
        Thread.sleep(500L);
        System.out.println("--------------");
        
        Flux.range(1, 10).takeLast(3).subscribe(System.err::println);
        
        Thread.sleep(500L);
        System.out.println("--------------");
        
        // 条件为true时获取，如果一开始为false则一个都不取
        Flux.range(1, 10).takeWhile(c -> c >= 1 && c < 5).subscribe(System.out::println);
        
        Thread.sleep(500L);
        System.out.println("--------------");
        
        // 先取元素，直接条件为true时停止
        Flux.range(1, 10).takeUntil(c -> c > 1 && c < 5).subscribe(System.err::println);
        
        Thread.sleep(500L);
        System.out.println("--------------");
        
        // 则是先取元素，直到别一个Flux序列产生元素
        Flux.range(1, 4).takeUntilOther(Flux.never()).subscribe(System.out::println);
    }
    
    @Test
    public void testCombine() {
        Flux.combineLatest(
            Arrays::toString,
            Flux.just(0, 1),
            Flux.just("A", "B"))
            .toStream().forEach(System.out::println);
        
        System.out.println("--------------");
        
        Flux.first(Flux.just(1,2,3)).subscribe(System.out::println);
        
        System.out.println("--------------");
        
        Flux.first(Flux.just(1,2,3), Flux.just(4,5)).subscribe(System.out::println);
    }
}
