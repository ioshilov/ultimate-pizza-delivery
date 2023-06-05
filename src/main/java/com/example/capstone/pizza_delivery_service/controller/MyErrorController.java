package com.example.capstone.pizza_delivery_service.controller;


import com.example.capstone.pizza_delivery_service.model.OrderCart;
import com.example.capstone.pizza_delivery_service.model.OrderDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
    public class MyErrorController implements ErrorController{
        private OrderCart orderCart;
        private final OrderDetails orderDetails;

        public MyErrorController(OrderCart orderCart, OrderDetails orderDetails) {
            this.orderCart = orderCart;
            this.orderDetails = orderDetails;
        }

        @RequestMapping("/error")
        public String handleError(final HttpServletRequest request, final Model model) {
            model.addAttribute("orderCart", orderCart);
            model.addAttribute("dishes", orderCart.getDishesList());
            model.addAttribute("orderDetails", orderDetails);
            model.addAttribute("status",request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString()+" error");
            if (request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).equals(404)){
            model.addAttribute("error","There is no such page!");}
            else {model.addAttribute("error",request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString());}
            return "error";
        }
}