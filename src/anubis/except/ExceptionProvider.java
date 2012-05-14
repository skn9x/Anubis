package anubis.except;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import anubis.ACallable;
import anubis.AnubisObject;
import anubis.ast.Position;
import anubis.code.asm.BlockEmitter.IllegalGotoException;
import anubis.runtime.ObjectFactory;
import anubis.runtime.Operator;

/**
 * @author SiroKuro
 */
public class ExceptionProvider {
	private static final String pat_FactoryNotReady_0;
	private static final String pat_JOverloadMismatch_0;
	private static final String pat_NotCallable_1;
	private static final String pat_SlotNotFound_2;
	private static final String pat_ObjectFreeze_0;
	private static final String pat_ObjectFreeze_1;
	private static final String pat_ObjectFreeze_2;
	private static final String pat_VoidOperation_0;
	private static final String pat_ParseExceptionByInvalidChars_3;
	private static final String pat_ParseExceptionByStringNotTerminated_3;
	private static final String pat_ParseException_0;
	private static final String pat_ParseException_1;
	private static final String pat_IllegalValue_2;
	private static final String pat_ClassCast_2;
	
	static {
		String baseName = ExceptionProvider.class.getPackage().getName() + "." + "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		
		pat_FactoryNotReady_0 = bundle.getString("message.FactoryNotReady.0");
		pat_JOverloadMismatch_0 = bundle.getString("message.JOverloadMismatch.0");
		pat_NotCallable_1 = bundle.getString("message.NotCallable.1");
		pat_SlotNotFound_2 = bundle.getString("message.SlotNotFound.2");
		pat_ObjectFreeze_0 = bundle.getString("message.ObjectFreeze.0");
		pat_ObjectFreeze_1 = bundle.getString("message.ObjectFreeze.1");
		pat_ObjectFreeze_2 = bundle.getString("message.ObjectFreeze.2");
		pat_VoidOperation_0 = bundle.getString("message.VoidOperation.0");
		pat_ParseExceptionByInvalidChars_3 = bundle.getString("message.ParseExceptionByInvalidChars.3");
		pat_ParseExceptionByStringNotTerminated_3 = bundle.getString("message.ParseExceptionByStringNotTerminated.3");
		pat_ParseException_0 = bundle.getString("message.ParseException.0");
		pat_ParseException_1 = bundle.getString("message.ParseException.1");
		pat_IllegalValue_2 = bundle.getString("message.IllegalValue.2");
		pat_ClassCast_2 = bundle.getString("message.ClassCast.2");
	}
	
	private ExceptionProvider() {
		;
	}
	
	public static AssertionException newAssertionException() {
		return new AssertionException();
	}
	
	public static ClassCastException newClassCastException(Class<?> expected, AnubisObject value) {
		return new ClassCastException(format(pat_ClassCast_2, expected, value));
	}
	
	public static CompileException newCompileException(String message, Position pos, IllegalGotoException cause) {
		CompileException result = new CompileException(message);
		if (cause != null) {
			result.initCause(cause);
		}
		return result;
	}
	
	/**
	 * {@link ObjectFactory} が準備できていないことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static FactoryNotReadyException newFactoryNotReady() {
		return new FactoryNotReadyException(format(pat_FactoryNotReady_0));
	}
	
	public static IllegalValueException newIllegalValue(Object expected, AnubisObject value) {
		return new IllegalValueException(format(pat_IllegalValue_2, expected, value));
	}
	
	public static CompileException newInternalCompileExeption(Throwable cause) {
		return new CompileException("internal compile error", cause);
	}
	
	/**
	 * Javaの関数が見つからなかったことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static JOverloadMismatchException newJOverloadMismatch() {
		return new JOverloadMismatchException(format(pat_JOverloadMismatch_0));
	}
	
	/**
	 * @param ex
	 * @return
	 */
	public static JOverloadMismatchException newJOverloadMismatch(ClassCastException ex) {
		return new JOverloadMismatchException(format(pat_JOverloadMismatch_0), ex);
	}
	
	/**
	 * オブジェクトが {@link ACallable} でなかったことを示す例外を作成します。
	 * @param obj 対象オブジェクト
	 * @return 例外オブジェクト
	 */
	public static NotCallableException newNotCallable(AnubisObject obj) {
		return new NotCallableException(format(pat_NotCallable_1, Operator.toDebugString(obj)));
	}
	
	/**
	 * オブジェクトが凍結されているためスロットが変更できなかったことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static ObjectFreezeException newObjectFreeze() {
		return new ObjectFreezeException(format(pat_ObjectFreeze_0));
	}
	
	/**
	 * スロットが読み取り専用のため変更できなかったことを示す例外を作成します。
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static ObjectFreezeException newObjectFreeze(AnubisObject obj, String name) {
		return new ObjectFreezeException(format(pat_ObjectFreeze_2, Operator.toDebugString(obj), name));
	}
	
	/**
	 * オブジェクトが凍結されているためスロットが変更できなかったことを示す例外を作成します。
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static ObjectFreezeException newObjectFreeze(String name) {
		return new ObjectFreezeException(format(pat_ObjectFreeze_1, name));
	}
	
	/**
	 * オブジェクトが凍結されているためスロットが変更できなかったことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static ObjectFreezeException newObjectFreeze(Throwable cause, AnubisObject obj, String name) {
		ObjectFreezeException result = newObjectFreeze(obj, name);
		result.initCause(cause);
		return result;
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @return 例外オブジェクト
	 */
	public static ParseException newParseException(String text, Throwable cause) {
		return new ParseException(format(pat_ParseException_1, text), cause);
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @return 例外オブジェクト
	 */
	public static ParseException newParseException(Throwable cause) { // TODO この時でも行番号など出せるようにしたい
		return new ParseException(format(pat_ParseException_0), cause);
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param message メッセージ
	 * @return 例外オブジェクト
	 */
	public static ParseException newParseExceptionByInvalidChars(String text, String code, int line, int column) {
		return new ParseException(format(pat_ParseExceptionByInvalidChars_3, text, code, line, column));
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param message メッセージ
	 * @return 例外オブジェクト
	 */
	public static ParseException newParseExceptionByStringNotTerminated(String text, int line, int column) {
		return new ParseException(format(pat_ParseExceptionByStringNotTerminated_3, text, line, column));
	}
	
	/**
	 * スロットが見つからなかったことを示す例外を作成します。
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static SlotNotFoundException newSlotNotFound(AnubisObject obj, String name) {
		return new SlotNotFoundException(format(pat_SlotNotFound_2, Operator.toDebugString(obj), name));
	}
	
	public static UserException newUserException(AnubisObject value) {
		return new UserException(value);
	}
	
	/**
	 * void に対する操作を行ったことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static VoidOperationException newVoidOperation() {
		return new VoidOperationException(format(pat_VoidOperation_0));
	}
	
	/**
	 * 例外オブジェクトを {@link AnubisRuntimeException} で包みます。
	 * @param ex 例外オブジェクト
	 * @return 例外オブジェクト
	 */
	public static AnubisRuntimeException wrapRuntimeException(Throwable ex) {
		return new AnubisRuntimeException(ex);
	}
	
	private static String format(String pattern, Object... args) {
		return MessageFormat.format(pattern, args);
	}
}
