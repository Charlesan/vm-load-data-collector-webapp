package collector.dto;

/**
 * 从虚拟机收集得到的负载信息
 * 
 * @author Wang Chao
 *
 * @date 2015-3-25 下午2:56:34
 *
 */
public class VmLoadData {

	private double cpuUsagePercentage;
	private double memoryUsagePercentage;
	private long threadNum;
	private long tcpConnectionNum;

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

	public long getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(long threadNum) {
		this.threadNum = threadNum;
	}

	public long getTcpConnectionNum() {
		return tcpConnectionNum;
	}

	public void setTcpConnectionNum(long tcpConnectionNum) {
		this.tcpConnectionNum = tcpConnectionNum;
	}

}

