package collector.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import collector.dto.VmLoadData;
import collector.dto.VmLoadNormalizedData;
import collector.dto.VmLoadWeightCache;
import collector.properties.RequestScheduleProperties;
import collector.service.VmLoadDataAsker;

/**
 * 跟新虚拟机负载权值的定时任务
 * 
 * @author Wang Chao
 *
 * @date 2015-4-7 下午12:34:06
 *
 */
public class UpdateWeightJob {
	
	@Resource
	private VmLoadDataAsker vmLoadDataAsker;
	
	@Autowired
	private VmLoadWeightCache vmLoadWeightCache;
	
	private final static String[] vmNames = {"vm0", "vm1", "vm2"};
	private final static String[] vmIps = {"10.1.1.88", "10.1.1.175", "10.1.1.60"};
	
	public void executeUpdatingWeight() {
		List<VmLoadData> allVmsLoadData = getAllVmsLoadData(vmIps);
		List<Double> allVmsResponseTime = getAllVmsReponseTime(vmIps);
		
		List<VmLoadNormalizedData> normalizedVmsLoadData = normalizeVmLoadData(allVmsLoadData, allVmsResponseTime);
		
		calculateAndUpdateVmWeight(vmNames, allVmsResponseTime, normalizedVmsLoadData);
	}
	
	private List<VmLoadData> getAllVmsLoadData(String[] vmIps) {
		List<VmLoadData> result = new ArrayList<VmLoadData>();
		
		for (String vmIp : vmIps) {
			result.add(vmLoadDataAsker.requestVmLoadData(vmIp));
		}
		
		return result;
	}
	
	private List<Double> getAllVmsReponseTime(String[] vmIps) {
		List<Double> result = new ArrayList<Double>();
		
		for (String vmIp : vmIps) {
			result.add(vmLoadDataAsker.getVmReponseTime(vmIp));
		}
		
		return result;
	}
	
	private List<VmLoadNormalizedData> normalizeVmLoadData(List<VmLoadData> allVmsLoadData, List<Double> allVmsResponseTime) {
		List<VmLoadNormalizedData> result = new ArrayList<VmLoadNormalizedData>();
		
		long totalThreadNum = 0L;
		long totalConnectionNum = 0L;
		double totalResponseTime = 0.0;
		
		for (VmLoadData vmLoadData : allVmsLoadData) {
			totalThreadNum += vmLoadData.getThreadNum();
			totalConnectionNum += vmLoadData.getTcpConnectionNum();
		}
		for (Double responseTime : allVmsResponseTime) {
			totalResponseTime += responseTime;
		}
		
		for (int i = 0; i < allVmsLoadData.size(); i++) {
			VmLoadNormalizedData vmLoadNormalizedData = new VmLoadNormalizedData();
			vmLoadNormalizedData.setCpuUsagePercentage(allVmsLoadData.get(i).getCpuUsagePercentage());
			vmLoadNormalizedData.setMemoryUsagePercentage(allVmsLoadData.get(i).getMemoryUsagePercentage()/100.0);
			vmLoadNormalizedData.setThreadNumPercentage((double)(allVmsLoadData.get(i).getThreadNum() / totalThreadNum));
			if (totalConnectionNum == 0L) {
				vmLoadNormalizedData.setTcpConnectionNumPercentage(0.0);
			}
			else {
				vmLoadNormalizedData.setTcpConnectionNumPercentage((double)(allVmsLoadData.get(i).getTcpConnectionNum() / totalConnectionNum));
			}
			vmLoadNormalizedData.setResponseTimeScale(allVmsResponseTime.get(i) / totalResponseTime);
			result.add(vmLoadNormalizedData);
		}
		
		return result;
	}
	
	private List<Double> factorVmLoadData(List<VmLoadNormalizedData> normalizedVmsLoadData) {
		List<Double> result = new ArrayList<Double>();
		
		for (VmLoadNormalizedData vmLoadNormalizedData : normalizedVmsLoadData) {
			result.add(vmLoadNormalizedData.getCpuUsagePercentage() * RequestScheduleProperties.getCpuCoefficient() +
					vmLoadNormalizedData.getMemoryUsagePercentage() * RequestScheduleProperties.getMemoryCoefficient() +
					vmLoadNormalizedData.getThreadNumPercentage() * RequestScheduleProperties.getThreadNumCoefficient() +
					vmLoadNormalizedData.getTcpConnectionNumPercentage() * RequestScheduleProperties.getTcpConnectionNumCoefficient() +
					vmLoadNormalizedData.getResponseTimeScale() * RequestScheduleProperties.getResponseTimeCoefficient());
		}
		
		return result;
	}
	
	private int max(int num1, int num2) {
		if (num1 >= num2) {
			return num1;
		}
		
		return num2;
	}
	
	private int min(int num1, int num2) {
		if (num1 <= num2) {
			return num1;
		}
		
		return num2;
	}
	
	private void calculateAndUpdateVmWeight(String[] vmNames, List<Double> allVmsResponseTime, List<VmLoadNormalizedData> normalizedVmsLoadData) {
		List<Double> factoredVmLoadData = factorVmLoadData(normalizedVmsLoadData);
		Map<String, Integer> vmLoadWeightCache = this.vmLoadWeightCache.getVmLoadWeight();
		Map<String, Integer> newVmLoadWeight = new HashMap<String, Integer>();
		
		for (int i = 0; i < factoredVmLoadData.size(); i++) {
			double vmLoadData = factoredVmLoadData.get(i);
			
			if (allVmsResponseTime.get(i) >= RequestScheduleProperties.getResponseTimeThreshold()) {
				newVmLoadWeight.put(vmNames[i], 0);
				continue;
			}
			
			int newWeight = 0;
			if (vmLoadData > RequestScheduleProperties.getOptimalLoad() + RequestScheduleProperties.getDeviation()) {
				newWeight = max(RequestScheduleProperties.getMinWeight(), (int)((1-RequestScheduleProperties.getConvergenceRateCoefficient1())*vmLoadWeightCache.get(vmNames[i])));
			}
			if (vmLoadData >= RequestScheduleProperties.getOptimalLoad() - RequestScheduleProperties.getDeviation() && vmLoadData <= RequestScheduleProperties.getOptimalLoad() + RequestScheduleProperties.getDeviation()) {
				newWeight = vmLoadWeightCache.get(vmNames[i]);
			}
			if (vmLoadData < RequestScheduleProperties.getOptimalLoad() - RequestScheduleProperties.getDeviation()) {
				newWeight = min(RequestScheduleProperties.getMaxWeight(), (int)((1+RequestScheduleProperties.getConvergenceRateCoefficient2())*vmLoadWeightCache.get(vmNames[i])));
			}
			newVmLoadWeight.put(vmNames[i], newWeight);
		}
		
		this.vmLoadWeightCache.updateVmLoadWeight(newVmLoadWeight);
	}
}
