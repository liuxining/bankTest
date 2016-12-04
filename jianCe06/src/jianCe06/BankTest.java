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
	//��ȡ�쳣��Ϣ
	public String getMessage() {
		return "�쳣��Ϣ��ȡ���ˣ� " + name + "   �˻���" + balance + "    ȡ�� " + withdraw + "     ԭ��͸֧";
	}
}


abstract class Account {
	String name;
	int aid;
	double balance;
	String dateStr;
	//���
	abstract public void deposit(double t);
	//ȡ��
	abstract public int withdraw(double t);
	abstract int getAid();
	
	abstract String getName();
	//��ѯ���
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
	
	//���
	public void deposit(double t) {
		//���
		balance += t;
	}

	//ȡ��
	public int withdraw(double t) {
		// TODO Auto-generated method stub
		//ȡ��
		try {
			//ȡ������������׳��쳣
			if(balance < t)
				throw new WithdrawException(balance, t, name);
			//���쳣�������������ȡ��
			balance -= t;
			
		}catch(WithdrawException e){
			System.out.println(e.getMessage());
		}finally{
			return 0;
		}
	}

	//��ȡ���
	public double getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
	//�����˻�����
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
		pw.println("0 " + aid + " " + name + " " + "�ֽ��˻�" + " " + type  + " " + balance + " " + dateStr);
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
	
	
	//���
	public void deposit(double t) {
		//���
		balance += t;
	}

	//ȡ��
	public int withdraw(double t) {
		// TODO Auto-generated method stub
		//ȡ��
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
			//ȡ������������Ͽ�͸֧�����׳��쳣
			if(balance + touZhi < t)
				throw new WithdrawException(balance + touZhi, t, name);
			//���쳣�������������ȡ��
			balance -= t;
		}catch(WithdrawException e){
			System.out.println(e.getMessage());
		}finally {
			if(balance < 0)
				return 1;
			return 0;
		}
		
	}

	//��ȡ���
	public double getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
	//��ȡ͸֧���
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
		pw.println("1 " + aid + " " + name + " " + "���ÿ��˻�" + " " + lineOfCredit + " " + balance + " " + dateStr);
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
		return "�˺ţ�" + aid + "  ������" + name + "  ���ڣ� " + dateStr + "   ���ͣ�" + type + "  �� " + jine;
	}
	
}

public class BankTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		char ch = 'y';
		int s;
		while(ch == 'y') {
			System.out.println("\t\t1�������˻�");
			System.out.println("\t\t2�����");
			System.out.println("\t\t3��ȡ��");
			System.out.println("\t\t4����ѯ");
			System.out.println("\t\t5��ͳ��");
			System.out.println("\t\t0���˳�");
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
				System.out.println("�������");break;
			}
		}
		}
	
		
		
		
	}
	

	public static void addAccount(Scanner scan) throws IOException {
		
		System.out.println("1���ֽ��˻�\t2�����ÿ��˻�");
		int t = scan.nextInt();
		Account[] arr = readAccountData();
		Account acc;
		if(t == 1){
			System.out.println("�����˻����롢�����������ͣ����ʿ�����ǿ�����ƿ���");
			int aid = scan.nextInt();
			String name = scan.next();
			String cardType = scan.next();// CashAccount(String name,int aid,double balance,String type)
			for(int i = 0;i < arr.length;i++)
				if(arr[i].getAid() == aid){
					System.out.println("�˺�Ϊ " + aid + "���˻��Ѵ��ڣ�");
					return;
				}
			acc = new CashAccount(name,aid,0,cardType);
		}else{
			System.out.println("�����˻����롢���������ÿ�͸֧���ͣ�A��B��C��D��");
			int aid = scan.nextInt();
			String name = scan.next();
			String lineOfCredit = scan.next();//itAccount(String name,int aid,double balance,String lineOfCredit)
			for(int i = 0;i < arr.length;i++)
				if(arr[i].getAid() == aid){
					System.out.println("�˺�Ϊ " + aid + "���˻��Ѵ��ڣ�");
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
		System.out.println("����˻��ɹ�");
	}
	
	
	public static void cunKuan(Scanner scan) throws IOException {
		Account[] arr = readAccountData();
		System.out.println("���������˺źʹ����");
		int aid = scan.nextInt();
		double jine = scan.nextDouble();
		Oper op;
		boolean flag = false;
		for(int i = 0;i < arr.length;i++){
			if(arr[i].getAid() == aid){
				flag = true;
				arr[i].deposit(jine);
				writeAccountData(arr);
				op = new Oper(arr[i].getAid(),arr[i].getName(),"���",jine);
				Oper[] oparr = readBankData();
				Oper[] oparr2 = new Oper[oparr.length + 1];
				for(int j = 0;j < oparr.length;j++)
					oparr2[j] = oparr[j];
				oparr2[oparr.length] = op;
				writeBankData(oparr2);
			}
		}
		if(flag)
			System.out.println("���ɹ���");
		else
			System.out.println("�˻������ڣ�");
	}
	
	
	public static void quKuan(Scanner scan) throws IOException {
		Account[] arr = readAccountData();
		System.out.println("������ȡ���˺ź�ȡ����");
		int aid = scan.nextInt();
		double jine = scan.nextDouble();
		Oper op;
		boolean flag = false;
		for(int i = 0;i < arr.length;i++){
			if(arr[i].getAid() == aid){
				flag = true;
				if(arr[i].withdraw(jine) == 1)
					op = new Oper(arr[i].getAid(),arr[i].getName(),"͸֧ȡ��",jine);
				else
					op = new Oper(arr[i].getAid(),arr[i].getName(),"ȡ��",jine);

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
			System.out.println("���ɹ���");
		else
			System.out.println("�˻������ڣ�");
	}
	
	
	public static void chaXun(Scanner scan) throws IOException {
		System.out.println("1�����˺Ų�ѯ  2����������ѯ");
		int a = scan.nextInt();
		Oper[] op = readBankData();
		if(a == 1){
			System.out.println("�������˺ţ�");
			int id = scan.nextInt();
			for(int i = 0;i < op.length;i++){
				if(op[i].aid == id)
					System.out.println(op[i]);
			}
		}
		else
		{
			System.out.println("������������");
			String name = scan.next();
			for(int i = 0;i < op.length;i++){
				if(op[i].name.equals(name))
					System.out.println(op[i]);
			}
		}
	}

	

	public static void tongJi(Scanner scan) throws IOException {
		System.out.println("�������˺š���ʼ���ڣ���ֹ���ڡ���ˮ���ͣ����ڸ�ʽ����2016.12.2.18.01.50��");
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
	
	//�ж�d1��d2��ʱ���Ⱥ�   -1����d1��d2֮ǰ��1����d1��d2��0�������
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
