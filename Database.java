package Project;
import java.util.ArrayList;
import Project.NameDirectory.BSTName;
/**
 * The DataBase stores related data for each table and interacts with each other for the specifications of the project 
 * @author luiseduardo @author jorgepadilla
 *
 */
public class Database {
	public NameDirectory table;
	public ArrayList<String> names;
	public Database(){
		table = new NameDirectory();
	}
	/**
	 * insertAddress --  Inserts name values to the table
	 * @param name
	 * @param address
	 */
	public void insertAddress(String name, Integer address){table.add(name, new Name(name, address));}
	/**
	 * getAddress -- Obtains the name values from the table
	 * @param name
	 * @param address
	 * @return
	 */
	public Name getAddress(String name, Integer address){return table.getName(name, address);}
	/**
	 * removeAddress -- Removes the name values from the table
	 * @param name
	 * @param address
	 * @return
	 */
	public Name removeAddress(String name, Integer address){return table.remove(name, address);}
	/**
	 * insertPayment -- Inserts invoice values to the table
	 * @param name
	 * @param noInvoice
	 * @param payment
	 */
	public void insertPayment(String name, Integer address, Integer invoiceNumber, Integer payment){
		table.getName(name, address).invoices.add(invoiceNumber, new Invoice(invoiceNumber,payment));
	}
	/**
	 * getPayment -- Obtains the invoice values from the table
	 * @param name
	 * @param noInvoice
	 * @return
	 */
	public Invoice getPayment(String name, Integer address, Integer invoiceNumber, Integer payment){
		return table.getName(name, address).invoices.getInvoice(invoiceNumber, payment);
	}
	/**
	 * removePayment -- Removes the invoice values from the table
	 * @param name
	 * @param noInvoice
	 * @return
	 */
	public Invoice removePayment(String name, Integer address, Integer invoiceNumber, Integer payment){
		return table.getName(name, address).removeInvoice(invoiceNumber, payment);
	}
	/**
	 * insertExpense -- Inserts item values to the table
	 * @param name
	 * @param noInvoice
	 * @param item
	 * @param expense
	 */
	public void insertExpense(String name, Integer address, Integer invoiceNumber, Integer payment, String item, Integer expense){
		table.getName(name, address).invoices.getInvoice(invoiceNumber, payment).expenses.add(item, new Expense(item,expense));
	}
	/**
	 * getExpense -- Obtains the item values from the table
	 * @param name
	 * @param noInvoice
	 * @param item
	 * @param expense
	 * @return
	 */
	public Expense getExpense(String name, Integer address, Integer invoiceNumber, Integer payment, String item, Integer expense){
		return table.getName(name, address).invoices.getInvoice(invoiceNumber, payment).expenses.getExpense(item, expense);
	}
	/**
	 * removeExpense -- Remove the item values from the table
	 * @param name
	 * @param noInvoice
	 * @param item
	 * @param expense
	 * @return
	 */
	public Expense removeExpense(String name, Integer address, Integer invoiceNumber, Integer payment, String item, Integer expense){
		return table.getName(name, address).removeExpense(item, expense);
	}
	/**
	 * displayExpenses -- Display all the expenses a person has made using ExpensesSingleUser
	 * @param name
	 */
	public void displayExpenses(String name){
		BSTName<Integer> invoices = this.table.table[this.table.hash(name)];
		System.out.print("Expenses of: " + name + "\nInvoice Expense"+invoices.ExpensesSingleUser());
		System.out.println("\n");
	}
	/**
	 * displaysPayments -- Display all the payments a person has made using PaymentsSingleUser
	 * @param name
	 */
	public void displayPayments(String name){
		BSTName<Integer> invoices = this.table.table[this.table.hash(name)];
		System.out.print("Payments of: " + name + "\nInvoice Payment"+invoices.PaymentsSingleUser());
		System.out.println("\n");
	}
	/**
	 * displayEarnings -- Prints the earnings after expense for a person using EarningsSingleUser
	 * @param name
	 */
	public void displayEarnings(String name){
		BSTName<Integer> invoices = this.table.table[this.table.hash(name)];
		System.out.println("Earnings of: " + name + invoices.EarningsSingleUser());
		System.out.println("\n");
	}
	/**
	 * getDifference -- Obtains the difference expense of two users
	 * @param x
	 * @param y
	 */
	public void getDifference(String x, String y){
		BSTName<Integer> invoicesX = this.table.table[this.table.hash(x)];
		BSTName<Integer> invoicesY = this.table.table[this.table.hash(y)];
		int expenseX = invoicesX.getExpenses();
		int expenseY = invoicesY.getExpenses();
		int output = Math.abs(expenseX-expenseY);
		System.out.println("Expenses of " + x +": " + expenseX + "\nExpenses of " + y +": " + expenseY + "\nDifference: " + output+"\n");
	}
	/**
	 * main -- Test the methods
	 * @param args
	 */
	public static void main(String[] args){
		Database database = new Database();
		
		database.insertAddress("Luis", 45138);
		database.insertPayment("Luis", 45138, 1, 1000);
		database.insertExpense("Luis", 45138, 1, 1000, "Food", 15);
		database.insertExpense("Luis", 45138, 1, 1000, "Food", 25);
		database.insertPayment("Luis", 45138, 2, 2000);
		database.insertExpense("Luis", 45138, 2, 2000, "Beer", 15);
		database.insertExpense("Luis", 45138, 2, 2000, "Beer", 15);
		database.displayExpenses("Luis");
		database.displayPayments("Luis");
		database.displayEarnings("Luis");
		
		database.insertAddress("Yorch", 45138);
		database.insertPayment("Yorch", 45138, 3, 600);
		database.insertExpense("Yorch", 45138, 3, 600,"Beer", 15);
		database.insertExpense("Yorch", 45138, 3, 600, "Food", 25);
		database.insertExpense("Yorch", 45138, 3, 600, "Food", 15);
		database.insertPayment("Yorch", 45138, 4, 500);
		database.insertExpense("Yorch", 45138, 4, 500, "Beer", 15);
		database.insertExpense("Yorch", 45138, 4, 500, "Beer", 15);
		database.insertExpense("Yorch", 45138, 4, 500, "Food", 15);
		database.displayExpenses("Yorch");
		database.displayPayments("Yorch");
		database.displayEarnings("Yorch");
		
		database.getDifference("Luis", "Yorch");
		
		System.out.println("Removed one invoice from Luis: "+database.removePayment("Luis", 45138, 1, 1000));
		System.out.println("Removed one invoice from Yorch: "+database.removePayment("Yorch", 45138, 3, 600));
		
	}
}