package com.springframework.springfundamental.config;

import com.springframework.springfundamental.auth.ApiKeyAuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;

@Slf4j //สำหรับการใช้งาน log
@Configuration // มักจะมีเมธอดที่ถูกประกาศด้วย @Bean, ซึ่งเป็นการบ่งบอกว่าเมธอดนั้นจะสร้างและส่งคืน bean ที่จะถูกจัดการโดย Spring container
public class SecurityConfig {
    //การลง spring-boot-starter-security แล้วจะต้องทำการกำหนดตามนี้ ถ้าไม่ได้กำหนด api ที่ใส่จะใช้งานไม่ได้
    private static final String[] WHITE_LIST = {
            "/actuator/**",

            // OpenAPI 3.0 Specification
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private  static  final  String[] BLACK_LIST = {
            "/actuator/restart" //เป็นคำสั่ง restart ของ api ไม่ต้องการให้ผู้อื่นทำการ restart
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        //SecurityFilterChain ใช้ในการกำหนดกฎและกลไกการควบคุมความปลอดภัยสำหรับแอปพลิเคชัน เป็นส่วนของการตั้งค่าความปลอดภัยที่อนุญาตให้กำหนดด้วยตัวเอง

        // X-API-KEY และ secret เป็นชื่อและรหัสที่ส่วนใหญ่ใช้กันในกรณีที่ไม่ได้ต้องการความปลอดภัย(ชื่อและรหัสพื้นฐาน)
        var username = "X-API-KEY";
        var password = "secret";

        var filter = new ApiKeyAuthFilter(username);
        //มีหน้าที่ตรวจสอบว่า API key ที่รับมาจาก request header (นั่นคือ principal) ตรงกับค่าที่กำหนดเอาไว้หรือไม่
        filter.setAuthenticationManager(authentication -> {
            var principal = (String) authentication.getPrincipal();
            if(!password.equals(principal)){
                throw new BadCredentialsException("Invalid API Key");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        http.csrf(csrf -> csrf.requireCsrfProtectionMatcher(new RequestMatcher() {
            private final Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|DELETE|PATCH)$");

            @Override
            public boolean matches(HttpServletRequest request) {
                return !allowedMethods.matcher(request.getMethod()).matches();
            }
        }));

        http.securityMatcher("/**")
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(filter)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(BLACK_LIST).denyAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
