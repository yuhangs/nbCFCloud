package common.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.definitions.ParameterDefine;

public class nbHTMLSecurityFilter implements Filter{

	private List<String> excludedPages = new ArrayList<String>();
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		Boolean isExcludedPath = false;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();
		
		for( String excludePath : excludedPages){
			if( servletPath.startsWith(excludePath) ){
				isExcludedPath = true;
			}
		}
		
		System.out.println("nbHTMLSecurityFilter: "+servletPath+" isExcludedPath:"+isExcludedPath);
		
		boolean isFiltered = false;
		//需要拦截的
		if( !isExcludedPath ){
			
			HttpSession session = httpServletRequest.getSession();
			boolean isAuthorized = false;
			
			if( session.getAttribute(ParameterDefine.SESSION_USERID) == null ){
				isAuthorized = false;
			}else{
				isAuthorized = true;
			}
			
			if( !isAuthorized ){
				
				session.setAttribute(ParameterDefine.SESSION_NEXT_URL_AFTER_LOGIN, 
									 httpServletRequest.getRequestURL()+"?"+ httpServletRequest.getQueryString());
				isFiltered = true;
				request.getRequestDispatcher("/openWeb/login.html").forward(request, response);
			}
		}
		if( !isFiltered )
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedPage = filterConfig.getInitParameter("excludedPages");
		String[] excludedPageArray = excludedPage.split(";");
		excludedPages.clear();
		for(int i = 0 ; i < excludedPageArray.length ; i++){
			excludedPages.add(excludedPageArray[i].trim());
		}
	}

}
