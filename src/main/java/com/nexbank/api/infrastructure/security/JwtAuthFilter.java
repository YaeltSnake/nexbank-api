package com.nexbank.api.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider,
                         UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Paso 1: extraer token del header
        String jwt = getJwtFromRequest(request);

        // Paso 2: si no hay token o es invalido , dejar pasar sin autenticar
        if (jwt == null || !jwtTokenProvider.isTokenValid(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        // Paso 3: extraer email del token
        String email = jwtTokenProvider.extractEmail(jwt);

        // Paso 4: solo autenticar si el SecurityContext está vacio
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // Paso 5: cargar el usuario de la DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Paso 6: construir el objeto de autenticacion
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Paso 7: agregar detalles del request
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Paso 8: meter en SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);

    }








    private String getJwtFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


}
