package cn.wincher.my_reactor.entity;

import lombok.ToString;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor.entity <p>
 */
@ToString(callSuper = true)
public class Son extends Father {
    public Son(String name) {
        super(name);
    }
}
