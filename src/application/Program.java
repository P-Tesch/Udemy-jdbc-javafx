package application;

import dao.DaoFactory;
import dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(sellerDao.findById(1));
	}

}
