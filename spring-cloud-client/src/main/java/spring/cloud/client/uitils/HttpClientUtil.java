package spring.cloud.client.uitils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * Created by Harry on 17/3/18.
 */
public class HttpClientUtil {

    private static final String UTF_8 = "utf-8";

    private static int time_out = 10*1000;
    private static int retry_time = 2;

    /**
     * 发送post数据
     * @param url
     * @param treeMap
     * @return
     */
    public static Optional<String> post(String url, TreeMap<String, String> treeMap) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(time_out)
                .setConnectTimeout(time_out)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        List<BasicNameValuePair> formparams = HttpClientUtil.getNamedValuePairFromTreeMap( treeMap );
        int times = 0;
        while ( times < retry_time ) {
            try {
                if ( null != formparams && !formparams.isEmpty() ) {
                    httpPost.setEntity(new UrlEncodedFormEntity(formparams, UTF_8));
                }
                CloseableHttpClient httpclient = HttpClients.createDefault();
                response = httpclient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString( entity );
                EntityUtils.consume(entity);
                return Optional.ofNullable(content);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (null!=response) response.close();
                } catch (IOException e) {}
            }
            times ++;
        }
        return Optional.empty();
    }

    public static Optional<String> postWidthBody(String url, String body, String contentType) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(time_out)
                .setConnectTimeout(time_out)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        if ( !Strings.isNullOrEmpty(body) ) {
            StringEntity stringEntity = new StringEntity(body, UTF_8);
            if (!Strings.isNullOrEmpty(contentType)){
                stringEntity.setContentType(contentType);
            }
            httpPost.setEntity(stringEntity);
        }
        
        int times = 0;
        while ( times < retry_time ) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                response = httpclient.execute(httpPost);
                
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString( entity );
                EntityUtils.consume(entity);
                return Optional.ofNullable(content);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (null!=response) response.close();
                } catch (IOException e) {}
            }
            times ++;
        }
        return Optional.empty();
    }

    public static Optional<String> postWidthBody(String url, String body){
        return postWidthBody(url, body, null);
    }

    public static Optional<String> post(String url) {
        return HttpClientUtil.post(url, null);
    }

    private static List<BasicNameValuePair> getNamedValuePairFromTreeMap(TreeMap<String, String> treeMap ) {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        if (null != treeMap && !treeMap.isEmpty()) {
            Iterator<String> keyIte = treeMap.keySet().iterator();
            while (keyIte.hasNext()) {
                String key = keyIte.next();
                String value = treeMap.get(key);
                if ( Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value) ) {
                    continue;
                }
                formparams.add(new BasicNameValuePair(key, value));
            }
        }
        return formparams;
    }
    
    public static Optional<String> postMultiPart(String url, String fileName, byte[] fileData){
    	if (Strings.isNullOrEmpty(fileName)) {
    		fileName = "file.jpg";
		}
    	if ( null == fileData ) {
			return Optional.empty();
		}
    	
    	HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(time_out)
                .setConnectTimeout(time_out)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    	builder.addBinaryBody("upload_file", fileData, ContentType.MULTIPART_FORM_DATA, fileName);
    	
    	HttpEntity requestEntity = builder.build();
    	
    	httpPost.setEntity(requestEntity);
    	
        int times = 0;
        while ( times < retry_time ) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                response = httpclient.execute(httpPost);
                
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString( entity );
                EntityUtils.consume(entity);
                return Optional.ofNullable(content);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (null!=response) response.close();
                } catch (IOException e) {}
            }
            times ++;
        }
        return Optional.empty();
    }
    
    public static String getParamStringFromMap(TreeMap<String, String> treeMap) {
    	if ( null == treeMap || treeMap.isEmpty() ) {
			return "";
		}
    	
    	List<String> keyValueList = new ArrayList<>();
        if (null != treeMap && !treeMap.isEmpty()) {
            Iterator<String> keyIte = treeMap.keySet().iterator();
            while (keyIte.hasNext()) {
                String key = keyIte.next();
                String value = treeMap.get(key);
                if ( Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value) ) {
                    continue;
                }
                keyValueList.add( String.format("%s=%s", key,value) );
            }
        }
		return Joiner.on("&").skipNulls().join(keyValueList);
	}
    
    /**
     * 发送get数据
     * @param url
     * @param treeMap
     * @return
     */
    public static Optional<String> get(String url, TreeMap<String, String> treeMap) {
        String innerUrl = url;
        String paramStr = HttpClientUtil.getParamStringFromMap(treeMap);
        if ( !Strings.isNullOrEmpty(paramStr) ) {
            innerUrl += "?"+ paramStr;
        }

        HttpGet httpGet = new HttpGet(innerUrl);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(time_out)
                .setConnectTimeout(time_out)
                .build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        int times = 0;
        while ( times < retry_time ) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                response = httpclient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString( entity );
                EntityUtils.consume(entity);
                return Optional.ofNullable(content);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (null!=response) response.close();
                } catch (IOException e) {}
            }
            times ++;
        }
        return Optional.empty();
    }

    public static Optional<String> get(String url) {
        return  HttpClientUtil.get(url, null);
    }
    
    /**
     * 发送get数据
     * @param url
     * @param treeMap
     * @return
     */
    public static Optional<String> delete(String url, TreeMap<String, String> treeMap) {
        String innerUrl = url;
        String paramStr = HttpClientUtil.getParamStringFromMap(treeMap);
        if ( !Strings.isNullOrEmpty(paramStr) ) {
            innerUrl += "?"+ paramStr;
        }

        HttpDelete httpDelete = new HttpDelete(innerUrl);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(time_out)
                .setConnectTimeout(time_out)
                .build();//设置请求和传输超时时间
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        int times = 0;
        while ( times < retry_time ) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                response = httpclient.execute(httpDelete);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString( entity );
                EntityUtils.consume(entity);
                return Optional.ofNullable(content);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (null!=response) response.close();
                } catch (IOException e) {}
            }
            times ++;
        }
        return Optional.empty();
    }

    public static Optional<String> delete(String url) {
        return  HttpClientUtil.delete(url, null);
    }

    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {

            }
        }
        return new byte[] {};
    }
    
    public static String getRequestBodyByString(InputStream is,int size) {
        byte[] reqBodyBytes = readBytes(is, size);
        String res = new String(reqBodyBytes);
        return res;
    }
    
}
