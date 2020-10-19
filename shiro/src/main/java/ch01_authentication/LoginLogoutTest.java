package ch01_authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.security.Security;

/**
 * @author huwq
 * @date 2018/6/28
 * <p> ch01_authentication <p>
 */
public class LoginLogoutTest {
    /**
     * 简单登陆登出
     */
    @Test
    public void testHelloShiro() {
        //1.获取SecurityManager工厂,此处通过ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:ch01/shiro-role.ini");
        //2.得到SecurityManager实例,绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.得到Subject以及创建用户名/密码身份验证Token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("john", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.err.println("======> seems like auth failed!");
        }
    
        Assert.assertEquals(true, subject.isAuthenticated());
        
        //6.logout
        subject.logout();
    
        Assert.assertEquals(false, subject.isAuthenticated());
    }
    
    /**
     * 自定义Realm
     */
    @Test
    public void testCustomRealm() {
        //1.获取SecurityManager工程,此处使用ini配合文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:ch01/shiro-realm.ini");
        //2.得到SecurityManger实例,绑定给SecurityUtils
        SecurityUtils.setSecurityManager(factory.getInstance());
        //3.得到Subject以及创建用户名/密码身份验证Token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("messi", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        
        Assert.assertEquals(true, subject.isAuthenticated());
        
        subject.logout();
        
        Assert.assertEquals(false, subject.isAuthenticated());
    }
    
    @Test
    public void testJDBCRealm() {
        //执行前请先执行sql ("classpath:ch01/sql/shiro.sql")
        //1.获取SecurityManager工程,此处使用ini配合文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:ch01/shiro-jdbc-realm.ini");
        //2.得到SecurityManger实例,绑定给SecurityUtils
        SecurityUtils.setSecurityManager(factory.getInstance());
        //3.得到Subject以及创建用户名/密码身份验证Token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("messi", "123");
        
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    
        Assert.assertEquals(true, subject.isAuthenticated());
    
        subject.logout();
    
        Assert.assertEquals(false, subject.isAuthenticated());
    }
    
    @After
    public void tearDown() throws Exception {
        //退出时清除绑定在线程的Subject
        ThreadContext.unbindSubject();
    }
    
    
}
