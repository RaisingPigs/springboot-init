package com.pan.app.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 15:01
 **/

@Getter
@ToString
public class BaseEvent<S, D> extends ApplicationEvent {
    private final D data;

    public BaseEvent(S source, D data) {
        super(source);
        this.data = data;
    }

    public S getSource() {
        return (S) source;
    }
}
