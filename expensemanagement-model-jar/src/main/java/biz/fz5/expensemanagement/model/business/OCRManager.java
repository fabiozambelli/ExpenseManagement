/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
 */
package biz.fz5.expensemanagement.model.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import biz.fz5.expensemanagement.model.command.CommandController;
import biz.fz5.expensemanagement.model.command.ConvertContrast0Level60Command;
import biz.fz5.expensemanagement.model.command.ConvertContrast2Level60Command;
import biz.fz5.expensemanagement.model.command.ConvertContrast2Level70Command;
import biz.fz5.expensemanagement.model.command.ConvertContrast4Level60Command;
import biz.fz5.expensemanagement.model.command.ConvertContrast4Level70Command;
import biz.fz5.expensemanagement.model.command.ConvertContrast6Level70Command;
import biz.fz5.expensemanagement.model.command.ConvertTifCommand;
import biz.fz5.expensemanagement.model.command.CopyFileToTmpCommand;
import biz.fz5.expensemanagement.model.command.ExtractInformationCommand;
import biz.fz5.expensemanagement.model.command.MoveFileToOutCommand;
import biz.fz5.expensemanagement.model.command.RemoveFileFromPubCommand;
import biz.fz5.expensemanagement.model.command.TesseractPsm1Command;
import biz.fz5.expensemanagement.model.command.TesseractPsm6Command;
import biz.fz5.expensemanagement.model.entity.ImageFile;
import biz.fz5.expensemanagement.model.entity.Receipt;
import biz.fz5.expensemanagement.model.hibernate.pojo.UserDevice;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * @author fabiozambelli
 * 
 */
public class OCRManager {

	protected static Logger log = Logger.getLogger(OCRManager.class.getName());

	private CommandController commandController = new CommandController();

	private ImageFile imageFile;

	private Receipt receipt;

	private biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receiptPojo;

	private Sender sender;

	public OCRManager() {
	}

	public OCRManager(
			biz.fz5.expensemanagement.model.hibernate.pojo.Receipt receiptPojo) {
		this.receiptPojo = receiptPojo;
		this.sender = newSender();
	}

	protected Sender newSender() {

		String PATH = "/api.key";

		String key = null;

		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(PATH);
		if (stream == null) {
			throw new IllegalStateException("Could not find file " + PATH
					+ " on web resources)");
		}

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		try {
			
			key = reader.readLine();
			
			log.debug("create sender with key:"+key);
			
		} catch (IOException e) {
			
			throw new RuntimeException("Could not read file " + PATH, e);
			
		} finally {
			
			try {
				reader.close();
			} catch (IOException e) {
				log.warn("Exception closing " + PATH, e);
			}
		}

		return new Sender(key);
	}

