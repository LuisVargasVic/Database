package Project;
/**
 * Node for the entries on the name's BST
 * @author luiseduardo @author jorgepadilla
 *
 */
public class Name{
	String name;
	Integer address;
	InvoiceDirectory invoices;
	ExpenseDirectory expenses;
	/**
	 * Constructor -- Initializes all the instance variables of the class
	 * @param name
	 * @param address
	 */
	public Name(String name, Integer address){
		this.name = name;
		this.address = address;
		this.invoices = new InvoiceDirectory();
		this.expenses = new ExpenseDirectory();
	}
	/**
	 * toString -- Prints the key and value of the node
	 */
	public String toString(){return this.name+" "+this.address;}
	/**
	 * insertInvoice -- Inserts invoice values
	 * @param invoice
	 */
	public void insertInvoice(Invoice invoice){invoices.add(invoice.invoiceNumber, invoice);}
	/**
	 * removeInvoice -- Removes invoice value from invoice number
	 * @param invoiceNumber
	 * @param payment
	 * @return
	 */
	public Invoice removeInvoice(Integer invoiceNumber, Integer payment){return invoices.remove(invoiceNumber, payment);}
	/**
	 * insertExpense -- Inserts expense values 
	 * @param expense
	 */
	public void insertExpense(Expense expense){expenses.add(expense.item, expense);}
	/**
	 * removeExpense -- Removes expense value from the item
	 * @param item
	 * @param expense
	 * @return
	 */
	public Expense removeExpense(String item, Integer expense){return expenses.remove(item, expense);}
}