package plus.extvos.builtin.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.extvos.builtin.quartz.entity.QuartzJob;
import plus.extvos.builtin.quartz.mapper.QuartzJobMapper;
import plus.extvos.builtin.quartz.service.QuartzJobService;
import plus.extvos.common.exception.ResultException;
import plus.extvos.restlet.service.impl.BaseServiceImpl;

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
    public int insert(QuartzJob entity) throws ResultException {
        entity.setUuid(UUID.randomUUID().toString());
        return super.insert(entity);
    }
}
