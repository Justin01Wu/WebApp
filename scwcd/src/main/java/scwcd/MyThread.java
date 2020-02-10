package scwcd;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MyThread {
	
	public static String show() {
		StringBuffer sb = new StringBuffer();
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		long[] threadIds = threadMXBean.getAllThreadIds();
		for (int ii = 0; ii < threadIds.length; ii++) {
			printStack(sb, threadMXBean.getThreadInfo(threadIds[ii], 100));
		}
		return sb.toString();
	}
	
	private static void printStack(StringBuffer sb, ThreadInfo threadInfo) {
		Thread.State state = threadInfo.getThreadState();
//		if (state != Thread.State.RUNNABLE && state != Thread.State.BLOCKED && state != Thread.State.TIMED_WAITING)
//			return;
		
		StackTraceElement[] ste = threadInfo.getStackTrace();
		String[] elements = new String[ste.length];
		int lastEl = -1;
		int ij;
		for (ij = ste.length-1; ij >=0 ; ij--) {
			elements[ij] = ste[ij].toString();
			if (lastEl>=0) {
				continue;
			}
			if ( elements[ij].startsWith("scwcd") || elements[ij].startsWith("listener") ) {
				lastEl = ij;
			}
		}
		
		String newLine = System.getProperty("line.separator");
		
		sb.append("Thread ").append(state).append(": ").append(threadInfo.getThreadName());
		sb.append(", stack="+ste.length);
		if (lastEl<0 && elements.length>0) {
			sb.append(", ").append(elements[0]).append(newLine);
			return;
		}
		
		sb.append(newLine);
		for (ij = 0; ij <= lastEl; ij++) {
			sb.append('\t');
			sb.append(elements[ij]).append(newLine);
		}
	}
}
