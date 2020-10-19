package mock;

import mock.demo.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.stream.Collectors;

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
		System.out.println(service.getDate());
		System.out.println(String.format("asdfdf %s", Arrays.asList("a", "b", "c", "d").stream().collect(Collectors.joining(", ", "[", "]"))));
		System.out.println(String.join(Arrays.asList("a", "b", "c").stream().collect(Collectors.joining(", ", "[", "]")), "asdf"));
	}
}
