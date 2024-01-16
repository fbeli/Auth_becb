package com.becb.localauthserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;


@Configuration
@EnableAuthorizationServer
public class AuthorizationsServerConfig extends AuthorizationServerConfigurerAdapter
{

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

   @Autowired
   private AuthenticationManager authenticationManager; // fluxo password flow (.authorizedGrantTypes("pasword") )


    /**
     * Configura o client da aplicação
     * esse cliente deve ser passado em Authorization da requisição como basic auth
     *
     * auth.local:8082/oauth/token
     * body -> x-www-form-urlencoded
     * username: xpto
     * password: xptoRe
     * grant_type: password
     *
     *
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { //client credencials
        clients
                .inMemory()
                    .withClient("xpto")//identificação do client
                    .secret(passwordEncoder.encode("xpto"))// client's password
                    .authorizedGrantTypes("password", "refresh_token")//gera tb um refresh token
                        .scopes("write","read")
                .and()
                    .withClient("authSeverXpto")//identificação do client
                    .secret(passwordEncoder.encode("xpto"))// client's password
                    .authorizedGrantTypes("authorization_code")//gera tb um refresh token
                    .scopes("write","read")
                    .redirectUris("http://localhost:9000/index.html")  //passo retorno de segurança
                .and()
                    .withClient("authSeverXptoPkce")//identificação do client
                    .secret(passwordEncoder.encode(""))// client's password
                    .authorizedGrantTypes("authorization_code")//gera tb um refresh token
                    .scopes("write","read")
                    .redirectUris("http://localhost:9000/html_pkce/index_pkce.html") ; //passo retorno de segurança


                /*
        http://auth.local:8082/oauth/authorize?response_type=code&state=abc&client_id=authSeverXpto&redirect_uri=http://localhost:9000/index.html


        Com PKCE
        code_challenge=123
        code_challenge=123
        code_challenge_method=plain
        http://auth.local:8082/oauth/authorize?response_type=code&state=abc&client_id=authSeverXpto&redirect_uri=http://localhost:9000/html_pkce/index_pkce.html&code_challenge=123&code_challenge_method=sha256

        http://auth.local:8082/oauth/authorize?response_type=code&state=abc&client_id=authSeverXpto&redirect_uri=http
        ://localhost:9000/index.html&code_challenge=123&code_challenge_method=sha256

*/
    }

    /**
     * to use refresh token
     *
     * @param endpoints the endpoints configurer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenGranter(tokenGranter(endpoints));
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("BECBmysecretkey0987654321123456789");// secret key (MAC) -
        // should be in application.properties
        return jwtAccessTokenConverter;
    }

    /**
     * to use PKCE
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }


    /**
     * auth.local:8082/oauth/check_token
     *
     * Enviar token no body
     *
      * @param security a fluent configurer for security features
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //security.checkTokenAccess("isAuthenticated()"); //  endpoint must authenticade and be valid
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients(); //  pode passar o client_secret no body ao invés do
        // Autorization (aba no porsman)

    }
}
