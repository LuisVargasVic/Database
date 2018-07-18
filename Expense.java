package Project;
/**
 * Node for the entries on the expense's BST
 * @author luiseduardo @author jorgepadilla
 *
 */
public class Expense {
	String item;
	Integer expense;
	/**
	 * Constructor -- Initializes all the instance variables of the class
	 * @param item
	 * @param expense
	 */
	public Expense(String item, int expense){
		this.item = item;
		this.expense = expense;
	}
	/**
	 *  toString -- Prints the key and value of the node
	 */
	public String toString(){return this.item+"/"+this.expense;}
}