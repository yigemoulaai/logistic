package com.cqupt.logistic.util.Antcore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Match {

	public static String readByGet(String inUrl) throws IOException {

		StringBuffer sbf = new StringBuffer();
		String strRead = null;
		// 模拟浏览�?
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
				+ "(KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
		// 连接URL地址
		URL url = new URL(inUrl);
		// 根据拼凑的URL，打�?��接，URL.openConnection函数会根据URL的类�?
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置连接访问方法及超时参�?
		connection.setRequestMethod("GET");
		connection.setReadTimeout(300000);
		connection.setConnectTimeout(300000);
		connection.setRequestProperty("User-agent", userAgent);
		connection.setInstanceFollowRedirects(false);
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发�?服务�?
		connection.connect();
		// 取得输入流，并使用Reader读取
		InputStream is = connection.getInputStream();
		// 读取数据编码处理
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		while ((strRead = reader.readLine()) != null) {
			sbf.append(strRead);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return sbf.toString();
	}


	public static String readByPost(String inUrl,String param) throws IOException {

		StringBuffer sbf = new StringBuffer();
		String strRead = null;
		// 模拟浏览器
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
				+ "(KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
		// 连接URL地址
		URL url = new URL(inUrl);
		// 根据拼凑的URL，，URL.openConnection函数会根据URL的类
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置连接访问方法及超时参数
		connection.setRequestMethod("POST");
		connection.setReadTimeout(300000);
		connection.setConnectTimeout(300000);
		connection.setRequestProperty("User-agent", userAgent);
		connection.setRequestProperty("content-type", "application/json");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发起服务请求
		connection.connect();
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write(param);
		out.flush();
		out.close();
		// 取得输入流，并使用Reader读取
		InputStream is = connection.getInputStream();
		// 读取数据编码处理
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		while ((strRead = reader.readLine()) != null) {
			sbf.append(strRead);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return sbf.toString();
	}

	public static boolean pattern_match(String s, String s_p) {
		Pattern pattern = Pattern.compile(s_p);
		Matcher match = pattern.matcher(s);
		return match.find();
	}

	/**
	 * 地理名称转化为经纬度
	 * @param locations
	 * @return
	 * @throws IOException
	 */
	public static List<LocationEntity> batchQueryLocation(List<String> locations){

		if(CollectionUtils.isEmpty(locations)) {
			return null;
		}
		List<LocationEntity> lists = new ArrayList<>(locations.size());

		//封装url
		for(int i=0;i<locations.size();i++) {
			String url = String.format("%s/geocode/geo?address=%s&output=JSON&key=%s",
					Constants.gaodeUrl, locations.get(i), Constants.gaodeKey);
			String locationStr = queryLocation(url);
			LocationEntity entity = new LocationEntity();
			entity.setSortNum(i+1);
			entity.setAddress(locations.get(i));
			String[] locas=locationStr.split(",");
			locationStr=locas[1]+","+locas[0];
			entity.setLocation(locationStr);
			lists.add(entity);
		}

		return lists;
	}

	private static String queryLocation(String url)  {
		String gaodeUrl = Constants.gaodeUrl;
		gaodeUrl = gaodeUrl.substring(0, gaodeUrl.lastIndexOf("/"));
		//调用批量接口，POST请求
		try {
			String jsonString = readByGet(url);
			JSONObject jsonObject =  JSONObject.parseObject(jsonString);
			Integer status = Integer.parseInt(jsonObject.getString("status"));
			if(1 == status) {
				JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
				if(!CollectionUtils.isEmpty(jsonArray)) {
					JSONObject json = (JSONObject) jsonArray.get(0);
					String location = json.getString("location");
					return location;
				}
			}
		}catch(IOException e) {
//			logger.error(">>>error",e);
		}
		return null;
	}


	/**
	 * 返回两点之间的距离，二维数组
	 * @return
	 * @throws IOException
	 */
	public static Double[][] returnShortCut(List<LocationEntity> lists) throws IOException {

		Double[][] twoDistances = new Double[lists.size()-1][lists.size()-1];
		//获取两点之间的距离
		if(!CollectionUtils.isEmpty(lists)) {
			LocationEntity[] entitys = new LocationEntity[lists.size()];
			lists.toArray(entitys);

			for(int i = 0 ; i<entitys.length-1;i++) {
				String destination=entitys[i+1].getLocation();;
				List<String> origins = new ArrayList<>(entitys.length-1);
				for(int j = 1 ;j<entitys.length;j++) {
					if(i+1!= j) {
						String ori=entitys[j].getLocation();
						origins.add(ori);
					}



				}

				//调用高德API批量查询个点之间的距离
				List<Double> distances = returnDistance(StringUtils.join(origins, "|"), destination);
				distances.add(i, Double.MAX_VALUE);
				Double[] doubles = new Double[distances.size()];
				distances.toArray(doubles);
				for(int j=0;j<doubles.length;j++)
				{
					twoDistances[i][j] = new Double(doubles[j]) ;
				}

			}
		}
		return twoDistances;
	}

	/**
	 * 返回两点之间的距离
	 * @param origins
	 * @param destination
	 * @return
	 * @throws IOException
	 */
	public static List<Double> returnDistance(String origins,String destination) throws IOException{
		//重新定义url
		String gaodeUrl = Constants.gaodeUrl;
		gaodeUrl = gaodeUrl.substring(0, gaodeUrl.lastIndexOf("/"));
		//type=0直线距离
		String url = String.format("%sroutematrix/v2/driving?output=json&tactics=12&origins=%s&destinations=%s&ak=%s",
				 Constants.baiduUrl,origins,destination, Constants.baiduKey);
		//gaodeUrl=gaodeUrl+"/batch/key="+Constants.gaodeKey;

		String jsonStr =readByGet(url);
        if(!StringUtils.isEmpty(jsonStr)) {
            JSONObject json = JSON.parseObject(jsonStr);

			JSONArray jsonArray = JSONObject.parseObject(jsonStr).getJSONArray("result");
			//String distanceString = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");


                List<Double> distances =  new ArrayList<>(jsonArray.size());
                if(!CollectionUtils.isEmpty(jsonArray)) {
					jsonArray.forEach(jsonO->{
						JSONObject jsonObject = (JSONObject) jsonO;
						Double distance =Double.valueOf(jsonObject.getJSONObject("distance").getString("value")) ;
						distances.add(distance);
						System.out.println(distance);
					});
				}
				return distances;

		}
		return null;
	}
}