	private File getFile() {

		File f = null;

		try {

			f = new File(receiptPojo.getImagePath());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return f;
	}

	private void performTesseractPsm1Command() throws Exception {
		log.debug("performTesseractPsm1Command");
		TesseractPsm1Command cTesseractPsm1 = new TesseractPsm1Command(
				imageFile);
		commandController.addCommand(TesseractPsm1Command.ID, cTesseractPsm1);
		commandController.executeCommand(TesseractPsm1Command.ID);
	}

	private void performTesseractPsm6Command() throws Exception {
		log.debug("performTesseractPsm6Command");
		TesseractPsm6Command cTesseractPsm6 = new TesseractPsm6Command(
				imageFile);
		commandController.addCommand(TesseractPsm6Command.ID, cTesseractPsm6);
		commandController.executeCommand(TesseractPsm6Command.ID);
	}

	private void performExtractInformationCommand() throws Exception {
		log.debug("performExtractInformationCommand");
		ExtractInformationCommand cExtractInformation = new ExtractInformationCommand(
				imageFile, receipt);
		commandController.addCommand(ExtractInformationCommand.ID,
				cExtractInformation);
		commandController.executeCommand(ExtractInformationCommand.ID);
	}

	private void performConvertContrast2Level60Command() throws Exception {
		log.debug("performConvertContrast2Level60Command");
		ConvertContrast2Level60Command cContrast2Level60 = new ConvertContrast2Level60Command(
				imageFile);
		commandController.addCommand(ConvertContrast2Level60Command.ID,
				cContrast2Level60);
		commandController.executeCommand(ConvertContrast2Level60Command.ID);
	}

	private void performConvertContrast2Level70Command() throws Exception {
		log.debug("performConvertContrast2Level70Command");
		ConvertContrast2Level70Command cContrast2Level70 = new ConvertContrast2Level70Command(
				imageFile);
		commandController.addCommand(ConvertContrast2Level70Command.ID,
				cContrast2Level70);
		commandController.executeCommand(ConvertContrast2Level70Command.ID);
	}

	private void performConvertContrast0Level60Command() throws Exception {
		log.debug("performConvertContrast0Level60Command");
		ConvertContrast0Level60Command cContrast0Level60 = new ConvertContrast0Level60Command(
				imageFile);
		commandController.addCommand(ConvertContrast0Level60Command.ID,
				cContrast0Level60);
		commandController.executeCommand(ConvertContrast0Level60Command.ID);
	}

	private void performConvertContrast4Level60Command() throws Exception {
		log.debug("performConvertContrast4Level60Command");
		ConvertContrast4Level60Command cContrast4Level60 = new ConvertContrast4Level60Command(
				imageFile);
		commandController.addCommand(ConvertContrast4Level60Command.ID,
				cContrast4Level60);
		commandController.executeCommand(ConvertContrast4Level60Command.ID);
	}

	private void performConvertContrast4Level70Command() throws Exception {
		log.debug("performConvertContrast4Level70Command");
		ConvertContrast4Level70Command cContrast4Level70 = new ConvertContrast4Level70Command(
				imageFile);
		commandController.addCommand(ConvertContrast4Level70Command.ID,
				cContrast4Level70);
		commandController.executeCommand(ConvertContrast4Level70Command.ID);
	}

	private void performConvertContrast6Level70Command() throws Exception {
		log.debug("performConvertContrast6Level70Command");
		ConvertContrast6Level70Command cContrast6Level70 = new ConvertContrast6Level70Command(
				imageFile);
		commandController.addCommand(ConvertContrast6Level70Command.ID,
				cContrast6Level70);
		commandController.executeCommand(ConvertContrast6Level70Command.ID);
	}

	private boolean isCompleted() {

		boolean retVal = false;

		if ((receipt.getDate() != null)
				&& (!"".equals(receipt.getDate()))
				&& (receipt.getDate().length() == 8 || receipt.getDate()
						.length() == 10) && (receipt.getTotal() != null)
				&& (!"".equals(receipt.getTotal())))
			retVal = true;

		return retVal;
	}

	private void performCompleteJob() throws Exception {
		log.debug("performCompleteJob");

		// (6) sposta output in processed
		MoveFileToOutCommand cMoveToOut = new MoveFileToOutCommand(imageFile);
		commandController.addCommand(MoveFileToOutCommand.ID, cMoveToOut);
		commandController.executeCommand(MoveFileToOutCommand.ID);

		// (7) rimuovi file pubblicato in orgine
		RemoveFileFromPubCommand cRemoveFromPub = new RemoveFileFromPubCommand(
				imageFile);
		commandController.addCommand(RemoveFileFromPubCommand.ID,
				cRemoveFromPub);
		commandController.executeCommand(RemoveFileFromPubCommand.ID);

	}

	private void notifyResult(biz.fz5.expensemanagement.model.entity.Receipt receipt) throws Exception {

		UserDeviceInterface userDeviceManager = new UserDeviceManager();
		List<UserDevice> userDevices = userDeviceManager.getUserDevices();
		
		if ((userDevices != null) && (userDevices.size() == 1) ) {
			// send a single message using plain post
			String registrationId = userDevices.get(0).getIdRegistration();
			log.debug("registrationId:"+registrationId);
			Message message = new Message.Builder()
            .addData("message", "{\"total\":\"" + receipt.getTotal() + "\",\"data\":\"" + receipt.getDate()+"\"}")
            .build();
			Result result = sender.send(message, registrationId, 5);
			log.debug("result of message is "+result);
		} 
	}

	public void performOCR() {

		try {

			File f = getFile();
			if (f == null)
				throw new Exception("Nothing to do");

			String fileName = f.getName().substring(0,
					getFile().getName().lastIndexOf("."));
			log.debug("get :" + fileName);
			imageFile = new ImageFile(fileName);

			// (1) il file viene copiato nella directory 'tmp'
			CopyFileToTmpCommand cCopyToTmp = new CopyFileToTmpCommand(
					imageFile);
			commandController.addCommand(CopyFileToTmpCommand.ID, cCopyToTmp);
			commandController.executeCommand(CopyFileToTmpCommand.ID);
			log.debug("(1) CopyFileToTmpCommand done");

			// (2) converti in tif
			ConvertTifCommand cTif = new ConvertTifCommand(imageFile);
			commandController.addCommand(ConvertTifCommand.ID, cTif);
			commandController.executeCommand(ConvertTifCommand.ID);
			log.debug("(2) ConvertTifCommand done");

			receipt = new Receipt();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast2Level60Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast2Level70Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast0Level60Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast4Level60Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast4Level70Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performConvertContrast6Level70Command();

			performTesseractPsm6Command();

			performExtractInformationCommand();

			// -

			performCompleteJob();

			// -

			receipt.optimize();
			// -

			receipt.print();

			// -
			notifyResult(receipt);
			
			receiptPojo.setDate(receipt.getDate());
			receiptPojo.setTotal(receipt.getTotal());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		OCRManager OCRManager_ = new OCRManager();
		OCRManager_.performOCR();
	}
}
