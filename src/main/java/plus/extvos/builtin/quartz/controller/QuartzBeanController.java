package plus.extvos.builtin.quartz.controller;

import plus.extvos.builtin.quartz.annotation.QuartzEnabled;
import plus.extvos.builtin.quartz.vo.BeanDescriptor;
import plus.extvos.builtin.quartz.vo.BeanMethod;
import plus.extvos.builtin.quartz.vo.BeanParameter;
import plus.extvos.restlet.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Mingcai SHEN
 */
@Slf4j
@RestController
@RequestMapping("/_builtin/quartz/beans")
@Api(tags = "Java Bean 管理")
public class QuartzBeanController {

    private List<BeanDescriptor> allBeans;

    @Autowired
    private ApplicationContext applicationContext;

    @ApiOperation(value = "获取可用BEAN", notes = "根据条件列举出系统中可用于执行定时任务的BEAN")
    @GetMapping()
    public Result<List<BeanDescriptor>> getAllBeans() {
        if (null == allBeans) {
            allBeans = new ArrayList<>();
            Map<String, Object> beans = applicationContext.getBeansWithAnnotation(QuartzEnabled.class);
            beans.forEach((name, obj) -> {
                BeanDescriptor bd = new BeanDescriptor();
                bd.setName(name);
                bd.setDescription(obj.getClass().getAnnotation(QuartzEnabled.class).value());
                List<BeanMethod> methods = new ArrayList<>();
                for (Method m : obj.getClass().getDeclaredMethods()) {
                    BeanMethod bm = new BeanMethod();
                    bm.setName(m.getName());
                    List<BeanParameter> params = new ArrayList<>();
                    for (Class<?> c : m.getParameterTypes()) {
                        BeanParameter bp = new BeanParameter();
                        bp.setType(c.getSimpleName());
                        bp.setName(c.getSimpleName());
                        params.add(bp);
                    }
                    bm.setParameters(params);
                    methods.add(bm);
                }
                bd.setMethods(methods);
                allBeans.add(bd);
            });
        }
        return Result.data(allBeans).success();
    }
}
