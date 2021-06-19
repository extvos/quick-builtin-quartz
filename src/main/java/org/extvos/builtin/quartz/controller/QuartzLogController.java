package org.extvos.builtin.quartz.controller;

import org.extvos.builtin.quartz.service.QuartzLogService;
import org.extvos.restlet.QuerySet;
import org.extvos.restlet.controller.BaseController;
import org.extvos.restlet.exception.RestletException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.extvos.builtin.quartz.entity.QuartzLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author shenmc
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
