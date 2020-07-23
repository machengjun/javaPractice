package com.example.saas.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马成军
 * @description mybatis分页插件
 */
@Configuration
@MapperScan("com.example.saas.dao.mapper")
public class MybatisConfig {

    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

    /**
     * SQL执行效率插件
     * 仅在 dev test 环境开启
     */
    @Bean
    @Profile({"dev", "test", "dev-cloud"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }


    /**
     * 攻击 SQL 阻断解析器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser() {
            @Override
            public void processDelete(Delete delete) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                if ("user".equals(delete.getTable().getName())) {
                    System.out.println("跳过user 表");
                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
//                    return ;
                }
                super.processDelete(delete);
            }

            @Override
            public void processUpdate(Update update) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                List<Table> tables = update.getTables();
                for (Table table : tables) {
                    boolean isUser = "user".equals(table.getName());
                    Assert.isTrue(isUser, "不可以更新user表");
                    // 自定义跳过user
                }
                super.processUpdate(update);
            }

            @Override
            public void processInsert(Insert insert) {
                if ("user".equals(insert.getTable().getName())) {
                    System.out.println("跳过user 表");

                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
                    return;
                }
                super.processInsert(insert);
            }

        });
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

}