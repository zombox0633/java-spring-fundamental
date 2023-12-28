package com.springframework.springfundamental.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@RequiredArgsConstructor //ช่วยลด boilerplate code ใน Java. มันทำหน้าที่สร้าง constructor
public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    //AbstractPreAuthenticatedProcessingFilter  ใช้สำหรับสร้าง custom authentication filters สำหรับการจัดการกับการเข้าถึงที่ได้รับการตรวจสอบล่วงหน้า (pre-authenticated)
    //AbstractPreAuthenticatedProcessingFilter ช่วยให้ ApiKeyAuthFilter สามารถจัดการ authentication โดยใช้ API key ที่ส่งมาใน header ของ HTTP request.

    private final String principalRequestHeader;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        // เมธอดนี้ใช้เพื่อระบุ principal  ของผู้ใช้จาก HTTP request. ในกรณีของ ApiKeyAuthFilter, principal คือ API key ที่ส่งมาใน header ของ request
        return request.getHeader(principalRequestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials (HttpServletRequest request){
        //ในกรณีของการเข้าถึงที่ได้รับการตรวจสอบล่วงหน้า, เช่นการใช้ API key, มักไม่จำเป็นต้องมี credentials หรือรหัสผ่านเพิ่มเติมใน ApiKeyAuthFilter, เมธอดนี้ส่งคืนค่า "N/A"
        return "N/A";
    }
}
