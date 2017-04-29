package com.sales.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sales.exceptions.EmptyID;
import com.sales.exceptions.NonExistID;
import com.sales.exceptions.InvalidQty;
import com.sales.models.Order;
import com.sales.services.OrderService;

@ControllerAdvice
@Controller
public class OrderController {

	@Autowired
	private OrderService os;
	
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/showOrders", method = RequestMethod.GET)
	public String showOrder(Model m) {

		ArrayList<Order> orders = os.getAll();

		m.addAttribute("orders", orders);

		return "displayOrders";
	}

	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String getOrder(@ModelAttribute("order1") Order o, HttpServletRequest h) {
		return "addOrder";
	}

	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String postOrder(@Valid @ModelAttribute("order1") Order o,	
	BindingResult result, HttpServletRequest h, Model m) {

		if (result.hasErrors()) {

			return "addOrder";

		} else {

			try {
				
				os.save(o);

				ArrayList<Order> orders = os.getAll();
				
				m.addAttribute("orders", orders);

				return "displayOrders";
			} catch (EmptyID | NonExistID | InvalidQty e) {
				e.printStackTrace();
				e.getMessage();
				
				
				return "redirect:addOrder.html";
			}
		}
	}
}
