package edu.baylor.propertypro.service.serviceexceptions;

public class UnauthorizedActionException extends ServiceException {
	private static final long serialVersionUID = -72074934199321842L;
	
	public UnauthorizedActionException(String message) {
		super(message);
	}

}
