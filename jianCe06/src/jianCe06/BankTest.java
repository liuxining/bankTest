package jianCe06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;



class WithdrawException extends Exception {
	
	double balance;
	double withdraw;
	String name;
	
	public WithdrawException(double balance,double withdraw,String name) {
		// TODO Auto-generated constructor stub
		this.balance = balance;
		this.withdraw = withdraw;
		this.name = name;
	}
	//获取异常信息
	public String getMessage() {
		return "异常信息：取款人： " + name + "   账户余额：" + balance + "    取款额： " + withdraw + "     原因：透支";
	}
}


abstract class Account {
	String name;
	int aid;
	double balance;
	String dateStr;
	//存款
	abstract public void deposit(double t);
	//取款
	abstract public int withdraw(double t);
	abstract int getAid();
	
	abstract String getName();
	//查询余额
	abstract public double getBalance();
	
	abstract public double findOverDraw();
	
	abstract public void writeData(PrintWriter pw);
	
	abstract public void readData(Scanner scan) throws IOException;
	
}

class CashAccount extends Account {
	String type;
	public CashAccount(){}
	
	public CashAccount(String name,int aid,double balance,String type) {
		this.name = name;
		this.aid = aid;
		this.balance = balance;
		this.type = type;
		setDate();
	}
	
	public void setDate() {
		Date date = new Date();
		dateStr = (date.getYear() + 1900)+ "." + (date.getMonth() + 1) + "." + (date.getDay() - 3) + "." + date.getHours() + "." + date.getMinutes() + "." + date.getSeconds();
	}
	
	//存款
	public void deposit(double t) {
		//存款
		balance += t;
	}

	//取款
	public int withdraw(double t) {
		// TODO Auto-generated method stub
		//取款
		try {
			//取款金额大于余额，则抛出异常
			if(balance < t)
				throw new WithdrawException(balance, t, name);
			//无异常发生则进行正常取款
			balance -= t;
			
		}catch(WithdrawException e){
			System.out.println(e.getMessage());
		}finally{
			return 0;
		}
	}

	//获取余额
	public double getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
	//设置账户类型
	public void setType(String type) {
		this.type = type;
	}
	
	public double findOverDraw(){
		return 0;
	}
	
	int getAid() {
		return aid;
	}
	
	String getName() {
		return name;
	}
	
	
	public void writeData(PrintWriter pw) {
		pw.println("0 " + aid + " " + name + " " + "现金账户" + " " + type  + " " + balance + " " + dateStr);
	}
	
	public void readData(Scanner scan) throws IOException {
		aid = Integer.parseInt(scan.next());
		name = scan.next();
		scan.next();
		type = scan.next();
		balance = Double.parseDouble(scan.next());
		dateStr = scan.next();
	}

}


class CreditAccount extends Account {
	String lineOfCredit;
	
	public CreditAccount(){}
	public CreditAccount(String name,int aid,double balance,String lineOfCredit) {
		this.name = name;
		this.aid = aid;
		this.balance = balance;
		this.lineOfCredit = lineOfCredit;
		setDate();
	}
	
	public void setDate() {
		Date date = new Date();
		dateStr = (date.getYear() + 1900)+ "." + (date.getMonth() + 1) + "." + (date.getDay() - 3) + "." + date.getHours() + "." + date.getMinutes() + "." + date.getSeconds();
	}
	
	
	int getAid() {
		return aid;
	}
	
	String getName() {
		return name;
	}
	
	
	//存款
	public void deposit(double t) {
		//存款
		balance += t;
	}

	//取款
	public int withdraw(double t) {
		// TODO Auto-generated method stub
		//取款
		double touZhi = 0;
		if(this.lineOfCredit.equals("A"))
			touZhi = 10000;
		else if(this.lineOfCredit.equals("B"))
			touZhi = 5000;
		else if(this.lineOfCredit.equals("C"))
			touZhi = 2000;
		else
			touZhi = 1000;
		try {
			//取款金额大于余额加上可透支金额，则抛出异常
			if(balance + touZhi < t)
				throw new WithdrawException(balance + touZhi, t, name);
			//无异常发生则进行正常取款
			balance -= t;
		}catch(WithdrawException e){
			System.out.println(e.getMessage());
		}finally {
			if(balance < 0)
				return 1;
			return 0;
		}
		
	}

	//获取余额
	public double getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
	//获取透支情况
	public double findOverDraw() {
		double touZhi = 0;
		if(this.lineOfCredit.equals("A"))
			touZhi = 10000;
		else if(this.lineOfCredit.equals("B"))
			touZhi = 5000;
		else if(this.lineOfCredit.equals("C"))
			touZhi = 2000;
		else
			touZhi = 1000;
		return (balance > 0) ? touZhi : (touZhi + balance); 
	}
	
	
	public void writeData(PrintWriter pw) {
		pw.println("1 " + aid + " " + name + " " + "信用卡账户" + " " + lineOfCredit + " " + balance + " " + dateStr);
	}
	
