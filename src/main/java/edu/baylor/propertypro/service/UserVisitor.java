package edu.baylor.propertypro.service;

import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Realtor;

public interface UserVisitor {
	public void visit(Buyer buyer);
	public void visit(Realtor realtor);
}
