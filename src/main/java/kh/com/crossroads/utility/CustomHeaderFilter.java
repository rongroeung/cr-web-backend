package kh.com.crossroads.utility;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000");
//        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
//        httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
//        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
//        httpResponse.setHeader("Content-Security-Policy", "frame-ancestors 'self'");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
