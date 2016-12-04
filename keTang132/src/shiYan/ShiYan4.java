package shiYan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ShiYan4 {
	public static void main(String[] args) throws IOException {

		File f1 = new File("G:\\test\\fenge.java");
		System.out.println(f1.getParent());
		File f2 = new File(f1.getName() + "123");
		f2.mkdirs();
		Scanner scan = new Scanner(System.in);
		char ch = 'y';
		int s;
		while(ch == 'y') {
			System.out.println("\t\t1�������ļ���С");
			System.out.println("\t\t2���ļ�����");
			System.out.println("\t\t3���ļ�����");
			System.out.println("\t\t4���ļ��ָ�");
			System.out.println("\t\t5���ļ��ϲ�");
			System.out.println("\t\t6���ļ����");
			System.out.println("\t\t7���ļ����");
			System.out.println("\t\t0���˳�");
			s = scan.nextInt();
			switch(s) {
			case 1:{
				System.out.println("�������ļ���Ŀ¼��");
				String fileName = scan.next();
				System.out.println(fileName + " ��СΪ�� " + fileSize(new File(fileName)));
				break;
			}
			case 2:{
				System.out.println("������Ҫ���ܵ��ļ���Ŀ¼��");
				String yuanFileName = scan.next();
				System.out.println("��������ܺ���ļ���Ŀ¼��");
				String resultFileName = scan.next();
				jiaMi(new File(yuanFileName),new File(resultFileName));
				System.out.println("�������");
				break;
			}
			case 3:{
				System.out.println("������Ҫ���ܵ��ļ���Ŀ¼��");
				String yuanFileName = scan.next();
				System.out.println("��������ܺ���ļ���Ŀ¼��");
				String resultFileName = scan.next();
				jieMi(new File(yuanFileName),new File(resultFileName));
				System.out.println("�������");
				break;
			}
			case 4:{
				//�ָ��ļ�
				System.out.println("������Ҫ�ָ���ļ���");
				String fileName = scan.next();
				File fy = new File(fileName);
				fenGe(fy);
				System.out.println("�ļ� " + fy.getName() + " �ָ����,�洢λ��Ϊ�� " + fy.getParent() + "\\" + fy.getName().substring(0,fy.getName().indexOf(".")) + "_fenGe Ŀ¼��");
				break;
			}
			case 5:{
				//�ϲ��ļ�
				System.out.println("������Ҫ�ϲ����ļ����ڵ�Ŀ¼��");
				File fy = new File(scan.next());
				System.out.println("������ϲ�����ļ���λ��");
				File fn = new File(scan.next());
				heBing(fy.listFiles(), fn);;
				System.out.println("�ļ���  " + fy.getAbsolutePath() + " �е��ļ��ϲ����,�ϲ�����ļ�Ϊ�� " + fn.getAbsolutePath());
				break;
			}
			case 6:{
				//����ļ�
				daBaoFrame(scan);
				break;
			}
			case 7:{
				jieBaoFrame(scan);
				break;
			}
			case 0:{
				ch = 'n';
				break;
			}
			default:{
				System.out.println("�������");
				break;
			}
			}
		}
	}
	
	public static void daBaoFrame(Scanner scan) throws IOException {
		System.out.println("������Ҫ������ļ����ڵ�Ŀ¼��");
		File fy = new File(scan.next());
		System.out.println("������������ļ��Ĵ洢λ�ã�");
		File fn = new File(scan.next() + "\\" + fy.getName() + ".dabao");
		fn.createNewFile();
		File ftemp = File.createTempFile(fn.getName(), ".tmp",fn.getParentFile());
		
		PrintWriter pw = new PrintWriter(ftemp);
		daBao(fy.listFiles(),pw);
		pw.close();
		
		jiaMiFile(ftemp,fn);
		ftemp.delete();
		System.out.println("�����ɣ�");
	}
	
	public static void jieBaoFrame(Scanner scan) throws NumberFormatException, FileNotFoundException, IOException {
		File ftemp,fy;

		System.out.println("������Ҫ������ļ���");
		fy = new File(scan.next());
		ftemp = File.createTempFile(fy.getName(), ".tmp", fy.getParentFile());
		jieMiFile(fy,ftemp);//���ļ�����
		System.out.println("����������Ĵ洢·��");
		jieBao(scan.next(), new BufferedReader(new FileReader(ftemp)));
		ftemp.delete();//����ʱ�ļ�ɾ��
		System.out.println("�����ɣ�");
	}
	//�����ļ�f�Ĵ�С
	public static long fileSize(File f) {
		long len = 0;
		if(f.isDirectory()) {
			File[] f1 = f.listFiles();
			for(int i = 0;i < f1.length;i++)
				len += fileSize(f1[i]);
		}else{
			len += f.length();
		}
		
		return len;
	}

	
	public static void jiaMi(File fy,File fn) throws IOException {
		fn.mkdirs();//����Ŀ¼
		File fnn;
		if(fy.isDirectory()){//fy��Ŀ¼
			File[] ziMuLu = fy.listFiles();
			fnn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jiaMi");
			for(int i = 0;i < ziMuLu.length;i++){
				jiaMi(ziMuLu[i],fnn);
			}
		}else{//fy���ļ�
				
			
			String fyName = fy.getName();
			int dot_index = fyName.indexOf(".");
			fnn = new File(fn.getAbsolutePath() + "\\" + fyName.substring(0,dot_index) + "_jiaMi" + fyName.substring(dot_index));
			fnn.createNewFile();//�������ļ�
			jiaMiFile(fy,fnn);
		}
	}
	//���ܵ��ļ�
	public static void jiaMiFile(File fy,File fn) throws IOException {
		FileInputStream  fis = new FileInputStream(fy);
		FileOutputStream fos = new FileOutputStream(fn);
		
		int b;
		while((b = fis.read()) != -1) {
			fos.write(b + 32);
		}
		
		fos.close();
		fis.close();
	}
//	//���ļ�fy���ܴ洢���ļ�fn��
//	public static void jiaMi(File fy,File fn) throws IOException {
//		if(fy.isDirectory()){//fy��Ŀ¼
//			File[] ziMuLu = fy.listFiles();
//			fn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jiaMi");
//			fn.mkdirs();
//			for(int i = 0;i < ziMuLu.length;i++){
//				fn = new File(fn.getAbsolutePath() + "\\" + ziMuLu[i].getName() + "_jiaMi");
//				jiaMi(ziMuLu[i],fn);
//			}
//		}else{//fy���ļ�
//				
//			
//			FileInputStream  fis = new FileInputStream(fy);
//			if(!fn.exists()) {
//				String fnName = fn.getAbsolutePath();
//				int dot_fn = 0,i;
//				while((i = fnName.indexOf("\\",dot_fn + 1)) != -1){
//					dot_fn = i;
//				}
//				File fnn = new File(fnName.substring(0, dot_fn));
//				System.out.println(fnn.getAbsolutePath() + "   " + fn.getAbsolutePath());
//				fnn.mkdirs();
//				fn.createNewFile();//�������ļ�
//			}
//			FileOutputStream fos = new FileOutputStream(fn);
//			
//			int b;
//			while((b = fis.read()) != -1) {
//				fos.write(b + 32);
//			}
//			
//			fos.close();
//			fis.close();
//		}
//	}
	
	public static void jieMi(File fy,File fn) throws IOException {
		fn.mkdirs();//����Ŀ¼
		File fnn;
		if(fy.isDirectory()){//fy��Ŀ¼
			File[] ziMuLu = fy.listFiles();
			fnn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jieMi");
			for(int i = 0;i < ziMuLu.length;i++){
				jieMi(ziMuLu[i],fnn);
			}
		}else{//fy���ļ�
				
			
			String fyName = fy.getName();
			int dot_index = fyName.indexOf(".");
			fnn = new File(fn.getAbsolutePath() + "\\" + fyName.substring(0,dot_index) + "_jieMi" + fyName.substring(dot_index));
			fnn.createNewFile();//�������ļ�
			jieMiFile(fy,fnn);
		}
	}
	//���ܵ��ļ�
	public static void jieMiFile(File fy,File fn) throws IOException {
		FileInputStream  fis = new FileInputStream(fy);
		FileOutputStream fos = new FileOutputStream(fn);
		
		int b;
		while((b = fis.read()) != -1) {
			fos.write(b - 32);
		}
		
		fos.close();
		fis.close();
	}
	
	//���ļ�fy�ָ�Ϊ���С�ļ�fn[]  ÿ���ļ�����Ϊ100���ֽ�
	public static void fenGe(File fy) throws IOException {
		FileInputStream fis = new FileInputStream(fy);
		FileOutputStream fos;
		long len = fy.length();//�ļ�fy�ĳ���
		
		int n = (int) (len / 100 + 1);
		//File[] fn = new File[(int) (len / 100 + 1)];
		
		String fileName = fy.getName();
		int dot = fileName.indexOf(".");
		fileName = fileName.substring(0, dot);//ȡ�ļ�����������չ��
		String dirName = fy.getParent() + "\\" + fileName + "_fenGe";
		File fenGeDir = new File(dirName);
		fenGeDir.mkdirs();
		fileName = dirName +  "\\" + fileName;
		File fn;
		
		
		byte[] b = new byte[100];
		int hasNext = 0;
		
		int index = 0;
		
		for(int i = 0;i < n;i++){
			//�������ļ�
			fn = new File(fileName + "-" + (++index) + ".dat");
			fn.createNewFile();
			//��Դ�ļ���ȡ�����100���ַ�
			hasNext = fis.read(b);
			//���������     �ô���fn[i]ָ�����ļ�
			fos = new FileOutputStream(fn);
			//��b�����е�����д�뵽�ļ���
			fos.write(b, 0, hasNext);
			fos.close();
		}
		fis.close();
	}
	
	//���ļ�����fy�е����ݰ�����˳��ϲ������ļ�fn��
	public static void heBing(File[] fy,File fn) throws IOException {
		FileInputStream fis;
		FileOutputStream fos = new FileOutputStream(fn);
		for(int i = 0;i < fy.length;i++)
			System.out.println(fy[i].getName());
		byte[] b = new byte[100];
		int hasNext = 0;
		System.out.println(fy.length);
		for(int i = 0;i < fy.length;i++) {
			fis = new FileInputStream(fy[i]);
			hasNext = fis.read(b);
			System.out.println(i + "  " +hasNext);
			fos.write(b, 0, hasNext);
			fis.close();
		}
		fos.close();
	}
	
	//���ļ�fy�����ݸ��Ƶ���ӡ�����pw��
	public static void copyFile2PW(File fy,PrintWriter pw) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fy));
		
		pw.println("-");//������������ļ�
		pw.println(fy.getName());//����ļ���
		pw.println(fy.hashCode());
		String data;
		while((data = br.readLine()) != null) {
			pw.println(data);
		}
		pw.println(fy.hashCode());
		br.close();
	}
	//������ļ����ܺ�ѹ����һ���ļ�
	

	
	public static void daBao(File[] fy,PrintWriter pw) throws IOException {
		pw.println(fy.length);
		for(int i = 0;i < fy.length;i++) {
			//���ļ����� �洢����ʱ�ļ�ftemp��
			if(fy[i].isDirectory()){
				pw.println("d");//�����������Ŀ¼
				pw.println(fy[i].getName());
				File[] ziMuLu = fy[i].listFiles();
				daBao(ziMuLu,pw);
			}else{
				copyFile2PW(fy[i], pw);
			}
			
		}
		
//		jiaMi(ftemp,fn);
		
	}
	
	

	//��ѹ  ���ļ�fy��ѹ�������ļ�������·��path��
	public static void jieBao(String path,BufferedReader br) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine());//��ȡ���ļ����е��ļ���Ŀ
		File[] fn = new File[n];
		File dir = new File(path);
		dir.mkdir();//�������ļ���
		for(int i = 0;i < n;i++) {
			if(br.readLine().equals("d")) {
				fn[i] = new File(path + "\\" + br.readLine());		
				jieBao(fn[i].getAbsolutePath(),br);
			}else{
				fn[i] = new File(path + "\\" + br.readLine());
				fn[i].createNewFile();
				copyBR2File(br,fn[i]);
			}
		}
	}
	
	//������������br�е����ݶ����ļ�fn��
	public static void copyBR2File(BufferedReader br,File fn) throws NumberFormatException, IOException {
		String data = null;
		String hasCode = br.readLine();
		PrintWriter pw = new PrintWriter(fn);
		while(!((data = br.readLine()).equals(hasCode))) {
			pw.println(data);
		}
		pw.close();
	}
	
}
