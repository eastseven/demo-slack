package cn.eastseven;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author d7
 */
@Getter
@Setter
public class MyLogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    Layout<ILoggingEvent> layout;

    /**
     * 自定义配置
     */
    String printString;

    String slackUrl;

    @Override
    public void start() {
        //这里可以做些初始化判断 比如layout不能为null ,
        if (layout == null) {
            addWarn("Layout was not defined");
        }
        //或者写入数据库 或者redis时 初始化连接等等
        super.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                // 执行资源释放操作
                SlackClient.send(slackUrl, "ShutdownHook");
            }
        }));
    }


    @Override
    public void stop() {
        //释放相关资源，如数据库连接，redis线程池等等
        SlackClient.send(slackUrl, "logback-stop方法被调用");
        if (!isStarted()) {
            return;
        }
        super.stop();
    }

    @Override
    public void append(ILoggingEvent event) {
        if (event == null || !isStarted()) {
            return;
        }
        // 此处自定义实现输出
        // 获取输出值：event.getFormattedMessage()
        // System.out.print(event.getFormattedMessage());
        // 格式化输出

        String text = layout.doLayout(event);
        System.out.print(printString + "：" + text);

        Level level = event.getLevel();
        if (Level.WARN.equals(level) || Level.ERROR.equals(level)) {
            SlackClient.send(slackUrl, text);
        }

    }
}
