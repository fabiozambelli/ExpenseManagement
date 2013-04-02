/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import biz.fz5.expensemanagement.model.business.ReceiptInterface;
import biz.fz5.expensemanagement.model.business.ReceiptManager;
import biz.fz5.expensemanagement.model.entity.Receipt;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author fabiozambelli
 *
 */

@Path("/receipt")
public class ReceiptService {

	
	@POST
	@Path("/upload/{id-user}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response putImage(
		@PathParam("id-user") String idUser,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		String output = "File uploaded to : " ;
				
		try {
		
			String uploadedFileLocationPrefix = Constants.getValue("uploadedFileLocationPrefix");
			String uploadedFileLocation = uploadedFileLocationPrefix + idUser + "_" + fileDetail.getFileName();
	 
			// save it
			writeToFile(uploadedInputStream, uploadedFileLocation);
	 
			output = output + uploadedFileLocation;
	 
			ReceiptInterface receiptManager = new ReceiptManager();
			receiptManager.create(idUser, uploadedFileLocation);
			
		} catch (Exception e) {
			
			e.printStackTrace();
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
				receipt.setName(receiptPojo.getImagePath());
				receipt.setTotal(receiptPojo.getTotal());
				receipt.setStatus(receiptPojo.getStatus().getDescription());
				receipts.add(receipt);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return receipts;
	}
	
	
	@GET
	@Path("/item/{id-user}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Receipt findItem(@PathParam("id-user") String idUser) {
	
		Receipt  receipt = null;
		
		try {
		
			ReceiptInterface receiptManager = new ReceiptManager();
			List<biz.fz5.expensemanagement.model.hibernate.pojo.Receipt> receiptList = receiptManager.getReceipts(idUser);
			
			if ( (receiptList!=null) && (receiptList.size()>0) ) {
				biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receiptPojo = (biz.fz5.expensemanagement.model.hibernate.pojo.Receipt)receiptList.get(0);
				receipt = new Receipt();
				receipt.setDate(receiptPojo.getDate());
				receipt.setName(receiptPojo.getImagePath());
				receipt.setTotal(receiptPojo.getTotal());
			}
							
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return receipt;
	}
	
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {
 
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
 
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
 
			e.printStackTrace();
		}
 
	}
 
}