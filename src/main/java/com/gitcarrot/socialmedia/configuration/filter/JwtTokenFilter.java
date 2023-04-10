package com.gitcarrot.socialmedia.configuration.filter;

import com.gitcarrot.socialmedia.model.User;
import com.gitcarrot.socialmedia.service.UserService;
import com.gitcarrot.socialmedia.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if( header == null || header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null of invalid");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            //token is valid?

            if(JwtTokenUtils.isExpired(token, key)){
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            //get username from token
            String userName = JwtTokenUtils.getUserName(token, key);

            //check the user is valid
            User user = userService.loadUserByUserName(userName);



            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null , List.of(new SimpleGrantedAuthority((user.getUserRole().toString())))
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }catch (RuntimeException e){
            log.error("Error from validating: {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);


    }
}
