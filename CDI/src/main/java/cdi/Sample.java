package cdi;

import com.google.inject.Guice;
//import com.google.inject.Inject;
import com.google.inject.Injector;


import javax.inject.Inject;
import javax.inject.Singleton;
//import javax.inject.Singleton;
//import com.google.inject.Singleton;

/**
 * @author wincher
 * @since 2020/6/26
 * <p> PACKAGE_NAME <p>
 */
@Singleton
public class Sample {
	
	/**
	 * Guice also support javax inject
	 */
	@Inject
	Echo echo;
	
	void hello() {
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
		
		sample = injector.getInstance(Sample.class);
		System.out.println(sample);
		sample.hello();
		
		sample = injector.getInstance(Sample.class);
		System.out.println(sample);
		sample.hello();
	}

}

@Singleton
class Echo {
	void print() {
		System.out.println("Hello cdi");
	}
}