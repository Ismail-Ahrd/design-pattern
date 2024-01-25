import java.util.ArrayList;
import java.util.List;

public class Employees implements Cloneable{

    private final List<String> empList;

    public Employees(){
        empList = new ArrayList<String>();
    }

    public Employees(List<String> list){
        this.empList=list;
    }
    public void loadData(){
        //read all employees from database and put into the list
        empList.add("Pankaj");
        empList.add("Raj");
        empList.add("David");
        empList.add("Lisa");
    }

    public List<String> getEmpList() {
        return empList;
    }

    @Override
    public Employees clone() throws CloneNotSupportedException{
        // List<String> temp = new ArrayList<String>(this.getEmpList());
        // return new Employees(temp);
        return (Employees) super.clone();
    }

}