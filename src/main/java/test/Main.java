import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.*;

public class Main {
	public static void main(String... args) throws Exception{
		A a = new A(4, 5);
		
		System.out.println(toJson(a));
	}
	private static String toJson(Object obj) throws Exception{
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder("{");
		for(Field f : fields){
			f.setAccessible(true);
			Annotation ignore = f.getAnnotation(Ignore.class);
			if(ignore != null){
				continue;
			}
			builder.append("\"")
					.append(f.getName())
					.append("\" : \"")
					.append(f.get(obj))
					.append("\" ,");
		}
		builder.setLength(builder.length() - 1);
		builder.append("}");
		return builder.toString();
	}
}
class A {
	@Ignore
	private int x;
	private int c;
	public A(int x, int c){
		this.x = x;
		this.c = c;
	}
	public int getX(){
		return this.x;
	}
	public int getC(){
		return this.c;
	}
}
class B extends A {
	private int y;
	public B(int x, int c, int y){
		super(x,c);
		this.y = y;
	}
	public int getY(){
		return this.y;
	}
}
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Ignore {

}
