package anubis.runtime;

import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.code.CodeBlock;
import anubis.except.ExceptionProvider;
import anubis.except.FactoryNotReadyException;

public class AObjects {
	/**
	 * �J�����g Factory
	 */
	private static final ThreadLocal<ObjectFactory> current = new ThreadLocal<ObjectFactory>();
	
	/**
	 * bool �I�u�W�F�N�g��Ԃ��܂��B
	 * @param bool bool�l
	 * @return true �܂��� false �I�u�W�F�N�g
	 */
	public static APrimitive getBool(boolean bool) {
		return bool ? getCurrent().getTrue() : getCurrent().getFalse();
	}
	
	/**
	 * ���݂̃X���b�h�Ɋ֘A�t����ꂽ Factory ��Ԃ��܂��B
	 * @return Factory �C���X�^���X
	 * @throws FactoryNotReadyException Factory ���֘A�t�����Ă��Ȃ��Ƃ�
	 */
	public static ObjectFactory getCurrent() {
		ObjectFactory result = current.get();
		if (result != null)
			return result;
		else
			throw ExceptionProvider.newFactoryNotReady();
	}
	
	/**
	 * false �I�u�W�F�N�g��Ԃ��܂��B
	 * @return false �I�u�W�F�N�g
	 */
	public static APrimitive getFalse() {
		return getCurrent().getFalse();
	}
	
	/**
	 * Class �ɑΉ����� JClass �I�u�W�F�N�g��Ԃ��܂��B
	 * @param cls �N���X�I�u�W�F�N�g
	 * @return JClass �I�u�W�F�N�g / ������ null �̏ꍇ�� null
	 */
	public static JClass getJClass(Class<?> cls) {
		return getCurrent().getJClass(cls);
	}
	
	/**
	 * Object �ɑΉ����� JObject �I�u�W�F�N�g��Ԃ��܂��B
	 * @param object �I�u�W�F�N�g
	 * @return JObject �I�u�W�F�N�g / ������ null �̏ꍇ�� null
	 */
	public static JObject getJObject(Object object) {
		return getCurrent().getJObject(object);
	}
	
	/**
	 * null �I�u�W�F�N�g��Ԃ��܂��B
	 * @return the null
	 */
	public static APrimitive getNull() {
		return getCurrent().getNull();
	}
	
	/**
	 * Number �ɑΉ����� ANumber �I�u�W�F�N�g��Ԃ��܂��B
	 * @param value Number �I�u�W�F�N�g
	 * @return ANumber �I�u�W�F�N�g
	 */
	public static ANumber getNumber(Number value) {
		return getCurrent().getNumber(value);
	}
	
	/**
	 * �I�u�W�F�N�g�ɑΉ����� AnubisObject ��Ԃ��܂��B
	 * @param obj �I�u�W�F�N�g
	 * @return AnubisObject �C���X�^���X
	 */
	public static AnubisObject getObject(Object obj) {
		return getCurrent().getObject(obj);
	}
	
	/**
	 * String �ɑΉ����� AString �I�u�W�F�N�g��Ԃ��܂��B
	 * @param value String �I�u�W�F�N�g
	 * @return AString �I�u�W�F�N�g
	 */
	public static AString getString(String value) {
		return getCurrent().getString(value);
	}
	
	/**
	 * ���݂̃X���b�h�Ɋ֘A�t����ꂽ {@link TraitsFactory} ��Ԃ��܂��B
	 * @return TraitsFactory �I�u�W�F�N�g
	 */
	public static TraitsFactory getTraitsFactory() {
		return getCurrent().getTraitsFactory();
	}
	
	/**
	 * true �I�u�W�F�N�g��Ԃ��܂��B
	 * @return the true
	 */
	public static APrimitive getTrue() {
		return getCurrent().getTrue();
	}
	
	/**
	 * Context �I�u�W�F�N�g���쐬���܂��B
	 * @param outer �O���̃R���e�L�X�g�I�u�W�F�N�g
	 * @return Context �I�u�W�F�N�g
	 */
	public static AnubisObject newContext(AnubisObject outer) {
		return getCurrent().newContext(outer.getSlot(SpecialSlot.THIS), outer);
	}
	
	/**
	 * Context �I�u�W�F�N�g���쐬���܂��B
	 * @param outer �O���̃R���e�L�X�g�I�u�W�F�N�g
	 * @return Context �I�u�W�F�N�g
	 */
	public static AnubisObject newContext(AnubisObject _this, AnubisObject outer) {
		return getCurrent().newContext(_this, outer);
	}
	
	/**
	 * Function �I�u�W�F�N�g���쐬���܂��B
	 * @param body ���[�U�[�R�[�h
	 * @param outer �O���̃R���e�L�X�g�I�u�W�F�N�g
	 * @param args ����
	 * @return Function �I�u�W�F�N�g
	 */
	public static AFunction newFunction(CodeBlock body, AnubisObject outer, String... args) {
		return getCurrent().newFunction(body, outer, args);
	}
	
	/**
	 * Object �ɑΉ����� JObject �I�u�W�F�N�g���쐬���܂��B
	 * @param object �I�u�W�F�N�g
	 * @return JObject �I�u�W�F�N�g
	 */
	public static JObject newJObject(Object object) {
		return getCurrent().newJObject(object);
	}
	
	/**
	 * ��� List �I�u�W�F�N�g���쐬���܂��B
	 * @return List �I�u�W�F�N�g
	 */
	public static AList newList() {
		return getCurrent().newList();
	}
	
	/**
	 * ��� Map �I�u�W�F�N�g���쐬���܂��B
	 * @return Map �I�u�W�F�N�g
	 */
	public static AMap newMap() {
		return getCurrent().newMap();
	}
	
	/**
	 * ���[�U�I�u�W�F�N�g���쐬���܂��B
	 * @param outer �O���̃R���e�L�X�g�I�u�W�F�N�g
	 * @return �I�u�W�F�N�g
	 */
	public static AnubisObject newObject(AnubisObject outer) {
		return getCurrent().newObject(outer);
	}
	
	/**
	 * Range �I�u�W�F�N�g���쐬���܂��B
	 * @param start �J�n�l
	 * @param end �I���l
	 * @param step �X�e�b�v�l
	 * @return Range �I�u�W�F�N�g
	 */
	public static ARange newRange(AnubisObject start, AnubisObject end, AnubisObject step) {
		return getCurrent().newRange(start, end, step);
	}
	
	/**
	 * ��� Set �I�u�W�F�N�g���쐬���܂��B
	 * @return Set �I�u�W�F�N�g
	 */
	public static ASet newSet() {
		return getCurrent().newSet();
	}
	
	/**
	 * ���݂̃X���b�h�� Factory ���֘A�t���܂��B
	 * @param factory �֘A�t���� Factory �C���X�^���X / null �̎��͊֘A�t�����폜
	 * @return ���O�Ɋ֘A�t�����Ă��� Factory �C���X�^���X
	 */
	public static ObjectFactory setCurrent(ObjectFactory factory) {
		ObjectFactory result = current.get();
		if (factory != null)
			current.set(factory);
		else
			current.remove();
		return result;
	}
}
