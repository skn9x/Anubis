package anubis;

/**
 * AnubisObject �̃X���b�g�ւ̎Q�Ƃ�\���N���X�ł��B
 * @author SiroKuro
 */
public class SlotRef {
	private final AnubisObject obj;
	private final String name;
	
	/**
	 * SlotRef �I�u�W�F�N�g�����������܂��B
	 * @param obj �Q�Ɛ�X���b�g���܂� AnubisObject
	 * @param name �Q�Ƃ���X���b�g��
	 */
	public SlotRef(AnubisObject obj, String name) {
		if (obj == null || name == null)
			throw new IllegalArgumentException();
		this.obj = obj;
		this.name = name;
	}
	
	/**
	 * �X���b�g����l���擾���܂��B
	 * @return �X���b�g�ɑ������Ă����l
	 */
	public AnubisObject get() {
		return obj.getSlot(name);
	}
	
	/**
	 * �Q�Ɛ�X���b�g���܂� KuzhaObject ��Ԃ��܂��B
	 * @return �Q�Ɛ�X���b�g���܂�  AnubisObject
	 */
	public AnubisObject getObject() {
		return obj;
	}
	
	/**
	 * �Q�Ƃ��Ă���X���b�g����Ԃ��܂��B
	 * @return �Q�Ƃ��Ă���X���b�g���B
	 */
	public String getSlotName() {
		return name;
	}
	
	/**
	 * �X���b�g�ɒl��ݒ肵�܂��B
	 * @param value �X���b�g�ɐݒ肷��l�B
	 */
	public void set(AnubisObject value) {
		obj.setSlot(name, value);
	}
	
	/**
	 * �X���b�g�ɒl��ݒ肵�܂��B
	 * @param value �X���b�g�ɐݒ肷��l�B
	 */
	public void set(AnubisObject value, boolean readonly) {
		obj.setSlot(name, value, readonly);
	}
}
