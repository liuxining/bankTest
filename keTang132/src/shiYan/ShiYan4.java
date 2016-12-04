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
			System.out.println("\t\t1、计算文件大小");
			System.out.println("\t\t2、文件加密");
			System.out.println("\t\t3、文件解密");
			System.out.println("\t\t4、文件分割");
			System.out.println("\t\t5、文件合并");
			System.out.println("\t\t6、文件打包");
			System.out.println("\t\t7、文件解包");
			System.out.println("\t\t0、退出");
			s = scan.nextInt();
			switch(s) {
			case 1:{
				System.out.println("请输入文件或目录名");
				String fileName = scan.next();
				System.out.println(fileName + " 大小为： " + fileSize(new File(fileName)));
				break;
			}
			case 2:{
				System.out.println("请输入要加密的文件或目录名");
				String yuanFileName = scan.next();
				System.out.println("请输入加密后的文件或目录名");
				String resultFileName = scan.next();
				jiaMi(new File(yuanFileName),new File(resultFileName));
				System.out.println("加密完成");
				break;
			}
			case 3:{
				System.out.println("请输入要解密的文件或目录名");
				String yuanFileName = scan.next();
				System.out.println("请输入解密后的文件或目录名");
				String resultFileName = scan.next();
				jieMi(new File(yuanFileName),new File(resultFileName));
				System.out.println("解密完成");
				break;
			}
			case 4:{
				//分割文件
				System.out.println("请输入要分割的文件名");
				String fileName = scan.next();
				File fy = new File(fileName);
				fenGe(fy);
				System.out.println("文件 " + fy.getName() + " 分割完成,存储位置为： " + fy.getParent() + "\\" + fy.getName().substring(0,fy.getName().indexOf(".")) + "_fenGe 目录中");
				break;
			}
			case 5:{
				//合并文件
				System.out.println("请输入要合并的文件所在的目录名");
				File fy = new File(scan.next());
				System.out.println("请输入合并后的文件的位置");
				File fn = new File(scan.next());
				heBing(fy.listFiles(), fn);;
				System.out.println("文件夹  " + fy.getAbsolutePath() + " 中的文件合并完成,合并后的文件为： " + fn.getAbsolutePath());
				break;
			}
			case 6:{
				//打包文件
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
				System.out.println("输入错误！");
				break;
			}
			}
		}
	}
	
	public static void daBaoFrame(Scanner scan) throws IOException {
		System.out.println("请输入要打包的文件所在的目录名");
		File fy = new File(scan.next());
		System.out.println("请输入打包后的文件的存储位置：");
		File fn = new File(scan.next() + "\\" + fy.getName() + ".dabao");
		fn.createNewFile();
		File ftemp = File.createTempFile(fn.getName(), ".tmp",fn.getParentFile());
		
		PrintWriter pw = new PrintWriter(ftemp);
		daBao(fy.listFiles(),pw);
		pw.close();
		
		jiaMiFile(ftemp,fn);
		ftemp.delete();
		System.out.println("打包完成！");
	}
	
	public static void jieBaoFrame(Scanner scan) throws NumberFormatException, FileNotFoundException, IOException {
		File ftemp,fy;

		System.out.println("请输入要解包的文件名");
		fy = new File(scan.next());
		ftemp = File.createTempFile(fy.getName(), ".tmp", fy.getParentFile());
		jieMiFile(fy,ftemp);//将文件解密
		System.out.println("请输入解包后的存储路径");
		jieBao(scan.next(), new BufferedReader(new FileReader(ftemp)));
		ftemp.delete();//将临时文件删除
		System.out.println("解包完成！");
	}
	//计算文件f的大小
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
		fn.mkdirs();//创建目录
		File fnn;
		if(fy.isDirectory()){//fy是目录
			File[] ziMuLu = fy.listFiles();
			fnn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jiaMi");
			for(int i = 0;i < ziMuLu.length;i++){
				jiaMi(ziMuLu[i],fnn);
			}
		}else{//fy是文件
				
			
			String fyName = fy.getName();
			int dot_index = fyName.indexOf(".");
			fnn = new File(fn.getAbsolutePath() + "\\" + fyName.substring(0,dot_index) + "_jiaMi" + fyName.substring(dot_index));
			fnn.createNewFile();//创建该文件
			jiaMiFile(fy,fnn);
		}
	}
	//加密单文件
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
//	//将文件fy加密存储到文件fn中
//	public static void jiaMi(File fy,File fn) throws IOException {
//		if(fy.isDirectory()){//fy是目录
//			File[] ziMuLu = fy.listFiles();
//			fn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jiaMi");
//			fn.mkdirs();
//			for(int i = 0;i < ziMuLu.length;i++){
//				fn = new File(fn.getAbsolutePath() + "\\" + ziMuLu[i].getName() + "_jiaMi");
//				jiaMi(ziMuLu[i],fn);
//			}
//		}else{//fy是文件
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
//				fn.createNewFile();//创建该文件
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
		fn.mkdirs();//创建目录
		File fnn;
		if(fy.isDirectory()){//fy是目录
			File[] ziMuLu = fy.listFiles();
			fnn = new File(fn.getAbsolutePath() + "\\" + fy.getName() + "_jieMi");
			for(int i = 0;i < ziMuLu.length;i++){
				jieMi(ziMuLu[i],fnn);
			}
		}else{//fy是文件
				
			
			String fyName = fy.getName();
			int dot_index = fyName.indexOf(".");
			fnn = new File(fn.getAbsolutePath() + "\\" + fyName.substring(0,dot_index) + "_jieMi" + fyName.substring(dot_index));
			fnn.createNewFile();//创建该文件
			jieMiFile(fy,fnn);
		}
	}
	//解密单文件
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
	
	//将文件fy分割为多个小文件fn[]  每个文件长度为100个字节
	public static void fenGe(File fy) throws IOException {
		FileInputStream fis = new FileInputStream(fy);
		FileOutputStream fos;
		long len = fy.length();//文件fy的长度
		
		int n = (int) (len / 100 + 1);
		//File[] fn = new File[(int) (len / 100 + 1)];
		
		String fileName = fy.getName();
		int dot = fileName.indexOf(".");
		fileName = fileName.substring(0, dot);//取文件名，不带扩展名
		String dirName = fy.getParent() + "\\" + fileName + "_fenGe";
		File fenGeDir = new File(dirName);
		fenGeDir.mkdirs();
		fileName = dirName +  "\\" + fileName;
		File fn;
		
		
		byte[] b = new byte[100];
		int hasNext = 0;
		
		int index = 0;
		
		for(int i = 0;i < n;i++){
			//创建此文件
			fn = new File(fileName + "-" + (++index) + ".dat");
			fn.createNewFile();
			//从源文件读取后面的100个字符
			hasNext = fis.read(b);
			//构建输出流     用处到fn[i]指定的文件
			fos = new FileOutputStream(fn);
			//将b数组中的数据写入到文件中
			fos.write(b, 0, hasNext);
			fos.close();
		}
		fis.close();
	}
	
	//将文件数组fy中的数据按数组顺序合并到新文件fn中
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
	
	//将文件fy的数据复制到打印输出流pw中
	public static void copyFile2PW(File fy,PrintWriter pw) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fy));
		
		pw.println("-");//代表输出的是文件
		pw.println(fy.getName());//输出文件名
		pw.println(fy.hashCode());
		String data;
		while((data = br.readLine()) != null) {
			pw.println(data);
		}
		pw.println(fy.hashCode());
		br.close();
	}
	//将多个文件加密后压缩成一个文件
	

	
	public static void daBao(File[] fy,PrintWriter pw) throws IOException {
		pw.println(fy.length);
		for(int i = 0;i < fy.length;i++) {
			//将文件加密 存储到临时文件ftemp中
			if(fy[i].isDirectory()){
				pw.println("d");//代表输出的是目录
				pw.println(fy[i].getName());
				File[] ziMuLu = fy[i].listFiles();
				daBao(ziMuLu,pw);
			}else{
				copyFile2PW(fy[i], pw);
			}
			
		}
		
//		jiaMi(ftemp,fn);
		
	}
	
	

	//解压  将文件fy解压缩，将文件保存在路径path中
	public static void jieBao(String path,BufferedReader br) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine());//读取该文件夹中的文件数目
		File[] fn = new File[n];
		File dir = new File(path);
		dir.mkdir();//创建该文件夹
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
	
	//将缓冲输入流br中的数据读到文件fn中
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
