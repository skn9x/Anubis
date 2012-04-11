package anubis;

/**
 * @author SiroKuro
 */
public interface AnubisObject {
	/**
	 * ���̃I�u�W�F�N�g(����уv���g�I�u�W�F�N�g)����X���b�g���������܂��B
	 * @param name �X���b�g��
	 * @return �X���b�g�ɐݒ肳��Ă����I�u�W�F�N�g
	 * @see AnubisObject#getSlot(String)
	 */
	public AnubisObject findSlot(String name);
	
	/**
	 * ���̃I�u�W�F�N�g(����уv���g�I�u�W�F�N�g)����X���b�g�Q�Ƃ��������܂��B
	 * @param name �X���b�g��
	 * @return �X���b�g�ւ̎Q��
	 * @see AnubisObject#findSlot(String)
	 */
	public SlotRef findSlotRef(String name);
	
	/**
	 * ���̃I�u�W�F�N�g�̓���X���b�g��Ԃ��܂��B
	 * @param type ����X���b�g�̎��
	 * @return �X���b�g�ɐݒ肳��Ă����I�u�W�F�N�g
	 */
	public AnubisObject getSlot(SpecialSlot type);
	
	/**
	 * ���̃I�u�W�F�N�g����X���b�g���������܂��B
	 * @param name �X���b�g��
	 * @return �X���b�g�ɐݒ肳��Ă����I�u�W�F�N�g
	 * @see AnubisObject#findSlot(String)
	 */
	public AnubisObject getSlot(String name);
	
	/**
	 * ���̃I�u�W�F�N�g�̎�ނ𕶎���ŕԂ��܂��B
	 * @return �I�u�W�F�N�g�̎�ނ�\��������
	 */
	public String getType();
	
	/**
	 * ���̃I�u�W�F�N�g�̓���X���b�g��ݒ肵�܂��B�I�u�W�F�N�g�� null �̏ꍇ�͓���X���b�g���폜����܂��B
	 * @param type ����X���b�g�̎��
	 * @param value �I�u�W�F�N�g
	 */
	public void setSlot(SpecialSlot type, AnubisObject value);
	
	/**
	 * ���̃I�u�W�F�N�g�ɃX���b�g��ݒ肵�܂��B�I�u�W�F�N�g�� null �̏ꍇ�̓X���b�g���폜����܂��B
	 * @param name �X���b�g��
	 * @param value �I�u�W�F�N�g
	 */
	public void setSlot(String name, AnubisObject value);
	
	/**
	 * ���̃I�u�W�F�N�g�ɃX���b�g��ݒ肵�܂��B�I�u�W�F�N�g�� null �̏ꍇ�̓X���b�g���폜����܂��B
	 * @param name �X���b�g��
	 * @param value �I�u�W�F�N�g
	 * @param readonly ���̃X���b�g��ǂݎ���p�ɂ���Ȃ�� true
	 */
	public void setSlot(String name, AnubisObject value, boolean readonly);
}
