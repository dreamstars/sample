package com.bytesvc.service.impl;

import org.bytesoft.compensable.Compensable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytesvc.ServiceException;
import com.bytesvc.service.IAccountService;
import com.bytesvc.service.ITransferService;

@Service("multiDsTransferService")
@Compensable(interfaceClass = ITransferService.class, confirmableKey = "transferServiceConfirm", cancellableKey = "transferServiceCancel")
public class MultiDsTransferServiceImpl implements ITransferService {

	@SuppressWarnings("restriction")
	@javax.annotation.Resource(name = "accountService")
	private IAccountService nativeAccountService;
	@SuppressWarnings("restriction")
	@javax.annotation.Resource(name = "jdbcTemplate2")
	private JdbcTemplate jdbcTemplate;

	@Transactional(rollbackFor = ServiceException.class)
	public void transfer(String sourceAcctId, String targetAcctId, double amount) throws ServiceException {

		this.nativeAccountService.decreaseAmount(sourceAcctId, amount);
		this.increaseAmount(targetAcctId, amount);

		// throw new ServiceException("rollback");
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

	}

	private void increaseAmount(String acctId, double amount) throws ServiceException {
		int value = this.jdbcTemplate.update("update tb_account_two set amount = amount + ? where acct_id = ?", amount, acctId);
		if (value != 1) {
			throw new ServiceException("ERROR!");
		}
		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

}
