public class Student {
    private String num;//学号
    private String name;//姓名
    private double programming;//程序成绩
    private double dataStructure;//数据结构趁机
    private double sum;//总分
    private double average;//平均分
    private String time;//上课时间
    private String keName;//课程名称
    private String diDian;//上课地点

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProgramming() {
        return programming;
    }

    public void setProgramming(double programming) {
        this.programming = programming;
    }

    public double getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(double dataStructure) {
        this.dataStructure = dataStructure;
    }

    public Student(String num, String name) {
        this.num = num;
        this.name = name;
    }

    public Student(String num, String name, double programming, double dataStructure) {
        this.num = num;
        this.name = name;
        this.programming = programming;
        this.dataStructure = dataStructure;
    }

    //计算某个学生的总分和平均分
    public Student fun1(Student student) {
        student.sum = student.getProgramming() + student.getDataStructure();
        student.average = student.sum / 2.0;
        return student;
    }

    //输出学生的学号等..
    public void fun2(Student student) {
        System.out.println("学号" + student.num);
        System.out.println("姓名" + student.name);
        System.out.println("平均分" + student.average);
        System.out.println("总分" + student.sum);
    }

    //学生上课
    public void fun3(String time, String keName, String diDian) {
        this.time = time;
        this.keName = keName;
        this.diDian = diDian;
    }
    //打印学生上课
    public void fun4(Student student){
        System.out.println(student.name +"在"+student.time+",在"+student.diDian+"学习"+student.keName);
    }

    public static void main(String[] args) {
        Student student1 = new Student("100", "杨1", 80.0, 89);
        Student student2 = new Student("101", "杨2", 83.0, 89);
        //计算平均分和总分
        student1 = student1.fun1(student1);
        student2 = student2.fun1(student2);
        //打印分数
        student1.fun2(student1);
        student2.fun2(student2);
        //弄上课方法
        student1.fun3("2017年9月29日", "java", "3233");
        student2.fun3("2017年9月29日", "数据结构", "1402");
        //打印学生上课
        student1.fun4(student1);
        student2.fun4(student2);
    }
}
