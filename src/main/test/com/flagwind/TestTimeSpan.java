package com.flagwind;

import com.flagwind.commons.Monment;
import com.flagwind.commons.TimeSpan;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestTimeSpan
{

	@Test
	public void test()
	{
		Monment dt1 = Monment.build();
		Monment dt2 = Monment.build().addDays(-10);
		TimeSpan span = dt1.diff(dt2);
		TestCase.assertEquals(10,span.totalDays());
	}
}
