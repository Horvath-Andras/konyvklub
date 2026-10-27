package hu.unideb.inf.konyvklub.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Minden /api/** hívásnál ellenőrzi az "Authorization: Bearer <idToken>" fejlécet.
 * Érvényes token esetén a kérés attribútumai közé teszi a felhasználó uid-ját és email-jét.
 */
@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/")
                || "OPTIONS".equalsIgnoreCase(request.getMethod()); // CORS előzetes kérés, nincs benne token
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Hiányzó token");
            return;
        }
        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(header.substring(7));
            request.setAttribute("uid", token.getUid());
            request.setAttribute("email", token.getEmail());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Érvénytelen token");
            return;
        }
        chain.doFilter(request, response);
    }
}
