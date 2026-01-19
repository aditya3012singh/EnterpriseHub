package com.aditya.enterprisehub.config.security;


import com.aditya.enterprisehub.config.auth.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username = jwtService.extractUsername(token);


        // SecurityContext empty? authenticate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 🔥 DB HIT HERE
//            UserDetails userDetails =
//                    userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenExpired(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            System.out.println(token);

            List<String> roles = jwtService.extractRoles(token);
            System.out.println(roles.get(0));

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            System.out.println(authorities);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,      // principal
                            null,          // credentials (not needed)
                            authorities    // 👈 JWT roles
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}


