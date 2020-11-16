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

/**
 * @author wincher
 * @date 2018/7/2
 * <p> ch02_grant.realm <p>
 */
public class AuthorizerTest {
    
    @Test
    public void testIsPermittedRelam() {
        login("classpath:ch02/shiro.ini", "messi", "123");
        Subject subject = SecurityUtils.getSubject();
        System.out.println("testIsPermitted");
        
        Assert.assertTrue(subject.isPermitted("user1:update"));
        Assert.assertTrue(subject.isPermitted("user2:update"));
        
        //通过BitPermission表示权限
        Assert.assertTrue(subject.isPermitted("+user1+2"));
        Assert.assertTrue(subject.isPermitted("+user1+8"));
        Assert.assertTrue(subject.isPermitted("+user1+10"));
        
        Assert.assertFalse(subject.isPermitted("+user1+4"));
        
        Assert.assertTrue(subject.isPermitted("menu:viewa"));
        
    }
    
    /**
     * 测试前执行ch02.sql.shiro.sql
     */
    @Test
    public void testIsPermittedJDBC() {
        login("classpath:ch02/shiro-jdbc-authorizer.ini", "messi", "123");
        //判断拥有权限：user:create
    
        Subject subject = SecurityUtils.getSubject();
        Assert.assertTrue(subject.isPermitted("user1:update"));
        Assert.assertTrue(subject.isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject.isPermitted("+user1+2"));//新增权限
        Assert.assertTrue(subject.isPermitted("+user1+8"));//查看权限
        Assert.assertTrue(subject.isPermitted("+user2+10"));//新增及查看
        
        Assert.assertFalse(subject.isPermitted("+user1+4"));//没有删除权限
        
        Assert.assertTrue(subject.isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
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
