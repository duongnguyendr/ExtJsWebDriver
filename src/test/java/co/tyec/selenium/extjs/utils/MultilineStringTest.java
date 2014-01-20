package co.tyec.selenium.extjs.utils;

import org.junit.Assert;
import org.junit.Test;

public class MultilineStringTest {
	@Test
	public void testMLS(){
		final String mls = MultilineString.MLS(/*line1
		line2
		line3*/);
		
		Assert.assertNotNull(mls);
		Assert.assertNotEquals("", mls);
		
		String[] mlsArray = mls.split("\n");
		Assert.assertEquals("line1", mlsArray[0].trim());
		Assert.assertEquals("line2", mlsArray[1].trim());
		Assert.assertEquals("line3", mlsArray[2].trim());
	}
}