	public void readData(Scanner scan) throws IOException {
		aid = Integer.parseInt(scan.next());
		name = scan.next();
		scan.next();
		lineOfCredit = scan.next();
		balance = Double.parseDouble(scan.next());
		dateStr = scan.next();
	}
	
}

class Oper {
	int aid;
	String name;
	String type;
	double jine;
	String dateStr;
	
	public Oper(){}
	public Oper(int a,String n,String type,double jine) {
		this.aid = a;
		name = n;
		this.type = type;
		this.jine = jine;
		setDate();
	}
	
	public void setDate() {
		Date date = new Date();
		dateStr = (date.getYear() + 1900)+ "." + (date.getMonth() + 1) + "." + (date.getDay() - 3) + "." + date.getHours() + "." + date.getMinutes() + "." + date.getSeconds();
	}
	
	public String getDate() {
		return dateStr;
	}
	public void writeData(PrintWriter pw) {
		pw.println(aid + "|" + name + "|" + dateStr + "|" + type + "|" + jine);
	}
	
	public void readData(BufferedReader br) throws IOException {
		String data = br.readLine();
		StringTokenizer t = new StringTokenizer(data,"|");
		aid = Integer.parseInt(t.nextToken());
		name = t.nextToken();
		dateStr = t.nextToken();
		type = t.nextToken();
		jine = Double.parseDouble(t.nextToken());
	}
	
	public String toString() {
		return "账号：" + aid + "  姓名：" + name + "  日期： " + dateStr + "   类型：" + type + "  金额： " + jine;
	}
	
}

