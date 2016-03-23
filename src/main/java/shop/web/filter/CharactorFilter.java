package shop.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Amysue on 2016/3/21.
 */
public class CharactorFilter implements Filter {

    private static String encode;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encode = filterConfig.getInitParameter("encode");
        if (encode == null || encode.equals("")) {
            encode = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encode);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
