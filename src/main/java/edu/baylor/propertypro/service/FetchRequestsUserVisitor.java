package edu.baylor.propertypro.service;

import java.util.List;

import edu.baylor.propertypro.data.RequestBaseRepository;
import edu.baylor.propertypro.domain.BaseRequest;
import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Realtor;

public class FetchRequestsUserVisitor<T extends BaseRequest> implements UserVisitor {
	
	private RequestBaseRepository<T> repo;
	
	private List<T> fetchedRequests;
	
	public List<T> getFetchedRequests() {
		return fetchedRequests;
	}

	public FetchRequestsUserVisitor(RequestBaseRepository<T> repo) {
		this.repo = repo;
	}

	@Override
	public void visit(Buyer buyer) {
		fetchedRequests = repo.findByBuyerId(buyer.getId());
	}

	@Override
	public void visit(Realtor realtor) {
		fetchedRequests = repo.findByListingRealtorId(realtor.getId());
	}

}
