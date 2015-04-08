package collector.dto;

/**
 * 进行归一化后的虚拟机负载数据
 * 
 * @author Wang Chao
 * 
 * @date 2015-3-25 下午3:03:20
 * 
 */
public class VmLoadNormalizedData {

	private double cpuUsagePercentage;
	private double memoryUsagePercentage;
	private double threadNumPercentage;
	private double tcpConnectionNumPercentage;
	private double responseTimeScale;

	public double getCpuUsagePercentage() {
		return cpuUsagePercentage;
	}

	public void setCpuUsagePercentage(double cpuUsagePercentage) {
		this.cpuUsagePercentage = cpuUsagePercentage;
	}

	public double getMemoryUsagePercentage() {
		return memoryUsagePercentage;
	}

	public void setMemoryUsagePercentage(double memoryUsagePercentage) {
		this.memoryUsagePercentage = memoryUsagePercentage;
	}

	public double getThreadNumPercentage() {
		return threadNumPercentage;
	}

	public void setThreadNumPercentage(double threadNumPercentage) {
		this.threadNumPercentage = threadNumPercentage;
	}

	public double getTcpConnectionNumPercentage() {
		return tcpConnectionNumPercentage;
	}

	public void setTcpConnectionNumPercentage(double tcpConnectionNumPercentage) {
		this.tcpConnectionNumPercentage = tcpConnectionNumPercentage;
	}

	public double getResponseTimeScale() {
		return responseTimeScale;
	}

	public void setResponseTimeScale(double responseTimeScale) {
		this.responseTimeScale = responseTimeScale;
	}

}
