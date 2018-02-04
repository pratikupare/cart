package com.app.cart.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.app.cart.entity.Brand;
import com.app.cart.repository.BrandRepository;

/**
 * @author pradnya.katkar
 * @since 04-02-2018
 */

@Service
public class BrandSeviceImpl implements BrandService{

	private static final Logger logger=Logger.getLogger(BrandSeviceImpl.class);

	@Autowired
	BrandRepository repo;

	/**
	 *@exception IllegalArgumentException,EntityExistsException,IllegalArgumentException 
	 */
	@Override
	public ResponseEntity<?> addBrand(Brand brand) {
		try {
			return new ResponseEntity<Integer>(repo.save(brand).getBrandId(),new HttpHeaders(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While Persisting Object"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @exception 
	 */	
	@Override
	public ResponseEntity<?> editBrand(Integer id, Brand brand) {
			Brand dbEntity=repo.findOne(id);
			if(null == dbEntity)
				return ResponseEntity.notFound().build();
			
			brand.setBrandId(dbEntity.getBrandId());
		try {
			return new ResponseEntity<Brand>(repo.save(brand),new HttpHeaders(),HttpStatus.OK);
		} catch (Exception e) {
			logger.error("No Record Found. Error While Updating Data"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @exception - IllegalArgumentException
	 */
	@Override
	public ResponseEntity<?> deleteBrand(Integer id) {	
			Brand dbEntity=repo.findOne(id);
			if(null == dbEntity)
				return ResponseEntity.notFound().build();
		try {
			repo.delete(id);
			return new ResponseEntity<Brand>(dbEntity,new HttpHeaders(),HttpStatus.OK);

		}catch(Exception e) {
			logger.error("No Record Found. Error While Deleting Data"+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @exception -
	 */
	@Override
	public ResponseEntity<?> getBrand(Integer id) {
		try {
			Brand dbEntity = repo.findOne(id);
			if(null == dbEntity)
				return ResponseEntity.notFound().build();
			return new ResponseEntity<Brand>(dbEntity,new HttpHeaders(),HttpStatus.OK);
		}catch(Exception e) {
			logger.error("No Record Found."+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @exception -
	 */
	@Override
	public ResponseEntity<?> getAllBrands() {
		try {
			List<Brand> brandsList=repo.findAll();
			if(null == brandsList) 
				return ResponseEntity.notFound().build();
			return new ResponseEntity<List<Brand>>(brandsList,new HttpHeaders(),HttpStatus.OK);
		}catch(Exception e) {
			logger.error("No Record Found."+e.getMessage());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
