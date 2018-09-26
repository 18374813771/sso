package cn.dhx.sso.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {
        //配置应用服务器能给哪些应用发令牌
        client.inMemory()
                .withClient("dhx1")
                .secret("dhxsecret1")
                .authorizedGrantTypes("authorization_code", "refresh_token")   //授权码
                .scopes("all")
                .and()
                .withClient("dhx2")
                .secret("dhxsecret2")
                .authorizedGrantTypes("authorization_code", "refresh_token")   //授权码
                .scopes("all");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {
        //使用下面两个配置来生成令牌
        endpoint.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
    }
    //下面两个配置jwt token
    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对jwt token令牌进行签名
        converter.setSigningKey("dhx");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //"isAuthenticated()"是授权表达式，表示访问tokenKey的应用要经过身份认证才能访问。默认是拒绝所有
        security.tokenKeyAccess("isAuthenticated()");
    }
}
