package com.dusun.whitewhale.discovery.config.bootstrap;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼           BUG辟易
//
//                             佛曰:
//
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

//
//                       .::::.
//                     .::::::::.
//                    :::::::::::  howdy!
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..
//

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

/**
 * 应用启动拉取配置文件抛出异常重试策略。
 * @author 程绍壮
 * @datetime 2019-09-09 14:00
 */
public class ServiceBootstrapRetryConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceBootstrapRetryConfig.class);

    private final Environment env;

    public ServiceBootstrapRetryConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @ConditionalOnMissingBean(name = "configServerRetryInterceptor")
    public RetryOperationsInterceptor configServerRetryInterceptor() {
        int initialInterval = Integer.parseInt(String.valueOf(
                this.env.getProperty("spring.cloud.config.fetch.fail.retry.initialInterval")));
        double multiplier = Double.parseDouble(String.valueOf(
                this.env.getProperty("spring.cloud.config.fetch.fail.retry.multiplier")));
        int maxInterval = Integer.parseInt(String.valueOf(
                this.env.getProperty("spring.cloud.config.fetch.fail.retry.maxInterval")));
        int maxAttempts = Integer.parseInt(String.valueOf(
                this.env.getProperty("spring.cloud.config.fetch.fail.retry.maxAttempts")));
        LOG.warn("构建自定义重试拦截器, 如果拉取配置服务【"+this.env.getProperty("spring.cloud.config.uri")
                +"】失败的情况下会进行重试。当达到定义的失败次数该服务会宕机。");
        return RetryInterceptorBuilder.stateless().backOffOptions(initialInterval, multiplier, maxInterval)
                .maxAttempts(maxAttempts).build();
    }
}
