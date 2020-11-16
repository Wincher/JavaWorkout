package ch01_authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wincher
 * @date 2018/6/30
 * <p> ch01_authentication <p>
 */
public class AuthenticatorTest {
    
    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("classpath:ch01/shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        
        //得到一个身份集合,包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        
        Assert.assertEquals(2, principalCollection.asList().size());
        
    }
    
    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("classpath:ch01/shiro-authenticator-all-fail.ini");
        
    }
    
    @Test
    public void testAtLeastOneSuccessfulStrategyWithSuccess() {
        login("classpath:ch01/shiro-authenticator-atLeastOne-success.ini");
        Subject subject = SecurityUtils.getSubject();
        
        //得到一个身份集合,包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        
        Assert.assertEquals(2, principalCollection.asList().size());
        
    }
    
    @Test
    public void testFirstOneSuccessfulStrategyWithSuccess() {
        login("classpath:ch01/shiro-authenticator-first-success.ini");
        Subject subject = SecurityUtils.getSubject();
        
        //得到一个身份集合,包含了第一个Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
        
    }
    
    @Test
    public void testAtLeastTwoStrategyWithSuccess() {
        login("classpath:ch01/shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();
        
        //得到一个身份集合,包含了第一个Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        //realm1和realm返回的身份一样所以输出时只返回一个
        Assert.assertEquals(1, principalCollection.asList().size());
        
    }
    
    @Test
    public void testOnlyOneStrategyWithSuccess() {
        login("classpath:ch01/shiro-authenticator-onlyOne-success.ini");
        Subject subject = SecurityUtils.getSubject();
        
        //得到一个身份集合,包含了第一个Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
        
    }
    
    private void login(String configFile) {
        //1.获取SecurityManager工厂,此处通过ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        //2.得到SecurityManager实例,绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.得到Subject以及创建用户名/密码身份验证Token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("messi", "123");
        subject.login(token);
    }
    
    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();
    }
}
