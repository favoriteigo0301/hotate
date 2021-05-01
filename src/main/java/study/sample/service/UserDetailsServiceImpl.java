package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.sample.entity.RoleEntity;
import study.sample.entity.UserEntity;
import study.sample.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> entity = userRepository.findByName(username);
        System.out.println(entity.get().getPassword());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        System.out.println("ロール件数"+ entity.get().getRoleEntity().size());

        for (RoleEntity roleEntity: entity.get().getRoleEntity()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleEntity.getName());
            grantedAuthorityList.add(grantedAuthority);
        }

        UserDetails userDetails = (UserDetails) new User(entity.get().getName(), entity.get().getPassword(), grantedAuthorityList);
        return userDetails;
    }
}
