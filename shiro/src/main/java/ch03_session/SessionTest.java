package ch03_session;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author huwq
 * @date 2018/6/28
 * <p> ch01_authentication <p>
 */
public class SessionTest {
    /**
     * 简单登陆登出
     */
    @Test
    public void testGetSession() {
        login("classpath:ch03/shiro.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        
        session.setTimeout(1000000000);
        System.out.println("========>> " + session.getId());
        System.out.println("========>> " + session.getHost());
        System.out.println("========>> " + session.getLastAccessTime());
        
        
        
        
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
    
        System.out.println("login------------" + subject.getSession().getId());
    }
    
    @After
    public void tearDown() throws Exception {
        //退出时清除绑定在线程的Subject
        ThreadContext.unbindSubject();
    }
    
    
}
