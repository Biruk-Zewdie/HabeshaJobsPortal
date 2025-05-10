package com.biruk.habeshaJobs.SecurityConfig;

import com.biruk.habeshaJobs.Model.User.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


// This class is used to filter the incoming requests and check if the JWT token is valid or not.
// It extends the OncePerRequestFilter class which is a spring class that allows us to filter the requests only once per request.
// This is useful for performance reasons and to avoid filtering the same request multiple times.
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public JwtFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //this method is called for each request that hits our server.
    //It checks if the request has a valid JWT in the Authorization header.
    //It uses a lots of helper methods that are defined below.

    //FilterChain is chain of filters that a request goes through before it reaching the final destination (the controller or endpoint).


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // this isn't required for requests that don't require bearer token/Auth header such as login and register
        // for those requests that requires bearer token, if the authorization header is not present, run the filter without doing anything (the request will fail later if JWT is needed).
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            System.out.println("Authoriztion header (bearer token) is not present");
            return;
        }
        // if the request has a bearer token, we will extract the token from the request and validate it.
        //used the helper method defined earlier to extract the token from the request
        String token = getAccessToken(request);

        // if the token is not valid (expired or malformed), run the filter without doing anything (the request will fail later if JWT is needed).
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //if the token is valid, update the authentication context with the user details (userId, email and role) extracted from the token.
        //then continue the filter chain. the request will pass through).
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);

    }

    //this method is used to check if the request has a header with the name "Authorization" and if it starts with "Bearer ".
    private boolean hasAuthorizationBearer (HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }

    //this method will return the token from the request Authorization header.
    private String getAccessToken (HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        // the auth header is look like "Bearer: {your JWT here}" and I use split method to get the token
        String token = header.split(" ")[1].trim();
        return token;
    }

    // this method will extract the userID, email and role from the JWT token and set the user object fields with the extracted values.
    // we used UserDetails Interface from spring security to encapsulate user information retrieved from a data source.
    // This data is then utilized during the authentication and authorization processes

    private UserDetails getUserDetails (String token) {

        User userDetails = new User();

        userDetails.setUserId(jwtUtil.extractUserId(token));
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setRole(jwtUtil.extractRole(token));

        System.out.println("user: " + userDetails);
        System.out.println("role: " + userDetails.getRole());

        return (UserDetails) userDetails;


    }

    //this method lets the spring determine who the user is and what privileges they have.
    private void setAuthenticationContext (String token, HttpServletRequest request) {

        User user = (User) getUserDetails(token);

        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorityList
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //After all the configurations above, we tell Spring who the user is and what their role is
        //This information is stored in the authentication object we created above
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
