package com.bytesvc.service.confirm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytesvc.ServiceException;
import com.bytesvc.service.ITransferService;

@Service("transferServiceConfirm")
public class TransferServiceConfirm implements ITransferService {

	@SuppressWarnings("restriction")
	@javax.annotation.Resource(name = "jdbcTemplate2")
	private JdbcTemplate jdbcTemplate;

	@Transactional(rollbackFor = ServiceException.class)
	public void transfer(String sourceAcctId, String targetAcctId, double amount) throws ServiceException {
		System.out.printf("done increase: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

	/**
	 * 可补偿型服务的Try/Confirm/Cancel实现类的方法必须定义Transactional注解，且propagation必须是Required, RequiresNew, Mandatory
	 * 中的一种（即业务代码必须参与事务，从0.3.0开始强制要求）；
	 *
	 * @param sourceAcctId
	 * @param targetAcctId
	 * @param amount
	 * @throws ServiceException
     */
	@Transactional(rollbackFor = ServiceException.class)
	public void transfer2(String sourceAcctId, String targetAcctId, double amount) throws ServiceException {
		System.out.printf("transfer2 confirm!");
	}

}
