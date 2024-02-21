package edu.baylor.propertypro.service.serviceexceptions;

public class AlreadyRespondedException extends ServiceException {
	private static final long serialVersionUID = 9118667330201917347L;

	public AlreadyRespondedException(String message) {
		super(message);
	}
}
