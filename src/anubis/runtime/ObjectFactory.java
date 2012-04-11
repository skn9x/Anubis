package anubis.runtime;

import anubis.AnubisObject;
import anubis.code.CodeBlock;

/**
 * @author SiroKuro
 */
public interface ObjectFactory {
	/**
	 * false �I�u�W�F�N�g��Ԃ��܂�
	 * @return the false
	 */
	public abstract APrimitive getFalse();
	
	/**
	 * Class �ɑΉ����� JClass �I�u�W�F�N�g��Ԃ��܂��B
	 * @param cls �N���X�I�u�W�F�N�g
	 * @return JClass �I�u�W�F�N�g / ������ null �̏ꍇ�� null
	 */
	public abstract JClass getJClass(Class<?> cls);
	
	/**
	 * Object �ɑΉ����� JObject �I�u�W�F�N�g��Ԃ��܂��B
	 * @param object �I�u�W�F�N�g
	 * @return JObject �I�u�W�F�N�g / ������ null �̏ꍇ�� null
	 */
	public abstract JObject getJObject(Object object);
	
	/**
	 * null �I�u�W�F�N�g��Ԃ��܂��B
	 * @return the null
	 */
	public abstract APrimitive getNull();
	
	/**
	 * Number �ɑΉ����� ANumber �I�u�W�F�N�g��Ԃ��܂��B
	 * @param value Number �I�u�W�F�N�g
	 * @return ANumber �I�u�W�F�N�g
	 */
	public abstract ANumber getNumber(Number value);
	
	/**
	 * �I�u�W�F�N�g�ɑΉ����� AnubisObject ��Ԃ��܂��B
	 * @param obj �I�u�W�F�N�g
	 * @return AnubisObject �C���X�^���X
	 */
	public abstract AnubisObject getObject(Object obj);
	
	/**
	 * String �ɑΉ����� AString �I�u�W�F�N�g��Ԃ��܂��B
	 * @param value String �I�u�W�F�N�g
	 * @return AString �I�u�W�F�N�g
	 */
	public abstract AString getString(String value);
	
	/**
	 * ���݂̃X���b�h�Ɋ֘A�t����ꂽ {@link TraitsFactory} ��Ԃ��܂��B
	 * @return TraitsFactory �I�u�W�F�N�g
	 */
	public abstract TraitsFactory getTraitsFactory();
	
	/**
	 * true �I�u�W�F�N�g��Ԃ��܂��B
	 * @return the true
	 */
	public abstract APrimitive getTrue();
	
	/**
	 * Context �I�u�W�F�N�g���쐬���܂��B
	 * @param outer �O���̃R���e�L�X�g�I�u�W�F�N�g
	 * @return Lobby �I�u�W�F�N�g
	 */
	public abstract AnubisObject newContext(AnubisObject _this, AnubisObject outer);
	
	public abstract AFunction newFunction(CodeBlock body, AnubisObject outer, String... args);
	
	/**
	 * Object �ɑΉ����� JObject �I�u�W�F�N�g���쐬���܂��B
	 * @param object �I�u�W�F�N�g
	 * @return JObject �I�u�W�F�N�g
	 */
	public abstract JObject newJObject(Object object);
	
	public abstract AList newList();
	
	public abstract AMap newMap();
	
	public abstract AnubisObject newObject(AnubisObject outer);
	
	public abstract ARange newRange(AnubisObject start, AnubisObject end, AnubisObject step);
	
	public abstract ASet newSet();
}
