package io.msj.security;

import io.msj.entity.User;
import io.msj.repository.UserRepository;
import io.msj.repository.UserRolesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*import io.msj.entity.Account;
import io.msj.repository.AccountRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            List<String> userRoles = userRolesRepository.findRoleByUserName(username);
            return new CustomUserDetails(user, userRoles);
        }
    }
    /*public class NewUserDetails implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Account account;

    @Autowired
    private User userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        account = accountRepository.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException("Usuário " + username + " not found!");
        }
        if (account.getRoles() == null) {
            // No Roles assigned to user...
            throw new UsernameNotFoundException("User not authorized.");
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        while (account.getRoles().getLabel() != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(account.getRoles().getLabel()));
        }

        userDetails = new User(account.getUsername(),
                account.getPassword(), account.isEnabled(),
                !account.isExpired(), !account.isCredentialsexpired(),
                !account.isLocked(), grantedAuthorities);

        return userDetails;
    }*/

}
