package ch01_authentication.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @author huwq
 * @since 2018/6/30
 * <p> ch01_authentication.strategy <p>
 */
public class OnlyOneAuthenticatorStrategy extends AbstractAuthenticationStrategy {
    
    @Override
    public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException {
        return new SimpleAuthenticationInfo();
    }
    
    @Override
    public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return aggregate;
    }
    
    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        AuthenticationInfo info;
        if (null == singleRealmInfo) {
            info = aggregateInfo;
        } else {
            if (null == aggregateInfo) {
                info = singleRealmInfo;
            } else {
                info = this.merge(singleRealmInfo, aggregateInfo);
                //只允许有一个验证成功的realm
                if (info.getPrincipals().getRealmNames().size() > 1) {
                    System.out.println(info.getPrincipals().getRealmNames());
                    throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] " +
                            "could not be authenticated by any configured realms.  Please make sure that only one realm can authenticate these tokens.");
                }
            }
        }
        
    
        return info;
    }
    
    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return aggregate;
    }
}
