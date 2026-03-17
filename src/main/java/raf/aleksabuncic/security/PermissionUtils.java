package raf.aleksabuncic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PermissionUtils {
    public static Set<Permission> toEnumSet(long permissions) {
        Set<Permission> perms = new HashSet<>();
        for (Permission p : Permission.values()) {
            if ((permissions & p.getValue()) != 0) {
                perms.add(p);
            }
        }
        return perms;
    }

    public static long toBitmask(Collection<Permission> permissions) {
        if (permissions == null) return 0L;
        long mask = 0L;
        for (Permission p : permissions) {
            mask |= p.getValue();
        }
        return mask;
    }

    public static Collection<GrantedAuthority> fromBitmask(long permissions) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        for (Permission p : Permission.values()) {
            if ((permissions & p.getValue()) != 0) {
                authorities.add(new SimpleGrantedAuthority(p.name()));
            }
        }

        return authorities;
    }

    public static Collection<String> toStringPermissions(long permissions) {
        Collection<String> perms = new HashSet<>();

        for (Permission p : Permission.values()) {
            if ((permissions & p.getValue()) != 0) {
                perms.add(p.name());
            }
        }
        return perms;
    }
}
