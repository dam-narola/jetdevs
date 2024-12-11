package com.narola.jetdevstest.security;

import com.narola.jetdevstest.model.UserEntity;
import com.narola.jetdevstest.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byUsername = usersRepo.findByUsername(username);
        if (byUsername == null)
            throw new UsernameNotFoundException("User not found");
        return User.withUsername(byUsername.getUsername()).password(byUsername.getPassword()).roles(byUsername.getRole()).build();
    }

    @PostConstruct
    public void init() {
        String superadmin = "superadmin";
        if (!usersRepo.existsByUsername(superadmin)) {
            usersRepo.save(new UserEntity(null, superadmin,
                    "$2a$12$zkt3px.nSC.t5AuoJ/cmV.ntABni8L/0yD6frYZc2dqaEI8Ks3.a.",
                    "ADMIN"));
        }
    }

}
