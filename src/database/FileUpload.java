//package database;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import org.apache.commons.fileupload.FileItemStream;
//
//public class FileUpload {
//	
//	public static boolean processFile(String path,FileItemStream item){
//		
//		try {
//
//			File file = new File(path+File.separator+"images");
//			if(!file.exists()) file.mkdir();
//			File savedFile = new File(file.getAbsolutePath()+File.separator+item.getName());
//			FileOutputStream fos = new FileOutputStream(savedFile);
//			InputStream is = item.openStream();
//			int x = 0 ;
//			byte[] b = new byte[1024];
//			while((x = is.read(b))!= -1){
//				fos.write(b,0,x);
//			}
//			fos.flush();
//			fos.close();
//			return true;
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//		
//	}
//	
//	
//}
