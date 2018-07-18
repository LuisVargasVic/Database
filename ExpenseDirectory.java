package Project;
/**
 * Directory of User's invoice number, item and expense
 * @author luiseduardo @author jorgepadilla
 *
 */
public class ExpenseDirectory {
	public class BSTExpense<Tree extends Comparable<Tree>> {
		public class BSTNode{
			int count;
			Tree element;
			Expense val;
			BSTNode left,right;
			int height;
			/**
			 * Contructor -- Initializes all the instance variables of the class
			 * @param element
			 * @param left
			 * @param right
			 * @param val
			 */
			private BSTNode(Tree element, BSTNode left, BSTNode right,Expense val){
				this.element 	= element;
				this.left 	    = this.right = null; 
				this.height 	= 1;
				this.val 		= val;
				this.count 		= 1;
			}
			/**
			 * toString -- Prints the key and value of the node
			 */
			public String toString(){
				return "["+this.element+"-"+this.val+"]";
			}
		}
		public BSTNode root;
		/**
		 *  Constructor -- Initializes all the instance variables of the class
		 */
		public BSTExpense(){this.root = null;}
		/**
		 * insert -- Root gets the method
		 * @param element
		 * @param val
		 */
		public void insert(Tree element, Expense val){this.root = insert(element,this.root, val);}
		/**
		 * insert -- Inserts the values
		 * @param element
		 * @param node
		 * @param val
		 * @return
		 */
		private BSTNode insert(Tree element,BSTNode node,Expense val){
			if(node == null){return new BSTNode(element,null,null,val);}
			int compareResult = element.compareTo(node.element);
			if(compareResult < 0){node.left = insert(element,node.left,val);}
			else if (compareResult > 0){node.right = insert(element,node.right,val);}
				else{node.count++;}
			return node;
		}
		/**
		 * getValue -- Obtains the value from the key
		 * @param key
		 * @return
		 */
		public Expense getvalue(Tree key){
			BSTNode node = this.root;
			int result;
			while(node != null){
				result = key.compareTo(node.element);
				if(result == 0){return node.val;}
				else if(result < 0){node = node.left;}
					else if(result > 0){node = node.right;}
			}
			return null;
		}
		/**
		 * remove -- Removes values from the key
		 * @param key
		 * @return
		 */
		public Expense remove(Tree key){
			BSTNode node = this.root;
			Expense temp;
			while(node != null){int cmp = key.compareTo(node.element);
				if(cmp == 0){break;}
				else if(cmp > 0){node = node.right;}
					else if(cmp < 0){node = node.left;}
			}
			if(node == null){return null;}
			else{
				temp = node.val;
				node.count--;
			}
			return temp;
		}
		/**
		 * selectExpenses -- Creates a string with the item and expense for all the nodes in the tree
		 * @return
		 */
		public String selectExpenses(){
			if(root != null){return this.selectExpenses(root);}
			return "";
		}
		private String selectExpenses(BSTNode node){
			if(node == null){return "";}
			String output = "";
			output = this.selectExpenses(node.left);
			for(int i = 1; i <= node.count; i++){output += "\n"+node.val.toString();}
			output += this.selectExpenses(node.right);
			return output;
		}
		/**
		 * selectGetExpensesItem -- Sum all the expenses from an item for a user
		 * @return
		 */
		public int selectGetExpensesItem(){
			if(root != null){return this.selectGetExpensesItem(root);}
			return 0;
		}
		private int selectGetExpensesItem(BSTNode node){
			if(node == null){return 0;}
			int output = 0;
			output = this.selectGetExpensesItem(node.left);
			for(int i = 1; i <= node.count; i++){output += node.val.expense;}
			output += this.selectGetExpensesItem(node.right);
			return output;
		}
	}
	private int capacity;
	private int size;
	protected BSTExpense<Integer>[] table;
	/**
	 * Constructor -- Defines the class
	 */
	protected ExpenseDirectory(){this(101);}
	/**
	 * Constructor -- Initializes all the instance variables of the class
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	private ExpenseDirectory(int capacity){
		this.capacity = capacity;
		this.size = 0;
		this.table = new BSTExpense[capacity];
	}
	/**
	 * hash -- Maps the data
	 * @param capacity
	 */
	protected int hash(String item){return (item.hashCode() & 0x7FFFFFFF) % this.capacity;}
	/**
	 * hash -- Digests the data stored in an instance of the class into a single hash
	 * @param k
	 * @param capacity
	 * @return
	 */
	private int hash(String item, int capacity){return (item.hashCode() & 0x7FFFFFFF) % capacity;}
	/**
	 * resize -- The table is resized to twice its capacity
	 */
	@SuppressWarnings("unchecked")
	private void resize(){
		BSTExpense<Integer>[] newTable = new BSTExpense[this.capacity*2+1];
		for(int i = 0; i < this.capacity; i++){
			if(this.table[i] != null){newTable[hash(this.table[i].root.val.item, this.capacity*2+1)] = this.table[i];}
		}
		this.capacity = this.capacity*2+1;
		this.table = newTable;
	}
	/**
	 * add -- Adds expense values 
	 * @param k
	 * @param item
	 * @return
	 */
	public Expense add(String item, Expense expense) {
		if(this.size >= this.capacity-1 || (this.size+0.0)/(this.capacity) >= 0.75){this.resize();}
		int h = this.hash(item);
		for(int i = h, count = 0; count < this.capacity; i = ++i%this.capacity, count++){
			if(this.table[i] == null){
				this.table[i] = new BSTExpense<Integer>();
				this.table[i].insert(expense.expense, expense);
				this.size++;
				break;
			}
			else if(this.table[i].root.val.item.equals(item)){
				this.table[i].insert(expense.expense, expense);
				break;
			}
		}
		return null;
	}
	/**
	 * remove --  Removes values from the key
	 * @param k
	 * @param number
	 * @return
	 */
	public Expense remove(String item, Integer expense) {
		int h = this.hash(item);
		Expense saved = null;
		for(int i = h; this.table[i] != null; i = ++i%this.capacity){
			if(this.table[i].root.val.item.equals(item)){
				BSTExpense<Integer> temp = this.table[i];
				saved = temp.remove(expense);
				this.table[i] = temp;
				i = -1;
				this.size--;
			}
			else if(i != -1 && this.hash(this.table[i].root.val.item)==(h)){
					this.table[-1] = this.table[i];
					this.table[i] = null;
					i = -1;
			}
		}
		return saved;
	}
	/**
	 * getSize -- Obtains size from the table
	 * @return
	 */
	public int getSize() {return this.size;}
	/**
	 * getExpense -- Obtains the expense from the item
	 * @param k
	 * @param i
	 * @return
	 */
	public Expense getExpense(String item,Integer i) {
		int h = this.hash(item);
		for(BSTExpense<Integer> n = this.table[h]; n != null; n = this.table[++h%this.capacity]){
			if(n.root.val.item.equals(item)){return n.getvalue(i);}
		}
		return null;
	}
	/**
	 * clear -- Sets the table to null
	 * @param k
	 * @param i
	 * @return
	 */
	public void clear() {for(int i = 0; i < this.capacity; i++){this.table[i] = null;}}	
}