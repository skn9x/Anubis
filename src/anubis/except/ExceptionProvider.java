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
	private static final String pattern_newNotCallable_0;
	private static final String pattern_newNotCallable_1;
	private static final String pattern_newSlotNotFound_2;
	private static final String pattern_newSlotReadonly_1;
	private static final String pattern_newSlotReadonly_2;
	private static final String pattern_newVoidOperation_0;
	private static final String pattern_newParseExceptionByInvalidChars_3;
	private static final String pattern_newParseExceptionByStringNotTerminated_3;
	
	static {
		String baseName = ExceptionProvider.class.getPackage().getName() + "." + "messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		
		pattern_newFactoryNotReady_0 = bundle.getString("message.FactoryNotReady.0");
		pattern_newJOverloadMismatch_0 = bundle.getString("message.JOverloadMismatch.0");
		pattern_newNotCallable_0 = bundle.getString("message.NotCallable.0");
		pattern_newNotCallable_1 = bundle.getString("message.NotCallable.1");
		pattern_newSlotNotFound_2 = bundle.getString("message.SlotNotFound.2");
		pattern_newSlotReadonly_1 = bundle.getString("message.SlotReadonly.1");
		pattern_newSlotReadonly_2 = bundle.getString("message.SlotReadonly.2");
		pattern_newVoidOperation_0 = bundle.getString("message.VoidOperation.0");
		pattern_newParseExceptionByInvalidChars_3 = bundle.getString("message.ParseExceptionByInvalidChars.3");
		pattern_newParseExceptionByStringNotTerminated_3 = bundle.getString("message.ParseExceptionByStringNotTerminated.3");
	}
	
	private ExceptionProvider() {
		;
	}
	
	public static AssertionException newAssertionException() {
		return new AssertionException();
	}
	
	/**
	 * {@link ObjectFactory} �������ł��Ă��Ȃ����Ƃ�������O���쐬���܂��B
	 * @return ��O�I�u�W�F�N�g
	 */
	public static FactoryNotReadyException newFactoryNotReady() {
		return new FactoryNotReadyException(format(pattern_newFactoryNotReady_0));
	}
	
	/**
	 * Java�̊֐���������Ȃ��������Ƃ�������O���쐬���܂��B
	 * @return ��O�I�u�W�F�N�g
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
	 * �I�u�W�F�N�g�� {@link ACallable} �łȂ��������Ƃ�������O���쐬���܂��B
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return ��O�I�u�W�F�N�g
	 */
	public static NotCallableException newNotCallable(AnubisObject obj) {
		return new NotCallableException(format(pattern_newNotCallable_1, Operator.toDebugString(obj)));
	}
	
	/**
	 * �X�N���v�g�̃p�[�X�Ɏ��s�������Ƃ�������O���쐬���܂��B
	 * @param cause �����ƂȂ�����O�I�u�W�F�N�g
	 * @return ��O�I�u�W�F�N�g
	 */
	public static AnubisParserException newParseException(Throwable cause) {
		return new AnubisParserException(cause);
	}
	
	/**
	 * �X�N���v�g�̃p�[�X�Ɏ��s�������Ƃ�������O���쐬���܂��B
	 * @param message ���b�Z�[�W
	 * @return ��O�I�u�W�F�N�g
	 */
	public static AnubisParserException newParseExceptionByInvalidChars(String text, String code, int line, int column) {
		return new AnubisParserException(format(pattern_newParseExceptionByInvalidChars_3, text, code, line, column));
	}
	
	/**
	 * �X�N���v�g�̃p�[�X�Ɏ��s�������Ƃ�������O���쐬���܂��B
	 * @param message ���b�Z�[�W
	 * @return ��O�I�u�W�F�N�g
	 */
	public static AnubisParserException newParseExceptionByStringNotTerminated(String text, int line, int column) {
		return new AnubisParserException(format(pattern_newParseExceptionByStringNotTerminated_3, text, line, column));
	}
	
	/**
	 * �X���b�g��������Ȃ��������Ƃ�������O���쐬���܂��B
	 * @param obj �X���b�g������I�u�W�F�N�g
	 * @param name �X���b�g��
	 * @return ��O�I�u�W�F�N�g
	 */
	public static SlotNotFoundException newSlotNotFound(AnubisObject obj, String name) {
		return new SlotNotFoundException(format(pattern_newSlotNotFound_2, Operator.toDebugString(obj), name));
	}
	
	/**
	 * �X���b�g���ǂݎ���p�̂��ߕύX�ł��Ȃ��������Ƃ�������O���쐬���܂��B
	 * @param obj �X���b�g������I�u�W�F�N�g
	 * @param name �X���b�g��
	 * @return ��O�I�u�W�F�N�g
	 */
	public static SlotReadonlyException newSlotReadonly(AnubisObject obj, String name) {
		return new SlotReadonlyException(format(pattern_newSlotReadonly_2, Operator.toDebugString(obj), name));
	}
	
	/**
	 * �X���b�g���ǂݎ���p�̂��ߕύX�ł��Ȃ��������Ƃ�������O���쐬���܂��B
	 * @param name �X���b�g��
	 * @return ��O�I�u�W�F�N�g
	 */
	public static SlotReadonlyException newSlotReadonly(String name) {
		return new SlotReadonlyException(format(pattern_newSlotReadonly_1, name));
	}
	
	/**
	 * �X���b�g���ǂݎ���p�̂��ߕύX�ł��Ȃ��������Ƃ�������O���쐬���܂��B
	 * @param cause �����ƂȂ�����O�I�u�W�F�N�g
	 * @param obj �X���b�g������I�u�W�F�N�g
	 * @param name �X���b�g��
	 * @return ��O�I�u�W�F�N�g
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
	 * void �ɑ΂��鑀����s�������Ƃ�������O���쐬���܂��B
	 * @return ��O�I�u�W�F�N�g
	 */
	public static VoidOperationException newVoidOperation() {
		return new VoidOperationException(format(pattern_newVoidOperation_0));
	}
	
	/**
	 * ��O�I�u�W�F�N�g�� {@link AnubisRuntimeException} �ŕ�݂܂��B
	 * @param ex ��O�I�u�W�F�N�g
	 * @return ��O�I�u�W�F�N�g
	 */
	public static AnubisRuntimeException wrapRuntimeException(Throwable ex) {
		return new AnubisRuntimeException(ex);
	}
	
	private static String format(String pattern, Object... args) {
		return MessageFormat.format(pattern, args);
	}
}
