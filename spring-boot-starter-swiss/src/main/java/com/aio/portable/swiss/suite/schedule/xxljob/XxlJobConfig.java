package com.aio.portable.swiss.suite.schedule.xxljob;

//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.context.annotation.Bean;

//@Configuration
//@ConfigurationProperties("xxl.job")
public class XxlJobConfig {
    private Admin admin;
    private Executor executor;
    private String accessToken;


    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


//    @Bean(initMethod = "start", destroyMethod = "destroy")
//    public XxlJobExecutor xxlJobExecutor() {
//        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
//        xxlJobExecutor.setAdminAddresses(admin.getAddresses());
//        xxlJobExecutor.setAppName(executor.getAppName());
//        xxlJobExecutor.setIp(executor.getIp());
//        xxlJobExecutor.setPort(executor.getPort());
//        xxlJobExecutor.setAccessToken(accessToken);
//        xxlJobExecutor.setLogPath(executor.getLogPath());
//        xxlJobExecutor.setLogRetentionDays(executor.getLogRetentionDays());
//
//        return xxlJobExecutor;
//    }

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(admin.getAddresses());
        xxlJobSpringExecutor.setAppName(executor.getAppName());
        xxlJobSpringExecutor.setIp(executor.getIp());
        xxlJobSpringExecutor.setPort(executor.getPort());
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(executor.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
