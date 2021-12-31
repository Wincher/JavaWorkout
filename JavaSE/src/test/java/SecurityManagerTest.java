import org.junit.Test;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class SecurityManagerTest {

    @Test
    public void test() {
        RuntimePermission modifyThreadPermission = new RuntimePermission("modifyThread");

        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(modifyThreadPermission);
    }
    @Test
    public void test2() {
        RuntimePermission modifyThreadPermission = new RuntimePermission("modifyThread");

        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(modifyThreadPermission);
    }
}
