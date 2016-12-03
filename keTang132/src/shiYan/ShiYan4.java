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

		File f1 = new File("D:\\EmbededFinally.java");
		f1.createNewFile();
		Scanner scan = new Scanner(System.in);
		char ch = 'y';
		int s;
		while(ch == 'y') {
			System.out.println("\t\t1�������ļ���С");
			System.out.println("\t\t2������");
			System.out.println("\t\t3������");
			System.out.println("\t\t4���ָ��ļ�");
			System.out.println("\t\t5���ϲ��ļ�");
			System.out.println("\t\t6��ѹ���ļ�");
			System.out.println("\t\t7����ѹ�ļ�");
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
				
			}
			}
		}
//		System.out.println("������Ҫ�����С���ļ���");
//		String sizeName = scan.next();
//		System.out.println(sizeName + " �ļ���СΪ�� " + fileSize(new File(sizeName)));
//		
//		
//		jieMi(new File(sizeName),new File(sizeName));
//		scan.close();
//		fenGe(new File("DataFileTest.java"));
//		
//		File[] fy = new File[40];
//		for(int i = 1;i <= 40;i++){
//			fy[i - 1] = new File("DataFileTest.java-" + i + ".dat");
//		}
//		
////		heBing(fy,new File("DataFileTestHeBing.dat"));
//		yaSuoFrame(scan);
//		jieYaFrame(scan);
	}
	
	public static void yaSuoFrame(Scanner scan) throws IOException {
		File[] fy = new File("G:\\javaceshi2").listFiles();
		System.out.println(fy.length);
//		System.out.println("������Ŀ���ļ�����");
//		String muBiao = scan.next();
		File ftemp = File.createTempFile("yaSuoFile_tmp", ".dat");
		File fn = new File("G:\\javaceshi2.rar");
		PrintWriter pw = new PrintWriter(ftemp);
		yaSuo(fy,pw);
		pw.close();
		
		jiaMi(ftemp,fn);
		ftemp.deleteOnExit();
		System.out.println("ѹ����ɣ�");
	}
	
	public static void jieYaFrame(Scanner scan) throws NumberFormatException, FileNotFoundException, IOException {
		File ftemp,fy;
		String resultPath;
//		System.out.println("D:\\Java_Project\\Liuxn1.rar");
		fy = new File("G:\\javaceshi2.rar");
//		System.out.println("ddd");
//		resultPath = scan.next();

		ftemp = new File("G:\\javaceshi3.rar");
		ftemp.createNewFile();
//		ftemp = File.createTempFile("jieYaFile_tmp", ".dat");
		jieMi(fy,ftemp);
		
		jieYa("G:\\result", new BufferedReader(new FileReader(ftemp)));
		
		ftemp.deleteOnExit();
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
	
	//���ļ�fy���ܴ洢���ļ�fn��
	public static void jiaMi(File fy,File fn) throws IOException {
		fn.mkdirs();//����Ŀ¼
		File fnn;
		if(fy.isDirectory()){//fy��Ŀ¼
			File[] ziMuLu = fy.listFiles();
			fnn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jm");
			for(int i = 0;i < ziMuLu.length;i++){
				jiaMi(ziMuLu[i],fnn);
			}
		}else{//fy���ļ�
				
			
			FileInputStream  fis = new FileInputStream(fy);
			String fyName = fy.getName();
			int dot_index = fyName.indexOf(".");
			fnn = new File(fn.getAbsolutePath() + "\\" + fyName.substring(0,dot_index) + "_jm" + fyName.substring(dot_index));
			fnn.createNewFile();//�������ļ�
			FileOutputStream fos = new FileOutputStream(fnn);
			
			int b;
			while((b = fis.read()) != -1) {
				fos.write(b + 32);
			}
			
			fos.close();
			fis.close();
		}
	}
	
	//���ļ�fy���ܴ洢���ļ�fn��
	public static void jieMi(File fy,File fn) throws IOException {
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
		String dirName = fileName + "_fenGe";
		File fenGeDir = new File(dirName);
		fenGeDir.mkdir();
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
		
		byte[] b = new byte[100];
		int hasNext = 0;
		
		for(int i = 0;i < fy.length;i++) {
			fis = new FileInputStream(fy[i]);
			hasNext = fis.read(b);
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
	

	
	public static void yaSuo(File[] fy,PrintWriter pw) throws IOException {
//		File ftemp = File.createTempFile("yaSuoFile_tmp", ".dat");//��ʱ�ļ�
//		PrintWriter pw = new PrintWriter(ftemp);
		
		
		pw.println(fy.length);
		System.out.println(fy.length);
		for(int i = 0;i < fy.length;i++) {
			//���ļ����� �洢����ʱ�ļ�ftemp��
			if(fy[i].isDirectory()){
				pw.println("d");//�����������Ŀ¼
				pw.println(fy[i].getName());
				System.out.println(fy[i].getAbsolutePath());
				File[] ziMuLu = fy[i].listFiles();
				yaSuo(ziMuLu,pw);
			}else{
				System.out.println(fy[i].getAbsolutePath());
				copyFile2PW(fy[i], pw);
			}
			
		}
		
//		jiaMi(ftemp,fn);
		
	}
	
	

	//��ѹ  ���ļ�fy��ѹ�������ļ�������·��path��
	public static void jieYa(String path,BufferedReader br) throws NumberFormatException, IOException {
		String nstr = br.readLine();
		System.out.println(nstr);
		int n = Integer.parseInt(nstr);//��ȡ���ļ����е��ļ���Ŀ
		String type;
		File[] fn = new File[n];
		File dir = new File(path);
		dir.mkdir();//�������ļ���
		for(int i = 0;i < n;i++) {
			type = br.readLine();
			if(type.equals("d")) {
				fn[i] = new File(path + "\\" + br.readLine());		
				jieYa(fn[i].getAbsolutePath(),br);
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
		System.out.println(hasCode);
		PrintWriter pw = new PrintWriter(fn);
		while(!((data = br.readLine()).equals(hasCode))) {
			pw.println(data);
		}
		pw.close();
	}
	
}
