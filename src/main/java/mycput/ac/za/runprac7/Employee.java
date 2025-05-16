package mycput.ac.za.runprac7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author mogamatyaseenkannemeyer
 */
public class Employee {
    
    protected String role;
    protected String name;
    protected double salary;

    public Employee(String role, String name, double salary) {
        this.role = role;
        this.name = name;
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    } 
}
