package com.fangxin365.core.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
	
	public static File transferFile(String path, MultipartFile file) throws IllegalStateException, IOException {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		
		if (file.getOriginalFilename().lastIndexOf(".") > 0) {
			File aFile = new File(path + file.getOriginalFilename());

			String strLast = path.substring(0, path.lastIndexOf("/"));

			int nameLength = strLast.substring(strLast.lastIndexOf("/")).length() + 1 + aFile.getName().length();
			//如果上传的文件的名字中含有中文字符或其他非单词字符，那么就进行重命名，并将其改为英文名字
			//这里所说的单词字符为：[a-zA-Z_0-9]
			Boolean rename = true;
			
			if (aFile != null && aFile.exists() || nameLength > 30 || rename ) {
				char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
						'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
						'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
						'5', '6', '7', '8', '9' };

				StringBuffer fileName = new StringBuffer("");
				Random r = new Random();
				int pos = -1;
				for (int i = 0; i < 15; i++) {
					pos = Math.abs(r.nextInt(36));
					fileName.append(str[pos]);
				}

				String newName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

				aFile = new File(path + fileName.toString().trim() + "." + newName);
			}
			file.transferTo(aFile);
			return aFile;
		} else {
			return null;
		}
	}

	/**
	 * 删除文件或者文件夹，对于文件夹遍历其子文件夹进行递归删除
	 * 
	 * @param f - File对象
	 * @return 删除是否成功
	 */
	public static boolean deleteFile(File f) {
		if (f.exists()) {
			if (f.isFile())
				return f.delete();
			else if (f.isDirectory()) {
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (!deleteFile(files[i]))
						return false;
				}
				return f.delete();
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 上传文件到uploadfiles文件夹
	 */
	public static File uploadFile(HttpServletRequest request) {
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 根据前台的name名称得到上传的文件
		MultipartFile file = multipartRequest.getFile("file");
		// 获取路径
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "/uploadfiles/";
		// 创建文件
		File dirPath = new File(ctxPath);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		File uploadFile = new File(ctxPath + DateUtil.format(new Date(), "yyyyMMddhhss") + ".xls");

		try {
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadFile;
	}
	
	/**
	 * 去掉文件后缀名
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	/**
	 * 默认导出名称
	 */
	public static String getFileName(String title) {
		String filename = title + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
		return filename;
	}
	
}
