package study.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.sample.entity.UserEntity;
import study.sample.exception.TargetNotException;
import study.sample.form.LoginRequest;
import study.sample.repository.UserRepository;

import java.lang.annotation.Target;

/**
 * ログインサービスクラス
 */
@Service
@Transactional
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void register(LoginRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getUserName());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            userRepository.saveAndFlush(userEntity);
        }catch (RuntimeException e) {
            System.out.println("ユーザ名" + request.getUserName());
            System.out.println("パスワード" + request.getPassword());

            throw new TargetNotException(e.getMessage(), e);
        }
    }
}
