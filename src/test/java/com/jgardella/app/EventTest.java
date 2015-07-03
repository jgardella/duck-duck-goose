package com.jgardella.app;

import com.jgardella.app.backend.Event;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertTrue;
import org.junit.*;


public class EventTest
{
	@Test
	public void earlierEventShouldComeAfterMoreRecentEvent()
	{
		Event earlierEvent = new Event("Earlier Event", "meeting", LocalDateTime.of(2015, Month.FEBRUARY, 1, 0, 0));
		Event moreRecentEvent = new Event("More Recent Event", "meeting", LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0));
		assertTrue("Earlier event should come after more recent event.", earlierEvent.compareTo(moreRecentEvent) < 0);
	}

}
