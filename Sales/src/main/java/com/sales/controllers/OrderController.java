package com.sales.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.sales.exceptions.EmptyID;
//import com.sales.exceptions.NonExistID;
//import com.sales.exceptions.InvalidQty;

import com.sales.models.Order;
import com.sales.services.OrderService;

@ControllerAdvice
@Controller
public class OrderController {

	@Autowired
	private OrderService ordS;
	
// List Orders page --------------------------------------------------------------------------------
	
	@RequestMapping(value = "/showOrders", method = RequestMethod.GET)
	public String showOrder(Model m) {

		// Get all orders from order service, same them to array
		ArrayList<Order> orders = ordS.getAll();

		// Add orders to the model
		m.addAttribute("orders", orders);
		
		return "showOrders";
	}


// Add Order page -------------------------------------------------------------------------------------
	
	// Get the page
	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String getOrder(@ModelAttribute("order1") Order o, HttpServletRequest h) {
		return "addOrder";
	}

	// Save the info
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String postOrder(@Valid @ModelAttribute("order1") Order o,	
	BindingResult result, HttpServletRequest h, Model m) /*throws EmptyID, NonExistID, InvalidQty*/ {
		
		if (result.hasErrors()) {
			
			// Refresh the Add Order page - won't add the incorrect order
			return "addOrder";

		} else {
		
			// Pass the order to the Order Service for saving
			ordS.save(o);
			
			// New order arraylist - get all orders from order service, including new one
			ArrayList<Order> orders = ordS.getAll();
			
			// Add the order to the model
			m.addAttribute("orders", orders);

			return "showOrders";
				
		}
	}
}
	