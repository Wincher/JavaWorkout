package ch02_grant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author huwq
 * @since 2018/7/1
 * <p> ch02_grant <p>
 */
public class PermissionTest {
    
    
    @Test
    public void testIsPermitted() {
        login("classpath:ch02/shiro-permission.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
        
        Assert.assertTrue(subject.isPermitted("user:create"));
        
        Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));
        
        Assert.assertFalse(subject.isPermitted("user:detail"));
        
    }
    
    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission() {
        login("classpath:ch02/shiro-role.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
        
        subject.checkPermission("user:create");
        
        //检验拥有角色,失败抛出异常
        subject.checkPermissions("user:detail", "user:delete");
        
    }
    
    private void login(String configFile, String username, String password) {
        //1.获取SecurityManager工厂,此处通过ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        //2.得到SecurityManager实例,绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.得到Subject以及创建用户名/密码身份验证Token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }
    
    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();
    }
}
