package Project;
/**
 * Node for the entries on the invoice's BST
 * @author luiseduardo @author jorgepadilla
 *
 */
public class Invoice{
	Integer invoiceNumber;
	Integer payment;
	ExpenseDirectory expenses;
	/**
	 * insertExpense -- Inserts expense values 
	 * @param expense
	 */
	public Invoice(int invoiceNumber, int payment){
		this.invoiceNumber = invoiceNumber;
		this.payment = payment;
		this.expenses = new ExpenseDirectory();
	}
	/**
	 *  toString -- Prints the key and value of the node
	 */
	public String toString(){return this.invoiceNumber+"/"+this.payment;}
}