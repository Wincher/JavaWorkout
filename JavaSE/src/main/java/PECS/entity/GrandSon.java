package PECS.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor.entity <p>
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class GrandSon extends Son {
    public GrandSon(String name) {
        super(name);
    }
}
