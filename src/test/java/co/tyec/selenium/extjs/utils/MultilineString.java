package co.tyec.selenium.extjs.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultilineString {
	final static Logger logger = LoggerFactory.getLogger(MultilineString.class);
	
	/**
	 * Implement multiple line strings via comments passed into the method.
	 * 
	 * <pre>
	 * {@code
	 *   import static MultiLineString.MLS;
	 *   ..
	 *   static final String myMls = S(&#47;*line 1
	 *   line 2
	 *   line 3*&#47;);
	 * 	 System.out.println(s);
	 * }
	 * </pre>
	 * 
	 * @return String representation of the multiple line string.
	 */
	public static String MLS() {
		String ret = "";
		try {
			// get the method that called this one
			StackTraceElement element = new RuntimeException().getStackTrace()[1];
			String name = element.getClassName().replace('.', '/') + ".java";
			InputStream in = getClassLoader().getResourceAsStream(name);
			if(in == null) {
				logger.debug("Could not find java file in classpath: " + name + " ... Add source and test source to classpath (add as resources)");
//				Collection<String> resList = ResourceList.getResources(Pattern.compile(".*"));
//				for(String current : resList ){
//					if(current.contains(".java"))
//						logger.debug("Resources: " + current);
//				}
			}
			String s = convertStreamToString(in);
			ret = s.substring(s.indexOf("/*") + 2, s.indexOf("*/"));
		} catch (Exception e) {
			// Do nothing
			logger.debug("Exception parsing comment", e);
		}
		return ret;
	}
	
	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	private static ClassLoader getClassLoader() {
		//Thread.currentThread().getContextClassLoader();
		return ClassLoader.getSystemClassLoader();
	}
}
