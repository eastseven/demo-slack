package cn.eastseven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

/**
 * @author d7
 *
 * @see [SpringBoot | 第二十五章：日志管理之自定义Appender](https://blog.lqdev.cn/2018/08/25/springboot/chapter-twenty-five/)
 */
@Slf4j
@SpringBootApplication
public class DemoSlackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DemoSlackApplication.class, args);

        // SpringApplication.exit(ctx, () -> 0);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> running ...");

        /*try {
            log.warn(">>> 开始测试异常抛出");
            String a = null;
            a.equals("a");
        } catch (Exception e) {
            log.error("", e);
        }*/
    }

    @EventListener
    public void handleStoppedEvent(ContextStoppedEvent event) {
        log.info(">>> ContextStoppedEvent");
    }

    @EventListener
    public void handleClosedEvent(ContextClosedEvent event) {
        log.info(">>> ContextClosedEvent");
    }
}
