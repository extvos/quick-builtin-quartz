package org.extvos.builtin.quartz.service.impl;

import org.extvos.builtin.quartz.entity.QuartzJob;
import org.extvos.builtin.quartz.mapper.QuartzJobMapper;
import org.extvos.builtin.quartz.service.QuartzJobService;
import org.extvos.restlet.exception.RestletException;
import org.extvos.restlet.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Mingcai SHEN
 */
@Service
public class QuartzJobServiceImpl extends BaseServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {
    @Autowired
    private QuartzJobMapper myMapper;

    @Override
    public QuartzJobMapper getMapper() {
        return myMapper;
    }

    @Override
    public int insert(QuartzJob entity) throws RestletException {
        entity.setUuid(UUID.randomUUID().toString());
        return super.insert(entity);
    }
}
