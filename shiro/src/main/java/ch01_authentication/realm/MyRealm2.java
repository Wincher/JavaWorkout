package ch01_authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author huwq
 * @since 2018/6/28
 * 自定义Realm实现
 * <p> ch01_authentication <p>
 */
public class MyRealm2 implements Realm {
    @Override
    public String getName() {
        return "myRealm2";
    }
    
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //仅支持UsernamePasswordToken类型的Token
        return authenticationToken instanceof UsernamePasswordToken;
    }
    
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // get username
        String username = (String) authenticationToken.getPrincipal();
        //get password
        String password = new String((char[])authenticationToken.getCredentials());
        // 判断用户名是否错误
        if (!"Ronaldo".equals(username)) {
            throw new UnknownAccountException();
        }
        //判断密码是否错误
        if (!"123".equals(password)){
            throw new IncorrectCredentialsException();
        }
        
        
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
