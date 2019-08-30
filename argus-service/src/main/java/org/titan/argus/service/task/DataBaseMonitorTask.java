package org.titan.argus.service.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.DataBaseMonitor;
import org.titan.argus.service.impl.DataBaseMonitorServiceImpl;
import org.titan.argus.service.util.DateUtil;
import org.titan.argus.storage.es.domain.DataBaseMonitorOriginData;
import org.titan.argus.storage.es.repo.DataBaseMonitorOriginDataRepository;
import org.titan.argus.util.SnowFakeIdUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Title: DataBaseMonitorTask
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Component
@Slf4j
public class DataBaseMonitorTask {
    @Autowired
    private DataBaseMonitorServiceImpl monitorService;
    @Autowired
    private DataBaseMonitorOriginDataRepository dataRepository;
    private static final int size = 20;
    private LambdaQueryWrapper<DataBaseMonitor> wrapper = new LambdaQueryWrapper<>();
    @Autowired
    private DataBaseMonitorTaskAsync taskAsync;

    private Map<String, JdbcTemplate> map = new HashMap<>();

    private Map<String, Object> ret = new HashMap<>(32, 4.0F);

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        long now = DateUtil.currentTime();
        Collection<DataBaseMonitor> monitors;
        while (!(monitors = this.monitorService.page(new Page<>(1, size), wrapper).getRecords()).isEmpty()) {
            monitors.forEach(monitor -> {
                try {
                    JdbcTemplate template = this.getDataSource(monitor);
                    Map<String, Object> r = template.query(this.getSql(), rs -> {
                        while (rs.next()) {
                            ret.put(rs.getString("Variable_name"), rs.getObject("Value"));
                        }
                        return ret;
                    });

                    DataBaseMonitorOriginData data = new DataBaseMonitorOriginData();
                    data.setMap(r).setPort(monitor.getPort()).setDbName(monitor.getDbName()).setIp(monitor.getHost())
                            .setId(SnowFakeIdUtil.snowFakeId()).setTime(now);
                    ret.clear();
                    this.taskAsync.doAsync(data);
                } catch (Exception e) {
                    log.error("error to execute task", e);
                }
            });
        }
    }


//    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<>();
//        DriverManagerDataSource source = new DriverManagerDataSource();
//        source.setDriverClassName("com.mysql.jdbc.Driver");
//        source.setUrl("jdbc:mysql://localhost:3306/mysql");
//        source.setUsername("root");
//        source.setPassword("zyp231021");
//        JdbcTemplate template = new JdbcTemplate(source);
//        Map<String, Object> m = template.query("show global status", rs -> {
//            while (rs.next()) {
//                map.put(rs.getString("Variable_name"), rs.getObject("Value"));
//            }
//            return map;
//        });
//
//        System.out.println();
//    }

    private String getSql() {

        return "show global status";
    }

    private JdbcTemplate getDataSource(DataBaseMonitor monitor) {
        String key = monitor.getHost() + "_" + monitor.getPort() + "_" + monitor.getDbName() + "_" + monitor.getUsername()
                + "_" + monitor.getPassword();

        JdbcTemplate template = map.get(key);

        if (Objects.isNull(template)) {
            DriverManagerDataSource source = new DriverManagerDataSource();
            source.setDriverClassName("com.mysql.jdbc.Driver");
            source.setUrl("jdbc:mysql://" + monitor.getHost() + ":" + monitor.getPort() + "/" + monitor.getDbName());
            source.setUsername(monitor.getUsername());
            source.setPassword(monitor.getPassword());
            try {
                template = new JdbcTemplate(source);
                this.map.put(key, template);
            } catch (Exception e) {
                log.error("error to init jdbc template,monitor:" + monitor.toString(), e);
            }
        }

        return template;
    }
}
