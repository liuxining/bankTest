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
//		System.out.println("请输入要计算大小的文件名");
//		String sizeName = scan.next();
//		System.out.println(sizeName + " 文件大小为： " + fileSize(new File(sizeName)));
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
		yaSuoFrame(scan);
		jieYaFrame(scan);
	}
	
	public static void yaSuoFrame(Scanner scan) throws IOException {
		File[] fy = new File("G:\\javaceshi3").listFiles();
		System.out.println(fy.length);
//		System.out.println("请输入目标文件名：");
//		String muBiao = scan.next();
		File ftemp = File.createTempFile("yaSuoFile_tmp", ".dat");
		File fn = new File("G:\\javaceshi2.rar");
		PrintWriter pw = new PrintWriter(ftemp);
		yaSuo(fy,pw);
		pw.close();
		
		jiaMi(ftemp,fn);
		ftemp.deleteOnExit();
		System.out.println("压缩完成！");
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
	
	//将文件fy加密存储到文件fn中
	public static void jiaMi(File fy,File fn) throws IOException {
		FileInputStream  fis = new FileInputStream(fy);
		FileOutputStream fos = new FileOutputStream(fn);
		
		int b;
		while((b = fis.read()) != -1) {
			fos.write(b + 32);
		}
		
		fos.close();
		fis.close();
	}
	
	//将文件fy解密存储到文件fn中
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
		String dirName = fileName + "_fenGe";
		File fenGeDir = new File(dirName);
		fenGeDir.mkdir();
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
	
	//将文件fy的数据复制到打印输出流pw中
	public static void copyFile2PW(File fy,PrintWriter pw) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fy));
		
		pw.println("-");//代表输出的是文件
		pw.println(fy.getName());//输出文件名
		System.out.println(fy.length());
		pw.println(fy.length());//输出文件大小
		System.out.println((fy.length() / 2) + 1);
		String st;
		int len = 0;
		while((st = br.readLine()) != null) {
			pw.println(st);
			len += st.length();
			System.out.println(len + "   " + st.length());
		}
		
		br.close();
	}
	//将多个文件加密后压缩成一个文件
	

	
	public static void yaSuo(File[] fy,PrintWriter pw) throws IOException {
//		File ftemp = File.createTempFile("yaSuoFile_tmp", ".dat");//临时文件
//		PrintWriter pw = new PrintWriter(ftemp);
		
		
		pw.println(fy.length);
		System.out.println(fy.length);
		for(int i = 0;i < fy.length;i++) {
			//将文件加密 存储到临时文件ftemp中
			if(fy[i].isDirectory()){
				pw.println("d");//代表输出的是目录
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
	
	

	//解压  将文件fy解压缩，将文件保存在路径path中
	public static void jieYa(String path,BufferedReader br) throws NumberFormatException, IOException {
		String nstr = br.readLine();
		System.out.println(nstr);
		int n = Integer.parseInt(nstr);//读取该文件夹中的文件数目
		String type;
		File[] fn = new File[n];
		File dir = new File(path);
		dir.mkdir();//创建该文件夹
		for(int i = 0;i < n;i++) {
			type = br.readLine();
			if(type.equals("d")) {
				fn[i] = new File(path + "\\" + br.readLine());		
				jieYa(fn[i].getAbsolutePath(),br);
			}else{
				String ttt = br.readLine();
				System.out.println(path + "\\" + ttt);
				fn[i] = new File(path + "\\" + ttt);
				fn[i].createNewFile();
				copyBR2File(br,fn[i]);
			}
		}
	}
	
	//将缓冲输入流br中的数据读到文件fn中
	public static void copyBR2File(BufferedReader br,File fn) throws NumberFormatException, IOException {
		long length = Long.parseLong(br.readLine());
		
		long len = 0;
		String data = null;
		
		PrintWriter pw = new PrintWriter(fn);
		while(len < length) {
			data = br.readLine();
			if(data != null)
				len += data.length();
			System.out.println(data);
			System.out.println(len + "   " + data.length());
			pw.println(data);
		}
		pw.close();
	}
	
}
