package mock;

import mock.demo.Service;
import mock.diy.Mocky;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import static org.mockito.Mockito.when;

/**
 * @author wincher
 * <p> mock <p>
 *  a complete Mock include:
 * 	target, consume condition, expected result, verify result
 */
public class mockitoTest {
	
	@Mock
	Service service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void helloMockito() {
		when(service.getDate()).thenAnswer(invocationOnMock -> "123");
		Assert.assertEquals(service.getDate(), "123");
	}
	
	@Test
	public void helloDiyMocky() {
		Calendar calendar = Mocky.mocky(Calendar.class);
		Mocky.when(calendar.getFirstDayOfWeek()).thenReturn(3);
		Assert.assertEquals(calendar.getFirstDayOfWeek(), 3);
	}
}
