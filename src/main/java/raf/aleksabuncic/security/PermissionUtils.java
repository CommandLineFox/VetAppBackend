package raf.aleksabuncic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class PermissionUtils {
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
