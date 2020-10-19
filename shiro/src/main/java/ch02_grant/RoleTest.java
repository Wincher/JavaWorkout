package ch02_grant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author huwq
 * @date 2018/6/30
 * <p> ch01_authentication <p>
 */
public class RoleTest {
    
    @Test
    public void testHasRole() {
        login("classpath:ch02/shiro-role.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
        Assert.assertTrue(subject.hasRole("role1"));
        
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
    
        boolean[] results = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(results[0]);
        Assert.assertTrue(results[1]);
        Assert.assertFalse(results[2]);
        
    }
    
    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("classpath:ch02/shiro-role.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
    
        subject.checkRole("role1");
    
        //检验拥有角色：role1 and role3 失败抛出异常
        subject.checkRoles("role1", "role3");
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
