/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.extvos.builtin.quartz.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.extvos.builtin.quartz.mapper.QuartzJobMapper;
import org.extvos.builtin.quartz.service.QuartzJobService;
import lombok.RequiredArgsConstructor;
import org.extvos.builtin.quartz.entity.QuartzJob;
import org.extvos.builtin.quartz.utils.QuartzManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mingcai SHEN
 */
@Component
@RequiredArgsConstructor
@Order(2)
public class BuiltinQuartzJobRunnerInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(BuiltinQuartzJobRunnerInitializer.class);
    private final QuartzJobService quartzJobService;
    private final QuartzManage quartzManage;

    /**
     * 项目启动时重新激活启用的定时任务
     *
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        try {
            log.info("--------------------注入定时任务---------------------");
            QueryWrapper<QuartzJob> qw = new QueryWrapper<>();
            List<QuartzJob> quartzJobs = quartzJobService.selectByWrapper(qw);
            quartzJobs.forEach(quartzManage::addJob);
            log.info("--------------------定时任务注入完成---------------------");
        } catch (Exception e) {
            log.error("定时任务注入失败:", e);
        }
    }
}
