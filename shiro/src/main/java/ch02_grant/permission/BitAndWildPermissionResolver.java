package ch02_grant.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author wincher
 * @date 2018/7/2
 * <p> ch02_grant.permission <p>
 *     处理permission
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
