package anubis.code;

public class Option {
	private String programName;
	private String srcFileName;
	private boolean disableAssertion;
	
	public String getProgramName() {
		return programName != null ? programName : "UserCode";
	}
	
	public String getSrcFileName() {
		return srcFileName != null ? srcFileName : "[unknown]";
	}
	
	public boolean isDisableAssertion() {
		return disableAssertion;
	}
	
	public void isNoAssert(boolean disableAssertion) {
		this.disableAssertion = disableAssertion;
	}
	
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}
}
