package plus.extvos.builtin.quartz.config;

import com.baomidou.mybatisplus.annotation.TableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import plus.extvos.builtin.quartz.entity.QuartzJob;
import plus.extvos.builtin.quartz.entity.QuartzLog;
import plus.extvos.restlet.utils.DatabaseHelper;

import javax.sql.DataSource;

/**
 * @author Mingcai SHEN
 */
@Component
@Order(1)
public class BuiltinQuartzInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(BuiltinQuartzInitializer.class);

    @Autowired
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("BuiltinQuartzInitializer ::run:> ...");
        DatabaseHelper dh = DatabaseHelper.with(dataSource);

        if (dh.tableAbsent(QuartzJob.class.getAnnotation(TableName.class).value(), QuartzLog.class.getAnnotation(TableName.class).value()) > 0) {
            dh.runScriptsIfMySQL("sql/mysql/01.builtin-quartz-schema.sql");
            dh.runScriptsIfPostgreSQL("sql/pg/01.builtin-quartz-schema.sql");
        }
    }
}
