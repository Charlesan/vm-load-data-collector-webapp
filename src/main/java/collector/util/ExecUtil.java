package collector.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用于在本地执行Shell命令
 * 
 * @author Wang Chao
 * 
 * @date 2015-4-6 下午7:22:08
 * 
 */
public class ExecUtil {

	public static String execCmd(String cmd) {
		String s = null;
		StringBuffer strBuffer = new StringBuffer();

		try {
			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

//			BufferedReader stdError = new BufferedReader(new InputStreamReader(
//					p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
//				System.out.println(s);
				strBuffer.append(s);
			}

			// read any errors from the attempted command
//			while ((s = stdError.readLine()) != null) {
//				System.out.println(s);
//			}

//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
//			System.exit(-1);
		}
		
		return strBuffer.toString();
	}
}
