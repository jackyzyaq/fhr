package com.aqap.matrix.faurecia.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.aqap.matrix.faurecia.entity.Attachment;
import com.aqap.matrix.faurecia.service.AttachmentService;
import com.aqap.matrix.faurecia.utils.FileUtil;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xguanjia.core.modules.mapper.JsonMapper;

public class UploadServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(UploadServlet.class);  
	
	

	public void init() throws ServletException {
		// Put your code here
	}

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * 附件上传
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AttachmentService attachmentService = (AttachmentService) SpringContextHolder.getBean("attachmentService");
		List<Attachment> returnLisr = Lists.newArrayList();
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		Date date = new Date();
		String savePath = request.getSession().getServletContext().getRealPath("/");
		if(StringUtils.isNotEmpty(savePath)){
			savePath = savePath.replace("\\", "/");
		}
		savePath = savePath.substring(0, savePath.lastIndexOf("/"));// 获取项目根路径
		List<String> list = null;
		String address = "";

		list = splitStr(Function.getEnvironment("filepath"), "/");

		if (list != null && list.size() > 0) {

			savePath = savePath + "/" + list.get(0);

			for (int i = 1; i < list.size(); i++) {
				savePath += "/" + list.get(i);

			}
			savePath += "/" + new SimpleDateFormat("yyyyMMdd").format(date) + "/";

			address = "/" + list.get(0);

			for (int i = 1; i < list.size(); i++) {
				address += "/" + list.get(i);
			}
			address += "/" + new SimpleDateFormat("yyyyMMdd").format(date) + "/";

		}
		FileUtil.createDir(savePath);
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						// 普通文本信息处理
						String paramName = item.getFieldName();
						String paramValue = item.getString();
//						logger.debug(paramName + ":" + paramValue);
					} else {
						// 上传文件信息处理
						String fileName = item.getName();
						// 获取拓展名
						if (fileName.lastIndexOf(".") >= 0) {
							extName = fileName.substring(fileName.lastIndexOf("."));
						}
						// 获取文件大小
						Long size = item.getSize();
						newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字

						byte[] data = item.get();

						FileOutputStream fos = new FileOutputStream(savePath + "/" + newFileName);
						fos.write(data);
						fos.close();

						// 入库操作
						String originalName = item.getName();
						String name = newFileName;// 名称
						address += newFileName;
						String fileExt = extName;// 附件后缀
						Date createDate = date;// 创建时间
						String descn = "";// 备注
						String type = "2";// 分类

						List<Attachment> attachments = attachmentService.getAttachmentList();
						int i = -1;
						if (attachments != null && attachments.size() > 0) {
							for (Attachment attachment : attachments) {

								String attachMd5 = getFileMD5(new File(savePath + attachment.getAddress()));
								// 要上传文件的md5
								String uploadAttachMd5 = getFileMD5(new File(savePath + address.substring(1)));

								if (attachMd5 != null && uploadAttachMd5 != null) {
									if (attachMd5.equals(uploadAttachMd5)) {
										i++;
									}
								}

							}
						}
						if (i > 0) {
							if (originalName != null) {

								String[] str = originalName.split("\\.");
								if (str.length == 1) {

									originalName = str[0] + "(" + i + ")";

								} else if (str.length == 2) {
									originalName = str[0] + "(" + i + ")" + fileExt;
								} else if (str.length > 2) {
									String flag = "";
									for (int j = 0; j < str.length - 1; j++) {
										flag += str[i];
									}
									originalName = flag + "(" + i + ")" + fileExt;
								}

							}
						}
						Attachment attac = new Attachment(originalName, name, address, fileExt, createDate, descn,
								type, size, savePath + newFileName);

						attachmentService.addAttachment(attac);
						returnLisr.add(attac);
					}
				}
			} catch (FileUploadException e) {
				logger.error(e.getMessage());
			}
		}

		Map<String, Object> map = Maps.newHashMap();
		map.put("data", returnLisr);
		String json = JsonMapper.nonEmptyMapper().toJson(map);
//		logger.debug("json:" + json);
		response.setContentType("text/json");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}

	private List<String> splitStr(String source, String reg) {
		List<String> list = new ArrayList<String>();

		if (source.equals("")) {
			return list;
		}

		String[] arr = source.split(reg);

		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].trim().equals("")) {
				list.add(arr[i]);
			}
		}

		return list;
	}

	private static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
}
