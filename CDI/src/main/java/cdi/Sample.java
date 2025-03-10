package cdi;

//import com.google.inject.*;


import com.google.inject.Guice;
import com.google.inject.Injector;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author wincher
 * @date 2020/6/26
 * <p> PACKAGE_NAME <p>
 */
@Singleton
public class Sample {
	
	/**
	 * Guice also support javax inject
	 */
	@Inject
	Echo echo;
	
	public Sample() {}

	
	void hello() {
		System.out.println("echo:" + echo);
		echo.print();
	}
	
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector();
		Sample sample = injector.getInstance(Sample.class);
		System.out.println(sample);
		sample.hello();

		sample = injector.getInstance(Sample.class);
		System.out.println(sample);
		sample.hello();
		System.out.println("是否一致:" + sample.equals(injector.getInstance(Sample.class)));
	}

}

@Singleton
class Echo {
	void print() {
		System.out.println("Hello cdi");
	}
}