package com.sales.controllers;

// Imports
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.models.Product;
import com.sales.services.ProductService;

// Controller
@Controller
public class ProductController {
	@Autowired
	private ProductService prodS;

	
// List Products page ---------------------------------------------------------------------------------------
	
	// Uses get request, no save functionality needed
	@RequestMapping(value = "/showProducts", method = RequestMethod.GET)
	public String showProduct(Model m) {

		// Gets all products in product arraylist
		ArrayList<Product> products = prodS.getAll();

		// Add to product model object
		m.addAttribute("products", products);

		return "displayProducts";
	}
	

// Add Products page ---------------------------------------------------------------------------------------------

	// Shows addProduct page using get request
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String getProduct(@ModelAttribute("product1") Product prod, HttpServletRequest http) {
		return "addProduct";
	}

	// Saves addProduct page info into database using post
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String postProduct(@Valid @ModelAttribute("product1") Product prod, BindingResult result, HttpServletRequest http,
			Model m) {

		if (result.hasErrors()) {
			
			return "addProduct";
		
		} else {
			// Save Product to database
			prodS.save(prod);

			// Get products from database, save into arraylist
			ArrayList<Product> products = prodS.getAll();
			
			// Add to product model object
			m.addAttribute("products", products);
			
			return "displayProducts";
		}
	}


}
