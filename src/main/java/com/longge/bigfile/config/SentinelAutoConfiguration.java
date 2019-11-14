 package com.longge.bigfile.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roger yang
 * @date 11/13/2019
 */
@Configuration
public class SentinelAutoConfiguration {
    
    
    @Bean
    public SentinelBootstrap sentinel(SentinelConfig config) {
        List<FlowRule> rules = new ArrayList<>(config.qps.size());
        config.qps.entrySet().forEach(item -> {
            FlowRule rule = new FlowRule();
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            rule.setResource(item.getKey());
            // Set limit QPS
            rule.setCount(item.getValue());
            rule.setStrategy(RuleConstant.STRATEGY_DIRECT);
            rules.add(rule);
        });
        FlowRuleManager.loadRules(rules);
        return new SentinelBootstrap();
    }
    
    @Configuration
    @ConfigurationProperties(prefix = "sentinel")
    @Valid
    @Getter
    @Setter
    static class SentinelConfig {
        @NotEmpty
        private Map<String, Integer> qps;
    }
    
    static class SentinelBootstrap {} 
}
