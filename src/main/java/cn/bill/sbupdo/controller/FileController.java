package cn.bill.sbupdo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
	@SuppressWarnings("resource")
	@RequestMapping("/download.do")
	public void download(HttpServletResponse response) throws Exception
	{
		File file = new File("F:\\upload\\d940f664-9cff-4090-82ce-defe70d395e6_技术要求.txt");
		FileInputStream fis = new FileInputStream(file);
		response.setContentType("application/force-download");
		response.addHeader("Content-disposition", "attachment;fileName=技术要求.txt");
		
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = fis.read(buf)) != -1)
		{
			os.write(buf, 0, len);
			os.flush();
		}
		
		os.close();
	}

}
