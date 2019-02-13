package com.yale.zc.qrcode.util;

import com.yale.zc.qrcode.config.MatrixToBgImageConfig;
import com.yale.zc.qrcode.config.MatrixToLogoImageConfig;
import com.yale.zc.qrcode.config.QrcodeSettings;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;


public class QrcodeUtils {
	private static final int DEFAULT_LENGTH = 256;// 生成二维码的默认边长，因为是正方形的，所以高度和宽度一致
	private static final String FORMAT = "jpg";// 生成二维码的格式

	private static  QrcodeSettings qrcodeSettings = new QrcodeSettings();

	private static final Logger LOGGER = LoggerFactory.getLogger(QrcodeUtils.class);

	/**
	 * 根据内容生成二维码数据
	 * 
	 * @param content
	 *            二维码文字内容[为了信息安全性，一般都要先进行数据加密]
	 * @param length
	 *            二维码图片宽度和高度
	 */
	private static BitMatrix createQrcodeMatrix(String content, int length) {
		Map<EncodeHintType, Object> hints = Maps.newEnumMap(EncodeHintType.class);
		// 设置字符编码
		hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);

		try {
			return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, length, length, hints);
		} catch (Exception e) {
			LOGGER.error("内容为：【" + content + "】的二维码生成失败！", e);
			return null;
		}

	}

	/**
	 * 根据指定边长创建生成的二维码
	 * 
	 * @param content
	 *            二维码内容
	 * @param length
	 *            二维码的高度和宽度
	 * @param logoFile
	 *            logo 文件对象，可以为空
	 * @return 二维码图片的字节数组
	 */
	public static byte[] createQrcode(String content, int length, File logoFile) {
		if (logoFile != null && !logoFile.exists()) {
			throw new IllegalArgumentException("请提供正确的logo文件！");
		}

		BitMatrix qrCodeMatrix = createQrcodeMatrix(content, length);
		if (qrCodeMatrix == null) {
			throw new IllegalArgumentException("请提供正确的二维码图片地址");
		}
		try {
			File file = Files.createTempFile("qrcode_" + UUID.randomUUID(), "." + FORMAT).toFile();
			//System.out.println("####file path:"+file.getAbsolutePath());

			MatrixToImageWriter.writeToFile(qrCodeMatrix, FORMAT, file);
			if (logoFile != null) {
				// 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
				BufferedImage img = ImageIO.read(file);
				overlapImage(img, FORMAT, file.getAbsolutePath(), logoFile, new MatrixToLogoImageConfig());
			}

			return toByteArray(file);
		} catch (Exception e) {
			LOGGER.error("内容为：【" + content + "】的二维码生成失败！", e);
			return null;
		}
	}

	/**
	 * 根据配置信息生成二维码
	 * @param config
	 * @return
	 */
	public static byte[] createQrcode(MatrixToBgImageConfig config) {
		LOGGER.info("### create qrcode" );

	//	System.out.println("### qrcode url:"+config.getQrCodeUrl());
	//	System.out.println("### getTempPath:"+config.getTempPath());
		try {

			if (StringUtils.isNotEmpty(config.getTempPath()) && !new File(config.getTempPath()).exists()){
			//	System.out.println("###临时文件夹不存在就创建");
				new File(config.getTempPath()).mkdir();   //临时文件夹不存在就创建
			}
			//InputStream inputStream = Thread.currentThread().getContextClassLoader()
			//		.getResourceAsStream(config.getBgFileName());
			Resource resource = new ClassPathResource("/static/bg.png");
			InputStream inputStream =resource.getInputStream();
			File bgFile = Files.createTempFile(Paths.get(config.getTempPath()),"bg_", ".jpg").toFile();
		 
			//System.out.println("###背景图片路径："+bgFile.getAbsolutePath());
			 
			FileUtils.copyInputStreamToFile(inputStream, bgFile);

			inputStream.close();

			//logger.info("背景图 {}", bgFile);

			if (bgFile != null && !bgFile.exists()) {
				throw new IllegalArgumentException("请提供正确的背景文件！");
			}

			// 头像图
			File headimgFile = null;

			if (StringUtils.isNotEmpty(config.getHeadimgUrl())) {

				CloseableHttpClient httpclient = HttpClientBuilder.create().build();
				HttpGet httpget = new HttpGet(config.getHeadimgUrl());
				httpget.addHeader("Content-Type", "text/html;charset=UTF-8");
				// 配置请求的超时设置
				RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(500)
						.setConnectTimeout(500).setSocketTimeout(500).build();
				httpget.setConfig(requestConfig);

				try (CloseableHttpResponse response = httpclient.execute(httpget);
						InputStream headimgStream = handleResponse(response)) {

					Header[] contentTypeHeader = response.getHeaders("Content-Type");
					if (contentTypeHeader != null && contentTypeHeader.length > 0) {
						if (contentTypeHeader[0].getValue().startsWith(ContentType.APPLICATION_JSON.getMimeType())) {

							// application/json; encoding=utf-8 下载媒体文件出错
							String responseContent = handleUTF8Response(response);

							LOGGER.error("下载网络头像出错{}", responseContent);
						}
					}

					headimgFile = createTmpFile(headimgStream, "headimg_" + UUID.randomUUID(), "jpg");
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					throw new Exception("头像文件读取有误！", e);
				} finally {

					httpget.releaseConnection();
				}

			}

			if (headimgFile != null && !headimgFile.exists()) {
				throw new IllegalArgumentException("请提供正确的头像文件！");
			}

			BitMatrix qrCodeMatrix = createQrcodeMatrix(config.getQrCodeUrl()+config.getUserId(), config.getQrcode_height());
			if (qrCodeMatrix == null) {
				throw new IllegalArgumentException("请提供正确的二维码图片地址");
			}
			//生成二维码临时文件
			File file = Files.createTempFile(Paths.get(config.getTempPath()),"qrcode_" + UUID.randomUUID(), "." + FORMAT).toFile();
			LOGGER.debug(file.getAbsolutePath());

			MatrixToImageWriter.writeToFile(qrCodeMatrix, FORMAT, file);
			if (bgFile != null) {
				// 添加背景图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
				BufferedImage img = ImageIO.read(file);
			//	System.out.println("##file.getAbsolutePath():"+file.getAbsolutePath());

				//微信头像转成圆形
				BufferedImage bi1 = ImageIO.read(headimgFile);
				//透明底的图片
				BufferedImage bi2 = new BufferedImage(200,200,BufferedImage.TYPE_4BYTE_ABGR);
				Ellipse2D.Double shape = new Ellipse2D.Double(0,0,config.getHeadimg_height(),config.getHeadimg_height());
				Graphics2D g2 = bi2.createGraphics();
				g2.setClip(shape);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// 使用 setRenderingHint 设置抗锯齿
				g2.drawImage(bi1,0,0,config.getHeadimg_height(),config.getHeadimg_height(),0,0,bi1.getWidth(),bi1.getHeight(),null);

				g2.dispose();
				File newHeadingFile = new File(config.getTempPath()+"/"+ UUID.randomUUID()+".jpg");
				ImageIO.write(bi2, "PNG",newHeadingFile);

				increasingImage(img, FORMAT, file.getAbsolutePath(), bgFile, config, newHeadingFile);
				bgFile.deleteOnExit();  //删除临时生成的背景文件
				newHeadingFile.deleteOnExit();
			}

			return toByteArray(file);
		} catch (Exception e) {
		//	logger.warn("内容为：【" + config.qrcode_url  + "】的二维码生成失败！", e);
			return null;
		}
	}

	/**
	 * 创建生成默认高度(300)的二维码图片 可以指定是否带logo
	 * 
	 * @param content
	 *            二维码内容
	 * @param logoFile
	 *            logo 文件对象，可以为空
	 * @return 二维码图片的字节数组
	 */
	public static byte[] createQrcode(String content, File logoFile) {
		return createQrcode(content, DEFAULT_LENGTH, logoFile);
	}

	/**
	 * 将文件转换为字节数组， 使用MappedByteBuffer，可以在处理大文件时，提升性能
	 * 
	 * @param file
	 *            文件
	 * @return 二维码图片的字节数组
	 */
	private static byte[] toByteArray(File file) {
		try (FileChannel fc = new RandomAccessFile(file, "r").getChannel();) {
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (Exception e) {
			LOGGER.error("文件转换成byte[]发生异常！", e);
			return null;
		}
	}

	/**
	 * 将logo添加到二维码中间
	 * 
	 * @param image
	 *            生成的二维码图片对象
	 * @param imagePath
	 *            图片保存路径
	 * @param logoFile
	 *            logo文件对象
	 * @param format
	 *            图片格式
	 * @throws Exception
	 */
	private static void overlapImage(BufferedImage image, String format, String imagePath, File logoFile,
			MatrixToLogoImageConfig logoConfig) throws Exception {
		try {
			BufferedImage logo = ImageIO.read(logoFile);
			Graphics2D g = image.createGraphics();
			// 考虑到logo图片贴到二维码中，建议大小不要超过二维码的1/5;
			int width = image.getWidth() / logoConfig.getLogoPart();
			int height = image.getHeight() / logoConfig.getLogoPart();
			// logo起始位置，此目的是为logo居中显示
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			// 绘制图
			g.drawImage(logo, x, y, width, height, null);

			g.dispose();
			// 写入logo图片到二维码
			ImageIO.write(image, format, new File(imagePath));
		} catch (Exception e) {
			throw new Exception("二维码添加logo时发生异常！", e);
		}
	}

	/**
	 * 
	 * @param image
	 * @param format
	 * @param imagePath  生成的二维码临时文件路径
	 * @param bgFile     生成的背景图临时文件
	 * @param config
	 * @param headimgFile  头像临时文件
	 * @throws Exception
	 */
	private static void increasingImage(BufferedImage image, String format, String imagePath, File bgFile,
			MatrixToBgImageConfig config, File headimgFile) throws Exception {
		try {
			BufferedImage bg = ImageIO.read(bgFile);

			Graphics2D g = bg.createGraphics();

			// 二维码的高度和宽度如何定义
			int width = config.getQrcode_height();
			int height = config.getQrcode_height();

			// logo起始位置，此目的是为logo居中显示
			int x = config.getQrcode_x();
			int y = config.getQrcode_y();
			// 绘制图
			g.drawImage(image, x, y, width, height, null);

			BufferedImage headimg = ImageIO.read(headimgFile);

			int headimg_width = config.getHeadimg_height();
			int headimg_height = config.getHeadimg_height();

			int headimg_x = config.getHeadimg_x();
			int headimg_y = config.getHeadimg_y();

			// 绘制头像
			g.drawImage(headimg, headimg_x, headimg_y, headimg_width, headimg_height, null);

			// 绘制文字
			g.setColor(Color.WHITE);// 文字颜色
			Font font = new Font("黑体", Font.BOLD, 29);
			g.setFont(font);
			//System.out.println("#昵称："+config.getRealname());
			FontMetrics fm = g.getFontMetrics(font);
			int textWidth = fm.stringWidth(config.getRealname());
			int widthX = (bg.getWidth() - textWidth) / 2;

			//微信昵称
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);// 设置抗锯齿属性
			g.drawString(config.getRealname(), widthX, config.getRealname_y());

			g.dispose();
			// 写入二维码到bg图片
			ImageIO.write(bg, format, new File(imagePath));

			
		} catch (Exception e) {
			throw new Exception("二维码添加bg时发生异常！", e);
		}
	}

	/**
	 * 解析二维码
	 * 
	 * @param file
	 *            二维码文件内容
	 * @return 二维码的内容
	 */
	public static String decodeQrcode(File file) throws IOException, NotFoundException {
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map<DecodeHintType, Object> hints = Maps.newEnumMap(DecodeHintType.class);
		hints.put(DecodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
		return new MultiFormatReader().decode(binaryBitmap, hints).getText();
	}

	public static InputStream handleResponse(final HttpResponse response) throws IOException {
		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			EntityUtils.consume(entity);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return entity == null ? null : entity.getContent();
	}

	public static String handleUTF8Response(final HttpResponse response) throws IOException {
		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			EntityUtils.consume(entity);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);
	}

	public static File createTmpFile(InputStream inputStream, String name, String ext) throws IOException {
		File tmpFile = File.createTempFile(name, '.' + ext);

		tmpFile.deleteOnExit();

		try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
			int read = 0;
			byte[] bytes = new byte[1024 * 100];
			while ((read = inputStream.read(bytes)) != -1) {
				fos.write(bytes, 0, read);
			}

			fos.flush();
			return tmpFile;
		}
	}

}
