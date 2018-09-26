package cn.dhx.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//在boot2版本后没有了@EnableOAuth2Sso,只能用@EnableOAuth2Client

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SsoClient2Application {
    //返回当前登录用户的用户信息
    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Application.class,args);
    }

}
