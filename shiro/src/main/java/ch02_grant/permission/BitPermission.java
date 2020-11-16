package ch02_grant.permission;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authz.Permission;

/**
 * @author wincher
 * @date 2018/7/2
 * <p> ch02_grant.permission <p>
 *
 * 规则:
 *  +资源字符串+权限位+实例ID
 * 以+开头 中间通过+分割
 *
 * 权限:
 *     0 代表全部权限
 *     1 新增 0001
 *     2 修改 0010
 *     4 删除 0100
 *     8 查看 1000
 *
 * eg: +user+10 表示对资源user的修改/查看权限
 */
public class BitPermission implements Permission {
    
    private final String WILDCARD = "*";
    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;
    
    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");
        
        int length = array.length;
        int _1 = 1;
        int _2 = 2;
        int _3 = 3;
        if (length > _1 ) {
            resourceIdentify = array[1];
        }
        if (StringUtils.isEmpty(resourceIdentify)) {
            resourceIdentify = WILDCARD;
        }
        if (length > _2) {
            permissionBit = Integer.valueOf(array[2]);
        }
        if (array.length > _3) {
            instanceId = array[3];
        }
        if (StringUtils.isEmpty(instanceId)) {
            instanceId = WILDCARD;
        }
    }
    
    @Override
    public boolean implies(Permission permission) {
        if(!(permission instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) permission;
        if (!(WILDCARD.equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }
        if (!(this.permissionBit == 0|| (this.permissionBit & other.permissionBit) !=0)) {
            return false;
        }
        if (!(WILDCARD.equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "BitPermission{" +
                "resourceIdentify='" + resourceIdentify +'\'' +
                " ,permissionBit='" + permissionBit +'\'' +
                " ,instanceId='" + instanceId +'\'' +
                '}';
    }
}
