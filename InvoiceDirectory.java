package Project;
import Project.ExpenseDirectory.BSTExpense;
/**
 * Directory of User's name, invoice number and payment
 * @author luiseduardo @author jorgepadilla
 *
 */
public class InvoiceDirectory {
	public class BSTInvoice<Tree extends Comparable<Tree>> {
		public class BSTNode{
			Tree element;
			Invoice val;
			BSTNode left;
			BSTNode right;   
			int height;
			/**
			 * Constructor -- Initializes all the instance variables of the class
			 * @param element
			 * @param lt
			 * @param rt
			 * @param val
			 */
			BSTNode( Tree element, BSTNode lt, BSTNode rt, Invoice val ){
				this.element  = element;
				this.val      = val; 
				this.left     = null;
				this.right    = null;
				this.height   = 1;
			}
			/**
			 *  toString -- Prints the key and value of the node
			 */
			public String toString (){
				return ""+"["+this.element+"-"+this.val+"]";
			}
		}

		public BSTNode root;
		/**
		 * Constructor -- Sets the root to null
		 */
		public BSTInvoice(){this.root = null;}
		/**
		 * insert -- Root gets the method
		 * @param element
		 * @param val
		 */
		public void insert(Tree element, Invoice val){this.root = insert(element, this.root, val);}
		/**
		 * insert -- Inserts the values
		 * @param element
		 * @param node
		 * @param val
		 * @return
		 */
		private BSTNode insert(Tree element,BSTNode node,Invoice val){
			if(node == null){return new BSTNode(element,null,null,val);}
			int compareResult = element.compareTo(node.element);
			if(compareResult < 0){node.left = insert(element,node.left,val);}
			else if (compareResult > 0){node.right = insert(element,node.right,val);}
			return node;
		}
		/**
		 * getValue -- Obtains the value from the key
		 * @param key
		 * @return
		 */
		public Invoice getvalue(Tree key){
			BSTNode node = this.root;
			int result;
			while(node != null){result = key.compareTo(node.element);
				if(result == 0){return node.val;}
				else if(result < 0){ node = node.left;}
					else if(result > 0){ node = node.right;}
			}
			return null;
		}
		/**
		 * remove -- Removes values from the key
		 * @param key
		 * @return
		 */
		public Invoice remove(Tree key){
			BSTNode node = this.root;
			Invoice temp;
			while(node != null){int cmp = key.compareTo(node.element);
				if(cmp == 0){break;}
				else if(cmp > 0){node = node.right;}
					else if(cmp < 0){node = node.left;}
			}
			if(node == null){return null;}
			else{
				temp = node.val;
			}
			return temp;
			
		}
		/**
		 * selectExpensesInvoice -- Uses selectExpenses to order the expenses by invoice
		 * @return
		 */
		public String selectExpensesInvoice(){
			if(root != null){return this.selectExpensesInvoice(root);}
			return "";
		}
		protected String selectExpensesInvoice(BSTNode node){
			if(node == null){return "";}
			String output = "";
			output = this.selectExpensesInvoice(node.left);
			BSTExpense<Integer> expense;
			int i = 0, j  = 0;
			for(expense = node.val.expenses.table[i] ; j < node.val.expenses.getSize() ; expense = node.val.expenses.table[++i]){
				if(expense != null){output += expense.selectExpenses(); j++;}
			}
			output += this.selectExpensesInvoice(node.right);
			return output;
		}
		/**
		 * selectPayments -- Creates a string with the invoice number and payment for all the nodes in the tree
		 * @return
		 */
		public String selectPayments(){
			if(root != null){return this.selectPayments(root);}
			return "";
		}
		private String selectPayments(BSTNode node){
			if(node == null){return "";}
			String output = "";
			output = this.selectPayments(node.left);
			output += "\n"+ node.val.toString();
			output += this.selectPayments(node.right);
			return output;
		}
		/**
		 * selectGetPayments -- Sum all the payments from an invoice for a user
		 * @return
		 */
		public int selectGetPayments(){
			if(root != null){return this.selectGetPayments(root);}
			return 0;
		}
		private int selectGetPayments(BSTNode node){
			if(node == null){return 0;}
			int totalPayments = 0;
			totalPayments = this.selectGetPayments(node.left);
			totalPayments += node.val.payment;
			totalPayments += this.selectGetPayments(node.right);
			return totalPayments;
		}
		/**
		 * selectGetExpenses --  Obtains total expenses Using selectGetExpensesItems 
		 * @return
		 */
		public int selectGetExpenses(){
			if(root != null){return this.selectGetExpenses(root);}
			return 0;
		}
		private int selectGetExpenses(BSTNode node){
			if(node == null){return 0;}
			int totalExpenses = this.selectGetExpenses(node.left);
			BSTExpense<Integer> exp;
			int i = 0, j  = 0;
			for(exp = node.val.expenses.table[i] ; j < node.val.expenses.getSize() ; exp = node.val.expenses.table[++i]){
				if(exp != null){totalExpenses += exp.selectGetExpensesItem(); j++;}
			}
			totalExpenses += this.selectGetExpenses(node.right);
			return totalExpenses;
		}
	}
	private int capacity;
	private int size;
	protected BSTInvoice<Integer>[] table;
	/**
	 * Constructor -- Defines the class
	 */
	protected InvoiceDirectory(){this(101);}
	/**
	 * Constructor -- Initializes all the instance variables of the class
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	private InvoiceDirectory(int capacity){
		this.capacity = capacity;
		this.size = 0;
		this.table = new BSTInvoice[capacity];
	}
	/**
	 * hash -- Maps the data
	 * @param name
	 * @return
	 */
	protected int hash(Integer invoiceNumber){return (invoiceNumber.hashCode() & 0x7FFFFFFF) % this.capacity;}
	/**
	 * hash -- Digests the data stored in an instance of the class into a single hash
	 * @param name
	 * @param capacity
	 * @return
	 */
	private int hash(Integer invoiceNumber, int capacity){return (invoiceNumber.hashCode() & 0x7FFFFFFF) % capacity;}
	/**
	 * resize -- The table is resize twice its capacity
	 */
	@SuppressWarnings("unchecked")
	private void resize(){
		BSTInvoice<Integer>[] newTable = new BSTInvoice[this.capacity*2+1];
		for(int i = 0; i < this.capacity; i++){
			if(this.table[i] != null){newTable[hash(this.table[i].root.val.invoiceNumber, this.capacity*2+1)] = this.table[i];}
		}
		this.capacity = this.capacity*2+1;
		this.table = newTable;
	}
	/**
	 * add -- Adds invoice values 
	 * @param name
	 * @param item
	 * @return
	 */
	public Invoice add(Integer invoiceNumber, Invoice payment) {
		if(this.size >= this.capacity-1 || (this.size+0.0)/(this.capacity) >= 0.75){this.resize();}
		int h = this.hash(invoiceNumber);
		for(int i = h, count = 0; count < this.capacity; i = ++i%this.capacity, count++){
			if(this.table[i] == null){
				this.table[i] = new BSTInvoice<Integer>();
				this.table[i].insert(payment.payment, payment);
				this.size++;
				break;
			}
			else if(this.table[i].root.val.invoiceNumber.equals(invoiceNumber)){
				this.table[i].insert(payment.payment, payment);
				break;
			}
		}
		return null;
	}
	/**
	 * remove --  Removes invoice values from the key
	 * @param name
	 * @param number
	 * @return
	 */
	public Invoice remove(Integer invoiceNumber,Integer payment) {
		int h = this.hash(invoiceNumber);
		Invoice saved = null;
		for(int i = h; this.table[i] != null; i = ++i%this.capacity){
			if(this.table[i].root.val.invoiceNumber.equals(invoiceNumber)){
				BSTInvoice<Integer> temp = this.table[i];
				saved = temp.remove(payment);
				this.table[i] = temp;
				i = -1;
				this.size--;
			}
			else if(i != -1 && this.hash(this.table[i].root.val.invoiceNumber) == h){
					this.table[-1] = this.table[i];
					this.table[i] = null;
					i = -1;
			}
		}
		return saved;
	}
	/**
	 * getSize -- Obtains size from the table
	 */
	public int getSize() {return this.size;}
	/**
	 * getInvoice -- Obtains values from the key 
	 * @param name
	 * @param i
	 * @return
	 */
	public Invoice getInvoice(Integer invoiceNumber,Integer i) {
		int h = this.hash(invoiceNumber);
		for(BSTInvoice<Integer> n = this.table[h]; n != null; n = this.table[++h%this.capacity]){
			if(n.root.val.invoiceNumber.equals(invoiceNumber)){return n.getvalue(i);}
		}
		return null;
	}
	/**
	 * clear -- Sets the table to null
	 */
	public void clear() {for(int i = 0; i < this.capacity; i++){this.table[i] = null;}}
}