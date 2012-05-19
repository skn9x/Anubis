package anubis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AnubisObject の実装クラスに対して、型名を付与します。
 * @author SiroKuro
 */
@Target({
	ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TypeName {
	public String value();
}
