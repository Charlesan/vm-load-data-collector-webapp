package collector.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 计费请求调度服务相关常量
 * 
 * @author Wang Chao
 *
 * @date 2015-4-5 下午7:40:53
 *
 */
public class RequestScheduleProperties {

	private static Properties requestScheduleProperties;
	
	static {
		requestScheduleProperties = new Properties();
		try {
			String filePath = RequestScheduleProperties.class.getClassLoader().getResource("/properties/request-schedule.properties").getPath();
			InputStream in =  new BufferedInputStream(new FileInputStream(filePath));
			requestScheduleProperties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getCityId() {
		return Integer.valueOf(requestScheduleProperties.getProperty("city.id"));
	}
	
	public static double getCpuCoefficient() {
		return Double.parseDouble(requestScheduleProperties.getProperty("cpu.coefficient"));
	}
	
	public static double getMemoryCoefficient() {
		return Double.parseDouble(requestScheduleProperties.getProperty("memory.coefficient"));
	}
	
	public static double getThreadNumCoefficient() {
		return Double.parseDouble(requestScheduleProperties.getProperty("thread.num.coefficient"));
	}
	
	public static double getTcpConnectionNumCoefficient() {
		return Double.parseDouble(requestScheduleProperties.getProperty("tcp.connection.coefficient"));
	}
	
	public static double getResponseTimeCoefficient() {
		return Double.parseDouble(requestScheduleProperties.getProperty("response.time.coefficient"));
	}
	
	public static double getConvergenceRateCoefficient1() {
		return Double.parseDouble(requestScheduleProperties.getProperty("convergence.rate.coefficient1"));
	}
	
	public static double getConvergenceRateCoefficient2() {
		return Double.parseDouble(requestScheduleProperties.getProperty("convergence.rate.coefficient2"));
	}
	
	public static double getDeviation() {
		return Double.parseDouble(requestScheduleProperties.getProperty("deviation"));
	}
	
	public static double getOptimalLoad() {
		return Double.parseDouble(requestScheduleProperties.getProperty("optimal.load"));
	}
	
	public static int getMinWeight() {
		return Integer.parseInt(requestScheduleProperties.getProperty("weight.min"));
	}
	
	public static int getMaxWeight() {
		return Integer.parseInt(requestScheduleProperties.getProperty("weight.max"));
	}
	
	public static int getResponseTimeThreshold() {
		return Integer.parseInt(requestScheduleProperties.getProperty("response.time.threshold"));
	}
}
