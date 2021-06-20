package org.extvos.builtin.quartz.service.impl;

import org.extvos.builtin.quartz.entity.QuartzLog;
import org.extvos.builtin.quartz.mapper.QuartzLogMapper;
import org.extvos.builtin.quartz.service.QuartzLogService;
import org.extvos.restlet.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mingcai SHEN
 */
@Service
public class QuartzLogServiceImpl extends BaseServiceImpl<QuartzLogMapper, QuartzLog> implements QuartzLogService {
    @Autowired
    private QuartzLogMapper myMapper;

    @Override
    public QuartzLogMapper getMapper() {
        return myMapper;
    }
}
