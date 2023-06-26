package kr.or.bit.dto;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Emp {
	private long empno;
	private String ename;
	private String job;
	private long mgr;
	private String hiredate;
	private long sal;
	private long comm;
	private long deptno;
	private String filename;
	
	private CommonsMultipartFile file;
	
	
	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getEmpno() {
		return empno;
	}

	public void setEmpno(long empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public long getMgr() {
		return mgr;
	}

	public void setMgr(long mgr) {
		this.mgr = mgr;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public long getSal() {
		return sal;
	}

	public void setSal(long sal) {
		this.sal = sal;
	}

	public long getComm() {
		return comm;
	}

	public void setComm(long comm) {
		this.comm = comm;
	}

	public long getDeptno() {
		return deptno;
	}

	public void setDeptno(long deptno) {
		this.deptno = deptno;
	}

	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate=" + hiredate
				+ ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
	}

}