public class BankTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		char ch = 'y';
		int s;
		while(ch == 'y') {
			System.out.println("\t\t1、创建账户");
			System.out.println("\t\t2、存款");
			System.out.println("\t\t3、取款");
			System.out.println("\t\t4、查询");
			System.out.println("\t\t5、统计");
			System.out.println("\t\t0、退出");
			s = scan.nextInt();
			switch(s) {
			case 1:{
					addAccount(scan);break;
				}
			case 2:{
				cunKuan(scan);break;
			}
			case 3:{
				quKuan(scan);break;
			}
			case 4:{
				chaXun(scan);break;
			}
			case 5:{
				tongJi(scan);break;
			}
			case 0:{
				ch = 'n';break;
			}
			default:{
				System.out.println("输入错误！");break;
			}
		}
		}
	
		
		
		
	}
	

	public static void addAccount(Scanner scan) throws IOException {
		
		System.out.println("1、现金账户\t2、信用卡账户");
		int t = scan.nextInt();
		Account[] arr = readAccountData();
		Account acc;
		if(t == 1){
			System.out.println("输入账户号码、姓名、卡类型（工资卡、借记卡、理财卡）");
			int aid = scan.nextInt();
			String name = scan.next();
			String cardType = scan.next();// CashAccount(String name,int aid,double balance,String type)
			for(int i = 0;i < arr.length;i++)
				if(arr[i].getAid() == aid){
					System.out.println("账号为 " + aid + "的账户已存在！");
					return;
				}
			acc = new CashAccount(name,aid,0,cardType);
		}else{
			System.out.println("输入账户号码、姓名、信用卡透支类型（A、B、C、D）");
			int aid = scan.nextInt();
			String name = scan.next();
			String lineOfCredit = scan.next();//itAccount(String name,int aid,double balance,String lineOfCredit)
			for(int i = 0;i < arr.length;i++)
				if(arr[i].getAid() == aid){
					System.out.println("账号为 " + aid + "的账户已存在！");
					return;
				}
			acc = new CreditAccount(name,aid,0,lineOfCredit);
		}
		
		
		Account[] arrt = new Account[arr.length + 1];
		for(int i = 0;i < arr.length;i++){
			arrt[i] = arr[i];
		}
		
		arrt[arr.length] = acc;
		writeAccountData(arrt);
		System.out.println("添加账户成功");
	}
	
	
	public static void cunKuan(Scanner scan) throws IOException {
		Account[] arr = readAccountData();
		System.out.println("请输入存款账号和存款金额");
		int aid = scan.nextInt();
		double jine = scan.nextDouble();
		Oper op;
		boolean flag = false;
		for(int i = 0;i < arr.length;i++){
			if(arr[i].getAid() == aid){
				flag = true;
				arr[i].deposit(jine);
				writeAccountData(arr);
				op = new Oper(arr[i].getAid(),arr[i].getName(),"存款",jine);
				Oper[] oparr = readBankData();
				Oper[] oparr2 = new Oper[oparr.length + 1];
				for(int j = 0;j < oparr.length;j++)
					oparr2[j] = oparr[j];
				oparr2[oparr.length] = op;
				writeBankData(oparr2);
			}
		}
		if(flag)
			System.out.println("存款成功！");
		else
			System.out.println("账户不存在！");
	}
	
	
	public static void quKuan(Scanner scan) throws IOException {
		Account[] arr = readAccountData();
		System.out.println("请输入取款账号和取款金额");
		int aid = scan.nextInt();
		double jine = scan.nextDouble();
		Oper op;
		boolean flag = false;
		for(int i = 0;i < arr.length;i++){
			if(arr[i].getAid() == aid){
				flag = true;
				if(arr[i].withdraw(jine) == 1)
					op = new Oper(arr[i].getAid(),arr[i].getName(),"透支取款",jine);
				else
					op = new Oper(arr[i].getAid(),arr[i].getName(),"取款",jine);

				writeAccountData(arr);
				Oper[] oparr = readBankData();
				Oper[] oparr2 = new Oper[oparr.length + 1];
				for(int j = 0;j < oparr.length;j++)
					oparr2[j] = oparr[j];
				oparr2[oparr.length] = op;
				writeBankData(oparr2);
			}
		}
		if(flag)
			System.out.println("存款成功！");
		else
			System.out.println("账户不存在！");
	}
	
	
	public static void chaXun(Scanner scan) throws IOException {
		System.out.println("1、按账号查询  2、按姓名查询");
		int a = scan.nextInt();
		Oper[] op = readBankData();
		if(a == 1){
			System.out.println("请输入账号：");
			int id = scan.nextInt();
			for(int i = 0;i < op.length;i++){
				if(op[i].aid == id)
					System.out.println(op[i]);
			}
		}
		else
		{
			System.out.println("请输入姓名：");
			String name = scan.next();
			for(int i = 0;i < op.length;i++){
				if(op[i].name.equals(name))
					System.out.println(op[i]);
			}
		}
	}

	

	public static void tongJi(Scanner scan) throws IOException {
		System.out.println("请输入账号、起始日期，终止日期、流水类型（日期格式例如2016.12.2.18.01.50）");
		Oper[] op = readBankData();
		
		int aid = scan.nextInt();
		String begin = scan.next();
		String end = scan.next();
		String type = scan.next();

		
		
		for(int i = 0;i < op.length;i++){
			if(op[i].aid == aid) {
				if(compare(begin,op[i].getDate()) != 1 && compare(op[i].getDate(),end) != 1){
					if(op[i].type.equals(type))
						System.out.println(op[i]);
				}
			}
		}
	}
	
	//判断d1与d2的时间先后   -1代表d1在d2之前，1代表d1在d2后，0代表相等
	public static int compare(String d1,String d2) {
		String[] s1 = d1.split("[.]");
		String[] s2 = d2.split("[.]");
		int[] it1 = new int[s1.length];
		int[] it2 = new int[s2.length];
		for(int i = 0;i < it1.length;i++){
			it1[i] = Integer.parseInt(s1[i]);
			it2[i] = Integer.parseInt(s2[i]);
		}
		for(int i = 0;i < it1.length;i++)
			if(it1[i] != it2[i]){
				if(it1[i] < it2[i])
					return -1;
				else
					return 1;
			}
		return 0;
	}

	public static void writeAccountData(Account[] arr) throws IOException {
		
		PrintWriter pw;
		pw = new PrintWriter(new FileWriter("ClientInfoTable.txt"));
	
		pw.println(arr.length);
		
		for(int i = 0;i < arr.length;i++){
			arr[i].writeData(pw);
		}
		pw.close();
	}
	
	public static Account[] readAccountData() throws IOException{
		Account[] arr;
		File f = new File("ClientInfoTable.txt");
		if(!f.exists()){
			arr = new Account[0];
			writeAccountData(arr);
			return arr;
		}
		Scanner scan = new Scanner(new BufferedReader((new FileReader(f))));
		
		String nstr = scan.next();
		int n = Integer.parseInt(nstr);
		
		arr = new Account[n];
		
		for(int i = 0;i < n;i++){
			String accountType = scan.next();
			if(accountType.equals("0")){
				arr[i] = new CashAccount();
			}
			else{
				arr[i] = new CreditAccount();
			}
			arr[i].readData(scan);
		}
		scan.close();
		return arr;
	}
	
	public static void writeBankData(Oper[] arr) throws IOException {
		
		PrintWriter pw;
		pw = new PrintWriter(new FileWriter("BankInfoTable.txt"));
	
		pw.println(arr.length);
		
		for(int i = 0;i < arr.length;i++){
			arr[i].writeData(pw);
		}
		pw.close();
	}
//	
//
//	
	public static Oper[] readBankData() throws IOException {
		Oper[] arr;
		File f = new File("BankInfoTable.txt");
		
		if(!f.exists()) {
			arr = new Oper[0];
			writeBankData(arr);
			return arr;
		}
		
		BufferedReader br;
		
		
		br = new BufferedReader(new FileReader("BankInfoTable.txt"));
		
		String nstr = br.readLine();
		int n = Integer.parseInt(nstr);
		arr = new Oper[n];
		
		for(int i = 0;i < n;i++){
			arr[i] = new Oper();
			arr[i].readData(br);
		}
		br.close();
		return arr;
	}
	
	
}
