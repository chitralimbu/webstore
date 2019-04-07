package com.packt.webstore.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;

@Controller
@RequestMapping("market")
public class ProductController {

	@Autowired
	private ProductValidator productValidator;
	
	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/update/stock")
	public String updateStock(Model model) {
		productService.updateAllStock();
		return "redirect:/products";
	}

	@RequestMapping("/products/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);
		if(products ==null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping("/products/filter/{params}")
	public String getProductsByFilter(@MatrixVariable(pathVar="params") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}

	@RequestMapping(value = "/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	
	@RequestMapping(value = "/products/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result, HttpServletRequest request) {
//	@RequestMapping(value = "/products/add", method = RequestMethod.POST)
//	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result, HttpServletRequest request) {
		
		if(result.hasErrors()) {
			System.out.println("Has Errors!!!!!!!!!!!!!!!");
			return "addProduct";
		}
		
		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		System.out.println("Root Directory: " + rootDirectory);
		if(productImage!=null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(
						new File(rootDirectory+"resources\\images\\"+newProduct.getProductId() + ".jpg"));
				System.out.println(rootDirectory+"resources\\images\\"+newProduct.getProductId() + ".jpg");
			}catch(Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}
		
		productService.addProduct(newProduct);
		System.out.println("Has No Errors: !!!!!!!!!!!!!");
		return "redirect:/market/products";
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
	
	@RequestMapping("/products/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
		binder.setAllowedFields("productId",
		"name",
		"unitPrice",
		"description",
		"manufacturer",
		"category",
		"unitsInStock",
		"condition",
		"productImage",
		"language");
	}
}
