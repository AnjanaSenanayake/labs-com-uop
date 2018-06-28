class Student
{
	private String name;
	private int attendance;
	public static int allattendance=0;

	public Student(String name,int attendance)
	{
		this.name=name;
		this.attendance=attendance;
		allattendance+=attendance;
		
	}
	public String name()
	{
		return this.name;
	}
	
	public int attendance()
	{
		return this.attendance;
	}
	
	public int attendancePercentage()
	{
		return this.attendance*100/45;
	}
	
	public static int avgStudentAttendance()
	{
		return allattendance*100/(61*45);
	}
	
}