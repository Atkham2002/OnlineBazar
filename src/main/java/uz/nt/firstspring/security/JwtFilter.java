package uz.nt.firstspring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.nt.firstspring.repository.UserSessionRepository;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.entity.UserSession;
import uz.nt.firstspring.utils.NumberUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserSessionRepository userSessionRepository;
    @Autowired
    private NumberUtil numberUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                Long id = numberUtil.toLong(jwtUtil.getClaim(token, "sub"));
                if (id != null) {
                    Optional<UserSession> userSession = userSessionRepository.findById(id);

                    if (userSession.isPresent()) {
                        UserInfoDto userInfoDto = userSession.get().getUserInfo();

                        if (userInfoDto != null) {
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfoDto,
                                    null,
                                    userInfoDto.getAuthorities());

                WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
//                            HashMap<String, String> details = new HashMap<>();
//                            details.put("address", request.getRemoteAddr());
//                            details.put("session", request.getSession().getId());
                            auth.setDetails(details);

                            SecurityContextHolder.getContext().setAuthentication(auth);

                            //SecurityContextHolder
                            //  SecurityContext
                            //      Authentication
                            //          credentials, principal, authorities, details
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
