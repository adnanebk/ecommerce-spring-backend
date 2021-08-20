package com.adnanbk.ecommerceang.Jwt;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {



	private final JwtTokenUtil jwtTokenUtil;
	private UserRepo userRepo;


	public JwtAuthorizationFilter(JwtTokenUtil jwtTokenUtil, UserRepo userRepo) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepo = userRepo;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getRequestURI().contains("userOrders") &&
				!request.getRequestURI().contains("appUsers") &&
				!request.getRequestURI().contains("creditCards");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
			
	/*		response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpStatus.BAD_REQUEST.value());*/

		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			AppUser appUser=userRepo.findByUserName(username);
			// if token is valid return the user details from the database
			boolean isTokenValid = appUser!=null && appUser.isEnabled() && jwtTokenUtil.validateToken(jwtToken, appUser.getUserName());

			// authentication
			if (isTokenValid) {
				jwtTokenUtil.setAuthenticationToken(appUser.getUserName(),appUser.getPassword(),request);
			}
		}
			chain.doFilter(request, response);

	}
}