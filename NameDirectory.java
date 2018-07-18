package Project;
import Project.InvoiceDirectory.BSTInvoice;
/**
 * Directory of User's name and address with it's own BST
 * @author luiseduardo @author jorgepadilla
 *
 */
public class NameDirectory {
	public class BSTName<Tree extends Comparable<Tree>> {
		public class BSTNode{
			Tree element;
			Name val;
			BSTNode left;
			BSTNode right;   
			int height;
			/**
			 * Constructor-- Initializes all the instance variables of the class
			 * @param element
			 * @param lt
			 * @param rt
			 * @param val
			 */
			BSTNode( Tree element, BSTNode lt, BSTNode rt, Name val ){
				this.element  = element;
				this.val      = val; 
				this.left     = null;
				this.right    = null;
				this.height   = 1;
			}
			/**
			 * toString -- Prints the key and value of the node
			 */
			public String toString (){
				return ""+"["+this.element+"-"+this.val+"]";
			}
		}
		BSTNode root;
		/**
		 * Constructor -- Initializes all the instance variables of the class
		 */
		public BSTName(){this.root = null;}
		/**
		 * insert -- Root gets the method
		 * @param key
		 * @return
		 */
		public void insert(Tree element, Name val){this.root = insert(element,this.root, val);}
		/**
		 * insert -- Inserts the values
		 * @param element
		 * @param val
		 */
		private BSTNode insert(Tree element,BSTNode node,Name val){
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
		public Name getvalue(Tree key){
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
		 * remove --  Removes values from the key
		 * @param key
		 * @return
		 */
		public Name remove(Tree key){
			BSTNode node = this.root;
			Name temp;
			while(node != null){int cmp = key.compareTo(node.element);
				if(cmp == 0){break;}
				else if(cmp > 0){node = node.right;}
					else if(cmp < 0){node = node.left;}
			}
			if(node == null){return null;}
			else{temp = node.val;}
			return temp;
		}
		/**
		 * ExpensesSingleUser -- Uses selectExpensesInvoice to order the expenses by user
		 * @return
		 */
		public String ExpensesSingleUser(){
			if(root != null){return this.ExpensesSingleUser(root);}
			return "";
		}
		protected String ExpensesSingleUser(BSTNode node){
			if(node == null){return "";}
			String output = "";
			output = this.ExpensesSingleUser(node.left);
			BSTInvoice<Integer> invoice;
			int i = 0, j  = 0;
			for(invoice = node.val.invoices.table[i] ; j < node.val.invoices.getSize() ; invoice = node.val.invoices.table[++i]){
				if(invoice != null){output += invoice.selectExpensesInvoice(); j++;}
			}
			output += this.ExpensesSingleUser(node.right);
			return output;
		}
		/**
		 * PaymentsSingleUser -- Uses selectPayments to order the payments by user
		 * @return
		 */
		public String PaymentsSingleUser(){
			if(root != null){return this.PaymentsSingleUser(root);}
			return "";
		}
		private String PaymentsSingleUser(BSTNode node){
			if(node == null){return "";}
			String output = "";
			output = this.PaymentsSingleUser(node.left);
			BSTInvoice<Integer> invoice;
			int i = 0, j  = 0;
			for(invoice = node.val.invoices.table[i] ; j < node.val.invoices.getSize() ; invoice = node.val.invoices.table[++i]){
				if(invoice != null){output += invoice.selectPayments(); j++;}
			}
			output += this.PaymentsSingleUser(node.right);
			return output;
		}
		/**
		 * EarningsSingleUser -- Earnings result after expense for a person
		 * @return
		 */
		public String EarningsSingleUser(){
			if(root != null){
				int totalExpenses, totalPayments;
				totalExpenses = this.getExpenses(root);
				totalPayments = this.getPayments(root);
				return ("Expenses: "+ totalExpenses+ "\nPayments: "+ totalPayments+ "\nEarnings: "+ (totalPayments-totalExpenses));
			}
			return "";
		}
		/**
		 * getTotalPayments -- Obtains total payments using selectGetPayments
		 * @return
		 */
		public int getPayments(){
			if(root != null){return this.getPayments(root);}
			return 0;
		}
		private int getPayments(BSTNode node){
			if(node == null){return 0;}
			int totalPayments = 0;
			totalPayments = this.getPayments(node.left);
			BSTInvoice<Integer> invoice;
			int i = 0, j  = 0;
			for(invoice = node.val.invoices.table[i] ; j < node.val.invoices.getSize() ; invoice = node.val.invoices.table[++i]){
				if(invoice != null){totalPayments += invoice.selectGetPayments(); j++;}
			}
			totalPayments += this.getPayments(node.right);
			return totalPayments;
		}
		/**
		 * getTotalExpenses -- Obtains total expenses using selectGetExpenses 
		 * @return
		 */
		public int getExpenses(){
			if(root != null){return this.getExpenses(root);}
			return 0;
		}
		private int getExpenses(BSTNode node){
			if(node == null){return 0;}
			int totalExpenses = this.getExpenses(node.left);
			BSTInvoice<Integer> invoice;
			int i = 0, j  = 0;
			for(invoice = node.val.invoices.table[i] ; j < node.val.invoices.getSize() ; invoice = node.val.invoices.table[++i]){
				if(invoice != null){totalExpenses += invoice.selectGetExpenses(); j++;}
			}
			totalExpenses += this.getExpenses(node.right);
			return totalExpenses;
		}
	}
	private int capacity;
	private int size;
	protected BSTName<Integer>[] table;
	/**
	 * Constructor -- Defines the class
	 */
	protected NameDirectory(){this(101);}
	/**
	 * Constructor -- Initializes all the instance variables of the class
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	private NameDirectory(int capacity){
		this.capacity = capacity;
		this.size = 0;
		this.table = new BSTName[capacity];
	}
	/**
	 * hash -- Maps the data
	 * @param name
	 * @return
	 */
	protected int hash(String name){return (name.hashCode() & 0x7FFFFFFF) % this.capacity;}
	/**
	 * hash -- Digests the data stored in an instance of the class into a single hash
	 * @param name
	 * @param capacity
	 * @return
	 */
	private int hash(String name, int capacity){return (name.hashCode() & 0x7FFFFFFF) % capacity;}
	/**
	 * resize -- The table is resize twice its capacity
	 */
	private void resize(){
		@SuppressWarnings("unchecked")
		BSTName<Integer>[] newTable = new BSTName[this.capacity*2+1];
		for(int i = 0; i < this.capacity; i++){
			if(this.table[i] != null){newTable[hash(this.table[i].root.val.name, this.capacity*2+1)] = this.table[i];}
		}
		this.capacity = this.capacity*2+1;
		this.table = newTable;
	}
	/**
	 * add -- Adds name values 
	 * @param name
	 * @param item
	 * @return
	 */
	public Name add(String name, Name address) {
		if(this.size >= this.capacity-1 || (this.size+0.0)/(this.capacity) >= 0.75){this.resize();}
		int h = this.hash(name);
		for(int i = h, count = 0; count < this.capacity; i = ++i%this.capacity, count++){
			if(this.table[i] == null){
				this.table[i] = new BSTName<Integer>();
				this.table[i].insert(address.address, address);
				this.size++;
				break;
			}
			else if(this.table[i].root.val.name.equals(name)){
				this.table[i].insert(address.address, address);
				break;
			}
		}
		return null;
	}
	/**
	 * remove --  Removes values from the key
	 * @param name
	 * @param address
	 * @return
	 */
	public Name remove(String name, Integer address) {
		int h = this.hash(name);
		Name saved = null;
		for(int i = h; this.table[i] != null; i = ++i%this.capacity){
			if(this.table[i].root.val.name.equals(name)){
				BSTName<Integer> temp = this.table[i];
				saved = temp.remove(address);
				this.table[i] = temp;
				i = -1;
				this.size--;
			}
			else if(i != -1 && this.hash(this.table[i].root.val.name) == h){
					this.table[-1] = this.table[i];
					this.table[i] = null;
					i = -1;
			}
		}
		return saved;
	}
	/**
	 * getSize --  Obtains size from the table
	 * @return
	 */
	public int getSize() {return this.size;}
	/**
	 * getAddress -- Obtains the address from the name
	 * @param name
	 * @param i
	 * @return
	 */
	public Name getName(String name, Integer i) {
		int h = this.hash(name);
		for(BSTName<Integer> n = this.table[h]; n != null; n = this.table[++h%this.capacity]){
			if(n.root.val.name.equals(name)){return n.getvalue(i);}
		}
		return null;
	}
	/**
	 * clear -- Sets the table to null
	 */
	public void clear() {for(int i = 0; i < this.capacity; i++){this.table[i] = null;}}
}