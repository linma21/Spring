package kr.co.oauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // 인증 설정(로그인)
        httpSecurity.formLogin(login -> login
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass"));

        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login?success=300"));

        // Oauth 설정
        httpSecurity.oauth2Login(config -> config
                                            .loginPage("/user/login")
                                            .defaultSuccessUrl("/"));

        // 인가 설정
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                                        .requestMatchers("/article/**").permitAll()
                                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                        .requestMatchers("/manager/**").hasAnyAuthority("ADMIN", "MANAGER")
                                        .anyRequest().permitAll());

        // 사이트 위변조 방지 설정
        httpSecurity.csrf(CsrfConfigurer::disable);



        return httpSecurity.build();
    }
    // Security 로그인 인증 암호화 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
