package ch01_authentication.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author wincher
 * @date 2018/6/28
 * 自定义Realm实现
 * <p> ch01_authentication <p>
 */
public class MyRealm implements Realm {
    /**
     * 这个返回值决定是不是被认为是同一个realm
     * @return
     */
    @Override
    public String getName() {
        return "myRealm";
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
        if (!"messi".equals(username)) {
            throw new UnknownAccountException();
        }
        //判断密码是否错误
        if (!"123".equals((password))){
            throw new IncorrectCredentialsException();
        }
        
        
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
