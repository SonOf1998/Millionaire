import static org.junit.Assert.*;

import org.junit.Test;

public class TimeAndPrizeFormatTest 
{
	@Test
	public void timeFormat()
	{
		assertEquals("00:00", Highscore.mmss(0));
		assertEquals("59:59", Highscore.mmss(3599));
	}
	
	@Test
	public void prizeFormat()
	{
		assertEquals("0", PrizeList.intGroupedByThreeDigits(0));
		assertEquals("25 000", PrizeList.intGroupedByThreeDigits(25000));
		assertEquals("999 999 999", PrizeList.intGroupedByThreeDigits(999999999));
	}

}
