package plus.extvos.builtin.quartz.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.extvos.builtin.quartz.entity.QuartzLog;
import plus.extvos.builtin.quartz.service.QuartzLogService;
import plus.extvos.restlet.QuerySet;
import plus.extvos.restlet.controller.BaseController;
import plus.extvos.restlet.exception.RestletException;

import java.io.Serializable;

/**
 * @author Mingcai SHEN
 */
@Slf4j
@RestController
@RequestMapping("/_builtin/quartz/logs")
@Api(tags = "任务日志管理")
public class QuartzLogController extends BaseController<QuartzLog, QuartzLogService> {

    @Autowired
    private QuartzLogService myService;

    @Override
    public QuartzLogService getService() {
        return myService;
    }

    @Override
    public QuartzLog preInsert(QuartzLog entity) throws RestletException {
        throw RestletException.forbidden("not allowed to create new record");
    }

    @Override
    public QuartzLog preUpdate(Serializable id, QuartzLog entity) throws RestletException {
        throw RestletException.forbidden("not allowed to update record(s)");
    }

    @Override
    public QuartzLog preUpdate(QuerySet<QuartzLog> qs, QuartzLog entity) throws RestletException {
        throw RestletException.forbidden("not allowed to update record(s)");
    }
}
