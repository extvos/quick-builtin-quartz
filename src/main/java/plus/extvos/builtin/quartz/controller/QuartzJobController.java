package plus.extvos.builtin.quartz.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.extvos.builtin.quartz.entity.QuartzJob;
import plus.extvos.builtin.quartz.service.QuartzJobService;
import plus.extvos.builtin.quartz.utils.QuartzManage;
import plus.extvos.restlet.QuerySet;
import plus.extvos.restlet.controller.BaseController;
import plus.extvos.common.exception.ResultException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mingcai SHEN
 */
@Slf4j
@RestController
@RequestMapping("/_builtin/quartz/jobs")
@Api(tags = "定时任务管理")
public class QuartzJobController extends BaseController<QuartzJob, QuartzJobService> {

    @Autowired
    private QuartzManage quartzManage;

    @Autowired
    private QuartzJobService myService;

    @Override
    public QuartzJobService getService() {
        return myService;
    }

    @Override
    public void postInsert(QuartzJob entity) throws ResultException {
        quartzManage.addJob(entity);
    }

    @Override
    public void postUpdate(Serializable id, QuartzJob entity) throws ResultException {

    }

    @Override
    public void postUpdate(QuerySet<QuartzJob> qs, QuartzJob entity) throws ResultException {

    }

    @Override
    public void preDelete(Serializable id) throws ResultException {
        QuartzJob job = myService.selectById(null, id);
        quartzManage.deleteJob(job);
    }

    @Override
    public QuerySet<QuartzJob> preDelete(QuerySet<QuartzJob> qs) throws ResultException {
        List<QuartzJob> jobs = myService.selectByMap(qs);
        jobs.forEach(quartzManage::addJob);
        return qs;
    }
}
