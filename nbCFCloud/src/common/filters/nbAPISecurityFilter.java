package common.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import common.definitions.ParameterDefine;
import common.definitions.ReturnCode;
import common.helper.ApplicationContextProvider;
import common.helper.CommonHelper;
import common.helper.HttpWebIOHelper;
import common.helper.nbReturn;
import database.models.NbTokenPublisher;
import service.major.UserInfoService;

public class nbAPISecurityFilter implements Filter{

	private List<String> excludedPages = new ArrayList<String>();
	
	WebApplicationContext springContext;
	
	@Override
	public void destroy() {
		
	}
	
	

	/**
	 * Filer对于需要授权访问的API都会检查Token的有效性，并对访问进行记录
	 * 如果是非授权可访问的API，Filter只是对访问进行记录
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		Boolean isExcludedPath = false;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Map<String, String[]> getParameter = request.getParameterMap();
		
		
		String tokenKey = null;
		String appID = null;
		if( getParameter.get(ParameterDefine.TOKEN) != null )
			tokenKey = getParameter.get(ParameterDefine.TOKEN)[0];
		
		if( getParameter.get(ParameterDefine.APPID) != null )
			appID = getParameter.get(ParameterDefine.APPID)[0];
		
		
		String servletPath = httpServletRequest.getServletPath();
		
		for( String excludePath : excludedPages){
			
			//设计上应该是/openAPI/路径下的所有的api都是不需要登录访问的，详细的参看web.xml中的配置
			if( servletPath.startsWith(excludePath) ){
				isExcludedPath = true;
			}
		}
		
		System.out.println("nbAPISecurityFilter: "+servletPath+" isExcludedPath:"+isExcludedPath);
		
		HttpSession session = httpServletRequest.getSession();
		//需要拦截的
		if( !isExcludedPath ){
			nbReturn nbRet = new nbReturn();
			
			if( tokenKey == null || appID == null ){
				//需要验证token, tokenKey appID都不为空
				nbRet.setError(ReturnCode.NEED_TOKEN_APPID_FOR_AUTH);
				HttpWebIOHelper.printReturnJson(nbRet, (HttpServletResponse) response);
				return;
			}
			
			//开始验证token
			UserInfoService userInfoService  = 
					(UserInfoService)ApplicationContextProvider.getBeanByName("userInfoService");
			nbRet = userInfoService.checkToken(tokenKey, appID, true);
			
			CommonHelper.setAccessLog(appID, tokenKey, nbRet );
			
			if( !nbRet.isSuccess() ){
				//验证token失败
				HttpWebIOHelper.printReturnJson(nbRet, (HttpServletResponse) response);
				return;
			}
			session.setAttribute(ParameterDefine.SESSION_TOKEN_INFO, (NbTokenPublisher)nbRet.getObject());
		}
			
		//验证token成功
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
		springContext = 
		        WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
	}
}
