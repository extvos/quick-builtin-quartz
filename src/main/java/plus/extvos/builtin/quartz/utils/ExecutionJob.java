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
package plus.extvos.builtin.quartz.utils;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import plus.extvos.builtin.quartz.entity.QuartzJob;
import plus.extvos.builtin.quartz.entity.QuartzLog;
import plus.extvos.builtin.quartz.mapper.QuartzLogMapper;
import plus.extvos.builtin.quartz.service.QuartzJobService;
import plus.extvos.common.utils.SpringContextHolder;
import plus.extvos.common.utils.ThrowableUtil;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 *
 * @author /
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    /**
     * 该处仅供参考
     */
    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.newFixedThreadPool(5);

    @Override
    public void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
        // 获取spring bean
        QuartzLogMapper quartzLogMapper = SpringContextHolder.getBean(QuartzLogMapper.class);
        QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);
//        RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);

        String uuid = quartzJob.getUuid();

        QuartzLog quartzLog = new QuartzLog();
        quartzLog.setJobName(quartzJob.getJobName());
        quartzLog.setBeanName(quartzJob.getBeanName());
        quartzLog.setMethodName(quartzJob.getMethodName());
        quartzLog.setParams(quartzJob.getParams());
        long startTime = System.currentTimeMillis();
        quartzLog.setCronExpression(quartzJob.getCronExpression());
        try {
            // 执行任务
            System.out.println("--------------------------------------------------------------");
            System.out.println("任务开始执行，任务名称：" + quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                quartzJob.getParams());
            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            quartzLog.setDuration(times);
//            if(StringUtils.isNotBlank(uuid)) {
//                redisUtils.set(uuid, true);
//            }
            // 任务状态
            quartzLog.setSuccess(true);
            System.out.println("任务执行完毕，任务名称：" + quartzJob.getJobName() + ", 执行时间：" + times + "毫秒");
            System.out.println("--------------------------------------------------------------");
            // 判断是否存在子任务
            if (quartzJob.getSubTask() != null) {
                String[] tasks = quartzJob.getSubTask().split("[,，]");
                // 执行子任务
//                quartzJobService.executionSubJob(tasks);
            }
        } catch (Exception e) {
//            if(StringUtils.isNotBlank(uuid)) {
//                redisUtils.set(uuid, false);
//            }
            System.out.println("任务执行失败，任务名称：" + quartzJob.getJobName());
            System.out.println("--------------------------------------------------------------");
            long times = System.currentTimeMillis() - startTime;
//            e.printStackTrace();
//            log.setExceptionDetail(e.getMessage());
            quartzLog.setDuration(times);
            // 任务状态 0：成功 1：失败
            quartzLog.setSuccess(false);
            quartzLog.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            // 任务如果失败了则暂停
            if (quartzJob.getPauseAfterFailure() != null && quartzJob.getPauseAfterFailure()) {
                quartzJob.setPaused(false);
                //更新状态
//                quartzJobService.updateIsPause(quartzJob);
            }
            if (quartzJob.getEmail() != null) {
//                EmailService emailService = SpringContextHolder.getBean(EmailService.class);
//                // 邮箱报警
//                if(StringUtils.isNoneBlank(quartzJob.getEmail())){
//                    EmailVo emailVo = taskAlarm(quartzJob, ThrowableUtil.getStackTrace(e));
//                    emailService.send(emailVo, emailService.find());
//                }
            }
        } finally {
            quartzLog.setId(null);
            quartzLogMapper.insert(quartzLog);
        }
    }

//    private EmailVo taskAlarm(QuartzJob quartzJob, String msg) {
//        EmailVo emailVo = new EmailVo();
//        emailVo.setSubject("定时任务【"+ quartzJob.getJobName() +"】执行失败，请尽快处理！");
//        Map<String, Object> data = new HashMap<>(16);
//        data.put("task", quartzJob);
//        data.put("msg", msg);
//        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
//        Template template = engine.getTemplate("email/taskAlarm.ftl");
//        emailVo.setContent(template.render(data));
//        List<String> emails = Arrays.asList(quartzJob.getEmail().split("[,，]"));
//        emailVo.setTos(emails);
//        return emailVo;
//    }
}
