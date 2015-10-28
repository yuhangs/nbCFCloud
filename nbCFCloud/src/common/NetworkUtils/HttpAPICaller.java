package common.NetworkUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.definitions.ParameterDefine;
import common.definitions.ReturnCode;
import common.helper.CommonHelper;
import common.helper.nbReturn;

public class HttpAPICaller {
	
	/**
	 * 后台long call一个外部API
	 * @param url
	 * @param dataPost
	 * @param dataGet
	 * @param method
	 * @param format 
	 * @return
	 */
	public nbReturn CallAPI(String url, Map<String, Object> dataPost, Map<String, Object> dataGet, String method, String format){
		nbReturn nbRet = new nbReturn();
		if( method.toUpperCase().equals(ParameterDefine.LONG_CALL_METHOD_GET) ){
			return CallAPI_get(url, dataGet);
		}
		if( method.toUpperCase().equals(ParameterDefine.LONG_CALL_METHOD_POST) ){
			return CallAPI_post(url, dataGet, dataPost, format);
		}
		nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
		return nbRet;
	}

	/**
	 * 把map转换成post参数String
	 * @param dataGet
	 * @param urlEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String formatGetParameters(Map<String, Object> dataGet, boolean urlEncode) throws UnsupportedEncodingException{
		StringBuilder stringBuilder = new StringBuilder();
		if( dataGet == null ) return "";
		
		stringBuilder.append("?");
		Set<String> keySet = dataGet.keySet();
		for(String key:keySet){
			if( urlEncode ){
				stringBuilder.append(key+"="+URLEncoder.encode(dataGet.get(key).toString(),"UTF-8")+"&" );
			}else{
				stringBuilder.append(key+"="+dataGet.get(key).toString()+"&" );
			}
			stringBuilder.append(key+"="+dataGet.get(key).toString()+"&" );
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		return stringBuilder.toString();
	}
	
	/**
	 * 把map转换成post参数String
	 * @param dataPost
	 * @param format
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String formatPostParameters(Map<String, Object> dataPost, String format) throws UnsupportedEncodingException{
		StringBuilder stringBuilder = new StringBuilder();
		if( dataPost == null ) return "";
		
		if(format.toUpperCase().equals(ParameterDefine.LONG_CALL_PARA_FORMAT_JSON) ){
			stringBuilder.append(CommonHelper.getStringOfObj(dataPost));
		}
		if(format.toUpperCase().equals(ParameterDefine.LONG_CALL_PARA_FORMAT_XFORM)){
			stringBuilder.append(formatGetParameters(dataPost, true));
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * 通过post方式调用
	 */
	private nbReturn CallAPI_post(String url, Map<String, Object> dataGet, Map<String, Object> dataPost, String format) {
		nbReturn nbRet = new nbReturn();
		Map<String, Object> contentRet = new HashMap<String, Object>();

		HttpURLConnection connection = null;
		
		try{
			String parameterGetString = formatGetParameters(dataGet, false);  
			String parameterPostString = formatPostParameters(dataPost, format);  
			String urlNameString = url+parameterGetString;
			byte[] sendContent = parameterPostString.getBytes();
			
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2");
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setRequestMethod(ParameterDefine.LONG_CALL_METHOD_POST);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);
            if( format.toUpperCase().equals(ParameterDefine.LONG_CALL_PARA_FORMAT_XFORM))
            	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if( format.toUpperCase().equals(ParameterDefine.LONG_CALL_PARA_FORMAT_JSON))
            	connection.setRequestProperty("Content-Type", "application/json"); 
            connection.setRequestProperty("Content-Type", String.valueOf(sendContent.length));
            // 建立实际的连接
            connection.connect();
            
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(sendContent);
            out.flush();
            out.close();
            
            Map<String, List<String>> headerFile = connection.getHeaderFields();
            contentRet.put(ParameterDefine.HTTP_HEADER_PART, headerFile);
            
            byte[] content = null;
            if( connection.getContentLengthLong() != 0 ){
            	content = new byte[1024]; //这个一次读取的buffer太大的话会造成内容丢失的现象
            	ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
            	int readCount;
            	while (-1 != (readCount = connection.getInputStream().read(content))) {
            		outputBytes.write(content, 0, readCount);
                }
	           content = outputBytes.toByteArray();
            }
            contentRet.put(ParameterDefine.HTTP_CONTENT_PART, content);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	connection.disconnect();
        }
		
		nbRet.setObject(contentRet);
		return nbRet;
	}
	
	/**
	 * 通过get方式调用
	 */
	private nbReturn CallAPI_get(String url, Map<String, Object> dataGet) {
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> contentRet = new HashMap<String, Object>();

		HttpURLConnection connection = null;
		try{
			String parameterString = formatGetParameters(dataGet, false);  
			String urlNameString = url+parameterString;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2");
            // 建立实际的连接
            connection.connect();
            
            Map<String, List<String>> headerFile = connection.getHeaderFields();
            contentRet.put(ParameterDefine.HTTP_HEADER_PART, headerFile);
            
            byte[] content = null;
            if( connection.getContentLengthLong() != 0 ){
            	content = new byte[1024]; //这个一次读取的buffer太大的话会造成内容丢失的现象
            	ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
            	int readCount;
            	while (-1 != (readCount = connection.getInputStream().read(content))) {
            		outputBytes.write(content, 0, readCount);
                }
	           content = outputBytes.toByteArray();
            }
            contentRet.put(ParameterDefine.HTTP_CONTENT_PART, content);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		finally{
			connection.disconnect();
		}
		
		
		nbRet.setObject(contentRet);
		return nbRet;
	}
}	
//  // 获取所有响应头字段
//  Map<String, List<String>> map = connection.getHeaderFields();
//  // 遍历所有的响应头字段
//  for (String key : map.keySet()) {
//      System.out.println(key + "--->" + map.get(key));
//      if( key != null && key.equals("Content-Type") ){
//      	String value = map.get(key).toString().substring(1);
//      	value = value.substring(0,value.length()-1);
//      	//以上去掉 头尾的 [] 方括号
//      	
//      }
//      
//      if( key != null && key.equals("Content-Length") ){
//      	String value = map.get(key).toString().substring(1);
//      	value = value.substring(0,value.length()-1);
//      	//以上去掉 头尾的 [] 方括号
//      	contentLength = Long.valueOf(value);
//      }
//  }

