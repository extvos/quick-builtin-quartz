package plus.extvos.builtin.quartz.config;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import plus.extvos.builtin.quartz.entity.QuartzJob;
import plus.extvos.builtin.quartz.entity.QuartzLog;

import javax.sql.DataSource;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setLogWriter(dataSource.getLogWriter());
        String[] tableNames = new String[]{
            QuartzJob.class.getAnnotation(TableName.class).value(),
            QuartzLog.class.getAnnotation(TableName.class).value(),
        };
        for (int i = 0; i < tableNames.length; i++) {
            tableNames[i] = "'" + tableNames[i] + "'";
        }
        PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*)  FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = Database() AND TABLE_NAME IN (" + String.join(",", tableNames) + ");");
        ResultSet rs = statement.executeQuery();
        rs.next();
        int n = rs.getInt(1);
        rs.close();
        if (n < tableNames.length) {
            String[] sqlFiles = {"sql/01.builtin-quartz-schema.sql"};
            for (String path : sqlFiles) {
                Reader reader = Resources.getResourceAsReader(path);
                ;
                //执行SQL脚本
                runner.runScript(reader);
                //关闭文件输入流
                reader.close();
            }
        }
    }
}
