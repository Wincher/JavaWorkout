package cn.wincher.my_reactor.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor.entity <p>
 */
@Getter
@Setter
@ToString()
public class Father {
    private String name;
    public Father(String name) {
        this.name = name;
    }
}
