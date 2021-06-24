package com.page27.project.config;


import com.page27.project.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;
    private final AuthenticationFailureHandler customFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    Spring Security에서 제공하는 비밀번호 암호화 객체
//    Service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록한다.

    @Override
    public void configure(WebSecurity web) throws Exception{
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

//    WebSecurity는 FilterChainProxy를 생성하는 필터이다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/admin/changepassword")

                .ignoringAntMatchers("/admin/changepassword_ok")
                .ignoringAntMatchers("/admin/itemList1")
                .ignoringAntMatchers("/admin/itemList2")
                .ignoringAntMatchers("/admin/itemList3")
                .ignoringAntMatchers("/admin/register")
                .ignoringAntMatchers("/admin/orderList1/**")
                .ignoringAntMatchers("/admin/userList/**");
//                .ignoringAntMatchers("/main/orderStatusChange/**");
//                .ignoringAntMatchers("/main/address/delete")
//                .ignoringAntMatchers("/main/basket/changequantity/**")
//                .ignoringAntMatchers("/main/basket/remove/**")
//                .ignoringAntMatchers("/main/basket/removeitems")
//                .ignoringAntMatchers("/main/basket/removeall");
//                .ignoringAntMatchers("/main/**");

        http
                .authorizeRequests()
                // 페이지 권한 설정
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/main/order","/main/profile","/main/mileage","/main/address",
                    "/main/basket","/main/payment","/main/product/basketadd_ok").hasRole("MEMBER")
                    .antMatchers("/main/index","/main/category/**","/main/product/**").permitAll()

                .and() // 로그인 설정
                    .formLogin()
                        .loginPage("/main/login")
                        .usernameParameter("loginId")
                        .successHandler(successHandler())
                        .failureHandler(customFailureHandler)


                .and() // 로그아웃 설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/main/index")
                    .invalidateHttpSession(true)
                .and()
                    // 403 예외처리 핸들링
                    .exceptionHandling().accessDeniedPage("/main/restrict");

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/defaultUrl");
    }
//    Http 요청에 대한 웹 기반 보안을 구성할 수 있다.
//    로그인 정보는 기본적으로 HttpSession을 이용한다.
//    커스텀 로그인 form의 action 경로와 loginPage()의 파라미터 경로가 일치해야 인증을 처리할 수 있다.
//    기본적으로 /logout에 접근하면 HTTP 세션을 제거한다.
//    .logoutRequestMatcher(new AntPathRequestMatcher -> 로그아웃의 기본 URL이 아닌 다른 URL로 재정의 한다.
//    .invaliddateHttpSession(true) -> HTTP 세션을 초기화 하는 작업이다.
//    .exceptionHandling().accessDeniedPage() -> 예외가 발생했을 때 exceptionHandling()메소드로 핸들링할 수 있다.
//    접근권한이 없을 때 로그인 페이지로 작성한 페이지로 이동하도록 명시해줬다.

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
//   스프링 시큐리티에서 모든 인증은 AuthenticationiManager를 통해 이루어지면 이를 생성하기 위해서는 ~Builder를 사용해야한다.
//    로그인 처리 즉, 인증을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져온다. 여기서는 memberService에서 처리한다.
//    UserDetailsService 인터페이스를 implements 하여 loadUserByUsername() 메소드를 구현하면 된다.
//    비밀번호 암호화를 위해 passwordEncoder를 사용하고 있다.
}