package anubis;

/**
 * @author SiroKuro
 */
public interface ACallable extends AnubisObject {
	/**
	 * ���̃I�u�W�F�N�g���֐��Ƃ��ČĂяo���܂��B
	 * @param _this
	 * @param objects
	 * @return
	 */
	public AnubisObject call(AnubisObject _this, AnubisObject... objects);
}
