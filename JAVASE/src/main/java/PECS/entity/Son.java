package PECS.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor.entity <p>
 */
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Son extends Father {
    public Son(String name) {
        super(name);
    }
}
