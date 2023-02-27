package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteByCustomerId(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
	    TripBooking tripBooking = new TripBooking();
		//make a null driver variable
		Driver driver = null;
		//write the logic
		//1 first take out all list of driver
		//check if the drivr is avialbel and its greater than the current driver id them assign the driver to
		//1 get he list of driver
		List<Driver> driverList = driverRepository2.findAll();
		//2 check the driver avilabilty for for each loop
		for(Driver tempDriver:driverList){
			if(tempDriver.getCab().isAvaiable()==Boolean.TRUE){
				if(tempDriver==null || driver.getDriverId()>tempDriver.getDriverId()){
					driver = tempDriver;
				}
			}
		}
		//if the driver is not availbe throw exception
		if(driver==null){
			throw new Exception("No cab available!");
		}
		//set attribute
		Customer customer = customerRepository2.findById(customerId).get();
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver);
		driver.getCab().setAvaiable(Boolean.FALSE);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistnaceInKm(distanceInKm);

		int rateperkm = driver.getCab().getRatePerKm();
		tripBooking.setBill(rateperkm*tripBooking.getDistnaceInKm());
		tripBooking.setTripStatus(TripStatus.CONFIRMED);
		//set bidrectional mapping

		customer.getBookingList().add(tripBooking);
		customerRepository2.save(customer);

		driver.getTripBookingList().add(tripBooking);
		driverRepository2.save(driver);

		tripBookingRepository2.save(tripBooking);


		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setTripStatus(TripStatus.CANCELED);
		tripBooking.getDriver().getCab().setAvaiable(Boolean.TRUE);
		tripBooking.setBill(0);
		tripBookingRepository2.save(tripBooking);


	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setTripStatus(TripStatus.COMPLETED);
		int bill = tripBooking.getDriver().getCab().getRatePerKm()*tripBooking.getDistnaceInKm();
		tripBooking.setBill(bill);
		tripBooking.getDriver().getCab().setAvaiable(Boolean.TRUE);
		tripBookingRepository2.save(tripBooking);

	}


}
