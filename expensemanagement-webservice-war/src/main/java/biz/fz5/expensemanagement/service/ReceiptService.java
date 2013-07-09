/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import biz.fz5.expensemanagement.model.Utils;
import biz.fz5.expensemanagement.model.business.ReceiptInterface;
import biz.fz5.expensemanagement.model.business.ReceiptManager;
import biz.fz5.expensemanagement.model.business.SchedulerComponent;
import biz.fz5.expensemanagement.model.entity.Receipt;
import biz.fz5.expensemanagement.model.hibernate.HibernateSessionFactory;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author fabiozambelli
 *
 */

@Path("/receipt")
public class ReceiptService {
	
	@Context ServletContext context;

	protected static Logger log = Logger.getLogger(ReceiptService.class.getName());
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response putImage(
		@FormDataParam("id-user") String idUser,
		@FormDataParam("tag") String tag,
		@FormDataParam("file") InputStream uploadedInputStream,		
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 		
		String output = null;

		log.debug("id-user:"+idUser);
		log.debug("tag:"+tag);
		
		try {
			
			ByteArrayOutputStream baos = writeToOutputStream(uploadedInputStream);
			
			ReceiptInterface receiptManager = new ReceiptManager();
			
			biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receipt = receiptManager.create(idUser, fileDetail.getFileName(), tag, Hibernate.createBlob(baos.toByteArray()));
			
			writeToFile(baos.toByteArray(), Constants.getValue("tempFileDir") + receipt.getIdUser() + "_" + receipt.getId() + "_" + receipt.getName());
			
			output = "<h3>File successfully uploaded </h3><p><a href=\"/expensemanagement\">Back to home</a></p>";
			
			ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			
			SchedulerComponent schedulerComponent = (SchedulerComponent)applicationContext.getBean("scheduler");
				
			schedulerComponent.startJob(receipt.getId());
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			output = "<h3>Error occurred during file upload</h3><p><a href=\"/expensemanagement\">Back to home</a></p>";
			
		} 
		
		
		return Response.status(200).entity(output).build();
 
	}
 
	@GET
	@Path("/items/{id-user}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Receipt> findItems(@PathParam("id-user") String idUser) {
	
		List<Receipt> receipts = new ArrayList<Receipt>();
		
		try {
		
			ReceiptInterface receiptManager = new ReceiptManager();
			List<biz.fz5.expensemanagement.model.hibernate.pojo.Receipt> receiptList = receiptManager.getReceipts(idUser);
			
			for (biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receiptPojo : receiptList) {
				Receipt receipt = new Receipt();
				receipt.setDate(receiptPojo.getDate());
				receipt.setName(receiptPojo.getName());
				receipt.setTag(receiptPojo.getTag());
				receipt.setTotal(receiptPojo.getTotal());
				receipt.setStatus(receiptPojo.getStatus().getDescription());
				receipt.setUploadtime(Utils.getDataFormat(receiptPojo.getUploadDate().getTime(), "dd/MM/yyyy HH:mm"));
				receipts.add(receipt);
			}
						
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
		
		
		return receipts;
	}
	
	
	@GET
	@Path("/download/{id-receipt}")
	@Produces({"image/png", "image/jpeg","image/jpg","image/gif"})
	public Response findItem(@PathParam("id-receipt") String idReceipt) {
	
		ByteArrayOutputStream baos = null;
		
		try {
		
			ReceiptInterface receiptManager = new ReceiptManager();
			log.debug("long:"+new Long(idReceipt));
			biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receipt = receiptManager.getReceipt(new Long(idReceipt));
			log.debug("I:"+receipt.getImageReceipt().length());
			baos = writeToOutputStream(receipt.getImageReceipt().getBinaryStream());
			log.debug("O:"+baos.size());
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
				
		return Response.ok(baos.toByteArray()).build();
	}

	// save uploaded file to new location
	private void writeToFile(byte[] bytes,
		String uploadedFileLocation) {
 
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (IOException e) {
 
			e.printStackTrace();
		}
 
	}
	
	private ByteArrayOutputStream writeToOutputStream(InputStream uploadedInputStream) {
		
		ByteArrayOutputStream baos = null;
		
		try {
			
			if (uploadedInputStream.markSupported()) {
				log.debug("marks the stream");
				uploadedInputStream.mark(0);
			}

			baos = new ByteArrayOutputStream();			
			int read = 0;
			byte[] bytes = new byte[1024]; 
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				baos.write(bytes, 0, read);
			}
			baos.flush();
			baos.close();
			
		}	catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return baos;
	}
}