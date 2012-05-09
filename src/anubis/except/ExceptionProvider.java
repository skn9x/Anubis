package anubis.except;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import anubis.ACallable;
import anubis.AnubisObject;
import anubis.runtime.ObjectFactory;
import anubis.runtime.Operator;

/**
 * @author SiroKuro
 */
public class ExceptionProvider {
	private static final String pattern_newFactoryNotReady_0;
	private static final String pattern_newJOverloadMismatch_0;
	private static final String pattern_newNotCallable_1;
	private static final String pattern_newSlotNotFound_2;
	private static final String pattern_newSlotReadonly_1;
	private static final String pattern_newSlotReadonly_2;
	private static final String pattern_newVoidOperation_0;
	private static final String pattern_newParseExceptionByInvalidChars_3;
	private static final String pattern_newParseExceptionByStringNotTerminated_3;
	private static final String pattern_newParseException_0;
	private static final String pattern_newParseException_1;
	
	static {
		String baseName = ExceptionProvider.class.getPackage().getName() + "." + "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		
		pattern_newFactoryNotReady_0 = bundle.getString("message.FactoryNotReady.0");
		pattern_newJOverloadMismatch_0 = bundle.getString("message.JOverloadMismatch.0");
		pattern_newNotCallable_1 = bundle.getString("message.NotCallable.1");
		pattern_newSlotNotFound_2 = bundle.getString("message.SlotNotFound.2");
		pattern_newSlotReadonly_1 = bundle.getString("message.SlotReadonly.1");
		pattern_newSlotReadonly_2 = bundle.getString("message.SlotReadonly.2");
		pattern_newVoidOperation_0 = bundle.getString("message.VoidOperation.0");
		pattern_newParseExceptionByInvalidChars_3 = bundle.getString("message.ParseExceptionByInvalidChars.3");
		pattern_newParseExceptionByStringNotTerminated_3 = bundle.getString("message.ParseExceptionByStringNotTerminated.3");
		pattern_newParseException_0 = bundle.getString("message.ParseException.0");
		pattern_newParseException_1 = bundle.getString("message.ParseException.1");
	}
	
	private ExceptionProvider() {
		;
	}
	
	public static AssertionException newAssertionException() {
		return new AssertionException();
	}
	
	/**
	 * {@link ObjectFactory} が準備できていないことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static FactoryNotReadyException newFactoryNotReady() {
		return new FactoryNotReadyException(format(pattern_newFactoryNotReady_0));
	}
	
	/**
	 * Javaの関数が見つからなかったことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static JOverloadMismatchException newJOverloadMismatch() {
		return new JOverloadMismatchException(format(pattern_newJOverloadMismatch_0));
	}
	
	/**
	 * @param ex
	 * @return
	 */
	public static JOverloadMismatchException newJOverloadMismatch(ClassCastException ex) {
		return new JOverloadMismatchException(format(pattern_newJOverloadMismatch_0), ex);
	}
	
	/**
	 * オブジェクトが {@link ACallable} でなかったことを示す例外を作成します。
	 * @param obj 対象オブジェクト
	 * @return 例外オブジェクト
	 */
	public static NotCallableException newNotCallable(AnubisObject obj) {
		return new NotCallableException(format(pattern_newNotCallable_1, Operator.toDebugString(obj)));
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @return 例外オブジェクト
	 */
	public static AnubisParserException newParseException(String text, Throwable cause) {
		return new AnubisParserException(format(pattern_newParseException_1, text), cause);
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @return 例外オブジェクト
	 */
	public static AnubisParserException newParseException(Throwable cause) {
		return new AnubisParserException(format(pattern_newParseException_0), cause);
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param message メッセージ
	 * @return 例外オブジェクト
	 */
	public static AnubisParserException newParseExceptionByInvalidChars(String text, String code, int line, int column) {
		return new AnubisParserException(format(pattern_newParseExceptionByInvalidChars_3, text, code, line, column));
	}
	
	/**
	 * スクリプトのパースに失敗したことを示す例外を作成します。
	 * @param message メッセージ
	 * @return 例外オブジェクト
	 */
	public static AnubisParserException newParseExceptionByStringNotTerminated(String text, int line, int column) {
		return new AnubisParserException(format(pattern_newParseExceptionByStringNotTerminated_3, text, line, column));
	}
	
	/**
	 * スロットが見つからなかったことを示す例外を作成します。
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static SlotNotFoundException newSlotNotFound(AnubisObject obj, String name) {
		return new SlotNotFoundException(format(pattern_newSlotNotFound_2, Operator.toDebugString(obj), name));
	}
	
	/**
	 * スロットが読み取り専用のため変更できなかったことを示す例外を作成します。
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static SlotReadonlyException newSlotReadonly(AnubisObject obj, String name) {
		return new SlotReadonlyException(format(pattern_newSlotReadonly_2, Operator.toDebugString(obj), name));
	}
	
	/**
	 * スロットが読み取り専用のため変更できなかったことを示す例外を作成します。
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static SlotReadonlyException newSlotReadonly(String name) {
		return new SlotReadonlyException(format(pattern_newSlotReadonly_1, name));
	}
	
	/**
	 * スロットが読み取り専用のため変更できなかったことを示す例外を作成します。
	 * @param cause 原因となった例外オブジェクト
	 * @param obj スロット検索先オブジェクト
	 * @param name スロット名
	 * @return 例外オブジェクト
	 */
	public static SlotReadonlyException newSlotReadonly(Throwable cause, AnubisObject obj, String name) {
		SlotReadonlyException result = newSlotReadonly(obj, name);
		result.initCause(cause);
		return result;
	}
	
	public static AnubisUserException newUserException(AnubisObject value) {
		return new AnubisUserException(value);
	}
	
	/**
	 * void に対する操作を行ったことを示す例外を作成します。
	 * @return 例外オブジェクト
	 */
	public static VoidOperationException newVoidOperation() {
		return new VoidOperationException(format(pattern_newVoidOperation_0));
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
