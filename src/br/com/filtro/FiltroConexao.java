package br.com.filtro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter()
public class FiltroConexao implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		
		String context = request.getServletContext().getContextPath();

		try {
			
			HttpSession	session = ((HttpServletRequest) request).getSession();
			String usuario = null;
			
			if(session != null) {
				usuario = (String) session.getAttribute("login");
			}
			if(usuario == null) {
				session.setAttribute("msg", "Você não está logado no sistema!");

				((HttpServletResponse) response).sendRedirect(context+"/index.html");
			}
			
			else {
				chain.doFilter(request, response);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
