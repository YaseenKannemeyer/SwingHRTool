// -------------------------
// 1. PACKAGE & IMPORTS
// -------------------------

// Defines the package this class belongs to. Helps organize classes and avoid naming conflicts.
package mycput.ac.za.runprac7;

// These imports allow us to build a GUI application using Java's Swing framework.
import javax.swing.*;
import javax.swing.table.DefaultTableModel; // For handling table data

// AWT provides more basic GUI tools that Swing builds on, including layout managers.
import java.awt.*;
import java.awt.event.*; // For handling button clicks, combo selections, etc.

// To store and manage multiple employee objects dynamically
import java.util.ArrayList;

// -------------------------
// 2. CLASS DECLARATION
// -------------------------

/**
 * RunPrac7 is a GUI application that allows users to input employee details (role, name, salary),
 * store them in memory, and display them in a table.
 *
 * Author: MogamatYaseenKannemeyer
 */
public class RunPrac7 extends JFrame {

    // -------------------------
    // 3. VARIABLE DECLARATIONS
    // -------------------------

    // 🔹 Combo box to let the user select the employee's role
    private JComboBox<String> roleComboBox;

    // 🔹 Text fields for user input: employee's name and salary
    private JTextField nameField, salaryField;

    // 🔹 Buttons to perform actions: add employee, show all employees
    private JButton addButton, showAllButton;

    // 🔹 Table components to display employee data
    private JTable table;
    private DefaultTableModel tableModel;

    // 🔹 ArrayList to hold Employee objects (acts as in-memory database)
    private ArrayList<Employee> employeeList = new ArrayList<>();

    // -------------------------
    // 4. CONSTRUCTOR (UI Setup)
    // -------------------------

    public RunPrac7() {
        setTitle("Employee Role Table"); // Sets window title
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Ensures app closes when window is closed
        setLayout(new BorderLayout()); // Divides the window into regions (North, Center, etc.)

        // ========== INPUT SECTION (TOP PANEL) ==========

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // 🔽 ROLE SELECTION COMBO BOX
        roleComboBox = new JComboBox<>(new String[]{"None Selected", "Manager", "Developer", "Intern"});
        inputPanel.add(new JLabel("Select Role:"));   // Label before combo box
        inputPanel.add(roleComboBox);                 // Combo box itself

        // 📝 NAME FIELD
        nameField = new JTextField(); // Input field for employee name
        inputPanel.add(new JLabel("Enter Name:"));    // Label before name field
        inputPanel.add(nameField);                    // Name input field

        // 💰 SALARY FIELD
        salaryField = new JTextField(); // Input field for salary
        inputPanel.add(new JLabel("Enter Salary:"));  // Label before salary field
        inputPanel.add(salaryField);                  // Salary input field

        // ➕ ADD BUTTON
        addButton = new JButton("Add");               // Button to add employee to the list
        inputPanel.add(addButton);                    // Add to panel

        // 📋 SHOW ALL BUTTON
        showAllButton = new JButton("Show All");      // Button to show all employees in the table
        inputPanel.add(showAllButton);                // Add to panel

        // ========== TABLE SECTION (CENTER PANEL) ==========

        // Table headers: Role | Name | Salary
        tableModel = new DefaultTableModel(new String[]{"Role", "Name", "Salary"}, 0);
        table = new JTable(tableModel); // JTable initialized with the model

        JScrollPane scrollPane = new JScrollPane(table); // Wrap in scroll pane to support scrolling

        // ========== ADD PANELS TO FRAME ==========

        add(inputPanel, BorderLayout.NORTH);  // Place input panel at the top
        add(scrollPane, BorderLayout.CENTER); // Table in the center

        // ========== BUTTON FUNCTIONALITY / EVENT HANDLING ==========

        // ➕ ADD EMPLOYEE FUNCTIONALITY
        addButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String salaryText = salaryField.getText().trim();

            // Ensure valid input: no empty fields and valid role selected
            if (!"None Selected".equals(role) && !name.isEmpty() && !salaryText.isEmpty()) {
                try {
                    double salary = Double.parseDouble(salaryText); // Convert salary to a number
                    Employee emp;

                    // Dynamically create appropriate Employee object
                    switch (role) {
                        case "Manager":
                            emp = new Manager(name, salary);
                            break;
                        case "Developer":
                            emp = new Developer(name, salary);
                            break;
                        case "Intern":
                            emp = new Intern(name, salary);
                            break;
                        default:
                            emp = new Employee(role, name, salary); // Default fallback
                    }

                    // Store employee object
                    employeeList.add(emp);

                    // Notify user
                    JOptionPane.showMessageDialog(this, "Employee added successfully!");

                    // Reset form for next entry
                    roleComboBox.setSelectedIndex(0);
                    nameField.setText("");
                    salaryField.setText("");

                } catch (NumberFormatException ex) {
                    // Error if salary is not a valid number
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for salary.");
                }
            } else {
                // Validation failed (missing info)
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        });

        // 📋 SHOW ALL EMPLOYEES FUNCTIONALITY
        showAllButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Clear existing table rows

            // Add each employee from the list into the table
            for (Employee emp : employeeList) {
                tableModel.addRow(new Object[]{emp.getRole(), emp.getName(), emp.getSalary()});
            }
        });

        // ========== FINAL WINDOW SETTINGS ==========

        setSize(500, 400);              // Window size
        setLocationRelativeTo(null);    // Center window on screen
        setVisible(true);               // Show window
    }

    // -------------------------
    // 5. MAIN METHOD
    // -------------------------

    public static void main(String[] args) {
        // Ensure GUI runs on the Event Dispatch Thread (best practice in Swing)
        SwingUtilities.invokeLater(RunPrac7::new);
    }
}
