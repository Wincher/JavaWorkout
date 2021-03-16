package PECS.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wincher
 * <p> cn.wincher.my_reactor.entity <p>
 */
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class Father {
    private String name;

    public Father(String name) {
        this.name = name;
    }
}
