package com.bytesvc.service;

import com.bytesvc.ServiceException;

public interface ITransferService {

	public void transfer(String sourceAcctId, String targetAcctId, double amount) throws ServiceException;

	public void transfer2(String sourceAcctId, String targetAcctId, double amount) throws ServiceException;

}
