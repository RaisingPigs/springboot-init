package com.pan.app.util;

import com.pan.app.event.BaseEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 15:27
 **/
public class EventPublishUtils {
    private static final ApplicationEventPublisher EVENT_PUBLISHER = SpringContextUtils.getApplicationContext();

    public static void publishEvent(BaseEvent<?, ?> event) {
        EVENT_PUBLISHER.publishEvent(event);
    }
}
