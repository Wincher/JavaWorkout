package ch02_grant.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author huwq
 * @date 2018/7/2
 * <p> ch02_grant.permission <p>
 *     处理role返回permission
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)) {
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));
        }
        return null;
    }
}
