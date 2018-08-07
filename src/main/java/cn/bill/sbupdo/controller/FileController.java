package cn.bill.sbupdo.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.bill.sbupdo.util.FileUpload;

/**
 * Description: <br/>
 * Date:2018年8月6日 下午3:29:05 <br/>
 *
 * @author fengminbiao@126.com
 * @version
 */
@RequestMapping("/file")
@Controller
public class FileController
{
	/**
	 * SpringBoot多文件上传使用 MultipartHttpServletRequest已包含HttpServletRequest
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multiUpload.do")
	public String upload(MultipartHttpServletRequest multipartRequest,Model model) throws Exception
	{
		System.out.println("FileController...upload()");
		Map<String, String> filePathMap = FileUpload.uploadFiles(multipartRequest);
		model.addAttribute("filePaths", filePathMap);
		return "/upload_success";
	}
	 /**
	  * 跳转到上传页面
	  * @return
	  */
	@RequestMapping("/upload.do")
	public String uploadPage()
	{
		return "/upload";
	}
	
	/**
	 * 文件下载
	 */
	@RequestMapping("/download.do")
	public void download(HttpServletResponse response) throws Exception
	{
		//方法一，中文名字显示有问题
		/*File downloadFile = new File("F:\\upload\\d940f664-9cff-4090-82ce-defe70d395e6_技术要求.txt");
		String fileName = downloadFile.getName();
		FileInputStream fis = new FileInputStream(downloadFile);
		//设置强制下载不打开
		response.setContentType("application/force-download");
		response.setContentLength((int) downloadFile.length());
		 response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName); 
		
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = fis.read(buf)) != -1)
		{
			os.write(buf, 0, len);
			os.flush();
		}
		
		os.close();*/
		
		//方法二，解决中文乱码问题，同时下载效率更高
		 String fileName = "d940f664-9cff-4090-82ce-defe70d395e6_技术要求.txt"; 
		 // 获取文件的Path对象 
		 Path filePath = Paths.get("F:\\upload\\",fileName); 
		 System.out.println(filePath);
		 fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()); 
		 response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString()); 
		 // 解决中文文件名乱码关键行 
		 response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName); 
		 Files.copy(filePath, response.getOutputStream());

	}

}
